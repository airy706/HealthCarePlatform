package com.nirvana.app.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nirvana.app.util.GsonUtils;
import com.nirvana.app.vo.NodeListVO;
import com.nirvana.app.vo.Result;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.po.User;

@RestController
@RequestMapping("/node")
public class NodeController extends BaseController {
	@Autowired
	private UserService userservicebo;

	@RequestMapping("/search")
	public void search(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key,
			@RequestParam("num") Integer num, @RequestParam("size") Integer size) throws IOException {
		Page<User> pages = userservicebo.findBykeypage(key, num, size);
		NodeListVO nodeListVO = new NodeListVO(pages);
		Result result = null;
		result = Result.getSuccessInstance(nodeListVO);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}
}
