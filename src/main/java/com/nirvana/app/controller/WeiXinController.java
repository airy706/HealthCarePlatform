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

import com.nirvana.app.util.GsonUtils;
import com.nirvana.app.vo.NodeHomePageVO;
import com.nirvana.app.vo.NoticeVO;
import com.nirvana.app.vo.Result;
import com.nirvana.bll.service.AlarmDataService;
import com.nirvana.bll.service.NoticeService;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.po.AlarmData;
import com.nirvana.dal.po.User;
/**
 * 微信视图转换层 
 * 接口处理可参考接口文档
 * @author Bin
 *
 */
@RestController
@RequestMapping("/weixin")
public class WeiXinController extends BaseController {

	@Autowired
	private UserService userservicebo;
	
	@Autowired
	private AlarmDataService alarmservice;
	
	@Autowired
	private NoticeService noticeservicebo;

	@RequestMapping("/latest")
	public void lastest(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("userid") Integer userid) throws IOException {
		User user = userservicebo.findById(userid);
		Result result = null;
		if(user==null){
		result = Result.getFailInstance("无此用户", null);	
		}else{
		List<NodeHomePageVO> list = userservicebo.findNodeDataByUid(userid);
		result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/alarminfo")
	public void alarminfo(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("userid") Integer userid) throws IOException {
		User user = userservicebo.findById(userid);
		Result result = null;
		if(user==null){
			result = Result.getFailInstance("无此用户", null);
		}else{
		List<AlarmData> volist = alarmservice.findUndoByDid(user.getUseridentity());
		result = Result.getSuccessInstance(volist);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/notice")
	public void notice(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Date start = new Date();
		start.setHours(0);
		start.setMinutes(0);
		start.setSeconds(0);
		Date end = new Date();
		end.setTime(start.getTime()+24*60*60*1000);
		System.out.println(start.toLocaleString());
		System.out.println(end.toLocaleString());
		List<NoticeVO> volist = noticeservicebo.findByDate(start,end);
		Result result = null;
		result = Result.getSuccessInstance(volist);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}
}
