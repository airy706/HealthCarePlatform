package com.nirvana.dal.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "alarmdata")
public class AlarmData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dataid;
	private String did;
	private Integer reasontype;
	@Temporal(TemporalType.TIMESTAMP)
	private Date status_change_time;

	private Integer hasresloved;

	private Integer level;

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getHasresloved() {
		return hasresloved;
	}

	public void setHasresloved(Integer hasresloved) {
		this.hasresloved = hasresloved;
	}

	public Integer getDataid() {
		return dataid;
	}

	public void setDataid(Integer dataid) {
		this.dataid = dataid;
	}

	public String getDid() {
		return did;
	}

	public void setDid(String did) {
		this.did = did;
	}

	public Integer getReasontype() {
		return reasontype;
	}

	public void setReasontype(Integer reasontype) {
		this.reasontype = reasontype;
	}

	public Date getStatus_change_time() {
		return status_change_time;
	}

	public void setStatus_change_time(Date status_change_time) {
		this.status_change_time = status_change_time;
	}

	@Override
	public String toString() {
		return "AlarmData [dataid=" + dataid + ", did=" + did + ", reasontype=" + reasontype + ", status_change_time="
				+ status_change_time + ", hasresloved=" + hasresloved + ", level=" + level + "]";
	}

}
