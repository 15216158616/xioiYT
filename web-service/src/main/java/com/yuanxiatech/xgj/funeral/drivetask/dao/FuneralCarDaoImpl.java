package com.yuanxiatech.xgj.funeral.drivetask.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDaoImpl;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralCar;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralCarQueryParam;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralStaff;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: TODO
 **/
@Repository
public class FuneralCarDaoImpl extends StringPojoAppBaseDaoImpl<FuneralCar> implements FuneralCarDao{

    @Override
    protected Class<FuneralCar> getPojoClass() {
        return FuneralCar.class;
    }

    @Override
    public List<FuneralStaff> getByCarIds(List<String> ids) {
        return selectList(getPrefix()+"getByCarIds",ids);
    }

    @Override
    public void deleteCarStaff(String carId) {
        delete(getPrefix()+"deleteCarStaff",carId);
    }

    @Override
    public List<FuneralCar> getCarScheduling(FuneralCarQueryParam carQueryParam) {
        return selectList(getPrefix()+"getCarScheduling",carQueryParam);
    }

    @Override
    public void releaseCarTask(String driveTaskId) {
        update(getPrefix()+"releaseCarTask",driveTaskId);
    }

    @Override
    public void updateDriveTask(FuneralCar car) {
        update(getPrefix()+"updateDriveTask",car);
    }
}
