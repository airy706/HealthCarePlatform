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
import com.nirvana.app.util.GsonUtils;
import com.nirvana.app.vo.NoticeVO;
import com.nirvana.app.vo.Result;
import com.nirvana.bll.service.NoticeService;

import com.nirvana.dal.po.Notice;

@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {
	@Autowired
	private NoticeService noticeservicebo;

	@RequestMapping("/create")
	public void create(HttpServletRequest request, HttpServletResponse response, Notice notice) throws IOException {
		notice.setNoticedate(new Date());
		noticeservicebo.add(notice);
		Result result = null;
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/del")
	public void del(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id)
			throws IOException {
		noticeservicebo.delById(id);
		Result result = null;
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/all")
	public void listall(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<NoticeVO> list = noticeservicebo.findAllList();
		Result result = null;
		result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/admin")
	public void listadmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<NoticeVO> list = noticeservicebo.findAdmin();
		Result result = null;
		result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/community")
	public void listadmin(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id)
			throws IOException {
		List<NoticeVO> list = noticeservicebo.findByCommunityId(id);
		Result result = null;
		result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/search")
	public void search(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key) throws IOException {
		List<NoticeVO> list = noticeservicebo.findByTitleOrUn(key);
		Result result = null;
		result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}
}
