package com.yuanxiatech.xgj.funeral.drivetask.model;

import com.yuanxiatech.xgj.core.pojo.QueryParamAdapter;

public class FuneralDriveTaskQueryParam extends QueryParamAdapter {

    private String deadName;    //逝者姓名
    private String startTime;   //开始时间 接运时间
    private String endTime;     //开始时间  接运时间
    private String collectAddress;     //接运地址
    private String carNum;     //车号
    private String staffName;     //员工名称
    private String id;

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDeadName() {
        return deadName;
    }

    public void setDeadName(String deadName) {
        this.deadName = deadName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCollectAddress() {
        return collectAddress;
    }

    public void setCollectAddress(String collectAddress) {
        this.collectAddress = collectAddress;
    }
}
