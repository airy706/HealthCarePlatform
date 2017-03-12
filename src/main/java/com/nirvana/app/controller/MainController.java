package com.nirvana.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nirvana.app.util.GsonUtils;
import com.nirvana.app.util.SendUtils;
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

	
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
		request.getSession().removeAttribute("userid");
		Integer userid=(Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if(userid==null){
			result = Result.getSuccessInstance(null);
			result.setMsg("注销成功");
		}else{
			result = Result.getFailInstance("注销失败", null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}
	
	@RequestMapping({ "/test" })
	public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String words = "";
		Random random=  new Random();
		for (int i = 0; i < 6; i++) {
			int r = random.nextInt(10);
			words = words + r + "";
		}
		SendUtils.send_tel("13797073054", words);
		Result result = Result.getSuccessInstance(null);
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
	public void broadcast(HttpServletRequest request, HttpServletResponse response,@RequestParam("num") Integer num,@RequestParam("size") Integer size) throws IOException {
		List<NoticeVO> list = noticebo.findAdmin(num,size);
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}
}
