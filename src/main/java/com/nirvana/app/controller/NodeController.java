package com.nirvana.app.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.nirvana.app.vo.Result;
import com.nirvana.bll.service.NodeDataService;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.po.User;

@RestController
@RequestMapping("/node")
public class NodeController extends BaseController {
	@Autowired
	private UserService userservicebo;
	
	@Autowired
	private NodeDataService dataservicebo;

	@RequestMapping("/search")
	public void search(HttpServletRequest request, HttpServletResponse response, @RequestParam("key") String key,
			@RequestParam("num") Integer num, @RequestParam("size") Integer size, Integer communityId)
					throws IOException {
		Page<User> pages = userservicebo.findBykeypage(key, num, size, communityId);
		NodeListVO nodeListVO = new NodeListVO(pages);
		Result result = null;
		result = Result.getSuccessInstance(nodeListVO);
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}

	@RequestMapping("/data")
	public void data(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("sensortype") Integer sensortype, Integer userid,@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime) throws IOException {
		Date start = null;
		Date end = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		if (startTime == null || "".equals(startTime)) {
			end = new Date();
			start = new Date(end.getTime());
			start.setTime(start.getTime() - 24 * 60 * 60 * 1000);
		} else {
			try {
				//2017/01/11 00:00:00
				//2017/01/12 24:00:00
				startTime+=" 00:00:00";
				endTime+=" 00:00:00";
				start = sdf.parse(startTime);
				end = sdf.parse(endTime);
				end.setTime(end.getTime()+24*60*60*1000);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Result result = null;
		if(userid==null){
			userid = (Integer) request.getSession().getAttribute("userid");
		}
		if(userid==null){
			result =Result.getFailInstance("can not found userid", null);
		}else{
		NodeDataVO vo = dataservicebo.findByUidAndType(userid,sensortype,start,end);
		result = Result.getSuccessInstance(vo);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(GsonUtils.getDateFormatGson().toJson(result));
	}
}
