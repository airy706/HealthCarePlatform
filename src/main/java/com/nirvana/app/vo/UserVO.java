package com.nirvana.app.vo;

import java.util.List;

import com.nirvana.dal.po.Node;
import com.nirvana.dal.po.User;

public class UserVO {
	private Integer userid;
	private String username;
	private String communityname;

	private List<NodeVO> nodes;

	public UserVO() {
		super();
	}

	public UserVO(User user) {
		super();
		this.userid = user.getUserid();
		this.username = user.getUsername();
		this.communityname = user.getCommunity().getCommunityname();
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCommunityname() {
		return communityname;
	}

	public void setCommunityname(String communityname) {
		this.communityname = communityname;
	}

	public List<NodeVO> getNodes() {
		return nodes;
	}

	public void setNodes(List<NodeVO> nodes) {
		this.nodes = nodes;
	}

}
