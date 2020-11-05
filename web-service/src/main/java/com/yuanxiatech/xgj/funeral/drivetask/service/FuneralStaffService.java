package com.yuanxiatech.xgj.funeral.drivetask.service;

import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.core.service.StringPojoAppBaseService;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralStaff;

import java.util.List;

/**
 * @description: TODO
 **/
public interface FuneralStaffService extends StringPojoAppBaseService<FuneralStaff> {

    /**
     * 逻辑删除
     * @param ids
     * @param logonUser
     */
    void deleteDatas(List<String> ids, User logonUser);
}
