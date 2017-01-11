package com.nirvana.app.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nirvana.app.vo.Result;
import com.nirvana.app.vo.UserVO;
import com.nirvana.bll.service.RelationshipService;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.po.Relationship;
import com.nirvana.dal.po.User;

@RestController
public class RelationshipController {

	@Autowired
	private RelationshipService shipservicebo;
	
	@Autowired
	private UserService userservicebo;
	
	@RequestMapping("/create")
	public void create(HttpServletRequest request,HttpServletResponse response,Relationship ship) throws IOException{
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			User user = userservicebo.findById(userid);
			ship.setUser(user);
			shipservicebo.add(ship);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}
	
	
	
}
