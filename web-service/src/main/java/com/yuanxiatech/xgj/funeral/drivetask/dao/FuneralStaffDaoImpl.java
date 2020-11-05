package com.yuanxiatech.xgj.funeral.drivetask.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDaoImpl;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralStaff;
import org.springframework.stereotype.Repository;

/**
 * @description: TODO
 **/
@Repository
public class FuneralStaffDaoImpl extends StringPojoAppBaseDaoImpl<FuneralStaff> implements FuneralStaffDao{

    @Override
    protected Class<FuneralStaff> getPojoClass() {
        return FuneralStaff.class;
    }

}
