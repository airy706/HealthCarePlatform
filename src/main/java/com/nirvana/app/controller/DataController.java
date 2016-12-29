package com.nirvana.app.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nirvana.app.vo.Result;
import com.nirvana.bll.service.AlarmDataService;
import com.nirvana.bll.service.LocationDataSerivce;
import com.nirvana.bll.service.NodeDataService;
import com.nirvana.dal.po.AlarmData;
import com.nirvana.dal.po.LocationData;
import com.nirvana.dal.po.NodeData;

@RestController
@RequestMapping(value = "/data")
public class DataController extends BaseController {
	@Autowired
	private NodeDataService nodedatabo;
	@Autowired
	private LocationDataSerivce locdatabo;
	@Autowired
	private AlarmDataService alarmdatabo;

	@RequestMapping(value = "/uploadnode")
	public void uploadnode(HttpServletRequest request, HttpServletResponse response, @RequestBody NodeData data)
			throws IOException {
		nodedatabo.addData(data);
		Result result = null;
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping(value = "/uploadloc")
	public void uploadloc(HttpServletRequest request, HttpServletResponse response, @RequestBody LocationData data)
			throws IOException {
		locdatabo.addData(data);
		Result result = null;
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping(value = "/uploadalarm")
	public void uploadalarm(HttpServletRequest request, HttpServletResponse response, @RequestBody AlarmData data)
			throws IOException {
		alarmdatabo.addData(data);
		Result result = null;
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

}
