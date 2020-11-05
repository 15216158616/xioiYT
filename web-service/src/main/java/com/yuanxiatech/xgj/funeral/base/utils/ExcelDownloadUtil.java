package com.yuanxiatech.xgj.funeral.base.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * excel样式工具类
 *
 * Created by Guo Yanshan on 2019/10/30.
 */
public class ExcelDownloadUtil {

    //添加边框设置
    public static void download(HSSFWorkbook hssf, String fileName, HttpServletResponse response, HttpServletRequest request) {

        //生成浏览器下载
        //浏览器下载名称
        //解决其他浏览器中文名称乱码问题
        String agent = request.getHeader("User-Agent");
        try {
            fileName = DownLoadUtils.getFileName(agent, fileName);
            //通知浏览器文件的类型
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            //设置要下载的名称
            response.setHeader("Content-disposition", "attachment;fileName=" + fileName);
            //获取输出流
            OutputStream outputStream = response.getOutputStream();
            hssf.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setTitle(CellStyle cellStyle, List<String> headers, HSSFRow row) {
        for (int i = 0; i < headers.size(); i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            HSSFRichTextString text = new HSSFRichTextString(headers.get(i));
            cell.setCellValue(text);
        }
    }

}
