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
import com.nirvana.app.vo.CommunityVO;
import com.nirvana.app.vo.Result;
import com.nirvana.bll.service.CommunityService;
import com.nirvana.dal.po.Community;

@RestController
@RequestMapping("/community")
public class CommunityController {
	@Autowired
	private CommunityService communityservicebo;

	@RequestMapping({ "/create", "/update" })
	public void create(HttpServletRequest request, HttpServletResponse response, Community community)
			throws IOException {
		boolean isSuc = communityservicebo.add(community);
		Result result = null;
		if (isSuc) {
			result = Result.getSuccessInstance(isSuc);
		} else {
			result = Result.getFailInstance("社区添加失败", isSuc);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping({ "/del" })
	public void del(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id)
			throws IOException {
		communityservicebo.delById(id);
		Result result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping({ "/search" })
	public void search(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key)
			throws IOException {
		List<CommunityVO> list = communityservicebo.findFuzzy(key);
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/all")
	public void all(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<CommunityVO> list = communityservicebo.findAll();
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}
}
