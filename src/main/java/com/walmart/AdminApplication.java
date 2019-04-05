package com.walmart;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;


@MapperScan("com.walmart.dao")
@SpringBootApplication
@Controller
public class AdminApplication {

 
	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

    
}