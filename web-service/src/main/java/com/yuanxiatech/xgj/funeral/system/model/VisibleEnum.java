package com.yuanxiatech.xgj.funeral.system.model;

import com.yuanxiatech.xgj.core.pojo.IDictionary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum VisibleEnum implements IDictionary {

    YES_VISIBLE(1, "可见"),
    NOT_VISIBLE(2, "不可见存");

    private int value;

    private String label;

    VisibleEnum(int value, String label) {
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

    private static Map<Integer, VisibleEnum> map = new HashMap<>();

    static {
        for (VisibleEnum type : values()) {
            map.put(type.value, type);
        }
    }

    public static VisibleEnum parse(int value) {
        return map.get(value);
    }

    public static List<VisibleEnum> getEnumValues() {
        return Arrays.asList(values());
    }

}
