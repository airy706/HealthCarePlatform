package com.nirvana.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nirvana.app.util.GsonUtils;
import com.nirvana.app.vo.*;
import com.nirvana.bll.service.*;
import com.nirvana.dal.po.*;

import org.apache.catalina.core.ApplicationContext;
import org.dom4j.io.STAXEventReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	private CommunityService communitybo;

	@Autowired
	private ConsultService consultbo;

	@Autowired
	private AlarmDataService alarmbo;

	@Autowired
	private ProductIntroService productIntro;

	@Autowired
	private SolutionCaseService solutionCase;

	@Autowired
	private NoticeService noticebo;

	@Autowired
	private NodeDataService dataservicebo;

	@Autowired
	private AlarmDataDao alarmdao;

	@Autowired
	private RelationshipService shipservicebo;

	@RequestMapping({ "/test" })
	public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Date end = new Date();
//		Date start = new Date(end.getTime());
//		start.setTime(start.getTime() - 7 * 24 * 60 * 60 * 1000);
		// Integer[] types = {6,12,99,7};
		// String[] dids={"111111"};
		// List<AlarmData> list = alarmdao.findFilter(Arrays.asList(types),
		// Arrays.asList(dids), start, end);
//		String[] ids = {};
//		String[] types = {};
//		AlarmFilterVO vo = alarmbo.findByFilter(ids, types, start, end);
		Page<User> pages = userbo.findBykeypage("", 1, 10,null);
		NodeListVO nodeListVO = new NodeListVO(pages);
		Result result = Result.getSuccessInstance(nodeListVO);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/register")
	public void register(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
		Result result = null;
		if (user.getUsername() == null || "".equals(user.getUsername().trim())) {
			result = Result.getFailInstance("用户名为空", null);
		} else if (user.getAccount() == null || "".equals(user.getAccount().trim())) {
			result = Result.getFailInstance("账户为空", null);
		} else if (user.getUseridentity() == null || "".equals(user.getUseridentity().trim())) {
			result = Result.getFailInstance("身份证为空", null);
		} else if (user.getPassword() == null || "".equals(user.getPassword().trim())) {
			result = Result.getFailInstance("密码为空", null);
		} else if (userbo.accountIsExist(user.getAccount())) {
			result = Result.getFailInstance("账户名已注册", null);
		} else if (userbo.didIsExist(user.getUseridentity())) {
			result = Result.getFailInstance("身份证已注册", null);
		} else {
			userbo.regist(user);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response, @RequestParam("account") String account,
			@RequestParam("password") String password) throws IOException {
		Result result = null;
		boolean flag = false;
		Integer userid = 0;
		UserVO vo = userbo.login(account, password);
		if (vo != null) {
			if (vo.getState() == 1) {
				userid = vo.getUserid();
				request.getSession().setAttribute("userid", userid);
				result = Result.getSuccessInstance(vo);
				result.setMsg("common");
				flag = true;
			} else {
				result = Result.getFailInstance("该账户被冻结", null);
			}
		} else {
			Relationship linkman = shipservicebo.findOneByAccountAndPsd(account, password);
			if (linkman == null) {
				result = Result.getFailInstance("用户名或密码错误", null);
			} else {
				// 将联系人的主人userid直接放入session中
				userid = linkman.getUser().getUserid();
				request.getSession().setAttribute("userid", userid);
				User u = linkman.getUser();
				UserVO uservo = new UserVO();
				uservo.setUserid(u.getUserid());
				uservo.setUsername(u.getUsername());
				uservo.setTypeid(u.getTypeid());
				if (u.getCommunity() != null) {
					uservo.setCommunityid(u.getCommunity().getCommunityid());
					uservo.setCommunityname(u.getCommunity().getCommunityname());
				}
				uservo.setState(u.getState());
				result = Result.getSuccessInstance(uservo);
				result.setMsg("link");
				flag = true;
			}
		}

		// ServletContext ctx = request.getServletContext();
		// ArrayList<HttpSession> sessions = (ArrayList<HttpSession>)
		// ctx.getAttribute("online");
		// if (sessions == null) {
		// sessions = new ArrayList<HttpSession>();
		// }
		//
		// if (flag == true) {
		// for (HttpSession session : sessions) {
		// System.out.println("userid:"+session.getAttribute("userid"));
		// if (userid==session.getAttribute("userid")) {
		// sessions.remove(session);
		// session.invalidate();
		// System.out.println("11111111111111");
		// break;
		// }
		// }
		// sessions.add(request.getSession());
		// ctx.setAttribute("online", sessions);
		// System.out.println("222222222");
		// }

		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/productintro")
	public void productintro(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<ProductIntroVO> list = productIntro.findShowProductIntro();
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/casesolution")
	public void casesolution(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<SolutionCaseVO> list = solutionCase.findShowSolutionCase();
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/broadcast")
	public void broadcast(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<NoticeVO> list = noticebo.findAdmin();
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}
}
