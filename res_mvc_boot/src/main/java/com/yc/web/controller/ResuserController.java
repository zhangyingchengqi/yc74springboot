package com.yc.web.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yc.bean.Resuser;
import com.yc.biz.ResuserBiz;
import com.yc.utils.Encrypt;
import com.yc.utils.YcConstants;
import com.yc.web.entity.JsonModel;

@RestController
public class ResuserController {
	@Autowired
	private ResuserBiz resuserBiz;
	
	@RequestMapping(value = "/logout", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonModel logout( JsonModel jm,HttpSession session)  {
		session.removeAttribute(   YcConstants.LOGINUSER );
		jm.setCode(1);
		return jm;
	}
	
	@RequestMapping(value = "/checkLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonModel checkLoginOp( JsonModel jm,HttpSession session) {
		if(   session.getAttribute(   YcConstants.LOGINUSER )==null){
			jm.setCode(0);
		}else{
			jm.setCode(1);
			Resuser user=(Resuser) session.getAttribute(   YcConstants.LOGINUSER );
			jm.setObj(   user );
		}
		return jm;
	}

	@RequestMapping(value = "/login", method = { RequestMethod.GET, RequestMethod.POST })
	public JsonModel loginOp(HttpSession session, JsonModel jm, String valcode, String username, String pwd) {
		if (valcode == null || "".equals(valcode)) {
			jm.setCode(0);
			jm.setMsg("验证码不能为空...");
			return jm;
		}
		String validateCode = (String) session.getAttribute("validateCode");
		if (!valcode.equalsIgnoreCase(validateCode)) { // equals -> 区分大小写
			jm.setCode(0);
			jm.setMsg("验证码输入错误...");
			return jm;
		}
		pwd = Encrypt.md5(pwd);   //加密
		Resuser u = new Resuser();
		u.setUsername(username);
		u.setPwd(pwd);
		Resuser users = resuserBiz.login(u);
		if (users != null) {
			session.setAttribute(YcConstants.LOGINUSER, users);   //保存这个用户:   在数据库中保存用户状态
			jm.setCode(1);
			// 在看地址
			if (session.getAttribute(YcConstants.LASTVISITEDADDR) != null) {
				jm.setUrl((String) session.getAttribute(YcConstants.LASTVISITEDADDR));
			} else {
				jm.setUrl(YcConstants.HOMEPAGE);  //没有历史页面，则登录成功后到首页
			}
		} else {
			jm.setCode(0);
			jm.setMsg("wrong username or password,please try again");
		}
		return jm;
	}
}
