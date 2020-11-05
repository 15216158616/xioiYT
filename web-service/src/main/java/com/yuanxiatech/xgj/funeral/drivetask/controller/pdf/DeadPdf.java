package com.yuanxiatech.xgj.funeral.drivetask.controller.pdf;

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;
import com.yuanxiatech.xgj.core.font.Fonts;
import com.yuanxiatech.xgj.core.web.spring.view.AbstractItext7PdfView;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralDriveTask;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralStaff;
import com.yuanxiatech.xgj.funeral.drivetask.model.FuneralStaffTypeEnum;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class DeadPdf extends AbstractItext7PdfView {
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private DateFormat df2 = new SimpleDateFormat("MM 月dd 日HH 时");
    private DateFormat df3 = new SimpleDateFormat("yyyy 年MM 月dd 日 HH 时mm 分");
    private PdfFont font = newPdfFont(Fonts.WRYH);
    private Integer cellHeight = 25;
    private float fontSize = 10;

    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfDocument pdfDocument, PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        FuneralDriveTask driveTask= (FuneralDriveTask) map.get("driveTask");
        Table table = new Table(new float[]{67,67,67,67,67,67,67,67});

        Cell cell = new Cell(1, 8);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setHeight(50);
        Paragraph paragraph = new Paragraph( "乡镇（街道）死亡人员情况登记表").setFont(font).setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18).setFontColor(DeviceRgb.BLACK);
        cell.add(paragraph).setBorder(Border.NO_BORDER);
        table.addCell(cell);

        table.addCell(addBigCell(1,6,"").setBorder(Border.NO_BORDER));
        table.addCell(addCell("南殡号：").setBorder(Border.NO_BORDER));
        table.addCell(addCell("").setBorder(Border.NO_BORDER));

        table.addCell(addCell("逝者姓名"));
        table.addCell(addTextFullCell(driveTask.getDeadName()));
        table.addCell(addCell("性别"));
        table.addCell(addTextFullCell(driveTask.getDeadGenderDesc()));
        table.addCell(addCell("年龄"));
        table.addCell(addTextFullCell(String.valueOf(driveTask.getDeadAge())));
        table.addCell(addCell("职业"));
        table.addCell(addTextFullCell(driveTask.getDeadProfession()));

        table.addCell(addCell("详细地址").setHeight(40));
        table.addCell(addBigCell(1,7,driveTask.getDeadAddress()).setHeight(45));

        table.addCell(addCell("死亡时间"));
        table.addCell(addBigCell(1,2,driveTask.getDeathTime()!=null?df.format(driveTask.getDeathTime()):""));
        table.addCell(addCell("死亡地点"));
        table.addCell(addBigCell(1,4,driveTask.getDeathAddress()));
        table.addCell(addCell("死亡原因"));
        table.addCell(addBigCell(1,7,driveTask.getDeathCause()));
        table.addCell(addCell("丧属姓名"));
        table.addCell(addCell(driveTask.getRelationName()));
        table.addCell(addCell("电话"));
        table.addCell(addBigCell(1,2,driveTask.getRelationPhone()));
        table.addCell(addCell("与逝者关系"));
        table.addCell(addBigCell(1,2,driveTask.getDeadRelation()));
        table.addCell(addCell("接尸时间"));
        table.addCell(addBigCell(1,2,driveTask.getDeathTime()!=null?df2.format(driveTask.getDeathTime()):""));
        table.addCell(addBigCell(1,2,"接尸地点"));
        table.addCell(addBigCell(1,3,driveTask.getCollectAddress()));
        table.addCell(addCell("租何棺木"));
        table.addCell(addBigCell(1,7,driveTask.getCoffin()));
        table.addCell(addCell("大中客车"));
        table.addCell(addBigCell(1,7,driveTask.getPassengerCar()));
        table.addCell(addCell("处理情况"));
        table.addCell(addBigCell(1,7,driveTask.getHandlingInformation()).setHeight(40));
        table.addCell(addCell("备注"));
        table.addCell(addBigCell(1,7,driveTask.getRemark()).setHeight(40));
        table.addCell(addCell("报告人"));
        table.addCell(addBigCell(1,1,driveTask.getReportUser()));
        table.addCell(addCell("记录人"));
        table.addCell(addBigCell(1,2,driveTask.getRecordUser()));
        table.addCell(addCell("经办人"));
        table.addCell(addBigCell(1,2,driveTask.getOperator()));
        table.addCell(addBigCell(1,8,"南安市殡仪馆："+df3.format(new Date())));

        document.add(table);
    }

    private Cell addBigCell(Integer x,Integer y,String text) {
        if(text==null){
            text= "";
        }
        Cell cell = new Cell(x,y);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setHeight(cellHeight);
        Paragraph paragraph = new Paragraph( text).setFont(font).setTextAlignment(TextAlignment.CENTER)
                .setFontSize(fontSize).setFontColor(DeviceRgb.BLACK);
        cell.add(paragraph);
        paragraph.setFixedLeading(10f);
        return cell;
    }

    private Cell addCell(String text){
        if(text==null){
            text= "";
        }
        Cell cell = new Cell();
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setHeight(cellHeight);
        Paragraph paragraph = new Paragraph( text).setFont(font).setTextAlignment(TextAlignment.CENTER)
                .setFontSize(fontSize).setFontColor(DeviceRgb.BLACK);
        cell.add(paragraph);
        return cell;
    }

    //文字填满 换行
    private Cell addTextFullCell(String text){
        if(text==null){
            text= "";
        }
        Cell cell = new Cell();
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setHeight(cellHeight);
        Paragraph paragraph = new Paragraph(text).setFont(font).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize).setFontColor(DeviceRgb.BLACK);
        cell.add(paragraph).setPadding(5);
        paragraph.setFixedLeading(10f);
        return cell;
    }

    @Override
    protected Document newDocument(PdfDocument pdfDocument) {
        Document document = new Document(pdfDocument,  PageSize.A4);
        document.setMargins(0, 10, 0, 30);
        return document;
    }

}
