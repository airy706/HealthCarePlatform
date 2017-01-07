package com.nirvana.app.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nirvana.app.vo.AlarmFilterVO;
import com.nirvana.app.vo.ExceptionVO;
import com.nirvana.app.vo.Result;
import com.nirvana.bll.service.AlarmDataService;

@RestController
@RequestMapping("/alarm")
public class AlarmController {

	@Autowired
	private AlarmDataService alarmservicebo;

	@RequestMapping("/resolved")
	public void resolved(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<ExceptionVO> list = alarmservicebo.findAllRedo();
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/detection")
	public void detection(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("exceptionId") Integer id) throws IOException {
		List<ExceptionVO> list = alarmservicebo.detect(id);
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/type")
	public void type(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<ExceptionVO> list = alarmservicebo.findAlltype();
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/times")
	public void times(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<ExceptionVO> list = alarmservicebo.findAllTimes();
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/filter")
	public void filter(HttpServletRequest request, HttpServletResponse response, @RequestParam("communityId") String id,
			@RequestParam("alarmType") String type, @RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime) throws IOException {
		String[] ids = id.split(",");
		String[] types = type.split(",");
		Date start = null;
		Date end = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		if(startTime==null||"".equals(startTime)){
			end = new Date();
			start = new Date();
			start.setTime(start.getTime()-7*24*60*60*1000);
		}else{
			try {
				start = sdf.parse(startTime);
				end = sdf.parse(endTime);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		List<AlarmFilterVO> list = alarmservicebo.findByFilter(ids,types,start,end);
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}
}
