package com.yuanxiatech.xgj.funeral.drivetask.model;

import com.yuanxiatech.xgj.core.pojo.IDictionary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * Created by Administrator on 2017/5/6.
 */
public enum FuneralTaskStatusEnum implements IDictionary {
    DIS_NOT_SEND(0, "未派车"),
    HAVE_SENT_CAR(1, "已派车"),
    HAS_RETURNED(2, "已完成"),
    CANCELED (3, "已取消"),
    ;

    FuneralTaskStatusEnum(int value, String label) {
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

    private static Map<Integer, FuneralTaskStatusEnum> map = new HashMap<>();

    static {
        for (FuneralTaskStatusEnum funeralTaskStatus : values()) {
            map.put(funeralTaskStatus.value, funeralTaskStatus);
        }
    }

    public static FuneralTaskStatusEnum parse(int value) {
        return map.get(value);
    }

    public static List<FuneralTaskStatusEnum> getEnumValues() {
        return Arrays.asList(values());
    }
}
