package com.nirvana.app.vo;

import java.util.List;

import com.nirvana.dal.po.User;

public class UserVO {
	private Integer userid;
	private String username;
	private Integer communityid;
	private String communityname;
	private String usertel;
	private String latitude;
	private String longitude;
	private Integer state;
	private Integer valid;
	private Integer frequency;
	private Integer typeid;
	private String token;

	private List<NodeVO> nodes;

	public UserVO() {
		super();
	}

	public UserVO(User user, int select) {
		if (select == 1) {
			this.userid = user.getUserid();
			this.username = user.getUsername();
			this.communityname = user.getCommunity().getCommunityname();
		} else if (select == 2) {
			this.usertel = user.getUsertel();
			this.userid = user.getUserid();
			this.username = user.getUsername();
			this.communityname = user.getCommunity().getCommunityname();
			this.communityid = user.getCommunity().getCommunityid();
			this.latitude = user.getLatitude();
			this.longitude = user.getLongtitude();
			this.state = user.getState();
		} else if (select == 3) {
			this.userid = user.getUserid();
			this.username = user.getUsername();
			this.communityname = user.getCommunity().getCommunityname();
			this.valid = user.getValid();
			this.frequency = user.getFrequency();
		}
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public String getUsertel() {
		return usertel;
	}

	public void setUsertel(String usertel) {
		this.usertel = usertel;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getCommunityid() {
		return communityid;
	}

	public void setCommunityid(Integer communityid) {
		this.communityid = communityid;
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

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

}
