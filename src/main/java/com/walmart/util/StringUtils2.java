package com.walmart.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StringUtils2 {
	
	
	/*public static  int exportConfirmContent(String entityId, String path) throws ApiException, IOException {
		String content="0";//�ж���ʲô���͵�excel
		if (StringUtils.isNotBlank(path)) {
			 String postfix=path.substring(path.lastIndexOf(".") + 1, path.length());
			 //2003  excel
			 if ("xls".equals(postfix)) {
				 content=readXls(path,entityId);
	          } else if ("xlsx".equals(postfix)) {//2010 excel
	        	  content=readXlsx(path,entityId);
	          	 }
	       }else {
	    	   return content;
	       	}
		return content;
	}*/
	/**
	 * ��2003excel
	 * @param path  excel�ļ�·��
	 * @param entityId   ѧУid
	 * @return
	 */
	public static  String readXls(String path,String entityId) throws IOException{
	   InputStream is = new FileInputStream(path);
	   XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
       String contentIds="";
       // Read the Sheet
       for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
         //�ӵ�һ��sheet��ʼ������Ϊ��ֱ����һ��
    	  XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
          if (xssfSheet == null) {
             continue;
          }
           // Read the Row
         for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
              XSSFRow xssfRow = xssfSheet.getRow(rowNum);
              if (xssfRow != null && rowNum!=1 ) {
                 XSSFCell no = xssfRow.getCell(0);
                 contentIds += no.getStringCellValue().toString()+",";
                }
           }
        }
 
		return contentIds ;
	}
	/**
	 * ��2010excel
	 * @param path
	 * @param entityId
	 * @return
	 * @throws ApiException 
	 * @throws  
	 */
	public static String readXlsx(String path,String entityId) throws IOException{
		InputStream is = new FileInputStream(path);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		String contentIds="";
   	    	 // Read the Sheet
      	 	 for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
	         HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
	         if (hssfSheet == null) {
	             continue;
	         }
             // Read the Row
             for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                 HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null && rowNum!=1) {
                     //��rownum�еĵ�һ����Ԫ��
                     HSSFCell no = hssfRow.getCell(0);
                     contentIds+= no.getStringCellValue().toString()+",";
                 }
             }
        }
     
		return contentIds;
	}


}
