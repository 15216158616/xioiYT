package com.yuanxiatech.xgj.funeral.drivetask.model;

import com.yuanxiatech.xgj.core.pojo.StringPojo;

import java.util.Date;
import java.util.List;

/**
 * 遗体接运表
 */
public class FuneralDriveTask extends StringPojo {

    private String deadName;        //逝者姓名
    private Integer deadGender;    //逝者性别
    private Integer deadAge;         //逝者年龄
    private String deadProfession;   //逝者职业
    private Date deathTime;         //死亡时间
    private String deathCause;         //死亡原因
    private String deadAddress;         //逝者详细地址
    private String deathAddress;         //逝者死亡地址
    private String relationName;         //家属姓名
    private String relationPhone;         //家属电话
    private String deadRelation;         //与逝者关系
    private Date taskTime;              //接运时间
    private Date returnTime;              //返回时间
    private String collectAddress;         //接运地址
    private String coffin;                  //租何棺木
    private String remark;                     //备注
    private String reportUser;                     //报告人
    private String recordUser;                     //记录人
    private String operator;                     //经办人
    private String passengerCar;              //大中客车
    private String handlingInformation;      //处理情况
    private String carType;      //车型
    private FuneralCar car;                     //接运车辆
    private List<FuneralStaff> staffList;       //随车人员

    private Integer taskStatus;         //接运状态


    /**
     * 获取接运状态的文字说明
     *
     * @return
     */
    public String getTaskStatusDesc() {
        if (taskStatus != null) {
            FuneralTaskStatusEnum taskStatusEnum = FuneralTaskStatusEnum.parse(taskStatus);
            if (taskStatusEnum != null) {
                return taskStatusEnum.getLabel();
            }
        }
        return null;
    }

    /**
     * 获取逝者性别的文字说明
     *
     * @return
     */
    public String getDeadGenderDesc() {
        if (deadGender != null) {
            FuneralGenderEnum userGender = FuneralGenderEnum.parse(deadGender);
            if (userGender != null) {
                return userGender.getLabel();
            }
        }
        return null;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getReportUser() {
        return reportUser;
    }

    public void setReportUser(String reportUser) {
        this.reportUser = reportUser;
    }

    public String getRecordUser() {
        return recordUser;
    }

    public void setRecordUser(String recordUser) {
        this.recordUser = recordUser;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public List<FuneralStaff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<FuneralStaff> staffList) {
        this.staffList = staffList;
    }

    public String getDeadName() {
        return deadName;
    }

    public void setDeadName(String deadName) {
        this.deadName = deadName;
    }

    public Integer getDeadGender() {
        return deadGender;
    }

    public void setDeadGender(Integer deadGender) {
        this.deadGender = deadGender;
    }

    public Integer getDeadAge() {
        return deadAge;
    }

    public void setDeadAge(Integer deadAge) {
        this.deadAge = deadAge;
    }

    public String getDeadProfession() {
        return deadProfession;
    }

    public void setDeadProfession(String deadProfession) {
        this.deadProfession = deadProfession;
    }

    public Date getDeathTime() {
        return deathTime;
    }

    public void setDeathTime(Date deathTime) {
        this.deathTime = deathTime;
    }

    public String getDeathCause() {
        return deathCause;
    }

    public void setDeathCause(String deathCause) {
        this.deathCause = deathCause;
    }

    public String getDeadAddress() {
        return deadAddress;
    }

    public void setDeadAddress(String deadAddress) {
        this.deadAddress = deadAddress;
    }

    public String getDeathAddress() {
        return deathAddress;
    }

    public void setDeathAddress(String deathAddress) {
        this.deathAddress = deathAddress;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getRelationPhone() {
        return relationPhone;
    }

    public void setRelationPhone(String relationPhone) {
        this.relationPhone = relationPhone;
    }

    public String getDeadRelation() {
        return deadRelation;
    }

    public void setDeadRelation(String deadRelation) {
        this.deadRelation = deadRelation;
    }

    public Date getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    public String getCollectAddress() {
        return collectAddress;
    }

    public void setCollectAddress(String collectAddress) {
        this.collectAddress = collectAddress;
    }

    public String getCoffin() {
        return coffin;
    }

    public void setCoffin(String coffin) {
        this.coffin = coffin;
    }

    public FuneralCar getCar() {
        return car;
    }

    public void setCar(FuneralCar car) {
        this.car = car;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPassengerCar() {
        return passengerCar;
    }

    public void setPassengerCar(String passengerCar) {
        this.passengerCar = passengerCar;
    }

    public String getHandlingInformation() {
        return handlingInformation;
    }

    public void setHandlingInformation(String handlingInformation) {
        this.handlingInformation = handlingInformation;
    }
}
