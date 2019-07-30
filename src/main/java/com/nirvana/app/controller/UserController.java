package com.nirvana.app.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import com.nirvana.app.util.GsonBuilderUtil;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nirvana.app.util.SendUtils;
import com.nirvana.app.util.GsonUtils;
import com.nirvana.app.util.SendRequestToNeteaseCloud;
import com.nirvana.app.vo.NodeHomePageVO;
import com.nirvana.app.vo.NodeVO;
import com.nirvana.app.vo.Result;
import com.nirvana.app.vo.UserVO;
import com.nirvana.bll.service.AlarmDataService;
import com.nirvana.bll.service.NodeService;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.api.UserDao;
import com.nirvana.dal.po.AlarmData;
import com.nirvana.dal.po.User;
/**
 * 家属视图转换层 
 * 接口处理可参考接口文档
 * @author Bin
 *
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {
	@Autowired
	private UserDao userdao;
	@Autowired
	private UserService userservicebo;

	@Autowired
	private NodeService nodeservicebo;

	@Autowired
	private AlarmDataService alarmservice;

//	@RequestMapping(value = "/test",method = RequestMethod.GET)
//	public @ResponseBody
//	String test(){
//		return GsonBuilderUtil.fromObjectToString(new User("12345","1234"));
//
//	}
//	@RequestMapping("/test1")
//    @ResponseBody
//	public Result test1(@RequestParam("arg") String arg){
//		if("test1".equals(arg)){
//			Result result=Result.getSuccessInstance(null);
//			result.setMsg("ok");
//
//			return result;
//		}
//		return null;
//	}

	@RequestMapping("/changetel")
	public void changetel(HttpServletRequest request,HttpServletResponse response,@RequestParam("usertel") String usertel) throws IOException{
		//登陆后的改变，session中已有用户信息，此时信息userid为登录时添加到session中的
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			User user = userservicebo.findById(userid);
			//todo 是否需要输入旧手机号，与数据库select tel from user where tel=oldtel进行比较
			user.setUsertel(usertel);
			userservicebo.update(user);
			result = Result.getSuccessInstance(null);
			result.setMsg("手机号码修改成功");
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}


	//todo attachurl的格式，如何与文件服务器连接？可能是URL1格式的文件，直接指向文件服务器上的文件
	@RequestMapping("/download")
	public void download(HttpServletRequest request,HttpServletResponse response,@RequestParam("url") String url) throws IOException{
		//改变编码格式
		String attachurl = new String(url.getBytes("iso-8859-1"),"UTF-8");
		int index =attachurl.lastIndexOf("/");
		//获取文件名
		String filename = attachurl.substring(index+1);
		//通过文件路径构造文件输入流，从该文件中获取字节
		FileInputStream fis = new FileInputStream(attachurl);
		//缓冲流
		BufferedInputStream bis = new BufferedInputStream(fis);
		//文件字节数组的大小
		byte[] bytes = new byte[bis.available()];
		//响应的设置
		response.addHeader("Content-Disposition", "attachment;filename="+filename);
		response.setContentType("application/octet-stream");
		OutputStream os = response.getOutputStream();
		//读取文件，存储到bytes中
		bis.read(bytes);
		//将bytes中的字节内容输出
		os.write(bytes);
		bis.close();
	}

	//显示头像
	@RequestMapping("/showavatar")
	public void showavatar(HttpServletRequest request,HttpServletResponse response,@RequestParam("url") String url) throws IOException{
		String attachurl = new String(url.getBytes("iso-8859-1"),"UTF-8");
		FileInputStream fis = new FileInputStream(attachurl);
		BufferedInputStream bis = new BufferedInputStream(fis);
		byte[] bytes = new byte[bis.available()];
		response.setContentType("image/*");
		OutputStream os = response.getOutputStream();
		bis.read(bytes);
		os.write(bytes);
		bis.close();
	}
	//检查是否登录，若已登录，返回登录用户的信息
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
				vo.setAvatar(user.getAvatar());
				result = Result.getSuccessInstance(vo);
				result.setMsg("已登录");
			}
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	//冻结用户
	@RequestMapping("/frozen")
	public void frozen(HttpServletRequest request, HttpServletResponse response, @RequestParam("userid") Integer userid)
			throws IOException {
		userservicebo.frozen(userid);
		Result result = null;
		result = Result.getSuccessInstance(null);
		//也可加上result.setMsg("冻结成功")
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	//恢复冻结状态
	@RequestMapping("/recovery")
	public void recovery(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("userid") Integer userid) throws IOException {
		userservicebo.recovery(userid);
		Result result = null;
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	//删除已注册的用户
	//注意dao层删除与用户级联的警报信息、定位数据、节点数据
	//一对多（级联删除），反过来不可
	@RequestMapping("/registerdel")
	public void registerdel(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("userid") Integer userid) throws IOException {
		userservicebo.delByUid(userid);
		Result result = null;
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	//已注册用户信息的编辑，除了ID外可修改
	//todo 此步骤有横向越权的危险，可以修改别人的个人信息，可进行Session中的用户ID与提交内容中的用户ID作比较后，再修改信息
	@RequestMapping("/registeredit")
	public void registeredit(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
		Integer sessionId= (Integer) request.getSession().getAttribute("userid");
		Result result = null;
//		if(sessionId==null){
//			 result=Result.getFailInstance("用户未登录",null);
//		}else{
//			if(sessionId==user.getUserid()){
//				userservicebo.updateregister(user);
//				result = Result.getSuccessInstance(null);
//			}
//			result=Result.getFailInstance("无权限修改",null);
//		}

		userservicebo.updateregister(user);
		result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	//根据相似关键字搜索可能的注册用户（用户姓名、电话、身份证、社区等）
	//TODO  分页的意义？此步中将之前的分页内容获取为用户集合
	// TODO 元素大小为long数字，可通过集合的size获取
	@RequestMapping("/search")
	public void search(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key,
			@RequestParam("size") Integer size, @RequestParam("num") Integer num) throws IOException {
		Result result = null;
		Page<User> page = userservicebo.findRegisterByKey(key, size, num);
		List<User> polist = page.getContent();
		List<UserVO> volist = new ArrayList<UserVO>();
		for (User user : polist) {
			//4类型的uservo信息较全面，除定位经纬度等信息没有
			UserVO vo = new UserVO(user, 4);
			volist.add(vo);
		}
		result = Result.getSuccessInstance(volist);
		result.setMsg(page.getTotalElements() + "");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	//修改密码，通过获取验证码，并将输入码与验证码比较，完成后注意删除之前验证码
	@RequestMapping("/changepsd")
	public void changepsd(HttpServletRequest request, HttpServletResponse response, @RequestParam("did") String did,
			@RequestParam("code") String code,@RequestParam("password") String password) throws IOException{
		User user = userservicebo.findByDid(did);
		Result result = null;
		if (user == null) {
			result = Result.getFailInstance("无此用户", null);
		} else {
			//通过保存在context中的信息与code比较，进行权限验证，防止非法篡改密码
			String words = (String) request.getServletContext().getAttribute(did);
			if (code.equals(words)) {
				result = Result.getSuccessInstance(null);
				result.setMsg("修改成功");
				//清除context中的did属性
				request.getServletContext().removeAttribute(did);
				//修改密码,相当于updateSelective
				user.setPassword(password);
				userservicebo.add(user);
			} else {
				result = Result.getFailInstance("修改失败", null);
			}
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	//验证验证码，验证码与身份证对应，防止非法操作
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
			} else {
				result = Result.getFailInstance("验证码错误", null);
			}
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	//密码反馈(身份证，获取验证码方式)
	@RequestMapping("/psdback")
	public void psdback(HttpServletRequest request, HttpServletResponse response, @RequestParam("did") String did,
			@RequestParam("way") String way) throws IOException {
		User user = userservicebo.findByDid(did);
		Result result = null;
		if (user == null) {
			result = Result.getFailInstance("无此用户", null);
		} else {
			//产生6位随机数，并通过服务平台（阿里云通信）
			Random random = new Random();
			String words = "";
			for (int i = 0; i < 6; i++) {
				int r = random.nextInt(10);//产生0到9之间的随机数
				words = words + r + "";
			}
			//通过邮箱
			if ("email".equals(way)) {
				if (user.getUseremail() == null || "".equals(user.getUseremail())) {
					result = Result.getFailInstance("无邮箱", null);
				} else {
					//context中加入属性，身份证与验证码对应，
					request.getServletContext().setAttribute(user.getUseridentity(), words);
					SendUtils.send_email(user.getUseremail(), words);
					//todo 应该对返回值做判断，是否成功
					result = Result.getSuccessInstance(null);
					result.setMsg("发送成功");
				}//通过短信
			} else if ("tel".equals(way)) {
				if (user.getUsertel() == null || "".equals(user.getUsertel())) {
					result = Result.getFailInstance("无手机号", null);
				} else {
					request.getServletContext().setAttribute(user.getUseridentity(), words);
					// 发送短信
					SendUtils.send_tel(user.getUsertel(), words);
					//todo 应该做判断
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

	//个人未解决警报数据
	@RequestMapping("/undoalarm")
	public void undoalarm(HttpServletRequest request, HttpServletResponse response, @RequestParam("did") String did)
			throws IOException {
		List<AlarmData> volist = alarmservice.findUndoByDid(did);
		Result result = null;
		result = Result.getSuccessInstance(volist);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	//网易云账户登录
	@RequestMapping("/mobilelogin")
	public void mobilelogin(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("account") String account, @RequestParam("password") String password) throws Exception {
		UserVO vo = userservicebo.commonlogin(account, password);
		Result result = null;
		if (vo == null) {
			result = Result.getFailInstance("用户名或密码错误", null);
		} else {
			if (vo.getState() == 1) {
				result = Result.getSuccessInstance(vo);
				User user = userdao.findByAccountandPsd(account, password);
				if(user.getToken() == null)
				{
					//网易云请求
					SendRequestToNeteaseCloud  sendRequestToNeteaseCloud = new SendRequestToNeteaseCloud();
					//HttpResponse的实体，通过实体工具类转换成字符串
					String jsonString = EntityUtils.toString(sendRequestToNeteaseCloud.creadId(account).getEntity(), "utf-8");
			        //JsonParser的parse方法，getAsJsonObject方法
					JsonObject  jsonObject  = new JsonParser().parse(jsonString).getAsJsonObject();
			        try {
			        	//从相应内容中获取token,get方法返回jsonElement,再返回成JsonObject格式
			        	JsonObject info = jsonObject.get("info").getAsJsonObject();
			        	String token = info.get("token").getAsString();
			        	user.setToken(token);
			        	userservicebo.update(user);
			        	result.setMsg("登陆成功,"+ token);
					} catch (Exception e) {
						// TODO: handle exception
					}
					//将token字符串写入到数据库
				}else{
					result.setMsg("登陆成功,"+ user.getToken());
				}
			} else {
				result = Result.getFailInstance("该用户已冻结", null);
			}
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	//todo 应该为删除管理员，实际为删除用户，应当添加findManagerByUid方法，delManagerByUid
	//或者理解为管理员删除用户
	@RequestMapping("/delmanager")
	public void delmanager(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("userid") Integer userid) throws IOException {
		Integer u = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (u == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			//删除普通用户
			userservicebo.delByUid(userid);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	//将已有用户设置为管理员
	@RequestMapping("/createmanager")
	public void createmanager(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			//设置管理员类型，修改注册时间
			user.setTypeid(2);
			user.setRegisttime(new Date());
			user.setState(1);
			userservicebo.add(user);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	//todo 应为编辑管理员信息，实际为编辑普通用户信息，注意横向越权问题
	//或者理解为管理员编辑普通用户信息
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
			newuser.setUsertel(user.getUsertel());
			newuser.setUseridentity(user.getUseridentity());
			userservicebo.add(newuser);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	//获取用户定位数据频率
	@RequestMapping("/frequency")
	public void frequency(HttpServletRequest request, HttpServletResponse response, @RequestParam("did") String did)
			throws IOException {
		Integer f = userservicebo.getFrequencyByDid(did);
		Result result = Result.getSuccessInstance(null);
		result.setMsg(f + "");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	//获取当前登录用户的所有节点集合
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

	//获取一个社区内所有在线的用户
	//TODO 该判断用户在线方法不准确，可通过添加在线属性，在dao层添加selectOnlineByCid
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

	//设置上传频率
	//todo 存在横向越权问题，应添加管理员权限认证或者session
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
							//String realPath = request.getSession().getServletContext().getRealPath("/upload/avatar");
							String realPath ="/data/upload/avatar";
							File uploadfile = new File(realPath, fileName);
							//  不必处理IO流关闭的问题，因为FileUtils.copyInputStreamToFile()方法内部会自动把用到的IO流关掉  
							FileUtils.copyInputStreamToFile(file.getInputStream(), uploadfile);
//							String url = "http://139.199.76.64:8080" + request.getServletContext().getContextPath()
//									+ "/upload/avatar/" + fileName;
							String url = "/data/upload/avatar/" + fileName;
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
