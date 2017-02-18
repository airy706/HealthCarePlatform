package com.nirvana.dal.po;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "node")
public class Node {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer nodeid;
	private String nodename;
	private Integer nodetype;
	private Integer nodestatus;
	@Temporal(TemporalType.TIMESTAMP)
	private Date nodeaddtime;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "userid")
	private User user;
	@ColumnDefault(value = "5")
	private Integer frequency;

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

	public Date getNodeaddtime() {
		return nodeaddtime;
	}

	public void setNodeaddtime(Date nodeaddtime) {
		this.nodeaddtime = nodeaddtime;
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

	public Integer getNodestatus() {
		return nodestatus;
	}

	public void setNodestatus(Integer nodestatus) {
		this.nodestatus = nodestatus;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Node [nodeid=" + nodeid + ", nodename=" + nodename + ", nodetype=" + nodetype + ", nodestatus="
				+ nodestatus + ", nodeaddtime=" + nodeaddtime + ", user=" + user + "]";
	}

}
