package com.nirvana.app.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nirvana.app.vo.ConsultVO;
import com.nirvana.app.vo.ConsulttypeVO;
import com.nirvana.app.vo.Result;
import com.nirvana.app.vo.UserVO;
import com.nirvana.bll.service.ConsultService;
import com.nirvana.bll.service.ConsulttypeService;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.po.Consult;
import com.nirvana.dal.po.Consulttype;
import com.nirvana.dal.po.User;

@RestController
@RequestMapping("/consult")
public class ConsultController {

	@Autowired
	private ConsultService consultbo;

	@Autowired
	private ConsulttypeService typebo;

	@Autowired
	private UserService userservicebo;

	@RequestMapping("/type")
	public void type(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("communityId") Integer communityId, String key) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<ConsulttypeVO> list = consultbo.findAllTypeByCid(communityId, key);
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/typedel")
	public void typedel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("typeid") Integer typeid) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			typebo.delById(typeid);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/typecreate")
	public void create(HttpServletRequest request, HttpServletResponse response, Consulttype consulttype)
			throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			typebo.add(consulttype);
			result = Result.getSuccessInstance(null);
		}
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
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			Date now = new Date();
			User user = userservicebo.findById(userid);
			consult.setCommittime(now);
			consult.setIsfinish(false);
			consult.setUser(user);
			consultbo.addOne(consult);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/del")
	public void del(HttpServletRequest request, HttpServletResponse response, @RequestParam("consultId") Integer id)
			throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			consultbo.delById(id);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/finish")
	public void finish(HttpServletRequest request, HttpServletResponse response, @RequestParam("consultId") Integer id)
			throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			consultbo.finishByCid(id);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/edit")
	public void edit(HttpServletRequest request, HttpServletResponse response, Consult consult) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			consultbo.update(consult);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/undo")
	public void undo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<ConsultVO> list = consultbo.findUndoByUid(userid);
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/done")
	public void done(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<ConsultVO> list = consultbo.findDoneByUid(userid);
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key,
			@RequestParam("size") Integer size, @RequestParam("num") Integer num,
			@RequestParam("communityid") Integer communityid) throws IOException {
		Page<Consult> pages = consultbo.findByKey(communityid, key, num, size);
		List<ConsultVO> volist = new ArrayList<ConsultVO>();
		List<Consult> list = pages.getContent();
		for (Consult consult : list) {
			ConsultVO vo = new ConsultVO();
			vo.setConsultId(consult.getConsultid());
			vo.setUsername(consult.getUser().getUsername());
			vo.setConsultType(consult.getConsulttype().getTypename());
			vo.setContent(consult.getContent());
			vo.setToaskName(consult.getToask().getUsername());
			vo.setCommintTime(consult.getCommittime());
			vo.setFinish(consult.isIsfinish());
			if (consult.isIsfinish() == true) {
				vo.setFinishTime(consult.getFinishtime());
			}
			volist.add(vo);
		}
		Result result = null;
		result = Result.getSuccessInstance(volist);
		result.setMsg(pages.getTotalElements() + "");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

}
