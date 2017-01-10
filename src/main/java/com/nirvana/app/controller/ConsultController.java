package com.nirvana.app.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nirvana.app.vo.Result;
import com.nirvana.app.vo.UserVO;
import com.nirvana.bll.service.ConsultService;
import com.nirvana.dal.po.Consult;
import com.nirvana.dal.po.Consulttype;

@RestController
@RequestMapping("/consult")
public class ConsultController {

	@Autowired
	private ConsultService consultbo;

	@RequestMapping("/type")
	public void type(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("communityId") Integer communityId) throws IOException {
		List<Consulttype> list = consultbo.findAllTypeByCid(communityId);
		Result result = null;
		result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/toask")
	public void toask(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("communityId") Integer communityId) throws IOException {
		List<UserVO> list = consultbo.findAskByCid(communityId);
		Result result = null;
		result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/create")
	public void create(HttpServletRequest request, HttpServletResponse response, Consult consult) throws IOException {
		Date now = new Date();
		consult.setCommittime(now);
		consult.setIsfinish(false);
		consultbo.addOne(consult);
		Result result = null;
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("del")
	public void del(HttpServletRequest request, HttpServletResponse response, @RequestParam("consultId") Integer id) throws IOException {
		consultbo.delById(id);
		Result result = null;
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}
	
	@RequestMapping("/finish")
	public void finish(HttpServletRequest request, HttpServletResponse response, @RequestParam("consultId") Integer id) throws IOException{
		consultbo.finishByCid(id);
		Result result = null;
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

}
