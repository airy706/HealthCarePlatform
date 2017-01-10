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
@Entity
@Table(name="consult")
public class Consult {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer consultid;
	
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "typeid")
	private Consulttype consulttype;
	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "userid")
	private User user;
	private String content;
	@Temporal(TemporalType.TIMESTAMP)
	private Date committime;
	private boolean isfinish;
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishtime;

	public Integer getConsultid() {
		return consultid;
	}

	public void setConsultid(Integer consultid) {
		this.consultid = consultid;
	}

	public Consulttype getConsulttype() {
		return consulttype;
	}

	public void setConsulttype(Consulttype consulttype) {
		this.consulttype = consulttype;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCommittime() {
		return committime;
	}

	public void setCommittime(Date committime) {
		this.committime = committime;
	}

	public boolean isIsfinish() {
		return isfinish;
	}

	public void setIsfinish(boolean isfinish) {
		this.isfinish = isfinish;
	}

	public Date getFinishtime() {
		return finishtime;
	}

	public void setFinishtime(Date finishtime) {
		this.finishtime = finishtime;
	}

	@Override
	public String toString() {
		return "Consult [consultid=" + consultid + ", consulttype=" + consulttype + ", user=" + user + ", content="
				+ content + ", committime=" + committime + ", isfinish=" + isfinish + ", finishtime=" + finishtime
				+ "]";
	}


}
