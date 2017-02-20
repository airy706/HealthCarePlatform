package com.nirvana.dal.po;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import org.hibernate.annotations.ColumnDefault;

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
	@Column(unique = true)
	private String useridentity;
	private Integer typeid;
	private String longtitude;
	private String latitude;
	private Integer valid;
	@Column(unique = true)
	private String account;
	@ColumnDefault(value = "5")
	private Integer frequency;
	@Temporal(TemporalType.TIMESTAMP)
	private Date logintime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date logouttime;
	@Temporal(TemporalType.TIMESTAMP)
	private Date registtime;
	private String ipaddress;
	@ColumnDefault(value = "1")
	private Integer state;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdatetime;
	@ManyToOne(cascade = { CascadeType.DETACH })
	@JoinColumn(name = "communityid")
	private Community community;
	private String avatar;
	private String password;
	private Integer gender;

	@OneToMany(cascade = CascadeType.DETACH, mappedBy = "user")
	private Set<Relationship> relationships;

	@OneToMany(cascade = CascadeType.DETACH, mappedBy = "user")
	private Set<Node> nodes;

	@OneToMany(cascade = CascadeType.DETACH, mappedBy = "user")
	private Set<Notice> notices;


	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public Set<Notice> getNotices() {
		return notices;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setNotices(Set<Notice> notices) {
		this.notices = notices;
	}

	public Set<Node> getNodes() {
		return nodes;
	}

	public Date getRegisttime() {
		return registtime;
	}

	public void setRegisttime(Date registtime) {
		this.registtime = registtime;
	}

	public void setNodes(Set<Node> nodes) {
		this.nodes = nodes;
	}

	public Set<Relationship> getRelationships() {
		return relationships;
	}

	public String getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	public Date getLastupdatetime() {
		return lastupdatetime;
	}

	public void setLastupdatetime(Date lastupdatetime) {
		this.lastupdatetime = lastupdatetime;
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

	@Override
	public String toString() {
		return "User [userid=" + userid + ", username=" + username + ", usertel=" + usertel + ", useremail=" + useremail
				+ ", useraddress=" + useraddress + ", userapartment=" + userapartment + ", useridentity=" + useridentity
				+ ", typeid=" + typeid + ", longtitude=" + longtitude + ", latitude=" + latitude + ", valid=" + valid
				+ ", account=" + account + ", frequency=" + frequency + ", logintime=" + logintime + ", logouttime="
				+ logouttime + ", registtime=" + registtime + ", ipaddress=" + ipaddress + ", state=" + state
				+ ", lastupdatetime=" + lastupdatetime + ", community=" + community + ", avatar=" + avatar
				+ ", password=" + password + ", gender=" + gender + ", relationships=" + relationships + ", nodes="
				+ nodes + ", notices=" + notices + "]";
	}

}
