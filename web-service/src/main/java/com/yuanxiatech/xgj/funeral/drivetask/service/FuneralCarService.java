package com.yuanxiatech.xgj.funeral.drivetask.service;

import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.core.pojo.DataSet;
import com.yuanxiatech.xgj.core.service.StringPojoAppBaseService;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralCar;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralCarQueryParam;

import java.util.List;

/**
 * @description: TODO
 **/
public interface FuneralCarService extends StringPojoAppBaseService<FuneralCar> {

    /**
     * 查询
     * @param carQueryParam
     * @return
     */
    DataSet<FuneralCar> queryCarData(FuneralCarQueryParam carQueryParam);

    /**
     * 逻辑删除
     * @param idList
     * @param logonUser
     */
    void deleteDatas(List<String> idList, User logonUser);

    /**
     * 删除车辆随车人员
     * @param carId
     */
    void deleteCarStaff(String carId);

    /**
     * 获取车辆 及 排班顺序
     * @param carQueryParam
     * @return
     */
    List<FuneralCar> getCarScheduling(FuneralCarQueryParam carQueryParam);

    /**
     * 释放车辆任务状态
     * @param driveTaskId
     */
    void releaseCarTask(String driveTaskId);

    /**
     * 修改已派车车辆
     * @param car
     */
    void updateDriveTask(FuneralCar car);
}
