package com.walmart.util;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.walmart.model.MyExcel;

public class Until {
	
	@SuppressWarnings("resource")
	public static boolean write(String outPath,List<MyExcel> list) throws Exception {
		String fileType = outPath.substring(outPath.lastIndexOf(".") + 1, outPath.length());
		System.out.println(fileType);
		// 创建工作文档对象
		Workbook wb = null;
		if (fileType.equals("xls")) {
			wb = new HSSFWorkbook();
		} else if (fileType.equals("xlsx")) {
			wb = new XSSFWorkbook();
		} else {
			System.out.println("您的文档格式不正确！");
			return false;
		}
		// 创建sheet对象
		Sheet sheet1 = (Sheet) wb.createSheet("sheet1");
		// 添加表头行
        Row hssfRow = sheet1.createRow(0);
        
        // 设置单元格格式居中
        CellStyle cellStyle =  wb.createCellStyle();
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        // 添加表头内容
        Cell headCell =  hssfRow.createCell(0);
        headCell.setCellValue("id");
        headCell.setCellStyle(cellStyle);
        
        headCell =  hssfRow.createCell(1);
        headCell.setCellValue("userId");
        headCell.setCellStyle(cellStyle);
        
        headCell =  hssfRow.createCell(2);
        headCell.setCellValue("userName");
        headCell.setCellStyle(cellStyle);
        
        headCell =  hssfRow.createCell(3);
        headCell.setCellValue("englishName");
        headCell.setCellStyle(cellStyle);
        
        headCell = hssfRow.createCell(4);
        headCell.setCellValue("number");
        headCell.setCellStyle(cellStyle);
        
        
        // 添加数据内容
        for (int i = 0; i < list.size(); i++) {
        	hssfRow = sheet1.createRow((int) i + 1);
        	MyExcel student = list.get(i);
        	// 创建单元格，并设置值
        	Cell cell =  hssfRow.createCell(0);
            cell.setCellValue(student.getId());
            cell.setCellStyle(cellStyle);
        	
            cell =  hssfRow.createCell(1);
            cell.setCellValue(student.getUserid());
            cell.setCellStyle(cellStyle);
            
            cell =  hssfRow.createCell(2);
            cell.setCellValue(student.getUsername());
            cell.setCellStyle(cellStyle);
            
            cell =  hssfRow.createCell(3);
            cell.setCellValue(student.getEnglishname());
            cell.setCellStyle(cellStyle);
            
            cell = hssfRow.createCell(4);
            cell.setCellValue(student.getNumber());
            cell.setCellStyle(cellStyle);
        }
		/*// 循环写入行数据
		for (int i = 0; i < 5; i++) {
			Row row = (Row) sheet1.createRow(i);
			// 循环写入列数据
			for (int j = 0; j < 8; j++) {
				Cell cell = row.createCell(j);
				cell.setCellValue("测试" + j);
			}
		}*/
		// 创建文件流
		OutputStream stream = new FileOutputStream(outPath);
		// 写入数据
		wb.write(stream);
		// 关闭文件流
		stream.close();
		return true;
	}

}
