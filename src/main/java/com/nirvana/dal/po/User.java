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
/**
 * 
 * @author Bin
 * 系统用户类 （超管，社管，普通用户）
 */
@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userid;
	//姓名
	private String username;
	//电话
	private String usertel;
	//邮箱
	private String useremail;
	//地址
	private String useraddress;
	@ColumnDefault(value="")
	//社区名
	private String userapartment;
	@Column(unique = true)
	//身份证
	private String useridentity;
	//用户类型id 1-2-3
	private Integer typeid;
	//经纬度
	private String longtitude;
	private String latitude;
	private Integer valid;
	//账户名
	@Column(unique = true)
	private String account;
	//位置数据上传频率
	@ColumnDefault(value = "5")
	private Integer frequency;
	//最近登入时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date logintime;
	//最近登出时间
	@Temporal(TemporalType.TIMESTAMP)
	private Date logouttime;
	@Temporal(TemporalType.TIMESTAMP)
	//注册时间
	private Date registtime;
	//登录ip
	private String ipaddress;
	@ColumnDefault(value = "1")
	//是否冻结
	private Integer state;
	//最新的数据上传时间 用来判断是否在线
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastupdatetime;
	@ManyToOne(cascade = { CascadeType.DETACH })
	@JoinColumn(name = "communityid")
	private Community community;
	//头像地址
	private String avatar;
	//密码
	private String password;
	private Integer gender;
	

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
	private Set<Relationship> relationships;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
	private Set<Node> nodes;

	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
	private Set<Notice> notices;
	
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
	private Set<Consult> consults ;
	
	@OneToMany(cascade = CascadeType.DETACH, mappedBy = "toask")
	private Set<Consult> toasks;

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
