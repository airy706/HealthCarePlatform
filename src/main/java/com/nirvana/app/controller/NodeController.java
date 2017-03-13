package com.nirvana.app.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nirvana.app.util.GsonUtils;
import com.nirvana.app.vo.NodeDataVO;
import com.nirvana.app.vo.NodeListVO;
import com.nirvana.app.vo.NodeVO;
import com.nirvana.app.vo.Result;
import com.nirvana.bll.service.NodeDataService;
import com.nirvana.bll.service.NodeService;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.po.User;

@RestController
@RequestMapping("/node")
public class NodeController extends BaseController {
	@Autowired
	private UserService userservicebo;

	@Autowired
	private NodeDataService dataservicebo;

	@Autowired
	private NodeService nodeservice;

	
	@RequestMapping("/cstatus")
	public void cstatus(HttpServletRequest request, HttpServletResponse response,@RequestParam("nodeid") Integer nodeid) throws IOException{
		nodeservice.cstatus(nodeid);
		Result result = null;
		result = Result.getSuccessInstance(null);
		result.setMsg("节点状态修改成功");
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}
	
	@RequestMapping("/type")
	public void type(HttpServletRequest request, HttpServletResponse response, @RequestParam("did") String did)
			throws IOException {
		User user = userservicebo.findByDid(did);
		Result result = null;
		if (user == null) {
			result = Result.getFailInstance("没有此用户", null);
		} else {
			List<NodeVO> list = nodeservice.findAllByUid(user.getUserid());
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response, @RequestParam("did") String did,
			@RequestParam("nodetype") Integer nodetype) throws IOException {
		Result result = null;
		if (nodetype == 3 || nodetype == 4 || nodetype == 6 || nodetype == 7 || nodetype == 12 || nodetype == 99
				|| nodetype == 66) {
			boolean flag = nodeservice.add(did, nodetype);
			if (flag) {
				result = Result.getSuccessInstance(null);
				result.setMsg("节点添加成功");
			} else {
				result = Result.getFailInstance("已添加", null);
			}
		} else {
			result = Result.getFailInstance("添加节点错误", null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/search")
	public void search(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key,
			@RequestParam("num") Integer num, @RequestParam("size") Integer size, Integer communityId)
					throws IOException {
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			Page<User> pages = userservicebo.findBykeypage(key, num, size, communityId);
			NodeListVO nodeListVO = new NodeListVO(pages);
			result = Result.getSuccessInstance(nodeListVO);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/data")
	public void data(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("sensortype") Integer sensortype, Integer userid, @RequestParam("startTime") String startTime,
			@RequestParam("endTime") String endTime) throws IOException {
		Date start = null;
		Date end = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (startTime == null || "".equals(startTime)) {
			end = new Date();
			start = new Date(end.getTime());
			start.setTime(start.getTime() - 24 * 60 * 60 * 1000);
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
		Result result = null;
		if (userid == null) {
			userid = (Integer) request.getSession().getAttribute("userid");
		}
		if (userid == null) {
			result = Result.getFailInstance("can not found userid", null);
		} else {
			NodeDataVO vo = dataservicebo.findByUidAndType(userid, sensortype, start, end);
			result = Result.getSuccessInstance(vo);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}
}
