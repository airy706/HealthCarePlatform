package com.nirvana.dal.po;

import javax.persistence.CascadeType;
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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer relationid;
	private String relationname;
	private String relationtel;
	private String relationtype;
	private String relationaddress;
	@ManyToOne(cascade=CascadeType.DETACH)
	@JoinColumn(name="userid")
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

	public String getRelationtype() {
		return relationtype;
	}

	public void setRelationtype(String relationtype) {
		this.relationtype = relationtype;
	}

	public String getRelationaddress() {
		return relationaddress;
	}

	public void setRelationaddress(String relationaddress) {
		this.relationaddress = relationaddress;
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
				+ relationtel + ", relationtype=" + relationtype + ", relationaddress=" + relationaddress + ", user="
				+ user + "]";
	}

}
