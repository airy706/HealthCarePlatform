package com.nirvana.app.interceptor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AjaxInterceptor extends HandlerInterceptorAdapter {
//	static String Access_Control_Allow_Origin = "";
//	static String Access_Control_Allow_Credentials = "";
//	static String Access_Control_Allow_Methods = "";
//	static String Access_Control_Max_Age = "";
//	static String Access_Control_Allow_Headers = "";
//	static{
//				FileInputStream fis = null;
//				try {
//					fis = new FileInputStream("response.properties");
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				Properties pro = new Properties();
//				try {
//					pro.load(fis);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				Access_Control_Allow_Origin = pro.getProperty("Access-Control-Allow-Origin");
//				Access_Control_Allow_Credentials = pro.getProperty("Access-Control-Allow-Credentials");
//				Access_Control_Allow_Methods = pro.getProperty("Access-Control-Allow-Methods");
//				Access_Control_Max_Age = pro.getProperty("Access-Control-Max-Age");
//				Access_Control_Allow_Headers = pro.getProperty("Access-Control-Allow-Headers");
//				try {
//					fis.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//调试域
		//response.setHeader("Access-Control-Allow-Origin", "http://localhost:8081");
		//运行域
		//response.setHeader("Access-Control-Allow-Origin", "http://139.199.76.64");
		response.setHeader("Access-Control-Allow-Origin","http://mh100.com.cn" );
		response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept");
		return true;
	}
	
	
}
