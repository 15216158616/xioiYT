package com.yuanxiatech.xgj.funeral.drivetask.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDao;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralDriveTask;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralDriveTaskQueryParam;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralStaff;

import java.util.List;
import java.util.Map;

public interface FuneralDriveTaskDao extends StringPojoAppBaseDao<FuneralDriveTask> {

    List<FuneralStaff> getByDriveTaskIds(List<String> ids);

    /**
     * 删除接运员工
     * @param driveTaskId
     */
    void deleteDriveTaskStaff(String driveTaskId);

    void taskFinishs(List<FuneralDriveTask> driveTaskList);

    List<Map> taskStatisticsQuery(FuneralDriveTaskQueryParam driveTaskQueryParam);

    List<Map> driveTaskStatusQuery(FuneralDriveTaskQueryParam driveTaskQueryParam);

    /**
     * 查询 一天 3个时间段各任务数量
     * 8:00-11:30
     * 12:00-14:00
     * 14:00-23:59
     * @param driveTaskQueryParam
     * @return
     */
    Map someDayTaskCount(FuneralDriveTaskQueryParam driveTaskQueryParam);
}
