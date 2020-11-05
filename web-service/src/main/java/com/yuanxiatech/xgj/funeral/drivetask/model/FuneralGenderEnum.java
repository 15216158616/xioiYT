package com.yuanxiatech.xgj.funeral.drivetask.model;

import com.yuanxiatech.xgj.core.pojo.IDictionary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FuneralGenderEnum implements IDictionary {

    MAN(1, "男"),
    WOMAN(2, "女"),
    UNKNOWN(3, "未知"),
    ;

    private int value;

    private String label;

    FuneralGenderEnum(int value, String label) {
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

    private static Map<Integer, FuneralGenderEnum> map = new HashMap<>();


    static {
        for (FuneralGenderEnum type : values()) {
            map.put(type.value, type);
        }
    }

    public static FuneralGenderEnum parse(int value) {
        return map.get(value);
    }

    public static List<FuneralGenderEnum> getEnumValues() {
        return Arrays.asList(values());
    }

}
