package com.nirvana.app.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nirvana.app.vo.*;
import com.nirvana.bll.service.*;
import com.nirvana.dal.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nirvana.dal.api.AlarmDataDao;
import com.nirvana.dal.api.NodeDataDao;
import com.nirvana.dal.api.UserDao;

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

	@Autowired
	private ProductIntroService productIntro;

	@Autowired
	private SolutionCaseService solutionCase;

	@RequestMapping({ "/test" })
	public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Consulttype> vo = consultbo.findAllTypeByCid(1);
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
	public void productintro(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<ProductIntroVO> list = productIntro.findShowProductIntro();
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/casesolution")
	public void casesolution(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<SolutionCaseVO> list = solutionCase.findShowSolutionCase();
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/broadcast")
	public void broadcast(HttpServletRequest request, HttpServletResponse response) throws IOException {

	}
}
