package com.yuanxiatech.xgj.funeral.system.service;

import com.yuanxiatech.xgj.core.service.StringPojoAppBaseService;
import com.yuanxiatech.xgj.funeral.system.model.Resource;

import java.util.List;

public interface ResourceService extends StringPojoAppBaseService<Resource> {

    //获取资源集合
    List<Resource> getResources();
}
