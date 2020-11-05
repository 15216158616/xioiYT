package com.yuanxiatech.xgj.funeral.drivetask.service;

import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.core.dao.AppBaseDao;
import com.yuanxiatech.xgj.core.service.StringPojoAppBaseServiceImpl;
import com.yuanxiatech.xgj.funeral.drivetask.dao.FuneralStaffDao;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralStaff;
import com.yuanxiatech.xgj.funeral.system.dao.ResourceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: TODO
 **/
@Service
public class FuneralStaffServiceImpl extends StringPojoAppBaseServiceImpl<FuneralStaff> implements FuneralStaffService {

    private FuneralStaffDao staffDao;

    @Autowired(required = false)
    public void setStaffDao(FuneralStaffDao staffDao){
        this.staffDao = staffDao;
    }

    @Override
    protected AppBaseDao<FuneralStaff, String> getDao() { return staffDao; }

    @Override
    public void deleteDatas(List<String> idList, User user) {
        List<FuneralStaff> staffList = new ArrayList<>();
        for (String id : idList) {
            FuneralStaff staff = new FuneralStaff();
            staff.setId(id);
            staff.setDeleteTime(new Date());
            staff.setDeleteUserId(user.getId());
            staff.setDataStatus(0);
            staffList.add(staff);
        }
        super.logicDelete(staffList);
    }
}
