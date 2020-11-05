package com.yuanxiatech.xgj.funeral.drivetask.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDao;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralCar;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralCarQueryParam;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralStaff;

import java.util.List;

public interface FuneralCarDao extends StringPojoAppBaseDao<FuneralCar> {

    /**
     * 获取车辆随车员工
     * @param ids
     * @return
     */
    List<FuneralStaff> getByCarIds(List<String> ids);

    void deleteCarStaff(String carId);

    /**
     * 获取车辆 及 排班顺序
     * @param carQueryParam
     * @return
     */
    List<FuneralCar> getCarScheduling(FuneralCarQueryParam carQueryParam);

    void releaseCarTask(String driveTaskId);

    void updateDriveTask(FuneralCar car);
}
