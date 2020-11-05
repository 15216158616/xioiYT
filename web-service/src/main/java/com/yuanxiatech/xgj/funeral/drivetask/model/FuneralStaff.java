package com.yuanxiatech.xgj.funeral.drivetask.model;

import com.yuanxiatech.xgj.core.pojo.StringPojo;

/**
 * 员工表
 */
public class FuneralStaff extends StringPojo {

    private String name;        //员工名字

    private Integer type;       //类型

    private String phone;       //联系电话

    private Integer age;        //年龄

    private Integer gender;     //性别

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }


    private String targetId;       //目标id (映射参数)

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    /**
     * 员工类型
     *
     * @return
     */
    public String getTypeDesc() {
        if (type != null) {
            FuneralStaffTypeEnum funeralStaffType = FuneralStaffTypeEnum.parse(type);
            if (funeralStaffType != null) {
                return funeralStaffType.getLabel();
            }
        }
        return "";
    }
    /**
     * 获取性别的文字说明
     *
     * @return
     */
    public String getGenderDesc() {
        if (gender != null) {
            FuneralGenderEnum userGender = FuneralGenderEnum.parse(gender);
            if (userGender != null) {
                return userGender.getLabel();
            }
        }
        return null;
    }
}
