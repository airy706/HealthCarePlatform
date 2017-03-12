package com.nirvana.dal.po;

import java.util.Date;

import javax.persistence.CascadeType;
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
import org.hibernate.validator.constraints.Length;
/**
 * 
 * @author Bin
 * 公告类
 */
@Entity
@Table(name = "notice")
public class Notice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer noticeid;
	//公告发布日期
	@Temporal(TemporalType.TIMESTAMP)
	private Date noticedate;
	//公告标题
	private String noticetitle;
	//公告内容
	@Length(max = 10000)
	private String noticecontent;
	//公告类型id 1-超管 公告 2-社区公告
	private Integer noticetype;
	//附件地址
	private String attachurl;
	//是否是外接url
	private boolean isurl;
	//外接链接URl
	private String url;

	//发布公告者
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "userid")
	private User user;
	//公告所属社区
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "communityid")
	private Community community;
	//是否置顶
	@ColumnDefault(value = "0")
	private boolean istop;
	//是否显示
	@ColumnDefault(value = "1")
	private boolean isshow;
	//是否是一句话
	@ColumnDefault(value="0")
	private boolean isshort;
	
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

	public boolean isIsurl() {
		return isurl;
	}

	public void setIsurl(boolean isurl) {
		this.isurl = isurl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	@Override
	public String toString() {
		return "Notice [noticeid=" + noticeid + ", noticedate=" + noticedate + ", noticetitle=" + noticetitle
				+ ", noticecontent=" + noticecontent + ", noticetype=" + noticetype + ", attachurl=" + attachurl
				+ ", isurl=" + isurl + ", url=" + url + ", user=" + user + ", community=" + community + "]";
	}

}
