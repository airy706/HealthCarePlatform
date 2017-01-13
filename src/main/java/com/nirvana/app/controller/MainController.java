package com.nirvana.app.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nirvana.app.vo.*;
import com.nirvana.bll.service.*;
import com.nirvana.dal.po.*;

import org.dom4j.io.STAXEventReader;
import org.springframework.beans.factory.annotation.Autowired;
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
		Date end = new Date();
		Date start = new Date(end.getTime());
		start.setTime(start.getTime() - 7 * 24 * 60 * 60 * 1000);
//		Integer[] types = {6,12,99,7};
//		String[] dids={"111111"};
//		List<AlarmData> list = alarmdao.findFilter(Arrays.asList(types), Arrays.asList(dids), start, end);
		String[] ids={};
		String[] types={};
		AlarmFilterVO vo = alarmbo.findByFilter(ids, types, start, end);
		Result result = Result.getSuccessInstance(vo);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/register")
	public void register(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
		userbo.regist(user);
		Result result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response,@RequestParam("account") String account,@RequestParam("password") String password) throws IOException {
		Result result = null;
		UserVO vo = userbo.login(account, password);
		if (vo != null) {
			request.getSession().setAttribute("userid", vo.getUserid());
			result = Result.getSuccessInstance(vo);
			result.setMsg("common");
		} else {
			LinkManVO linkman= shipservicebo.findOneByAccountAndPsd(account,password);
			if(linkman==null){
			result = Result.getFailInstance("用户名或密码错误", null);
			}else{
				//将联系人的主人userid直接放入session中
				request.getSession().setAttribute("userid", linkman.getUserid());
				result = Result.getSuccessInstance(linkman);
				result.setMsg("link");
			}
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
		List<NoticeVO> list = noticebo.findAdmin();
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}
}
