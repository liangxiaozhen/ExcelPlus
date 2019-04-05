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
 * ����excel����
 * 
 * @author Administrator
 *
 */
public abstract class ExcelReportHandler {

	/**
	 * Ĭ�ϵ�ͷ����ʽ
	 */
	protected HSSFCellStyle defaultHeadCellStyle;

	/**
	 * Ĭ�ϵ��岿��ʽ
	 */
	protected HSSFCellStyle defaultBodyCellStye;

	protected HSSFFont defaultHeadFont;

	protected HSSFFont defaultBodyFont;

	/**
	 * ����excel�ļ�
	 * 
	 * @param response
	 * @param headTitle ������
	 * @param fileName �ļ�����
	 * @param list ���ݼ���
	 * @throws Exception
	 */
	public void exportExcel(HttpServletResponse response, String[] headTitle,
			String fileName, List list) throws Exception {
		response.reset();
		response.setHeader("Content-disposition", "attachment; filename=\""
				+ fileName + ".xls\"");// ����response��Header
		ServletOutputStream out = response.getOutputStream();// �õ������
		HSSFWorkbook wb = new HSSFWorkbook();// ʵ��������������
		defaultHeadCellStyle = wb.createCellStyle();
		defaultBodyCellStye = wb.createCellStyle();
		defaultHeadFont = wb.createFont();
		defaultBodyFont = wb.createFont();
		HSSFSheet sheet = wb.createSheet();// ����sheet
		createHead(sheet, headTitle);// ����execlͷ
		createBody(sheet, list);// ����execl��
		wb.write(out);// �ѹ�����д�뵽�����
		out.flush();// ˢ����
		out.close();// �ر���
	}

	/**
	 * ����������
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
	 * Ĭ�ϵ�excelͷ��ʽ
	 * 
	 * @return
	 */
	protected HSSFCellStyle getHeadCellStyle() {
		defaultHeadCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// ���þ���
		defaultHeadFont.setFontHeightInPoints((short) 13);// ����������ʽ
		defaultHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// ������ʾ
		defaultHeadCellStyle.setFont(defaultHeadFont);
		return defaultHeadCellStyle;
	}

	/**
	 * ��ȡĬ�ϵ�excel����ʽ
	 * 
	 * @return
	 */
	protected HSSFCellStyle getBodyCellStyle() {
		defaultBodyCellStye.setAlignment(HSSFCellStyle.ALIGN_CENTER);// ���þ���
		defaultBodyFont.setFontHeightInPoints((short) 12);// ����������ʽ
		defaultBodyCellStye.setFont(defaultBodyFont);
		return defaultBodyCellStye;
	}

	/**
	 * ����excel������
	 * 
	 * @param sheet
	 * @param list
	 */
	public abstract void createBody(HSSFSheet sheet, List list);
}