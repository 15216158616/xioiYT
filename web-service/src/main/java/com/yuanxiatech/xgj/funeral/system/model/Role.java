package com.yuanxiatech.xgj.funeral.system.model;

import com.yuanxiatech.xgj.common.partner.model.Partner;
import com.yuanxiatech.xgj.core.pojo.StringPojo;

/**
 * @description 用户角色表
 * @date 2020/6/9 18:57
 */
public class Role extends StringPojo {

    private String name;//名称

    private String code;//标识

    private String remark;//描述

    private Partner partner;//机构

    public Role(){}

    public Role(String id){super(id);}

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
