package com.nirvana.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
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
	public void uploadnode(HttpServletRequest request, HttpServletResponse response, NodeData data) {
		nodedatabo.addData(data);
	}

	@RequestMapping(value = "/uploadloc")
	public void uploadloc(HttpServletRequest request, HttpServletResponse response, LocationData data) {
		locdatabo.addData(data);
	}

	@RequestMapping(value = "/uploadalarm")
	public void uploadalarm(HttpServletRequest request, HttpServletResponse response, AlarmData data) {
		alarmdatabo.addData(data);
	}

}
