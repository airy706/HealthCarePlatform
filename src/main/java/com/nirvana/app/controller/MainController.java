package com.nirvana.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nirvana.app.vo.CommunityVO;
import com.nirvana.app.vo.ExceptionVO;
import com.nirvana.app.vo.Result;
import com.nirvana.bll.service.AlarmDataService;
import com.nirvana.bll.service.CommunityService;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.po.User;

@RestController
public class MainController extends BaseController {
	@Autowired
	private UserService userbo;
	
	@Autowired
	private AlarmDataService alarmbo;
	
	@Autowired
	private CommunityService communitybo;

	@RequestMapping({ "/test" })
	public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// User u = new User();
		// u.setUsername("test");
		// u.setPassword("123");
		// userbo.save(u);
		// response.setContentType("text/html;charset=utf-8");
		// response.getWriter().print("Congratulations!");
		// userbo.test(null);
		List<ExceptionVO> list = alarmbo.findAllTimes();
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Result result= null;
		User user = userbo.login(username, password);
		if (user != null) {
			result = Result.getSuccessInstance(user);
		}else{
			result = Result.getFailInstance("用户名或密码错误", user);
		}
		 response.setContentType("text/html;charset=utf-8");
		 response.getWriter().print(new Gson().toJson(result));
	}

}
