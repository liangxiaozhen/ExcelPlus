package com.walmart.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.walmart.model.MyExcel;
import com.walmart.service.MyExcelService;
import com.walmart.util.POIUtil;
import com.walmart.util.Until;

@Controller 
@RequestMapping("/pc")
public class MyExcelUpLoad {
	
	@Autowired
	MyExcelService myExcelService;
	
	@RequestMapping("/index")
	public String hello() throws Exception{
		/*ArrayList<MyExcel> arrayList1 = StringUtils.ExcelIn();
		for (MyExcel myExcel : arrayList1) {
			System.out.println(myExcel);
		}*/
		List<MyExcel> list = myExcelService.selectAll();
		String path = "D:" + File.separator + "out3.xls";
		Until.write(path,list);
		return "index";
	}
	
	/**
	 * �ļ�����
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/download")
	public String download() throws Exception{
		/*ArrayList<MyExcel> arrayList1 = StringUtils.ExcelIn();
		for (MyExcel myExcel : arrayList1) {
			System.out.println(myExcel);
		}*/
		List<MyExcel> list = myExcelService.selectAll();
		String path = "D:" + File.separator + "out2.xlsx";
		Until.write(path,list);
		return "index";
	}
	
	/**
	 * �ļ��ϴ�
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file) throws IOException{
		List<MyExcel> list = POIUtil.readExcel(file);
		for (MyExcel strings : list) {
				//System.out.println(strings);
				int flag=myExcelService.insertSelective(strings);
				System.out.println(flag);
		}
		return null;
		
	}
	
	
	
	
	   
		
	 
		public static void main(String[] args) throws IOException {
			
			//�����ݵ�����Excel��
			//ExcelBook book = new ExcelBook();
//			ArrayList<Book> arrayList = new ArrayList<Book>();
//			Book bo = new Book();
//			bo.setId(1);
//			bo.setName("�ƹ�");
//			bo.setType("����");
	//
//			Book bo1 = new Book();
//			bo1.setId(2);
//			bo1.setName("�ƹ�1");
//			bo1.setType("����1");
	//
//			arrayList.add(bo);
//			arrayList.add(bo1);
//			
			
		}

}
