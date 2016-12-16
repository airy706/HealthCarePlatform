package com.nirvana.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nirvana.bll.service.UserService;
import com.nirvana.dal.po.User;

@RestController
public class MainController extends BaseController {
	@Autowired
	private UserService userbo;

	@RequestMapping({ "/test" })
	public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		User u = new User();
		u.setUsername("test");
		u.setPassword("123");
		userbo.save(u);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print("Congratulations!");
	}

}
