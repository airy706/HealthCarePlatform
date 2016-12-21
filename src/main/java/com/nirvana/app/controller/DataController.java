package com.nirvana.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nirvana.bll.service.NodeDataService;
import com.nirvana.dal.po.NodeData;

@RestController
@RequestMapping(value="/data")
public class DataController extends BaseController{
	@Autowired
	private NodeDataService nodedatabo;
	
	@RequestMapping(value="/upload/{jsonStr}")
	public void upload(@PathVariable(value="jsonStr")String jsonStr){
		NodeData data = new Gson().fromJson(jsonStr, NodeData.class);
		nodedatabo.addData(data);
	}
	
}
