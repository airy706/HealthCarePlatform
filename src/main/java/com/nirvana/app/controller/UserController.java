package com.nirvana.app.controller;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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
import com.nirvana.app.util.EmailUtils;
import com.nirvana.app.util.GsonUtils;
import com.nirvana.app.vo.NodeHomePageVO;
import com.nirvana.app.vo.NodeVO;
import com.nirvana.app.vo.Result;
import com.nirvana.app.vo.UserVO;
import com.nirvana.bll.service.AlarmDataService;
import com.nirvana.bll.service.NodeService;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.po.AlarmData;
import com.nirvana.dal.po.User;

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	@Autowired
	private UserService userservicebo;

	@Autowired
	private NodeService nodeservicebo;

	@Autowired
	private AlarmDataService alarmservice;

	@RequestMapping("/logincheck")
	public void logincheck(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("请输入用户名密码登录", null);
		} else {
			User user = userservicebo.findById(userid);
			if (user == null) {
				result = Result.getFailInstance("请输入用户名密码登录", null);
			} else {
				UserVO vo = new UserVO();
				vo.setUserid(user.getUserid());
				vo.setUsername(user.getUsername());
				vo.setTypeid(user.getTypeid());
				// 防止nullpoint
				if (user.getCommunity() != null) {
					vo.setCommunityid(user.getCommunity().getCommunityid());
					vo.setCommunityname(user.getCommunity().getCommunityname());
				}
				vo.setState(user.getState());
				result = Result.getSuccessInstance(vo);
				result.setMsg("已登录");
			}
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/frozen")
	public void frozen(HttpServletRequest request, HttpServletResponse response, @RequestParam("userid") Integer userid)
			throws IOException {
		userservicebo.frozen(userid);
		Result result = null;
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/recovery")
	public void recovery(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("userid") Integer userid) throws IOException {
		userservicebo.recovery(userid);
		Result result = null;
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/registerdel")
	public void registerdel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("userid") Integer userid) throws IOException {
		userservicebo.delByUid(userid);
		Result result = null;
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/registeredit")
	public void registeredit(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
		userservicebo.updateregister(user);
		Result result = null;
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/search")
	public void search(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key,
			@RequestParam("size") Integer size, @RequestParam("num") Integer num) throws IOException {
		Result result = null;
		Page<User> page = userservicebo.findRegisterByKey(key, size, num);
		List<User> polist = page.getContent();
		List<UserVO> volist = new ArrayList<UserVO>();
		for (User user : polist) {
			UserVO vo = new UserVO(user, 4);
			volist.add(vo);
		}
		result = Result.getSuccessInstance(volist);
		result.setMsg(page.getTotalElements() + "");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/checkcode")
	public void checkcode(HttpServletRequest request, HttpServletResponse response, @RequestParam("did") String did,
			@RequestParam("code") String code) throws IOException {
		User user = userservicebo.findByDid(did);
		Result result = null;
		if (user == null) {
			result = Result.getFailInstance("无此用户", null);
		} else {
			String words = (String) request.getServletContext().getAttribute(did);
			if (code.equals(words)) {
				result = Result.getSuccessInstance(null);
				result.setMsg("验证成功");
				request.removeAttribute(did);
				request.getSession().setAttribute("userid", user.getUserid());
			} else {
				result = Result.getFailInstance("验证码错误", null);
			}
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/psdback")
	public void psdback(HttpServletRequest request, HttpServletResponse response, @RequestParam("did") String did,
			@RequestParam("way") String way) throws IOException {
		User user = userservicebo.findByDid(did);
		Result result = null;
		if (user == null) {
			result = Result.getFailInstance("无此用户", null);
		} else {
			Random random = new Random();
			String words = "";
			for (int i = 0; i < 6; i++) {
				int r = random.nextInt(10);
				words = words + r + "";
			}
			if ("email".equals(way)) {
				if (user.getUseremail() == null || "".equals(user.getUseremail())) {
					result = Result.getFailInstance("无邮箱", null);
				} else {
					request.getServletContext().setAttribute(user.getUseridentity(), words);
					EmailUtils.send_common(user.getUseremail(), words);
					result = Result.getSuccessInstance(null);
					result.setMsg("发送成功");
				}
			} else if ("tel".equals(way)) {
				if (user.getUsertel() == null || "".equals(user.getUsertel())) {
					result = Result.getFailInstance("无手机号", null);
				} else {
					request.getServletContext().setAttribute(user.getUseridentity(), words);
					// 发送短信
					result = Result.getSuccessInstance(null);
					result.setMsg("发送成功");
				}
			} else {
				result = Result.getFailInstance("错误发送方式", null);
			}
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/undoalarm")
	public void undoalarm(HttpServletRequest request, HttpServletResponse response, @RequestParam("did") String did)
			throws IOException {
		List<AlarmData> volist = alarmservice.findUndoByDid(did);
		Result result = null;
		result = Result.getSuccessInstance(volist);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/mobilelogin")
	public void mobilelogin(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("account") String account, @RequestParam("password") String password) throws IOException {
		UserVO vo = userservicebo.commonlogin(account, password);
		Result result = null;
		if (vo == null) {
			result = Result.getFailInstance("用户名或密码错误", null);
		} else {
			if (vo.getState() == 1) {
				result = Result.getSuccessInstance(vo);
				result.setMsg("登陆成功");
			} else {
				result = Result.getFailInstance("该用户已冻结", null);
			}
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/delmanager")
	public void delmanager(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("userid") Integer userid) throws IOException {
		Integer u = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (u == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			userservicebo.delByUid(userid);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/createmanager")
	public void createmanager(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			user.setTypeid(2);
			user.setRegisttime(new Date());
			user.setState(1);
			userservicebo.add(user);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/editmanager")
	public void editmanager(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
		Integer u = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (u == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			User newuser = userservicebo.findById(user.getUserid());
			newuser.setAccount(user.getAccount());
			newuser.setCommunity(user.getCommunity());
			newuser.setUsername(user.getUsername());
			userservicebo.add(newuser);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/frequency")
	public void frequency(HttpServletRequest request, HttpServletResponse response, @RequestParam("did") String did)
			throws IOException {
		Integer f = userservicebo.getFrequencyByDid(did);
		Result result = Result.getSuccessInstance(null);
		result.setMsg(f + "");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/node")
	public void node(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<NodeVO> list = nodeservicebo.findAllByUid(userid);
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/online")
	public void online(HttpServletRequest request, HttpServletResponse response, Integer communityId)
			throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<UserVO> list = userservicebo.findOnline(communityId);
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/setf")
	public void setf(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			userservicebo.setFrequency(user);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/getf")
	public void getf(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key,
			@RequestParam("num") Integer num, @RequestParam("size") Integer size, Integer communityId)
					throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			Page<User> pages = userservicebo.findBykeypage(key, num, size, communityId);
			List<User> list = pages.getContent();
			List<UserVO> polist = new ArrayList<UserVO>();
			for (User user : list) {
				polist.add(new UserVO(user, 3));
			}
			// 通过message传递总条数
			result = Result.getSuccessInstance(polist);
			result.setMsg(pages.getTotalElements() + "");
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/home")
	public void home(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<NodeHomePageVO> list = userservicebo.findNodeDataByUid(userid);
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/detail")
	public void detail(HttpServletRequest request, HttpServletResponse response, @RequestParam("userid") Integer userid)
			throws IOException {
		UserVO vo = userservicebo.getDetailByUid(userid);
		Result result = Result.getSuccessInstance(vo);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
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
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/psdcheck")
	public void psdcheck(HttpServletRequest request, HttpServletResponse response, String oldPassword,
			String newPassword) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			User user = userservicebo.findById(userid);
			if (user.getPassword().equals(oldPassword)) {
				if ("".equals(newPassword) || newPassword == null) {
					result = Result.getFailInstance("新密码为空", null);
				}
				userservicebo.updatePassword(userid, newPassword);
				result = Result.getSuccessInstance(null);
				result.setMsg("密码修改成功");
			} else {
				result = Result.getFailInstance("旧密码错误", null);
			}

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
		Result result = null;
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
						if (file.getSize() > 200 * 1000) {
							result = Result.getFailInstance("文件过大", null);
						} else {
							String realPath = request.getSession().getServletContext().getRealPath("/upload/avatar");
							File uploadfile = new File(realPath, fileName);
							//  不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉  
							FileUtils.copyInputStreamToFile(file.getInputStream(), uploadfile);
							String url = "http://139.199.76.64:8080" + request.getServletContext().getContextPath()
									+ "/upload/avatar/" + fileName;
							System.out.println(url);
							result = Result.getSuccessInstance(url);
						}
						response.setContentType("text/html;charset=utf-8");
						response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
					}
				}
			}
		}
	}
}
