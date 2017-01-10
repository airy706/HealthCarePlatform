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
import com.nirvana.bll.service.AlarmDataService;
import com.nirvana.bll.service.CommunityService;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.api.AlarmDataDao;
import com.nirvana.dal.api.NodeDataDao;
import com.nirvana.dal.api.UserDao;
import com.nirvana.dal.po.AlarmData;
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
	private AlarmDataService alarmbo;
	
	@RequestMapping({ "/test" })
	public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// User u = new User();
		// u.setUsername("test");
		// u.setPassword("123");
		// userbo.save(u);
		// response.setContentType("text/html;charset=utf-8");
		// response.getWriter().print("Congratulations!");
		// userbo.test(null);
		Date start = null;
		Date end = null;
		end = new Date();
		start = new Date(end.getTime());
		start.setTime(start.getTime() - 7 * 24 * 60 * 60 * 1000);
		//List<NodeHomePageVO> vo = userbo.findNodeDataByUid(1);
		//NodeData vo = nodeDataDao.findLatestByDidAndType("420105198311234245", 7, new PageRequest(0, 1,null)).getContent().get(0);
		String[] ids={};
		String[] types={};
		AlarmFilterVO vo = alarmbo.findByFilter(ids, types, start, end);
		//AlarmData vo = alarmDataDao.findLatest(6,"111111",new PageRequest(0, 1, null)).getContent().get(0);
		Result result = Result.getSuccessInstance(vo);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/login")
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Result result = null;
		User user = userbo.login(username, password);
		if (user != null) {
			result = Result.getSuccessInstance(user);
		} else {
			result = Result.getFailInstance("用户名或密码错误", user);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

}
