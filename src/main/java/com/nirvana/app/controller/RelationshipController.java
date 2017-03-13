package com.nirvana.app.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.nirvana.app.vo.LinkManVO;
import com.nirvana.app.vo.Result;
import com.nirvana.bll.service.RelationshipService;
import com.nirvana.bll.service.UserService;
import com.nirvana.dal.po.Relationship;
import com.nirvana.dal.po.User;
/**
 * 家属视图转换层 
 * 接口处理可参考接口文档
 * @author Bin
 *
 */
@RestController
@RequestMapping("/linkman")
public class RelationshipController extends BaseController{

	@Autowired
	private RelationshipService shipservicebo;
	
	@Autowired
	private UserService userservicebo;
	
	@RequestMapping("/create")
	public void create(HttpServletRequest request,HttpServletResponse response,Relationship ship) throws IOException{
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			User user = userservicebo.findById(userid);
			ship.setUser(user);
			shipservicebo.add(ship);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}
	
	@RequestMapping("/list")
	public void list(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			List<LinkManVO> list = shipservicebo.findAllByUid(userid);
			result = Result.getSuccessInstance(list);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}
	
	@RequestMapping("/edit")
	public void edit(HttpServletRequest request,HttpServletResponse response,Relationship ship) throws IOException{
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			if(ship.getRelationpassword()==null||"".equals(ship.getRelationpassword())){
				result = Result.getFailInstance("密码不得为空", null); 
			}else{
			User user = userservicebo.findById(userid);
			ship.setUser(user);
			shipservicebo.add(ship);
			result = Result.getSuccessInstance(null);
			result.setMsg("修改联系人成功");
			}
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}
	
	@RequestMapping("/del")
	public void del(HttpServletRequest request,HttpServletResponse response,@RequestParam("relationid") Integer relationid) throws IOException{
		Integer userid = (Integer) request.getSession().getAttribute("userid");
		Result result = null;
		if (userid == null) {
			result = Result.getFailInstance("userid cannot been found", null);
		} else {
			shipservicebo.delById(relationid);
			result = Result.getSuccessInstance(null);
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print(new Gson().toJson(result));
	}
	
}
