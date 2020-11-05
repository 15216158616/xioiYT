package com.yuanxiatech.xgj.funeral.system.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDao;
import com.yuanxiatech.xgj.funeral.system.model.Resource;

import java.util.List;

public interface ResourceDao extends StringPojoAppBaseDao<Resource> {

    /**
     * 获取资源集合
     * @return
     */
    List<Resource> getResources();
}
