package com.nirvana.app.vo;

import java.util.ArrayList;
import java.util.List;

import com.nirvana.dal.po.Community;

public class CommunityVO {

	private Integer communityid;
	private String areaname;
	private String communityname;
	private String communitytel;
	private String communitylocation;
	private String latitude;
	private String longtitude;

	public CommunityVO() {
	}

	public CommunityVO(Community community) {
		this.areaname = community.getAreaname();
		this.communityid = community.getCommunityid();
		this.communitylocation = community.getCommunitylocation();
		this.communityname = community.getCommunityname();
		this.communitytel = community.getCommunitytel();
		this.latitude = community.getLatitude();
		this.longtitude = community.getLongtitude();
	}
	
	public static List<CommunityVO> toListVO(List<Community> polist){
		List<CommunityVO> list = new ArrayList<CommunityVO>();
		for(int i=0;i<polist.size();i++){
			list.add(new CommunityVO(polist.get(i)));
		}
		return list;
	}
	
	

	public Integer getCommunityid() {
		return communityid;
	}

	public void setCommunityid(Integer communityid) {
		this.communityid = communityid;
	}

	public String getAreaname() {
		return areaname;
	}

	public void setAreaname(String areaname) {
		this.areaname = areaname;
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

	@Override
	public String toString() {
		return "CommunityVO [communityid=" + communityid + ", areaname=" + areaname + ", communityname=" + communityname
				+ ", communitytel=" + communitytel + ", communitylocation=" + communitylocation + ", latitude="
				+ latitude + ", longtitude=" + longtitude + "]";
	}

}
