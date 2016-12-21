package com.nirvana.dal.po;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "node")
public class Node {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer nodeid;
	private String nodename;
	private Integer nodetype;
	private Integer nodestatus;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="userid")
	private User user;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="node")
	private Set<NodeData> nodedatas;

	public Set<NodeData> getNodedatas() {
		return nodedatas;
	}

	public void setNodedatas(Set<NodeData> nodedatas) {
		this.nodedatas = nodedatas;
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
				+ nodestatus + ", user=" + user + ", nodedatas=" + nodedatas + "]";
	}

}
