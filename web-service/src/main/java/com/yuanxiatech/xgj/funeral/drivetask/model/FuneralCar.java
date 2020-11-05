package com.yuanxiatech.xgj.funeral.drivetask.model;

import com.yuanxiatech.xgj.core.pojo.StringPojo;
import com.yuanxiatech.xgj.core.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * 车辆班次表
 */
public class FuneralCar extends StringPojo {

    private String carNum;      //车牌号码
    private String className;      //班次名称
    private String carType;     //车型
    private Integer seq;        //排序
    private Integer usingStatus;     //是否启用
    private List<FuneralStaff> staffList; //默认随车人员
    private Date taskTime;              //当前正在执行的出车接运时间或者上一次任务接运时间
    private FuneralDriveTask driveTask; //正在执行的遗体接运任务

    public Boolean getHaveSentCar(){
        if(this.driveTask==null||StringUtils.isBlank(this.driveTask.getId())){
            return false;
        }
        return true;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Date getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    public FuneralDriveTask getDriveTask() {
        return driveTask;
    }

    public void setDriveTask(FuneralDriveTask driveTask) {
        this.driveTask = driveTask;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<FuneralStaff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<FuneralStaff> staffList) {
        this.staffList = staffList;
    }

    public Integer getUsingStatus() {
        return usingStatus;
    }

    public void setUsingStatus(Integer usingStatus) {
        this.usingStatus = usingStatus;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    /**
     * 获取启用的文字说明
     *
     * @return
     */
    public String getUsingStatusDesc() {
        if (usingStatus != null) {
            FuneralWhetherEnum whetherStatusEnum = FuneralWhetherEnum.parse(usingStatus);
            if (whetherStatusEnum != null) {
                return whetherStatusEnum.getLabel();
            }
        }
        return null;
    }

    public Boolean getStatus(){
        if (usingStatus != null&& usingStatus==FuneralWhetherEnum.YES.getValue()) {
            return true;
        }
        return false;
    }
}
