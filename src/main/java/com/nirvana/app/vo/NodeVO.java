package com.nirvana.app.vo;

import java.util.Date;

import com.nirvana.dal.po.Node;

public class NodeVO {
	private Integer nodeid;
	private String nodename;
	private Integer nodetype;
	private Date nodeaddtime;
	private String communityname;
	private Integer frequency;

	public NodeVO() {
		super();
	}

	public NodeVO(Node node) {
		super();
		this.nodeid = node.getNodeid();
		this.nodename = node.getNodename();
		this.nodetype = node.getNodetype();
		this.nodeaddtime = node.getNodeaddtime();
		this.frequency = node.getFrequency();
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public Integer getNodeid() {
		return nodeid;
	}

	public void setNodeid(Integer nodeid) {
		this.nodeid = nodeid;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}

	public Integer getNodetype() {
		return nodetype;
	}

	public void setNodetype(Integer nodetype) {
		this.nodetype = nodetype;
	}

	public Date getNodeaddtime() {
		return nodeaddtime;
	}

	public void setNodeaddtime(Date nodeaddtime) {
		this.nodeaddtime = nodeaddtime;
	}

	public String getCommunityname() {
		return communityname;
	}

	public void setCommunityname(String communityname) {
		this.communityname = communityname;
	}

}
