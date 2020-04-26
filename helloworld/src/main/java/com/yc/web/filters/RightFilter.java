package com.yc.web.filters;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yc.web.controller.HelloController;

public class RightFilter implements HandlerInterceptor {
	private static Log log=LogFactory.getLog(  RightFilter.class  );
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		ServletContext application=request.getSession().getServletContext();
		Long count=new Long(0);
		if( application.getAttribute("count")!=null) {
			count=(Long) application.getAttribute("count");
		}
		count++;
		application.setAttribute("count", count);
		log.info("访问次数:"+ count);
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		log.info("postHandle");
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
		//controller处理完后调用
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		//页面渲染完后调用
		log.info("afterCompletion");
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}
}
