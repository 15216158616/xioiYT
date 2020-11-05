package com.yuanxiatech.xgj.funeral.system.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDaoImpl;
import com.yuanxiatech.xgj.funeral.system.model.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResourceDaoImpl extends StringPojoAppBaseDaoImpl<Resource> implements ResourceDao {

    @Override
    protected Class<Resource> getPojoClass() {
        return Resource.class;
    }

    /**
     * 获取资源集合
     * @return
     */
    @Override
    public List<Resource> getResources() {
        return selectList(getPrefix() + "getResources");
    }
}
