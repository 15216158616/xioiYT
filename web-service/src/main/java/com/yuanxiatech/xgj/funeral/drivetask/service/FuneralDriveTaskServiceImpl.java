package com.yuanxiatech.xgj.funeral.drivetask.service;

import com.yuanxiatech.xgj.common.rbac.model.User;
import com.yuanxiatech.xgj.core.dao.AppBaseDao;
import com.yuanxiatech.xgj.core.pojo.DataSet;
import com.yuanxiatech.xgj.core.service.StringPojoAppBaseServiceImpl;
import com.yuanxiatech.xgj.core.utils.UuidUtil;
import com.yuanxiatech.xgj.funeral.drivetask.dao.FuneralDriveTaskDao;
import com.yuanxiatech.xgj.funeral.drivetask.dao.FuneralStaffDao;
import com.yuanxiatech.xgj.funeral.drivetask.model.*;
import org.apache.commons.collections4.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.osgi.framework.util.Headers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: TODO
 * @date 2020/6/9 19:37
 **/
@Service
public class FuneralDriveTaskServiceImpl extends StringPojoAppBaseServiceImpl<FuneralDriveTask> implements FuneralDriveTaskService {

    private FuneralDriveTaskDao driveTaskDao;

    @Autowired(required = false)
    private FuneralCarService carService;

    @Autowired(required = false)
    public void setDriveTaskDao(FuneralDriveTaskDao driveTaskDao){
        this.driveTaskDao = driveTaskDao;
    }


    @Override
    protected AppBaseDao<FuneralDriveTask, String> getDao() { return driveTaskDao; }

    @Override
    public void saveDriveTask(FuneralDriveTask driveTask) {
        judgeTaskStatus(driveTask);//判断派车状态
        super.save(driveTask);
    }

    /**
     * 判断派车状态
     * @param driveTask
     */
    private void judgeTaskStatus(FuneralDriveTask driveTask) {
        if(driveTask.getTaskStatus()!=null&&driveTask.getTaskStatus()==FuneralTaskStatusEnum.HAS_RETURNED.getValue()){   //已完成的任务不做修改
            return;
        }
        if(driveTask.getCar()!=null&&!StringUtils.isBlank(driveTask.getCar().getId())){
            driveTask.setTaskStatus(FuneralTaskStatusEnum.HAVE_SENT_CAR.getValue());
            if (StringUtils.isBlank(driveTask.getId())) {
                driveTask.setId(UuidUtil.get32UUID());
            }
            FuneralDriveTask dt = new FuneralDriveTask();
            dt.setId(driveTask.getId());
            FuneralCar car = driveTask.getCar();
            car.setTaskTime(driveTask.getTaskTime());
            car.setDriveTask(dt);
            carService.updateDriveTask(car);    //修改已派车车辆
        }else if(driveTask.getTaskStatus()!=null&&driveTask.getTaskStatus()==FuneralTaskStatusEnum.HAVE_SENT_CAR.getValue()){//已派车的任务 设置为取消
            driveTask.setTaskStatus(FuneralTaskStatusEnum.CANCELED.getValue());
            FuneralCar car = new FuneralCar();
            driveTask.setCar(car);
            carService.releaseCarTask(driveTask.getId());           //释放车辆任务状态
        }else {
            driveTask.setTaskStatus(FuneralTaskStatusEnum.DIS_NOT_SEND.getValue());     //未派车
        }
    }

    @Override
    public DataSet<FuneralDriveTask> queryDriveTaskData(FuneralDriveTaskQueryParam driveTaskQueryParam) {
        DataSet<FuneralDriveTask> driveTaskDataSet = super.queryByParam(driveTaskQueryParam);
        appendStaff(driveTaskDataSet.getData());
        return driveTaskDataSet;
    }

    @Override
    public void updateDriveTask(FuneralDriveTask driveTask) {
        judgeTaskStatus(driveTask);
        driveTaskDao.deleteDriveTaskStaff(driveTask.getId());
        super.update(driveTask);
    }

    @Override
    public void deleteTasks(List<String> idList, User user) {
        List<FuneralDriveTask> driveTaskList = new ArrayList<>();
        for (String id : idList) {
            FuneralDriveTask driveTask = new FuneralDriveTask();
            driveTask.setId(id);
            driveTask.setDeleteTime(new Date());
            driveTask.setDeleteUserId(user.getId());
            driveTask.setDataStatus(0);
            driveTaskList.add(driveTask);
            carService.releaseCarTask(id);      //释放车辆任务状态
        }
        super.logicDelete(driveTaskList);
    }

    @Override
    public void taskFinishs(List<String> idList, User user) {
        List<FuneralDriveTask> driveTaskList = new ArrayList<>();
        for (String id : idList) {
            FuneralDriveTask driveTask = new FuneralDriveTask();
            driveTask.setId(id);
            driveTask.setReturnTime(new Date());
            driveTask.setLastUpdateTime(new Date());
            driveTask.setLastUpdateUserId(user.getId());
            driveTask.setTaskStatus(FuneralTaskStatusEnum.HAS_RETURNED.getValue());
            driveTaskList.add(driveTask);
            carService.releaseCarTask(id);      //释放车辆任务状态
        }
        driveTaskDao.taskFinishs(driveTaskList);
    }

    @Override
    public FuneralDriveTask getDriveTaskDetail(String id) {
        FuneralDriveTask driveTask = super.getById(id);
        List<FuneralDriveTask> driveTaskList = new ArrayList<>();
        driveTaskList.add(driveTask);
        appendStaff(driveTaskList);
        return driveTask;
    }

    @Override
    public List<Map> taskStatisticsQuery(FuneralDriveTaskQueryParam driveTaskQueryParam) {
        List<Map> mapList = driveTaskDao.taskStatisticsQuery(driveTaskQueryParam);
        return mapList;
    }

    @Override
    public Map driveTaskStatusQuery(FuneralDriveTaskQueryParam driveTaskQueryParam) {
        List<Map> mapList = driveTaskDao.driveTaskStatusQuery(driveTaskQueryParam);
        driveTaskQueryParam.setPaging(null);
        List<FuneralDriveTask> driveTaskList = super.queryByParam(driveTaskQueryParam).getData();
        Map map = driveTaskDao.someDayTaskCount(driveTaskQueryParam);
        map.put("carList",mapList);
        map.put("taskList",driveTaskList);
        return map;
    }

    private void appendStaff(List<FuneralDriveTask> driveTaskList) {
        if(driveTaskList.isEmpty())return;
        List<String> ids = driveTaskList.stream().map(FuneralDriveTask::getId).collect(Collectors.toList());
        if(ids.isEmpty())return;
        List<FuneralStaff> staffList = driveTaskDao.getByDriveTaskIds(ids);
        if(staffList.isEmpty())return;
        driveTaskList.forEach(driveTask -> {
            List<FuneralStaff> staffs = new ArrayList<>();
            staffList.forEach(staff -> {
                if(driveTask.getId().equals(staff.getTargetId())){
                    staffs.add(staff);
                }
            });
            driveTask.setStaffList(staffs);
        });
    }
}
