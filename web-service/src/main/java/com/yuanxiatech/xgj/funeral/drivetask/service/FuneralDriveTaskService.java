package com.yuanxiatech.xgj.funeral.drivetask.service;

import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.core.pojo.DataSet;
import com.yuanxiatech.xgj.core.service.StringPojoAppBaseService;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralDriveTask;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralDriveTaskQueryParam;

import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 **/
public interface FuneralDriveTaskService extends StringPojoAppBaseService<FuneralDriveTask> {

    /**
     * 新增遗体接运
     * @param driveTask
     */
    void saveDriveTask(FuneralDriveTask driveTask);

    /**
     * 查询列表信息
     * @param driveTaskQueryParam
     * @return
     */
    DataSet<FuneralDriveTask> queryDriveTaskData(FuneralDriveTaskQueryParam driveTaskQueryParam);

    /**
     * 编辑遗体接运
     * @param driveTask
     */
    void updateDriveTask(FuneralDriveTask driveTask);

    /**
     * 删除遗体接运
     * @param idList
     * @param logonUser
     */
    void deleteTasks(List<String> idList, User logonUser);

    /**
     * 回车 完成遗体接运
     * @param idList
     * @param logonUser
     */
    void taskFinishs(List<String> idList, User logonUser);

    /**
     * 详情
     * @param id
     * @return
     */
    FuneralDriveTask getDriveTaskDetail(String id);

    /**
     * 任务查询统计
     * @param driveTaskQueryParam
     * @return
     */
    List<Map> taskStatisticsQuery(FuneralDriveTaskQueryParam driveTaskQueryParam);

    /**
     * 接运任务状态查询
     * @param driveTaskQueryParam
     * @return
     */
    Map driveTaskStatusQuery(FuneralDriveTaskQueryParam driveTaskQueryParam);
}
