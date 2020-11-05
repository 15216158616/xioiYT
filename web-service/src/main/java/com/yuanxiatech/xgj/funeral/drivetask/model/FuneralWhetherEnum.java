package com.yuanxiatech.xgj.funeral.drivetask.model;

import com.yuanxiatech.xgj.core.pojo.IDictionary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FuneralWhetherEnum implements IDictionary {

    YES(1, "是"),
    NO(2, "否"),
    ;

    private int value;

    private String label;

    FuneralWhetherEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    private static Map<Integer, FuneralWhetherEnum> map = new HashMap<>();


    static {
        for (FuneralWhetherEnum type : values()) {
            map.put(type.value, type);
        }
    }

    public static FuneralWhetherEnum parse(int value) {
        return map.get(value);
    }

    public static List<FuneralWhetherEnum> getEnumValues() {
        return Arrays.asList(values());
    }

}
