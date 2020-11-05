package com.yuanxiatech.xgj.funeral.base.utils;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Administrator on 2018/5/14.
 */
public final class ExcelUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static List<Map<String, String>> importExcel(MultipartFile file, Resource exportConfigName) {
        ImportConfig importConfig = null;
        try {
            importConfig = parseImportConfig(exportConfigName);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            InputStream fileIn = file.getInputStream();
            POIFSFileSystem fs = new POIFSFileSystem(fileIn);
            Workbook wb0 = new HSSFWorkbook(fs);
            Sheet sht0 = wb0.getSheetAt(0);
            for (Row r : sht0) {
                //如果当前行的行号（从0开始）未达到第5行则从新循环
                if (r.getRowNum() < 4) {
                    continue;
                }
                Map<String, String> map = new HashMap<String, String>();
                for (int i = 0; i < r.getLastCellNum(); i++) {
                    map.put(importConfig.getDataList().get(i).get("name"), checkCell(r, i));
                }
                list.add(map);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 财务对账账单导入
     *
     * @param file
     * @param exportConfigName
     * @return
     */
    public static List<Map<String, String>> importExcelFinance(MultipartFile file, Resource exportConfigName, Boolean ifCard) {
        ImportConfig importConfig = null;
        try {
            importConfig = parseImportConfig(exportConfigName);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {

            InputStream fileIn = file.getInputStream();
            Workbook wb = null;
            try {
                wb = WorkbookFactory.create(fileIn);
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
            //3.得到Excel工作表对象
            Sheet sheet = wb.getSheetAt(0);
            fileIn.close();
            for (Row r : sheet) {
                //如果当前行的行号（从0开始）未达到第5行则从新循环
                if (r.getRowNum() < 4) {
                    continue;
                }
                Map<String, String> map = new HashMap<String, String>();
                if (ifCard) {
                    for (int i = 0; i < 15; i++) {  //只作用于 财务银联卡账单导入
                        map.put(importConfig.getDataList().get(i).get("name"), String.valueOf(r.getCell(i)));
                    }
                } else {
                    for (int i = 0; i < 16; i++) {  //只作用于 财务手机支付账单导入
                        map.put(importConfig.getDataList().get(i).get("name"), String.valueOf(r.getCell(i)));
                    }
                }
                list.add(map);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Map<String, String>> importExcelCad(MultipartFile file, Resource exportConfigName) {
        ImportConfig importConfig = null;
        try {
            importConfig = parseImportConfig(exportConfigName);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        try {
            InputStream fileIn = file.getInputStream();
            Workbook wb = null;
            try {
                wb = WorkbookFactory.create(fileIn);
            } catch (InvalidFormatException e) {
                e.printStackTrace();
            }
            //3.得到Excel工作表对象
            Sheet sheet = wb.getSheetAt(0);
            fileIn.close();
            for (Row r : sheet) {
                //如果当前行的行号（从0开始）未达到第5行则从新循环
                if (r.getRowNum() < 1) {
                    continue;
                }
                //如果没有使用人跳过本条数据
                String cell1 = String.valueOf(r.getCell(9));
                if (cell1.length() > 0) {
                    Map<String, String> map = new HashMap<String, String>();
                    for (int i = 0; i < r.getLastCellNum(); i++) {
                        if (i >= importConfig.getDataList().size()) {
                            System.out.println("读取的excel文件的列与配置的数据列不一致");
                            break;
                        }
                        Cell cell = r.getCell(i);
                        if (cell != null) {

                            /*cell.setCellType(Cell.CELL_TYPE_STRING);
                            String data = String.valueOf(cell);*/
                            String data = getCellValue(cell);

                            Map<String, String> target = importConfig.getDataList().get(i);
                            map.put(target.get("name"), data == null ? "" : data);
                        }
                    }
                    list.add(map);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }



    // 读取cell单元格的值，如果为日期格式，进行转换
    @SuppressWarnings("deprecation")
    public static String getCellValue(Cell cell) {
        if (cell == null)
            return "";
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
            return cell.getCellFormula();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            short format = cell.getCellStyle().getDataFormat();
            System.out.println("format:" + format + ";;;;;value:" + cell.getNumericCellValue());
            SimpleDateFormat sdf = null;
            if (format == 14 || format == 31 || format == 57 || format == 58
                    || (176 <= format && format <= 178) || (182 <= format && format <= 196)
                    || (210 <= format && format <= 213) || (208 == format)) { // 日期
                sdf = new SimpleDateFormat("yyyy-MM-dd");
            } else if (format == 20 || format == 32 || format == 183 || (200 <= format && format <= 209)) { // 时间
                sdf = new SimpleDateFormat("HH:mm");
            } else { // 不是日期格式
                return String.valueOf(cell.getNumericCellValue());
            }
            double value = cell.getNumericCellValue();
            Date date = DateUtil.getJavaDate(value);
            if (date == null) {
                return "";
            }
            String result = "";
            try {
                result = sdf.format(date);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
            return result;
        }
        return "";
    }

    /**
     * 解析POI导入Excel中日期格式数据
     *
     * @param currentCell
     * @return currentCellValue
     */
    public static String importByExcelForDate(Cell currentCell) {
        String currentCellValue = "";
        // 判断单元格数据是否是日期
        if ("yyyy/mm;@".equals(currentCell.getCellStyle().getDataFormatString())
                || "m/d/yy".equals(currentCell.getCellStyle().getDataFormatString())
                || "yy/m/d".equals(currentCell.getCellStyle().getDataFormatString())
                || "mm/dd/yy".equals(currentCell.getCellStyle().getDataFormatString())
                || "dd-mmm-yy".equals(currentCell.getCellStyle().getDataFormatString())
                || "yyyy/m/d".equals(currentCell.getCellStyle().getDataFormatString())) {
            if (DateUtil.isCellDateFormatted(currentCell)) {
                // 用于转化为日期格式
                Date d = currentCell.getDateCellValue();
                DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
                currentCellValue = formater.format(d);
            }
        }
        // 不是日期格式返回""
        return currentCellValue;
    }

    public static String checkCell(Row r, int i) {
        XSSFCell cell = (XSSFCell) r.getCell(i);
        if (cell == null || cell.equals("") || cell.getCellType() == HSSFCell.CELL_TYPE_BLANK) {
            return "";
        } else {
            return cell.getStringCellValue();
        }
    }




    /**
     * 通用导出excel
     *
     * @param exportData       需要导出的数据
     * @param exportConfigName 导出配置文件名称
     * @return
     */
    public static void commonExportExcel(HttpServletResponse response, ExportExcelData exportData, Resource exportConfigName, String fileName) {
        response.reset();

        ExportConfig exportConfig = parseExportConfig(exportConfigName);
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();

        HSSFSheet hssfSheet = hssfWorkbook.createSheet();
        //页脚 固定内容
        if (exportData.getExcelFootType() == 1 && exportData.getExcelFoot() != null && !"".equals(exportData.getExcelFoot())) {
            HSSFFooter hssfFooter = hssfSheet.getFooter();
            if (exportData.getFootDirection() == 1) {
                hssfFooter.setLeft(exportData.getExcelFoot());
            } else if (exportData.getFootDirection() == 2) {
                hssfFooter.setCenter(exportData.getExcelFoot());
            } else {
                hssfFooter.setRight(exportData.getExcelFoot());
            }

        }


        //页脚  页数
        if (exportData.getExcelFootType() == 2) {
            HSSFFooter hssfFooter = hssfSheet.getFooter();
            if (exportData.getFootDirection() == 1) {
                hssfFooter.setLeft(HSSFFooter.page());
            } else if (exportData.getFootDirection() == 2) {
                hssfFooter.setCenter(HSSFFooter.page());
            } else {
                hssfFooter.setRight(HSSFFooter.page());
            }

        }


        //设置打印方向
        HSSFPrintSetup printSetup = hssfSheet.getPrintSetup();
        printSetup.setLandscape(exportData.isLandscape());
        //A4纸
        printSetup.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);

        //默认表格宽度
        hssfSheet.setDefaultColumnWidth(exportData.getDefaultWidth());
        //默认表格高度
        hssfSheet.setDefaultRowHeightInPoints(exportData.getDefaultHeight());
        //默认样式
        HSSFCellStyle style = setCommonHSSFCellStyle(hssfWorkbook, true);
        //为特殊列设置宽度
        if (exportData.getOtherWidth() != null) {
            for (Map.Entry<Integer, Integer> entry : exportData.getOtherWidth().entrySet()) {
                hssfSheet.setColumnWidth(entry.getKey(), entry.getValue() * 291);
            }
        }
        //填充表格内容
        hssfSheet = commonConfigRowDate(exportConfig, hssfWorkbook, hssfSheet, style, exportData);

        try {
            response.setHeader("Content-Disposition", "attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(fileName,"UTF-8")+".xls");
            OutputStream output = response.getOutputStream();
            hssfWorkbook.write(output);
            output.close();
            hssfWorkbook.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    /**
     * 通用填充内容
     */
    private static HSSFSheet commonConfigRowDate(ExportConfig exportConfig, HSSFWorkbook hssfWorkbook, HSSFSheet hssfSheet, HSSFCellStyle style, ExportExcelData exportData) {

        //填充标题
        List<RowConfig> titles = exportConfig.getTitleRowConfigList();
        Map<String, Object> titleMap = exportData.getTitleMap();

        if (!MapUtils.isEmpty(titleMap)) {
            for (RowConfig config : titles) {
                HSSFRow row = hssfSheet.createRow(config.getStartNum());
                row.setHeight((short) 500);
                for (RowData rowData : config.getRowData()) {
                    if (!(rowData.getStartCell() == rowData.getEndCell() && rowData.getStartRow() == rowData.getEndRow())) {
                        CellRangeAddress region = new CellRangeAddress(rowData.startRow, rowData.getEndRow(), rowData.getStartCell(), rowData.getEndCell());
                        hssfSheet.addMergedRegion(region);
                        //合并单元格边框
//                        SetRegionBorder(region,hssfSheet);
                    }
                    String cellValue = rowData.getValue();
                    if (StringUtils.isBlank(cellValue)) {
                        cellValue = MapUtils.getString(titleMap, rowData.getValueOf());
                    }
                    Cell cell = row.createCell(rowData.getStartCell());
                    HSSFFont font = hssfWorkbook.createFont();
                    font.setFontHeightInPoints(exportData.getTitleSize());//设置字体大小
                    HSSFCellStyle titleStyle = setCommonHSSFCellStyle(hssfWorkbook, false);
                    titleStyle.setFont(font);
                    cell.setCellStyle(titleStyle);
                    cell.setCellValue(cellValue);
                }
            }
        }

        //填充头部
        List<RowConfig> heads = exportConfig.getHeadRowConfigList();
        Map<String, Object> headMap = exportData.getHeadMap();
        if (!MapUtils.isEmpty(headMap)) {
            for (RowConfig config : heads) {
                HSSFRow row = hssfSheet.createRow(config.getStartNum());
                row.setHeight((short) 400);
                for (RowData rowData : config.getRowData()) {
                    if (!(rowData.getStartCell() == rowData.getEndCell() && rowData.getStartRow() == rowData.getEndRow())) {
                        CellRangeAddress region = new CellRangeAddress(rowData.startRow, rowData.getEndRow(), rowData.getStartCell(), rowData.getEndCell());
                        hssfSheet.addMergedRegion(region);
                        //合并单元格边框
//                        SetRegionBorder(region,hssfSheet);
                    }
                    String cellValue = rowData.getValue();
                    if (StringUtils.isBlank(cellValue)) {
                        cellValue = MapUtils.getString(headMap, rowData.getValueOf());
                    }
                    Cell cell = row.createCell(rowData.getStartCell());
                    HSSFFont font = hssfWorkbook.createFont();
                    font.setFontHeightInPoints(exportData.getHeadSize());//设置字体大小
                    HSSFCellStyle titleStyle = setCommonHSSFCellStyle(hssfWorkbook, false);
                    titleStyle.setFont(font);
                    titleStyle.setAlignment(HorizontalAlignment.LEFT);// 居左
                    cell.setCellStyle(titleStyle);
                    cell.setCellValue(cellValue);
                }
            }
        }

        //填充表格
        List<RowConfig> tables = exportConfig.getTableRowConfigList();
        List<Map<String, Object>> tableMap = exportData.getDataList();
        Map<String, Object> colsMap = exportData.getColsMap();
        List<Integer> mergeList = exportData.getMergeList();
        int startNum = 0;
        {
            boolean dataConfig = false;
            for (RowConfig config : tables) {
                //表头写入-for循环表格头部数据
                if (!dataConfig) {
                    HSSFRow row = hssfSheet.createRow(config.getStartNum());
                    row.setHeight((short) 400);
                    for (RowData rowData : config.getRowData()) {
                        if (StringUtils.isBlank(rowData.getValue())) {
                            dataConfig = true;
                            break;
                        }
                        String cellValue = rowData.getValue();
                        //动态列
                        if (rowData.getValueOf() != null) {
                            cellValue = MapUtils.getString(colsMap, rowData.getValueOf());
                        }
                        Cell cell = row.createCell(rowData.getStartCell());
                        HSSFFont font = hssfWorkbook.createFont();
                        font.setFontHeightInPoints((short) 10);//设置字体大小
                        font.setFontName("宋体");
                        font.setBold(true);
                        if (!"mergeCell".equals(cellValue))
                            cell.setCellValue(cellValue);
                        HSSFCellStyle titleStyle = setCommonHSSFCellStyle(hssfWorkbook, true);
                        titleStyle.setFont(font);
                        cell.setCellStyle(titleStyle);
                    }
                    for (RowData rowData : config.getRowData()) {
                        if (StringUtils.isBlank(rowData.getValue())) {
                            dataConfig = true;
                            break;
                        }
                        if (!(rowData.getStartCell() == rowData.getEndCell() && rowData.getStartRow() == rowData.getEndRow())) {
                            CellRangeAddress region = new CellRangeAddress(config.getStartNum() + rowData.startRow, config.getStartNum() + rowData.getEndRow(), rowData.getStartCell(), rowData.getEndCell());
                            hssfSheet.addMergedRegion(region);
                        }
                    }
                }
                //表格数据开始写入
                if (dataConfig) {
                    startNum = config.getStartNum();
                    Map<Integer, Integer> mergeMap = new HashMap<>();
                    //判断是否有合并列
                    if (mergeList != null) {
                        //初始化合并单元格
                        for (Integer col : mergeList) {
                            mergeMap.put(col, 0);
                        }
                        //存储上个数据
                        Map<String, Object> beforMap = new HashMap<>();
                        for (Map<String, Object> map : tableMap) {
                            HSSFRow row = hssfSheet.createRow(startNum);
                            for (RowData rowData : config.getRowData()) {
                                String cellValue = MapUtils.getString(map, rowData.getValueOf());
                                //判断当前列是否为合并列
                                if (mergeList.contains(rowData.getStartCell())) {
                                    //获取上个当前列数据
                                    String beforCellValue = MapUtils.getString(beforMap, rowData.getValueOf());
                                    //为空或上下两个数据不等时，当前行正常写入,上面的行根据当前列的合并数合并
                                    if (cellValue == null || !cellValue.equals(beforCellValue)) {
                                        Cell cell = row.createCell(rowData.getStartCell());
                                        cell.setCellStyle(style);
                                        cell.setCellValue(cellValue);
                                        if (mergeMap.get(rowData.getStartCell()) > 0) {
                                            //合并单元格
                                            CellRangeAddress region = new CellRangeAddress(startNum - 1 - mergeMap.get(rowData.getStartCell()), startNum - 1, rowData.getStartCell(), rowData.getStartCell());
                                            hssfSheet.addMergedRegion(region);
                                            //合并单元格边框
                                            SetRegionBorder(region, hssfSheet);
                                        }
                                        mergeMap.put(rowData.getStartCell(), 0);
                                        //相等则不写数据
                                    } else {
                                        mergeMap.put(rowData.getStartCell(), mergeMap.get(rowData.getStartCell()) + 1);
                                    }
                                } else {
                                    Cell cell = row.createCell(rowData.getStartCell());
                                    cell.setCellStyle(style);
                                    cell.setCellValue(cellValue);
                                }
                            }
                            startNum++;
                            beforMap = map;
                        }
                    } else {
                        for (Map<String, Object> map : tableMap) {
                            HSSFRow row = hssfSheet.createRow(startNum);
                            for (RowData rowData : config.getRowData()) {
                                String cellValue = MapUtils.getString(map, rowData.getValueOf());
                                Cell cell = row.createCell(rowData.getStartCell());
                                cell.setCellStyle(style);
                                cell.setCellValue(cellValue);
                            }
                            startNum++;
                        }
                    }
                }

            }

        }
        //填充底部
        List<RowConfig> foots = exportConfig.getFootRowConfigList();
        Map<String, Object> footMap = exportData.getFootMap();
        {
            for (RowConfig config : foots) {
                HSSFRow row = hssfSheet.createRow(startNum);
                row.setHeight((short) 300);
                for (RowData rowData : config.getRowData()) {
                    if (!(rowData.getStartCell() == rowData.getEndCell() && rowData.getStartRow() == rowData.getEndRow())) {
                        CellRangeAddress region = new CellRangeAddress(rowData.startRow + startNum, rowData.getEndRow() + startNum, rowData.getStartCell(), rowData.getEndCell());
                        hssfSheet.addMergedRegion(region);
                    }
                    String cellValue = rowData.getValue();
                    if (StringUtils.isBlank(cellValue)) {
                        cellValue = MapUtils.getString(footMap, rowData.getValueOf());
                    }
                    Cell cell = row.createCell(rowData.getStartCell());
                    HSSFFont font = hssfWorkbook.createFont();
                    font.setFontHeightInPoints(exportData.getHeadSize());//设置字体大小
                    HSSFCellStyle titleStyle = setCommonHSSFCellStyle(hssfWorkbook, false);//去除head上面对应的第一类的上边框
                    titleStyle.setFont(font);
                    if ("left".equals(rowData.getAlign())) {
                        titleStyle.setAlignment(HorizontalAlignment.LEFT);// 居左
                    } else if ("right".equals(rowData.getAlign())) {
                        titleStyle.setAlignment(HorizontalAlignment.RIGHT);// 居右
                    } else {
                        titleStyle.setAlignment(HorizontalAlignment.CENTER);// 居中
                    }

                    cell.setCellStyle(titleStyle);
                    cell.setCellValue(cellValue);
                }
                startNum++;
            }
        }

        return hssfSheet;
    }


    /**
     * 通用样式
     */
    private static HSSFCellStyle setCommonHSSFCellStyle(HSSFWorkbook hssfWorkbook, Boolean isBorder) {
        HSSFCellStyle style = hssfWorkbook.createCellStyle();
        if (isBorder) {
            style.setBorderBottom(BorderStyle.THIN); //下边框
            style.setBorderLeft(BorderStyle.THIN);//左边框
            style.setBorderTop(BorderStyle.THIN);//上边框
            style.setBorderRight(BorderStyle.THIN);//右边框
        } else {
            style.setBorderBottom(BorderStyle.NONE); //下边框
            style.setBorderLeft(BorderStyle.NONE);//左边框
            style.setBorderTop(BorderStyle.NONE);//上边框
            style.setBorderRight(BorderStyle.NONE);//右边框
        }
        style.setAlignment(HorizontalAlignment.CENTER);// 居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直
        style.setWrapText(true);
        return style;
    }

    /**
     * 合并单元格添加边框
     *
     * @param region
     * @param hssfSheet
     */
    private static void SetRegionBorder(CellRangeAddress region, HSSFSheet hssfSheet) {
        //合并单元格边框
        RegionUtil.setBorderBottom(BorderStyle.THIN, region, hssfSheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, region, hssfSheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, region, hssfSheet);
        RegionUtil.setBorderTop(BorderStyle.THIN, region, hssfSheet);
    }

    private static HSSFCellStyle setHSSFCellStyle(HSSFWorkbook hssfWorkbook) {
        HSSFCellStyle style = hssfWorkbook.createCellStyle();
        style.setWrapText(true);//自动换行
        style.setAlignment(HorizontalAlignment.CENTER);// 居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直

        style.setBorderBottom(BorderStyle.THIN); //下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        return style;
    }


    //填充内容
    private static HSSFSheet configRowDate(List<RowConfig> rowConfigList, HSSFWorkbook hssfWorkbook, HSSFSheet hssfSheet, HSSFCellStyle style, Map<String, Object> dataMap, List<Map<String, Object>> dataMapList) {

        if (!MapUtils.isEmpty(dataMap)) {
            for (RowConfig config : rowConfigList) {
                HSSFRow row = hssfSheet.createRow(config.getStartNum());
                if (config.getStartNum() == 0) {
                    row.setHeight((short) 800);
                }

                for (RowData rowData : config.getRowData()) {
                    if (!(rowData.getStartCell() == rowData.getEndCell() && rowData.getStartRow() == rowData.getEndRow())) {
                        hssfSheet.addMergedRegion(new CellRangeAddress(rowData.startRow, rowData.getEndRow(), rowData.getStartCell(), rowData.getEndCell()));
                    }
                    String cellValue = rowData.getValue();
                    if (StringUtils.isBlank(cellValue)) {
                        cellValue = MapUtils.getString(dataMap, rowData.getValueOf());
                    }
                    Cell cell = row.createCell(rowData.getStartCell());
                    if (config.getStartNum() == 0) {
                        HSSFFont font = hssfWorkbook.createFont();
                        font.setFontHeightInPoints((short) 18);//设置字体大小
                        HSSFCellStyle titleStyle = setCommonHSSFCellStyle(hssfWorkbook, false);//设置表头head是否有边框
                        titleStyle.setFont(font);
                        cell.setCellStyle(titleStyle);
                    } else {
                        HSSFFont font = hssfWorkbook.createFont();
                        font.setFontHeightInPoints((short) 14);//设置字体大小
                        row.setHeight((short) 500);//设置头部列的高度
                        //HSSFCellStyle titleStyle = setHSSFCellStyle(hssfWorkbook);
                        HSSFCellStyle titleStyle = setCommonHSSFCellStyle(hssfWorkbook, false);//设置表头head是否有边框
                        titleStyle.setFont(font);
                        cell.setCellStyle(titleStyle);
                    }
                    cell.setCellValue(cellValue);
                }
            }
            return hssfSheet;
        } else {
            boolean dataConfig = false;
            for (RowConfig config : rowConfigList) {
                if (!dataConfig) {
                    HSSFRow row = hssfSheet.createRow(config.getStartNum());
                    for (RowData rowData : config.getRowData()) {
                        if (StringUtils.isBlank(rowData.getValue())) {
                            dataConfig = true;
                            break;
                        }
                        if (!(rowData.getStartCell() == rowData.getEndCell() && rowData.getStartRow() == rowData.getEndRow())) {
                            hssfSheet.addMergedRegion(new CellRangeAddress(rowData.startRow, rowData.getEndRow(), rowData.getStartCell(), rowData.getEndCell()));
                        }
                        String cellValue = rowData.getValue();
                        Cell cell = row.createCell(rowData.getStartCell());
                        HSSFFont font = hssfWorkbook.createFont();
                        font.setFontHeightInPoints((short) 14);//设置字体大小
                        font.setFontName("宋体");
                        font.setBold(true);
                        HSSFCellStyle titleStyle = setCommonHSSFCellStyle(hssfWorkbook, true);
                        titleStyle.setFont(font);
                        cell.setCellStyle(titleStyle);
                        cell.setCellValue(cellValue);
                    }
                }
                //表格数据开始写入
                if (dataConfig) {
                    int startNum = config.getStartNum();
                    for (Map<String, Object> map : dataMapList) {
                        HSSFRow row = hssfSheet.createRow(startNum);
                        for (RowData rowData : config.getRowData()) {
                            String cellValue = MapUtils.getString(map, rowData.getValueOf());
                            Cell cell = row.createCell(rowData.getStartCell());
                            cell.setCellStyle(style);
                            cell.setCellValue(cellValue);
                        }
                        startNum++;
                    }
                }
            }
            return hssfSheet;
        }
    }

    private static ImportConfig parseImportConfig(Resource exportConfigName) throws DocumentException {
        ImportConfig importConfig = new ImportConfig();
        SAXReader reader = new SAXReader();
        try {
            Document doc = null;
            try {
                doc = reader.read(exportConfigName.getInputStream());
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            //解析配置文件
            Element node = doc.getRootElement();
            importConfig.setDataList(importConfig(node, "data"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return importConfig;
    }

    private static ExportConfig parseExportConfig(Resource exportConfigName) {
        ExportConfig exportConfig = new ExportConfig();
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(exportConfigName.getInputStream());
            //解析配置文件
            Element node = doc.getRootElement();
            exportConfig.setTitleRowConfigList(rowConfig(node, "title"));
            exportConfig.setHeadRowConfigList(rowConfig(node, "head"));
            exportConfig.setTableRowConfigList(rowConfig(node, "table"));
            exportConfig.setFootRowConfigList(rowConfig(node, "foot"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exportConfig;
    }

    private static List<Map<String, String>> importConfig(Element node, String name) {
        List<Element> resultList = node.elements(name);
        List<Map<String, String>> list = new ArrayList<>();
        for (Element dataResult : resultList) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("name", dataResult.attributeValue("name"));
            map.put("cell", dataResult.attributeValue("cell"));
            list.add(map);
        }

        return list;
    }

    private static List<RowConfig> rowConfig(Element node, String name) {

        List<Element> resultList = node.elements(name);
        List<RowConfig> rowConfigList = new ArrayList<RowConfig>();
        for (Element element : resultList) {
            List<Element> rowResultList = element.elements("row");
            for (Element rowResult : rowResultList) {
                RowConfig rowConfig = new RowConfig();
                rowConfig.setStartNum(Integer.parseInt(rowResult.attributeValue("start-num")));
                List<Element> dataResultList = rowResult.elements("data");
                List<RowData> rowDataList = new ArrayList<RowData>();
                for (Element dataResult : dataResultList) {
                    RowData rowData = new RowData();
                    rowData.setStartCell(Integer.parseInt(dataResult.attributeValue("start-cell")));
                    rowData.setEndCell(Integer.parseInt(dataResult.attributeValue("end-cell")));
                    rowData.setAlign(dataResult.attributeValue("align"));
                    if (!StringUtils.isBlank(dataResult.attributeValue("start-row"))) {
                        rowData.setStartRow(Integer.parseInt(dataResult.attributeValue("start-row")));
                    } else {
                        rowData.setStartRow(Integer.parseInt(rowResult.attributeValue("start-num")));
                    }
                    if (!StringUtils.isBlank(dataResult.attributeValue("end-row"))) {
                        rowData.setEndRow(Integer.parseInt(dataResult.attributeValue("end-row")));
                    } else {
                        rowData.setEndRow(Integer.parseInt(rowResult.attributeValue("start-num")));
                    }
                    if (!StringUtils.isBlank(dataResult.attributeValue("value"))) {
                        rowData.setValue(dataResult.attributeValue("value"));
                    }
                    if (!StringUtils.isBlank(dataResult.attributeValue("value-of"))) {
                        rowData.setValueOf(dataResult.attributeValue("value-of"));
                    }
                    rowDataList.add(rowData);
                }
                rowConfig.setRowData(rowDataList);
                rowConfigList.add(rowConfig);
            }
        }
        return rowConfigList;
    }


    private static class ImportConfig {

        private List<Map<String, String>> dataList;

        public List<Map<String, String>> getDataList() {
            return dataList;
        }

        public void setDataList(List<Map<String, String>> dataList) {
            this.dataList = dataList;
        }
    }

    private static class ExportConfig {

        private List<RowConfig> titleRowConfigList;

        private List<RowConfig> headRowConfigList;

        private List<RowConfig> tableRowConfigList;

        private List<RowConfig> footRowConfigList;

        public List<RowConfig> getTitleRowConfigList() {
            return titleRowConfigList;
        }

        public void setTitleRowConfigList(List<RowConfig> titleRowConfigList) {
            this.titleRowConfigList = titleRowConfigList;
        }

        public List<RowConfig> getHeadRowConfigList() {
            return headRowConfigList;
        }

        public void setHeadRowConfigList(List<RowConfig> headRowConfigList) {
            this.headRowConfigList = headRowConfigList;
        }

        public List<RowConfig> getTableRowConfigList() {
            return tableRowConfigList;
        }

        public void setTableRowConfigList(List<RowConfig> tableRowConfigList) {
            this.tableRowConfigList = tableRowConfigList;
        }

        public List<RowConfig> getFootRowConfigList() {
            return footRowConfigList;
        }

        public void setFootRowConfigList(List<RowConfig> footRowConfigList) {
            this.footRowConfigList = footRowConfigList;
        }
    }

    private static class RowConfig {

        private int startNum;

        private List<RowData> rowData;

        public int getStartNum() {
            return startNum;
        }

        public void setStartNum(int startNum) {
            this.startNum = startNum;
        }

        public List<RowData> getRowData() {
            return rowData;
        }

        public void setRowData(List<RowData> rowData) {
            this.rowData = rowData;
        }
    }

    private static class RowData {

        private String value;

        private String valueOf;

        private int startCell;

        private int endCell;

        private int startRow;

        private int endRow;

        private String align;

        public String getAlign() {
            return align;
        }

        public void setAlign(String align) {
            this.align = align;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getValueOf() {
            return valueOf;
        }

        public void setValueOf(String valueOf) {
            this.valueOf = valueOf;
        }

        public int getStartCell() {
            return startCell;
        }

        public void setStartCell(int startCell) {
            this.startCell = startCell;
        }

        public int getEndCell() {
            return endCell;
        }

        public void setEndCell(int endCell) {
            this.endCell = endCell;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public int getEndRow() {
            return endRow;
        }

        public void setEndRow(int endRow) {
            this.endRow = endRow;
        }
    }
}
