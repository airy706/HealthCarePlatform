package com.nirvana.app.controller;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nirvana.app.vo.Result;
import com.nirvana.bll.service.AlarmDataService;
import com.nirvana.bll.service.LocationDataSerivce;
import com.nirvana.bll.service.NodeDataService;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.po.AlarmData;
import com.nirvana.dal.po.LocationData;
import com.nirvana.dal.po.NodeData;
/**
 * 数据视图转换层 
 * 接口处理可参考接口文档
 * @author Bin
 *
 */
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
		// String dd = URLDecoder.decode(d);
		System.out.println("json:   " + d);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		NodeData data = gson.fromJson(d, NodeData.class);
		Result result = null;
		if (data.getData() == null || "".equals(data.getData())) {
			result = Result.getFailInstance("上传失败", null);
		} else {
			nodedatabo.addData(data);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping(value = "/uploadloc")
	public void uploadloc(HttpServletRequest request, HttpServletResponse response, @RequestParam("nodedata") String d)
			throws IOException {
		// String dd = URLDecoder.decode(d);
		System.out.println("json:   " + d);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		LocationData data = gson.fromJson(d, LocationData.class);
		userservicebo.updateloc(data.getDid(), data.getLongtitude(), data.getLatitude(), data.getStatus_change_time());
		locdatabo.addData(data);
		Result result = null;
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping(value = "/uploadalarm")
	public void uploadalarm(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("nodedata") String d) throws IOException {
		// String dd = URLDecoder.decode(d);
		System.out.println("json:   " + d);
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		Result result = null;
		AlarmData data = gson.fromJson(d, AlarmData.class);
		if (data.getData() == null || "".equals(data.getData())) {
			result = Result.getFailInstance("上传失败", null);
		} else {
			alarmdatabo.addData(data);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

}
