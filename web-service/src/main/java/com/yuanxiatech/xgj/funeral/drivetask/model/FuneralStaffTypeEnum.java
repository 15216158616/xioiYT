package com.yuanxiatech.xgj.funeral.drivetask.model;


import com.yuanxiatech.xgj.core.pojo.IDictionary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/6.
 */
public enum FuneralStaffTypeEnum implements IDictionary {
    DRIVER(1, "灵车司机"),
    PORTER(2, "接尸工"),
    OTHER(4,"其它"),
    ;

    FuneralStaffTypeEnum(int value, String label) {
        this.value = value;
        this.label = label;
    }

    private int value;

    private String label;

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String getLabel() {
        return label;
    }

    private static Map<Integer, FuneralStaffTypeEnum> map = new HashMap<>();

    static {
        for (FuneralStaffTypeEnum funeralStaffType : values()) {
            map.put(funeralStaffType.value, funeralStaffType);
        }
    }

    public static FuneralStaffTypeEnum parse(int value) {
        return map.get(value);
    }

    public static List<FuneralStaffTypeEnum> getEnumValues() {
        return Arrays.asList(values());
    }
}
