package com.nirvana.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nirvana.app.vo.NodeHomePageVO;
import com.nirvana.app.vo.Result;
import com.nirvana.app.vo.UserVO;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.po.User;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userservicebo;

	@RequestMapping("/online")
	public void online(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<UserVO> list = userservicebo.findOnline();
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/setf")
	public void setf(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
		userservicebo.setFrequency(user);
		Result result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/getf")
	public void getf(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key,
			@RequestParam("num") Integer num, @RequestParam("size") Integer size) throws IOException {
		Page<User> pages = userservicebo.findBykeypage(key, num, size);
		List<User> list = pages.getContent();
		List<UserVO> polist = new ArrayList<UserVO>();
		for (User user : list) {
			polist.add(new UserVO(user, 3));
		}
		//通过message传递总条数
		Result result = Result.getSuccessInstance(polist);
		result.setMsg(pages.getTotalElements()+"");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/home")
	public void home(HttpServletRequest request, HttpServletResponse response, @RequestParam("userid") Integer userid) throws IOException {
		List<NodeHomePageVO> list = userservicebo.findNodeDataByUid(userid);
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

}
