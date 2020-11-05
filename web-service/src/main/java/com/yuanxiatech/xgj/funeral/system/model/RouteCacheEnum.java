package com.yuanxiatech.xgj.funeral.system.model;

import com.yuanxiatech.xgj.core.pojo.IDictionary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum RouteCacheEnum implements IDictionary {

    YES_CACHE(1, "缓存"),
    NOT_CACHE(2, "不缓存");

    private int value;

    private String label;

    RouteCacheEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    private static Map<Integer, RouteCacheEnum> map = new HashMap<>();

    static {
        for (RouteCacheEnum type : values()) {
            map.put(type.value, type);
        }
    }

    public static RouteCacheEnum parse(int value) {
        return map.get(value);
    }

    public static List<RouteCacheEnum> getEnumValues() {
        return Arrays.asList(values());
    }

}
