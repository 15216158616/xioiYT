package com.yuanxiatech.xgj.funeral.base.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class InternetplusCacheManage {

    private final Long SUCCESS = Long.valueOf("1");

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RedisTemplate<String, Object> redisTemplate;

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取锁
     *
     * @param lockKey 锁key
     * @param value 值
     * @param expireTime：秒
     * @return
     */
    public boolean getLock(String lockKey, String value, int expireTime) {
        boolean ret = false;
        try {
            String script = "if redis.call('setnx', KEYS[1], ARGV[1]) == 1 then redis.call('pexpire', KEYS[1], ARGV[2]) return 1 else return 0 end";
            RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
            Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), value, expireTime * 1000);
            if (SUCCESS.equals(result)) {
                ret = true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ret;
    }

    /**
     * 释放锁
     *
     * @param lockKey 锁key
     * @param value 值
     * @return
     */
    public boolean releaseLock(String lockKey, String value) {
        boolean ret = false;
        try {
            String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
            RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);
            Object result = redisTemplate.execute(redisScript, Collections.singletonList(lockKey), value);
            if (SUCCESS.equals(result)) {
                ret = true;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ret;
    }

    /**
     * 获取过期时间
     *
     * @param key 缓存key
     * @return
     */
    public long getTtl(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 指定缓存失效时间
     *
     * @param key 缓存key
     * @param ttl 时间（秒）
     */
    public void expire(String key, int ttl) {
        redisTemplate.expire(key, ttl, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存
     *
     * @param key   缓存key
     * @param value 缓存值
     * @param ttl
     */
    public void set(String key, Object value, int ttl) {
        if (ttl >= 0) {
            redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(key, value);
        }
    }

    /**
     * 获取缓存
     *
     * @param key 缓存key
     * @return
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 清除缓存
     *
     * @param key 缓存key
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 清楚缓存
     *
     * @param keys 缓存key
     */
    public void delete(List<String> keys) {
        redisTemplate.delete(keys);
    }
}
