package com.yuanxiatech.xgj.funeral.drivetask.model;

import com.yuanxiatech.xgj.core.pojo.QueryParamAdapter;

public class FuneralStaffQueryParam extends QueryParamAdapter {

    private String name;
    private Integer type;

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
}
