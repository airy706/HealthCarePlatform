package com.nirvana.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nirvana.app.vo.Result;
import com.nirvana.app.vo.UserVO;
import com.nirvana.bll.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userservicebo;
	
	@RequestMapping("/online")
	public void online(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<UserVO> list = userservicebo.findOnline();
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}
}
