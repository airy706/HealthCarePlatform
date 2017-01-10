package com.nirvana.app.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nirvana.app.vo.AlarmFilterVO;
import com.nirvana.app.vo.CommunityVO;
import com.nirvana.app.vo.ExceptionVO;
import com.nirvana.app.vo.NodeHomePageVO;
import com.nirvana.app.vo.Result;
import com.nirvana.app.vo.UserVO;
import com.nirvana.bll.service.AlarmDataService;
import com.nirvana.bll.service.CommunityService;
import com.nirvana.bll.service.ConsultService;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.api.AlarmDataDao;
import com.nirvana.dal.api.NodeDataDao;
import com.nirvana.dal.api.UserDao;
import com.nirvana.dal.po.AlarmData;
import com.nirvana.dal.po.Consulttype;
import com.nirvana.dal.po.NodeData;
import com.nirvana.dal.po.User;

@RestController
public class MainController extends BaseController {
	@Autowired
	private UserService userbo;

	@Autowired
	private AlarmDataDao alarmDataDao;

	@Autowired
	private CommunityService communitybo;

	@Autowired
	private NodeDataDao nodeDataDao;

	@Autowired
	private ConsultService consultbo;

	@Autowired
	private AlarmDataService alarmbo;

	@RequestMapping({ "/test" })
	public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<UserVO> vo = consultbo.findAskByCid(1);
		Result result = Result.getSuccessInstance(vo);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/regist")
	public void regist(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
		userbo.regist(user);
		Result result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String account = request.getParameter("account");
		String password = request.getParameter("password");
		Result result = null;
		UserVO vo = userbo.login(account, password);
		if (vo != null) {
			result = Result.getSuccessInstance(vo);
		} else {
			result = Result.getFailInstance("用户名或密码错误", null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/productintro")
	public void productintro(HttpServletRequest request, HttpServletResponse response) {

	}

	@RequestMapping("/casesolution")
	public void casesolution(HttpServletRequest request, HttpServletResponse response) {

	}
}
