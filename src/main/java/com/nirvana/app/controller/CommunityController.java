package com.nirvana.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nirvana.app.util.GsonUtils;
import com.nirvana.app.vo.CommunityVO;
import com.nirvana.app.vo.Result;
import com.nirvana.app.vo.UserVO;
import com.nirvana.bll.service.CommunityService;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.po.Community;

@RestController
@RequestMapping("/community")
public class CommunityController extends BaseController {
	@Autowired
	private CommunityService communityservicebo;

	@Autowired
	private UserService userservicebo;

	@RequestMapping("/userlist")
	public void userlist(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("communityid") Integer communityid) throws IOException {
		List<UserVO> volist = userservicebo.findAllByCid(communityid);
		Result result = null;
		result = Result.getSuccessInstance(volist);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/location")
	public void location(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("communityid") Integer communityid) throws IOException {
		Community po = communityservicebo.findById(communityid);
		Result result = null;
		if (po == null) {
			result = Result.getFailInstance("无此社区", null);
		} else {
			CommunityVO vo = new CommunityVO();
			vo.setLatitude(po.getLatitude());
			vo.setLongtitude(po.getLongtitude());
			result = Result.getSuccessInstance(vo);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/manager")
	public void manager(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key)
			throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<UserVO> list = userservicebo.findManagersByKey(key);
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/create")
	public void create(HttpServletRequest request, HttpServletResponse response, Community community)
			throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			communityservicebo.add(community);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping({ "/del" })
	public void del(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id)
			throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			communityservicebo.delById(id);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping({ "/search" })
	public void search(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key)
			throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<CommunityVO> list = communityservicebo.findFuzzy(key);
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/all")
	public void all(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<CommunityVO> list = communityservicebo.findAll();
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}
	
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<CommunityVO> list = communityservicebo.findAllNotEmpty();
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}
	
}
