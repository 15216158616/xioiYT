package com.yuanxiatech.xgj.funeral.system.service;

import com.yuanxiatech.xgj.core.dao.AppBaseDao;
import com.yuanxiatech.xgj.core.service.StringPojoAppBaseServiceImpl;
import com.yuanxiatech.xgj.funeral.system.dao.ResourceDao;
import com.yuanxiatech.xgj.funeral.system.model.Resource;
import com.yuanxiatech.xgj.funeral.system.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceServiceImpl extends StringPojoAppBaseServiceImpl<Resource> implements ResourceService {

    private ResourceDao resourceDao;

    @Autowired(required = false)
    public void setResourceDao(ResourceDao resourceDao){
        this.resourceDao = resourceDao;
    }

    @Override
    protected AppBaseDao<Resource, String> getDao() {
        return resourceDao;
    }

    /**
     * 获取资源集合
     * @return
     */
    @Override
    public List<Resource> getResources() {
        return resourceDao.getResources();
    }
}
