package com.yuanxiatech.xgj.funeral.system.model;

import com.yuanxiatech.xgj.core.pojo.IDictionary;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MenuTypeEnum implements IDictionary {

    MENU(1, "菜单"),
    BUTTON(2, "按钮");

    private int value;

    private String label;

    MenuTypeEnum(int value, String label) {
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

    private static Map<Integer, MenuTypeEnum> map = new HashMap<>();


    static {
        for (MenuTypeEnum type : values()) {
            map.put(type.value, type);
        }
    }

    public static MenuTypeEnum parse(int value) {
        return map.get(value);
    }

    public static List<MenuTypeEnum> getEnumValues() {
        return Arrays.asList(values());
    }

}
