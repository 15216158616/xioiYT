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
import java.util.List;
import java.util.Map;


public class TaskPdf extends AbstractItext7PdfView {
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private PdfFont font = newPdfFont(Fonts.WRYH);
    private Integer cellHeight = 25;
    private float fontSize = 10;

    @Override
    protected void buildPdfDocument(Map<String, Object> map, Document document, PdfDocument pdfDocument, PdfWriter pdfWriter,
                                    HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        List<FuneralDriveTask> driveTaskList= (List<FuneralDriveTask>) map.get("driveTasks");
        Table table = new Table(new float[]{67,67,67,67,67,67,67,67});

        Cell cell = new Cell(1, 8);
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setHeight(50);
        Paragraph paragraph = new Paragraph( "南安市殡仪馆接尸登记表").setFont(font).setTextAlignment(TextAlignment.CENTER)
                .setFontSize(18).setFontColor(DeviceRgb.BLACK);
        cell.add(paragraph).setBorder(Border.NO_BORDER);
        table.addCell(cell);

        table.addCell(addCell("接尸地址"));
        table.addCell(addCell("车型"));
        table.addCell(addCell("联系人"));
        table.addCell(addCell("联系电话"));
        table.addCell(addCell("接尸时间"));
        table.addCell(addCell("棺木"));
        table.addCell(addCell("工人"));
        table.addCell(addCell("备注"));

        for (FuneralDriveTask driveTask : driveTaskList) {
            String staffName = "";
            if(driveTask.getStaffList()!=null){
                for (FuneralStaff staff : driveTask.getStaffList()) {
                    staffName+=staffName.equals("")?staff.getName():","+staff.getName();
                }
            }
            table.addCell(addTextFullCell(driveTask.getCollectAddress()));
            table.addCell(addTextFullCell(driveTask.getCar()!=null?driveTask.getCar().getCarType():""));
            table.addCell(addTextFullCell(driveTask.getRelationName()));
            table.addCell(addTextFullCell(driveTask.getRelationPhone()));
            table.addCell(addTextFullCell(driveTask.getTaskTime()!=null?df.format(driveTask.getTaskTime()):""));
            table.addCell(addTextFullCell(driveTask.getCoffin()));
            table.addCell(addTextFullCell(staffName));
            table.addCell(addTextFullCell(driveTask.getRemark()));
        }
        for (int i=driveTaskList.size();i<7;i++) {
            table.addCell(addTextFullCell(""));
            table.addCell(addTextFullCell(""));
            table.addCell(addTextFullCell(""));
            table.addCell(addTextFullCell(""));
            table.addCell(addTextFullCell(""));
            table.addCell(addTextFullCell(""));
            table.addCell(addTextFullCell(""));
            table.addCell(addTextFullCell(""));
        }
        document.add(table);
    }

    private Cell addBigCell(Integer x,Integer y,String text) {
        if(text==null){
            text="";
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
            text="";
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
            text="";
        }
        Cell cell = new Cell();
        cell.setVerticalAlignment(VerticalAlignment.MIDDLE);
        cell.setHeight(cellHeight);
        Paragraph paragraph = new Paragraph(text).setFont(font).setTextAlignment(TextAlignment.LEFT).setFontSize(fontSize).setFontColor(DeviceRgb.BLACK);
        cell.add(paragraph).setPadding(1);
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
