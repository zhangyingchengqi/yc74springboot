package com.yc.yc_spring_boot_starter_test1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.yc.IYc;
@SpringBootApplication
public class App implements CommandLineRunner{
	
	@Autowired
	private  IYc yc;
	
	
	public static void main(String[] args) {
		SpringApplication.run( App.class,args);  // 阻塞式
	}
	public void run(String... args) throws Exception {
		yc.sayHello();
	}
}
