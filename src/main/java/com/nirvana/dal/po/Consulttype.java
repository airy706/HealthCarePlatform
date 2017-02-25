package com.nirvana.dal.po;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 * 
 * @author Bin
 * 咨询类型类
 */
@Entity
@Table(name = "consulttype")
public class Consulttype {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer typeid;
	//类型名称
	private String typename;
	//所属社区
	@ManyToOne(cascade = { CascadeType.DETACH})
	@JoinColumn(name = "communityid")
	private Community community;

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	public Community getCommunity() {
		return community;
	}

	public void setCommunity(Community community) {
		this.community = community;
	}

	@Override
	public String toString() {
		return "Consulttype [typeid=" + typeid + ", typename=" + typename + ", community=" + community + "]";
	}

}
