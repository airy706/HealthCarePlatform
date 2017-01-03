package com.nirvana.app.controller;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nirvana.app.vo.Result;
import com.nirvana.bll.service.AlarmDataService;
import com.nirvana.bll.service.LocationDataSerivce;
import com.nirvana.bll.service.NodeDataService;
import com.nirvana.bll.service.UserService;
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
	@Autowired
	private UserService userservicebo;

	@RequestMapping(value = "/uploadnode")
	public void uploadnode(HttpServletRequest request, HttpServletResponse response, @RequestParam("nodedata") String d)
			throws IOException {
		System.out.println("before:   "+d);
		String dd = URLDecoder.decode(d);
		System.out.println("after:   "+dd);
		NodeData data = new Gson().fromJson(d,NodeData.class);
		nodedatabo.addData(data);
		Result result = null;
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping(value = "/uploadloc")
	public void uploadloc(HttpServletRequest request, HttpServletResponse response, @RequestBody LocationData data)
			throws IOException {
		userservicebo.updateloc(data.getDid(), data.getLongtitude(), data.getLatitude(),data.getStatus_change_time());
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
