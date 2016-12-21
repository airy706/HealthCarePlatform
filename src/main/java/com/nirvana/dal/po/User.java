package com.nirvana.dal.po;

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userid;
	private String username;
	private String usertel;
	private String useremail;
	private String useraddress;
	private String userapartment;
	private String useridentity;
	private Integer typeid;
	@Temporal(TemporalType.TIMESTAMP)
	private Date logintime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date logouttime;
	private String ipaddress;
	private Integer status;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "communityid")
	private Community community;

	private String password;

	@OneToMany(cascade=CascadeType.ALL,mappedBy="user")
	private Set<Relationship> relationships;

	@OneToMany(cascade=CascadeType.ALL,mappedBy="user")
	private Set<Node> nodes;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private Set<Notice> notices;

	public Set<Notice> getNotices() {
		return notices;
	}

	public void setNotices(Set<Notice> notices) {
		this.notices = notices;
	}

	public Set<Node> getNodes() {
		return nodes;
	}

	public void setNodes(Set<Node> nodes) {
		this.nodes = nodes;
	}

	public Set<Relationship> getRelationships() {
		return relationships;
	}

	public void setRelationships(Set<Relationship> relationships) {
		this.relationships = relationships;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getUsertel() {
		return usertel;
	}

	public void setUsertel(String usertel) {
		this.usertel = usertel;
	}

	public String getUseremail() {
		return useremail;
	}

	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}

	public String getUseraddress() {
		return useraddress;
	}

	public void setUseraddress(String useraddress) {
		this.useraddress = useraddress;
	}

	public String getUserapartment() {
		return userapartment;
	}

	public void setUserapartment(String userapartment) {
		this.userapartment = userapartment;
	}

	public String getUseridentity() {
		return useridentity;
	}

	public void setUseridentity(String useridentity) {
		this.useridentity = useridentity;
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public Date getLogintime() {
		return logintime;
	}

	public void setLogintime(Date logintime) {
		this.logintime = logintime;
	}

	public Date getLogouttime() {
		return logouttime;
	}

	public void setLogouttime(Date logouttime) {
		this.logouttime = logouttime;
	}

	public String getIpaddress() {
		return ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + ", usertel=" + usertel + ", useremail=" + useremail
				+ ", useraddress=" + useraddress + ", userapartment=" + userapartment + ", useridentity=" + useridentity
				+ ", typeid=" + typeid + ", logintime=" + logintime + ", logouttime=" + logouttime + ", ipaddress="
				+ ipaddress + ", status=" + status + ", community=" + community + ", password=" + password
				+ ", relationships=" + relationships + ", nodes=" + nodes + ", notices=" + notices + "]";
	}

}
