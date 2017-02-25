package com.nirvana.dal.po;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
/**
 * 
 * @author Bin
 *社区类
 */
@Entity
@Table(name = "community")
public class Community {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer communityid;
	//区域名
	private String areaname;
	//社区名
	private String communityname;
	//社区电话
	private String communitytel;
	//社区地址
	private String communitylocation;
	//经纬度
	private String latitude;
	private String longtitude;

	//社区下普通用户
	@OneToMany(cascade = CascadeType.DETACH, mappedBy = "community")
	private Set<User> users;

	//社区下的公告
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "community")
	private Set<Notice> notices;

	//社区下的服务类型
	@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "community")
	private Set<Consulttype> consulttypes;

	
	public Set<Consulttype> getConsulttypes() {
		return consulttypes;
	}

	public void setConsulttypes(Set<Consulttype> consulttypes) {
		this.consulttypes = consulttypes;
	}

	public Set<Notice> getNotices() {
		return notices;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
	}

	public void setNotices(Set<Notice> notices) {
		this.notices = notices;
	}

	public Integer getCommunityid() {
		return communityid;
	}

	public void setCommunityid(Integer communityid) {
		this.communityid = communityid;
	}

	public String getCommunityname() {
		return communityname;
	}

	public void setCommunityname(String communityname) {
		this.communityname = communityname;
	}

	public String getCommunitytel() {
		return communitytel;
	}

	public void setCommunitytel(String communitytel) {
		this.communitytel = communitytel;
	}

	public String getCommunitylocation() {
		return communitylocation;
	}

	public void setCommunitylocation(String communitylocation) {
		this.communitylocation = communitylocation;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongtitude() {
		return longtitude;
	}

	public void setLongtitude(String longtitude) {
		this.longtitude = longtitude;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {
		return "Community [communityid=" + communityid + ", areaname=" + areaname + ", communityname=" + communityname
				+ ", communitytel=" + communitytel + ", communitylocation=" + communitylocation + ", latitude="
				+ latitude + ", longtitude=" + longtitude + ", users=" + users + ", notices=" + notices + "]";
	}

}
