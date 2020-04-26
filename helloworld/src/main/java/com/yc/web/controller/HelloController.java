package com.yc.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yc.bean.User;

import ch.qos.logback.classic.Logger;

@Controller
public class HelloController {
	private static Log log=LogFactory.getLog(  HelloController.class  );
	
	
	@RequestMapping("/hello")
	public @ResponseBody String hello() {
		return "hello,world";
	}

	@RequestMapping("/index1")
	public String index1() {
		return "/index1.html";
	}

	@RequestMapping("/index3")
	public @ResponseBody Map<String, Object> index3() {
		// 如果返回的是内容，而不是视图名，则需在方法返回值上加上 @ResponseBody
		// ，如果返回值为string，则直接返回，如果不是，则使用 Jsckson序列化成json字符串后返回
		Map<String, Object> map = new HashMap<>();
		map.put("msg", "欢迎您");
		map.put("time", new Date());
		return map;
	}

	@RequestMapping(value = "/get/{id}")
	public @ResponseBody String getById(@PathVariable("id") Long id) {
		return "用户" + id;
	}

	@RequestMapping(path = "/user/**/{id}.json", method = RequestMethod.GET)
	public @ResponseBody List<String> getById2(@PathVariable("id") Long id) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			list.add(i + "");
		}
		return list;
	}

	/*
	 * 1。 先使用浏览器测试,因为浏览器不支持设置content-type,它为null , 所以异常. 2. 使用ajax来调用，并设置
	 * content-type 3. produces:指的是请求中的 Accept字段
	 */
	@PostMapping(value = "/consumes/test.json", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String testConsumes() {
		return "测试consumes";
	}

	@PostMapping(path = "/param/test.json", params = "action=save", headers = "uname=a")
	public @ResponseBody String saveUser(@RequestHeader HttpHeaders headers,User u) {
		log.info(  headers.get("uname")  );
		log.info( headers.get("Host"));
		log.info(    u);
		return "测试 params 及  headers";
	}

}
