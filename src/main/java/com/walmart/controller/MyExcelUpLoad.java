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
		String path = "D:" + File.separator + "out4.xls";
		Until.write(path,list);
		return "index";
	}
	
	/**
	 * 文件下载  把数据库数据导出为Excel 文件
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
	 * 文件上传 Excel的上传，并存进去数据库
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
			/* SchedulerFactory sf = new StdSchedulerFactory(); 
			 try { 
				 Scheduler sched = sf.getScheduler(); 
				 Date nowTime = new Date(); 
				 System.out.println(nowTime+"开始时间"); 
				 //将分秒数进位取整nowTime=15:40:28 runTime=15:41:00 
				 Date runTime = DateBuilder.evenMinuteDate(nowTime);
				 System.out.println(runTime+"结束时间"); 
				 JobDetail job = JobBuilder.newJob(HelloJob.class).withIdentity("job1", "group1").build(); 
				 //单次定时任务 //Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startAt(runTime).build(); 
				 //多次循环定时任务"0/20 * * * * ?"从*年*月*日  *：*：00秒开始每20秒执行一次 
				 CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").withSchedule(CronScheduleBuilder.cronSchedule("00 35 22 * * ?")) .build(); 
				 Date ft = sched.scheduleJob(job, trigger); 
				 System.out.println(job.getKey() + " has been scheduled to run at: " + ft + " and repeat based on expression: " + trigger.getCronExpression());
				 sched.start(); 
				 Thread.sleep(60000*5); 
				 sched.shutdown(true); 
				 }catch (Exception e) {
					 System.out.println(e.getMessage());
				}*/
		}
	


}
