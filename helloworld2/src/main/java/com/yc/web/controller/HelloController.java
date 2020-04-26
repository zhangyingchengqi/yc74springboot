package com.yc.web.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.HelloApplication;

@Controller
public class HelloController {
	
	private static Log log=LogFactory.getLog(  HelloApplication.class  );
	
	@RequestMapping("/index1")   //访问路径为   /hello/index1.html
	public  String index1(   ){
		log.info("index1");
		return "/index1.html";   //此时返回的字符串代表一个在  页面地址，在   resources/templates 中查找 
	}

	
	@RequestMapping("/hello")
	public @ResponseBody String hello() {
		log.info("请求过来了");
		return "hello,world:";
	}
}
