package com.yuanxiatech.xgj.funeral.drivetask.dao;

import com.yuanxiatech.xgj.core.dao.StringPojoAppBaseDaoImpl;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralDriveTask;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralDriveTaskQueryParam;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralStaff;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @description: TODO
 **/
@Repository
public class FuneralDriveTaskDaoImpl extends StringPojoAppBaseDaoImpl<FuneralDriveTask> implements FuneralDriveTaskDao{

    @Override
    protected Class<FuneralDriveTask> getPojoClass() {
        return FuneralDriveTask.class;
    }

    @Override
    public List<FuneralStaff> getByDriveTaskIds(List<String> ids) {
        return selectList(getPrefix()+"getByDriveTaskIds",ids);
    }

    @Override
    public void deleteDriveTaskStaff(String driveTaskId) {
        delete(getPrefix()+"deleteDriveTaskStaff",driveTaskId);
    }

    @Override
    public void taskFinishs(List<FuneralDriveTask> driveTaskList) {
        update(getPrefix()+"taskFinishs",driveTaskList);
    }

    @Override
    public List<Map> taskStatisticsQuery(FuneralDriveTaskQueryParam driveTaskQueryParam) {
        return selectList(getPrefix()+"taskStatisticsQuery",driveTaskQueryParam);
    }

    @Override
    public List<Map> driveTaskStatusQuery(FuneralDriveTaskQueryParam driveTaskQueryParam) {
        return selectList(getPrefix()+"driveTaskStatusQuery",driveTaskQueryParam);
    }

    @Override
    public Map someDayTaskCount(FuneralDriveTaskQueryParam driveTaskQueryParam) {
        return selectOne(getPrefix()+"someDayTaskCount",driveTaskQueryParam);
    }
}
