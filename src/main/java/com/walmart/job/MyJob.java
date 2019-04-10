package com.walmart.job;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.walmart.model.MyExcel;
import com.walmart.service.MyExcelService;


@Component  //表示是一个配置文件
@EnableScheduling //快速开启任务调度功能
public class MyJob {
	
	 @Resource	
	 private MyExcelService myExcelService;
	 
	 //每30秒执行一次
    /*@Scheduled(fixedRate = 1000 * 3)
    public void reportCurrentTime(){
	   LocalDateTime localDateTime =LocalDateTime.now();
       System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }*/
	 //定时执行
	 @Scheduled(cron = "50 20 20 * * ?")
	 public void   reportCurrentByCron(){
        //获取当前时间
        LocalDateTime localDateTime =LocalDateTime.now();
        System.out.println("当前时间为:" + localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        MyExcel  myExcel = myExcelService.selectByPrimaryKey(Long.valueOf(37));
		System.out.println(myExcel.toString()); 
	 }
	
}

