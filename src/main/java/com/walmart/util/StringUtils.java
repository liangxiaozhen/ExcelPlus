package com.walmart.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.walmart.model.MyExcel;

public class StringUtils {
	
	 //�����ݵ�����Excel
	public static void excelOut(ArrayList<MyExcel> arrayList) throws IOException {
		WritableWorkbook bWorkbook = null;
		try {
			// ����Excel����
			bWorkbook = Workbook.createWorkbook(new File("D:/book.xls"));
			// ͨ��Excel���󴴽�һ��ѡ�����
			WritableSheet sheet = bWorkbook.createSheet("sheet1", 0);
			//ʹ��ѭ�������ݶ���
			for (int i = 0; i < arrayList.size(); i++) {
				MyExcel book=arrayList.get(i);
				Label label=new Label(0,i,String.valueOf(book.getId()));
				Label label1=new Label(1,i,String.valueOf(book.getUsername()));
				Label label2=new Label(2,i,String.valueOf(book.getEnglishname()));
				Label label3=new Label(3,i,String.valueOf(book.getNumber()));
				sheet.addCell(label);
				sheet.addCell(label1);
				sheet.addCell(label2);
				sheet.addCell(label3);
			}
			
			
			// ����һ����Ԫ����󣬵�һ��Ϊ�У��ڶ���Ϊ�У�������Ϊֵ
			Label label = new Label(0, 2, "test");
			// �������õĵ�Ԫ�����ѡ���
			//sheet.addCell(label);
			// д��Ŀ��·��
			bWorkbook.write();
 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			bWorkbook.close();
			
		}
 
	}
	
	
	//��Excel�е����ݵ���
	public static ArrayList<MyExcel> ExcelIn(){
		ArrayList<MyExcel>arrayList=new ArrayList<MyExcel>();
		Workbook bWorkbook=null;
		try {
			bWorkbook=Workbook.getWorkbook(new File("D:/books.xls"));
			Sheet sheet=bWorkbook.getSheet(0);
			for (int i = 0; i < sheet.getRows(); i++) {
				MyExcel book=new MyExcel();
				//��ȡ��Ԫ�����:��ȡ����Ԫ�����ж�����MyExcel [id=null, username=username, englishname=englishname, number=englishname]
				Cell cell =sheet.getCell(0,i);
				//��ȡ��Ԫ���ֵ��ȡ�������ֵMyExcel [id=null, username=��ѧ��1, englishname=denny1, number=denny1]
				//book.setId(cell.getContents());
				book.setUsername(sheet.getCell(1,i).getContents());
				book.setEnglishname(sheet.getCell(2, i).getContents() );
				book.setNumber(sheet.getCell(2, i).getContents() );
				arrayList.add(book);
				
			}
			
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			bWorkbook.close();
		}
		return arrayList;
	}
	
	
/*    public static void writer(String path, String fileName,String fileType) throws IOException {  
        //���������ĵ�����   
        Workbook wb = null;  
        if (fileType.equals("xls")) {  
            wb = new HSSFWorkbook();  
        }  
        else if(fileType.equals("xlsx"))  
        {  
            wb = new XSSFWorkbook();  
        }  
        else  
        {  
            System.out.println("�����ĵ���ʽ����ȷ��");  
        }  
        //����sheet����   
        Sheet sheet1 = (Sheet) wb.createSheet("sheet1");  
        //ѭ��д��������   
        for (int i = 0; i < 5; i++) {  
            Row row = (Row) sheet1.createRow(i);  
            //ѭ��д��������   
            for (int j = 0; j < 8; j++) {  
                Cell cell = row.createCell(j);  
                cell.setCellValue("����"+j);  
            }  
        }  
        //�����ļ���   
        OutputStream stream = new FileOutputStream(path+fileName+"."+fileType);  
        //д������   
        wb.write(stream);  
        //�ر��ļ���   
        stream.close();  
    }  
    public static void read(String path,String fileName,String fileType) throws IOException  
    {  
        InputStream stream = new FileInputStream(path+fileName+"."+fileType);  
        Workbook wb = null;  
        if (fileType.equals("xls")) {  
            wb = new HSSFWorkbook(stream);  
        }  
        else if (fileType.equals("xlsx")) {  
            wb = new XSSFWorkbook(stream);  
        }  
        else {  
            System.out.println("�������excel��ʽ����ȷ");  
        }  
        Sheet sheet1 = wb.getSheetAt(0);  
        for (Row row : sheet1) {  
            for (Cell cell : row) {  
                System.out.print(cell.getStringCellValue()+"  ");  
            }  
            System.out.println();  
        }  
    }*/

}
