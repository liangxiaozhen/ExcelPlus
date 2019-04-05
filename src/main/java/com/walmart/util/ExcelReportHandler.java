package com.walmart.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * 导出excel数据
 * 
 * @author Administrator
 *
 */
public abstract class ExcelReportHandler {

	/**
	 * 默认的头部样式
	 */
	protected HSSFCellStyle defaultHeadCellStyle;

	/**
	 * 默认的体部样式
	 */
	protected HSSFCellStyle defaultBodyCellStye;

	protected HSSFFont defaultHeadFont;

	protected HSSFFont defaultBodyFont;

	/**
	 * 导出excel文件
	 * 
	 * @param response
	 * @param headTitle 标题列
	 * @param fileName 文件名称
	 * @param list 数据集合
	 * @throws Exception
	 */
	public void exportExcel(HttpServletResponse response, String[] headTitle,
			String fileName, List list) throws Exception {
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename=\""
				+ fileName + ".xls\"");// 设置response的Header
		ServletOutputStream out = response.getOutputStream();// 得到输出流
		HSSFWorkbook wb = new HSSFWorkbook();// 实例化工作薄对象
		defaultHeadCellStyle = wb.createCellStyle();
		defaultBodyCellStye = wb.createCellStyle();
		defaultHeadFont = wb.createFont();
		defaultBodyFont = wb.createFont();
		HSSFSheet sheet = wb.createSheet();// 创建sheet
		createHead(sheet, headTitle);// 创建execl头
		createBody(sheet, list);// 创建execl体
		wb.write(out);// 把工作薄写入到输出流
		out.flush();// 刷新流
		out.close();// 关闭流
	}

	/**
	 * 创建标题列
	 * 
	 * @param sheet
	 * @param headTitle
	 * @throws IOException
	 */
	protected void createHead(HSSFSheet sheet, String[] headTitle)
			throws IOException {
		Row row = sheet.createRow(0);
		Cell cell = null;
		for (int i = 0; i < headTitle.length; i++) {
			cell = row.createCell((short) i);
			cell.setCellValue(headTitle[i]);
			cell.setCellStyle(getHeadCellStyle());
		}
	}

	/**
	 * 默认的excel头样式
	 * 
	 * @return
	 */
	protected HSSFCellStyle getHeadCellStyle() {
		defaultHeadCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置居中
		defaultHeadFont.setFontHeightInPoints((short) 13);// 设置字体样式
		defaultHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
		defaultHeadCellStyle.setFont(defaultHeadFont);
		return defaultHeadCellStyle;
	}

	/**
	 * 获取默认的excel体样式
	 * 
	 * @return
	 */
	protected HSSFCellStyle getBodyCellStyle() {
		defaultBodyCellStye.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 设置居中
		defaultBodyFont.setFontHeightInPoints((short) 12);// 设置字体样式
		defaultBodyCellStye.setFont(defaultBodyFont);
		return defaultBodyCellStye;
	}

	/**
	 * 导出excel体内容
	 * 
	 * @param sheet
	 * @param list
	 */
	public abstract void createBody(HSSFSheet sheet, List list);
}