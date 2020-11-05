package com.yuanxiatech.xgj.funeral.base.utils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/15.
 */
public class ExportExcelData implements Serializable {

    private Map<String, Object> titleMap;

    private Map<String, Object> headMap;

    private List<Map<String, Object>> dataList;

    private Map<String, Object> footMap;

    private Map<String, Object> colsMap;

    private Integer defaultWidth = 22;//默认宽度

    private Integer defaultHeight = 20;//默认高低(像素点)

    private Map<Integer, Integer> otherWidth;//特殊书列宽度<列，列宽度>

    private Short titleSize = 16;//标题字体大小

    private Short headSize = 10;//数据头字体大小

    private Short dataSize = 8;//数据内容字体大小

    private String excelFoot;//页脚固定内容

    private Integer footDirection = 2;//页脚方向  1 左，2 中 3 右

    private Integer excelFootType = 2;//页脚类型  1 固定内容 2，页数

    private String excelHead;//页头

    private boolean landscape = true;//打印方向 true为横向 false 为纵向

    public Integer getExcelFootType() {
        return excelFootType;
    }

    public void setExcelFootType(Integer excelFootType) {
        this.excelFootType = excelFootType;
    }

    public Integer getFootDirection() {
        return footDirection;
    }

    public void setFootDirection(Integer footDirection) {
        this.footDirection = footDirection;
    }

    private List<Integer> mergeList = null;//合并列

    public boolean isLandscape() {
        return landscape;
    }

    public void setLandscape(boolean landscape) {
        this.landscape = landscape;
    }

    public String getExcelFoot() {
        return excelFoot;
    }

    public void setExcelFoot(String excelFoot) {
        this.excelFoot = excelFoot;
    }

    public String getExcelHead() {
        return excelHead;
    }

    public void setExcelHead(String excelHead) {
        this.excelHead = excelHead;
    }

    public Map<String, Object> getColsMap() {
        return colsMap;
    }

    public void setColsMap(Map<String, Object> colsMap) {
        this.colsMap = colsMap;
    }

    public List<Integer> getMergeList() {
        return mergeList;
    }

    public void setMergeList(List<Integer> mergeList) {
        this.mergeList = mergeList;
    }

    public Integer getDefaultWidth() {
        return defaultWidth;
    }

    public void setDefaultWidth(Integer defaultWidth) {
        this.defaultWidth = defaultWidth;
    }

    public Integer getDefaultHeight() {
        return defaultHeight;
    }

    public void setDefaultHeight(Integer defaultHeight) {
        this.defaultHeight = defaultHeight;
    }

    public Map<Integer, Integer> getOtherWidth() {
        return otherWidth;
    }

    public void setOtherWidth(Map<Integer, Integer> otherWidth) {
        this.otherWidth = otherWidth;
    }

    public Short getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(Short titleSize) {
        this.titleSize = titleSize;
    }

    public Short getHeadSize() {
        return headSize;
    }

    public void setHeadSize(Short headSize) {
        this.headSize = headSize;
    }

    public Short getDataSize() {
        return dataSize;
    }

    public void setDataSize(Short dataSize) {
        this.dataSize = dataSize;
    }

    public Map<String, Object> getTitleMap() {
        return titleMap;
    }

    public void setTitleMap(Map<String, Object> titleMap) {
        this.titleMap = titleMap;
    }

    public Map<String, Object> getHeadMap() {
        return headMap;
    }

    public void setHeadMap(Map<String, Object> headMap) {
        this.headMap = headMap;
    }

    public List<Map<String, Object>> getDataList() {
        return dataList;
    }

    public void setDataList(List<Map<String, Object>> dataList) {
        this.dataList = dataList;
    }

    public Map<String, Object> getFootMap() {
        return footMap;
    }

    public void setFootMap(Map<String, Object> footMap) {
        this.footMap = footMap;
    }
}
