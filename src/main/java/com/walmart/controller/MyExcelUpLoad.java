package com.walmart.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.walmart.model.MyExcel;
import com.walmart.service.MyExcelService;
import com.walmart.util.POIUtil;
import com.walmart.util.StringUtils;

@Controller 
@RequestMapping("/pc")
public class MyExcelUpLoad {
	
	@Autowired
	MyExcelService myExcelService;
	
	@RequestMapping("/index")
	public String hello(){
		/*ArrayList<MyExcel> arrayList1 = StringUtils.ExcelIn();
		for (MyExcel myExcel : arrayList1) {
			System.out.println(myExcel);
		}*/
		return "index";
	}
	
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
	
	
	   
		
	 
		public static void main(String[] args) {
			
			//将数据导出到Excel中
			//ExcelBook book = new ExcelBook();
//			ArrayList<Book> arrayList = new ArrayList<Book>();
//			Book bo = new Book();
//			bo.setId(1);
//			bo.setName("酒馆");
//			bo.setType("生活");
	//
//			Book bo1 = new Book();
//			bo1.setId(2);
//			bo1.setName("酒馆1");
//			bo1.setType("生活1");
	//
//			arrayList.add(bo);
//			arrayList.add(bo1);
//			book.excelOut(arrayList);
			
			//将数据从Excel中导入
			ArrayList<MyExcel> arrayList1 = StringUtils.ExcelIn();
			for (MyExcel myExcel : arrayList1) {
				System.out.println(myExcel);
			}
			
		}

}
