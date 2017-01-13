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
@Table(name = "locationdata")
public class LocationData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dataid;
	private String did;
	private String gps;
	private String longtitude;
	private String latitude;
	@Temporal(TemporalType.TIMESTAMP)
	private Date status_change_time;

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

	public String getGps() {
		return gps;
	}

	public void setGps(String gps) {
		this.gps = gps;
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

	public Date getStatus_change_time() {
		return status_change_time;
	}

	public void setStatus_change_time(Date status_change_time) {
		this.status_change_time = status_change_time;
	}

	@Override
	public String toString() {
		return "LocationData [dataid=" + dataid + ", did=" + did + ", gps=" + gps + ", longtitude=" + longtitude
				+ ", latitude=" + latitude + ", status_change_time=" + status_change_time + "]";
	}

}
