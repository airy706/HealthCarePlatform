package com.nirvana.app.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.nirvana.app.vo.AlarmFilterVO;
import com.nirvana.app.vo.ExceptionVO;
import com.nirvana.app.vo.Result;
import com.nirvana.bll.service.AlarmDataService;
/**
 * 报警视图转换层 
 * 接口处理可参考接口文档
 * @author Bin
 *
 */
@RestController
@RequestMapping("/alarm")
public class AlarmController extends BaseController {

	@Autowired
	private AlarmDataService alarmservicebo;

	@RequestMapping("/rmall")
	public void rmall(HttpServletRequest request, HttpServletResponse response, Integer communityId)
			throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			alarmservicebo.rmall(communityId);
			result = Result.getSuccessInstance(null);
			result.setMsg("全部去除");
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/resolved")
	public void resolved(HttpServletRequest request, HttpServletResponse response, Integer communityId)
			throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			// System.out.println(communityId);
			List<ExceptionVO> list = alarmservicebo.findAllRedo(communityId);
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/detection")
	public void detection(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("exceptionId") Integer id, Integer communityId) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<ExceptionVO> list = null;
			if (id == 0) {
				list = alarmservicebo.findAllRedo(communityId);
			} else {
				list = alarmservicebo.detect(id, communityId);
			}
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/type")
	public void type(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<ExceptionVO> list = alarmservicebo.findAlltype();
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/solve")
	public void solve(HttpServletRequest request, HttpServletResponse response, @RequestParam("exceptionId") Integer id)
			throws IOException {
		alarmservicebo.sloveByAid(id);
		Result result = Result.getSuccessInstance(null);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/times")
	public void times(HttpServletRequest request, HttpServletResponse response,Integer communityid) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<ExceptionVO> list = alarmservicebo.findAllTimes(communityid);
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/filter")
	public void filter(HttpServletRequest request, HttpServletResponse response, @RequestParam("communityId") String id,
			@RequestParam("alarmType") String type, @RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime) throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			String[] ids = id.split(",");
			String[] types = type.split(",");
			Date start = null;
			Date end = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			if (startTime == null || "".equals(startTime)) {
				end = new Date();
				start = new Date(end.getTime());
				start.setTime(start.getTime() - 7 * 24 * 60 * 60 * 1000);
			} else {
				try {
					// 2017/01/11 00:00:00
					// 2017/01/12 24:00:00
					startTime += " 00:00:00";
					endTime += " 00:00:00";
					start = sdf.parse(startTime);
					end = sdf.parse(endTime);
					end.setTime(end.getTime() + 24 * 60 * 60 * 1000);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			System.out.println(ids.length + "  " + types.length);
			AlarmFilterVO filtervo = alarmservicebo.findByFilter(ids, types, start, end);
			result = Result.getSuccessInstance(filtervo);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

	@RequestMapping("/people")
	public void people(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("communityId") String communityid, @RequestParam("userId") String userids,
			@RequestParam("alarmType") String type, @RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime) throws IOException {
		//前端数据基本处理 包括字符串分数组
		Result result = null;
		String[] ids = userids.split(",");
		String[] types = type.split(",");
		Date start = null;
		Date end = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (startTime == null || "".equals(startTime)) {
			//默认时间间隔
			end = new Date();
			start = new Date(end.getTime());
			start.setTime(start.getTime() - 7 * 24 * 60 * 60 * 1000);
		} else {
			try {
				// 2017/01/11 00:00:00
				// 2017/01/12 24:00:00
				startTime += " 00:00:00";
				endTime += " 00:00:00";
				start = sdf.parse(startTime);
				end = sdf.parse(endTime);
				end.setTime(end.getTime() + 24 * 60 * 60 * 1000);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		System.out.println(ids.length + "  " + types.length);
		AlarmFilterVO filtervo = alarmservicebo.findPeopleByFilter(communityid, ids, types, start, end);
		result = Result.getSuccessInstance(filtervo);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}

}
