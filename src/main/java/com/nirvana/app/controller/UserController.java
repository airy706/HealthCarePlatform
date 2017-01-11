package com.nirvana.app.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.gson.Gson;
import com.nirvana.app.util.GsonUtils;
import com.nirvana.app.vo.NodeHomePageVO;
import com.nirvana.app.vo.Result;
import com.nirvana.app.vo.UserVO;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.po.User;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userservicebo;

	@RequestMapping("/online")
	public void online(HttpServletRequest request, HttpServletResponse response, Integer communityId)
			throws IOException {
		List<UserVO> list = userservicebo.findOnline(communityId);
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/setf")
	public void setf(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
		userservicebo.setFrequency(user);
		Result result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/getf")
	public void getf(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key,
			@RequestParam("num") Integer num, @RequestParam("size") Integer size, Integer communityId)
					throws IOException {
		Page<User> pages = userservicebo.findBykeypage(key, num, size, communityId);
		List<User> list = pages.getContent();
		List<UserVO> polist = new ArrayList<UserVO>();
		for (User user : list) {
			polist.add(new UserVO(user, 3));
		}
		// 通过message传递总条数
		Result result = Result.getSuccessInstance(polist);
		result.setMsg(pages.getTotalElements() + "");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/home")
	public void home(HttpServletRequest request, HttpServletResponse response, @RequestParam("userid") Integer userid)
			throws IOException {
		List<NodeHomePageVO> list = userservicebo.findNodeDataByUid(userid);
		Result result = Result.getSuccessInstance(list);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/detail")
	public void detail(HttpServletRequest request, HttpServletResponse response, @RequestParam("userid") Integer userid)
			throws IOException {
		UserVO vo = userservicebo.getDetailByUid(userid);
		Result result = Result.getSuccessInstance(vo);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/info")
	public void info(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			UserVO vo = userservicebo.findInfoByUid(userid);
			result = Result.getSuccessInstance(vo);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/psdcheck")
	public void psdcheck(HttpServletRequest request, HttpServletResponse response, String oldPassword)
			throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			boolean isTrue = userservicebo.checkPassword(userid, oldPassword);
			result = Result.getSuccessInstance(isTrue);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/edit")
	public void edit(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			userservicebo.updateinfo(userid, user);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/avatar")
	public void avatar(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//  创建一个通用的多部分解析器 ，用于解析SpringMVC的上下文  
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//  解析request，判断是否为MultipartFile类型数据,即多部分请求
		if (multipartResolver.isMultipart(request)) {
			//  转换成多部分request  
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			//  取得request中的所有文件名  
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				//  取得上传文件  
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {//  取得当前上传文件的文件名称  
					String myFileName = file.getOriginalFilename();
					//  如果名称不为“”,说明该文件存在，否则说明该文件不存在  
					if (myFileName.trim() != "") {
						//  重命名上传后的文件名  
						String fileName = new Date().getTime() + "_" + file.getOriginalFilename();
						/*
						 *    //定义上传路径 String path = "H:/" + fileName; File
						 *  localFile = new File(path); //
						 *  把文件拷贝到本地：transferTo（gest）将上传文件写到服务器指定文件上
						 *  file.transferTo(localFile);
						 */
						// 如果用的是Tomcat服务器，则文件会上传到\\%TOMCAT_HOME%\\webapps\\YourWebProject\\WEB-INF\\upload\\文件夹中  
						String realPath = request.getSession().getServletContext().getRealPath("/upload/avatar");
						File uploadfile = new File(realPath, fileName);
						//  不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉  
						FileUtils.copyInputStreamToFile(file.getInputStream(), uploadfile);
						String url = request.getServletContext().getContextPath() + "/upload/avatar/" + fileName;
						System.out.println(url);
						Result result = Result.getSuccessInstance(url);
						response.setContentType("text/html;charset=utf-8");
						response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
					}
				}
			}
		}
	}
}
