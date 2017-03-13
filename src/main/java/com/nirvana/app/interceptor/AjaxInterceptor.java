package com.nirvana.app.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AjaxInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Integer userId=(Integer)request.getSession().getAttribute("userid");
		System.out.println("userId:"+userId);
		request.setAttribute("userId", userId);
		//调试域
		response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
		//运行域
		//response.setHeader("Access-Control-Allow-Origin", "http://139.199.76.64");
		//response.setHeader("Access-Control-Allow-Origin", "http://mh100.com.cn");
		response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
		return true;
	}
	
	
}
