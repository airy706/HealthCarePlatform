package com.nirvana.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
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

}
