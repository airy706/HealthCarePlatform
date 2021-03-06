package com.nirvana.app.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nirvana.dal.po.Notice;

public class NoticeVO {
	private Integer noticeid;
	private Date noticedate;
	private String noticetitle;
	private String noticecontent;
	private Integer noticetype;
	private String attachurl;
	private Integer userid;
	private Integer communityid;
	private String username;
	private String communityname;
	private String url;
	private boolean isurl;
	private boolean istop;
	private boolean isshow;
	private boolean isshort;

	public NoticeVO() {

	}

	public NoticeVO(Notice notice) {
		this.isshow = notice.isIsshow();
		this.noticeid = notice.getNoticeid();
		this.attachurl = notice.getAttachurl();
		this.noticecontent = notice.getNoticecontent();
		this.noticedate = notice.getNoticedate();
		this.noticetitle = notice.getNoticetitle();
		this.noticetype = notice.getNoticetype();
		this.userid = notice.getUser().getUserid();
		this.username = notice.getUser().getUsername();
		this.isurl = notice.isIsurl();
		this.istop = notice.isIstop();
		this.isshort = notice.isIsshort();
		
		if (isurl == true) {
			this.url = notice.getUrl();
		}
		if (this.noticetype == 1) {
			this.communityid = null;
			this.communityname = null;
		} else {
			this.communityid = notice.getCommunity().getCommunityid();
			this.communityname = notice.getCommunity().getCommunityname();
		}
	}

	public static List<NoticeVO> toVoList(List<Notice> polist) {
		List<NoticeVO> list = new ArrayList<NoticeVO>();
		for (int i = 0; i < polist.size(); i++) {
			list.add(new NoticeVO(polist.get(i)));
		}
		return list;
	}

	public boolean isIsshort() {
		return isshort;
	}

	public void setIsshort(boolean isshort) {
		this.isshort = isshort;
	}

	public boolean isIsshow() {
		return isshow;
	}

	public void setIsshow(boolean isshow) {
		this.isshow = isshow;
	}

	public boolean isIstop() {
		return istop;
	}

	public void setIstop(boolean istop) {
		this.istop = istop;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isIsurl() {
		return isurl;
	}

	public void setIsurl(boolean isurl) {
		this.isurl = isurl;
	}

	public Integer getNoticeid() {
		return noticeid;
	}

	public void setNoticeid(Integer noticeid) {
		this.noticeid = noticeid;
	}

	public Date getNoticedate() {
		return noticedate;
	}

	public void setNoticedate(Date noticedate) {
		this.noticedate = noticedate;
	}

	public String getNoticetitle() {
		return noticetitle;
	}

	public void setNoticetitle(String noticetitle) {
		this.noticetitle = noticetitle;
	}

	public String getNoticecontent() {
		return noticecontent;
	}

	public void setNoticecontent(String noticecontent) {
		this.noticecontent = noticecontent;
	}

	public Integer getNoticetype() {
		return noticetype;
	}

	public void setNoticetype(Integer noticetype) {
		this.noticetype = noticetype;
	}

	public String getAttachurl() {
		return attachurl;
	}

	public void setAttachurl(String attachurl) {
		this.attachurl = attachurl;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getCommunityid() {
		return communityid;
	}

	public void setCommunityid(Integer communityid) {
		this.communityid = communityid;
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

	@Override
	public String toString() {
		return "NoticeVO [noticeid=" + noticeid + ", noticedate=" + noticedate + ", noticetitle=" + noticetitle
				+ ", noticecontent=" + noticecontent + ", noticetype=" + noticetype + ", attachurl=" + attachurl
				+ ", userid=" + userid + ", communityid=" + communityid + ", username=" + username + ", communityname="
				+ communityname + "]";
	}

}
