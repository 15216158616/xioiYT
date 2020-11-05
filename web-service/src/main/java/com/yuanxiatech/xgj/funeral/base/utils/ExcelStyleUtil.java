package com.yuanxiatech.xgj.funeral.base.utils;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * excel样式工具类
 *
 * Created by Guo Yanshan on 2019/10/30.
 */
public class ExcelStyleUtil {

    //添加边框设置
    public static HSSFCellStyle createBorder(HSSFWorkbook hssf) {
        HSSFCellStyle cellStyle = hssf.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);    //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);      //左边框
        cellStyle.setBorderRight(BorderStyle.THIN);     //右边框
        cellStyle.setBorderTop(BorderStyle.THIN);       //上边框
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        return cellStyle;
    }

    //设置灰色填充+边框
    public static HSSFCellStyle createTitle(HSSFWorkbook hssf) {

        return createCellStyle(hssf, IndexedColors.GREY_25_PERCENT.getIndex());
    }

    //设置样式
    public static HSSFCellStyle createCellStyle(HSSFWorkbook hssf, short color) {
        HSSFCellStyle cellStyle = createBorder(hssf);
        cellStyle.setFillForegroundColor(color);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }
}
