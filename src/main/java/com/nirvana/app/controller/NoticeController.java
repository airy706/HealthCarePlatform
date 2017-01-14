package com.nirvana.app.controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
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
import com.nirvana.app.vo.NoticeVO;
import com.nirvana.app.vo.Result;
import com.nirvana.bll.service.NoticeService;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.po.Notice;
import com.nirvana.dal.po.User;

@RestController
@RequestMapping("/notice")
public class NoticeController extends BaseController {
	@Autowired
	private NoticeService noticeservicebo;

	@Autowired
	private UserService userservicebo;

	@RequestMapping("/create")
	public void create(HttpServletRequest request, HttpServletResponse response, Notice notice) throws IOException {
		//TODO
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			User user = userservicebo.findById(userid);
			if(user.getTypeid()==1){
				notice.setNoticetype(1);
			}else if(user.getTypeid()==2){
				notice.setNoticetype(2);
				notice.setCommunity(user.getCommunity());
			}else{
				
			}
			notice.setNoticedate(new Date());
			notice.setUser(user);
			noticeservicebo.add(notice);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/del")
	public void del(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id)
			throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			noticeservicebo.delById(id);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/all")
	public void listall(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<NoticeVO> list = noticeservicebo.findAllList();
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/admin")
	public void listadmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<NoticeVO> list = noticeservicebo.findAdmin();
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/community")
	public void listadmin(HttpServletRequest request, HttpServletResponse response, @RequestParam("id") Integer id)
			throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<NoticeVO> list = noticeservicebo.findByCommunityId(id);
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/search")
	public void search(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key,
			@RequestParam("num") Integer num, @RequestParam("size") Integer size,Integer communityId) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			Page<Notice> list = noticeservicebo.findByTitleOrUn(key, num, size,communityId);
			List<NoticeVO> volist = NoticeVO.toVoList(list.getContent());
			result = Result.getSuccessInstance(volist);
			result.setMsg(list.getTotalElements() + "");
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/user")
	public void user(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<NoticeVO> list = noticeservicebo.findNoticeByUid(userid);
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/upload")
	public void upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
						String realPath = request.getSession().getServletContext().getRealPath("/upload/notice");
						File uploadfile = new File(realPath, fileName);
						//  不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉  
						FileUtils.copyInputStreamToFile(file.getInputStream(), uploadfile);
						String url = request.getServletContext().getContextPath() + "/upload/notice/" + fileName;
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
