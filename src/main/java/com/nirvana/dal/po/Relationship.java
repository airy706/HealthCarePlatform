package com.nirvana.dal.po;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "relationship")
public class Relationship {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer relationid;
	@Column(nullable=false,unique=true)
	private String relationname;
	@Column(nullable=false)
	private String relationtel;
	@Column(nullable=false)
	private String relationaccount;
	@Column(nullable=false)
	private String relationpassword;
	@ManyToOne(cascade = CascadeType.DETACH)
	@JoinColumn(name = "userid")
	private User user;

	public Integer getRelationid() {
		return relationid;
	}

	public void setRelationid(Integer relationid) {
		this.relationid = relationid;
	}

	public String getRelationname() {
		return relationname;
	}

	public void setRelationname(String relationname) {
		this.relationname = relationname;
	}

	public String getRelationtel() {
		return relationtel;
	}

	public void setRelationtel(String relationtel) {
		this.relationtel = relationtel;
	}

	public String getRelationaccount() {
		return relationaccount;
	}

	public void setRelationaccount(String relationaccount) {
		this.relationaccount = relationaccount;
	}

	public String getRelationpassword() {
		return relationpassword;
	}

	public void setRelationpassword(String relationpassword) {
		this.relationpassword = relationpassword;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Relationship [relationid=" + relationid + ", relationname=" + relationname + ", relationtel="
				+ relationtel + ", relationaccount=" + relationaccount + ", relationpassword=" + relationpassword
				+ ", user=" + user + "]";
	}

}
