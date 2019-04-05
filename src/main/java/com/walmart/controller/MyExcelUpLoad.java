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
	 * 文件下载
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
	 * 文件上传
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
//			
			
		}

}
