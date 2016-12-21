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
@Table(name = "nodedata")
public class NodeData {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer dataid;
	private String did;
	private String active;
	private String alarmstatus;
	private String cardiotach;
	private String drangstye;
	private String onekeycall;
	private String pid;
	private String sensortype;
	@Temporal(TemporalType.TIMESTAMP)
	private Date status_change_time;
	private String wheelstyle;
	private String gps;
	private String longtitude;
	private String latitude;
	private String pressure;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="nodeid")
	private Node node;

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

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getAlarmstatus() {
		return alarmstatus;
	}

	public void setAlarmstatus(String alarmstatus) {
		this.alarmstatus = alarmstatus;
	}

	public String getCardiotach() {
		return cardiotach;
	}

	public void setCardiotach(String cardiotach) {
		this.cardiotach = cardiotach;
	}

	public String getDrangstye() {
		return drangstye;
	}

	public void setDrangstye(String drangstye) {
		this.drangstye = drangstye;
	}

	public String getOnekeycall() {
		return onekeycall;
	}

	public void setOnekeycall(String onekeycall) {
		this.onekeycall = onekeycall;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSensortype() {
		return sensortype;
	}

	public void setSensortype(String sensortype) {
		this.sensortype = sensortype;
	}

	public Date getStatus_change_time() {
		return status_change_time;
	}

	public void setStatus_change_time(Date status_change_time) {
		this.status_change_time = status_change_time;
	}

	public String getWheelstyle() {
		return wheelstyle;
	}

	public void setWheelstyle(String wheelstyle) {
		this.wheelstyle = wheelstyle;
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

	public String getPressure() {
		return pressure;
	}

	public void setPressure(String pressure) {
		this.pressure = pressure;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	@Override
	public String toString() {
		return "NodeData [dataid=" + dataid + ", did=" + did + ", active=" + active + ", alarmstatus=" + alarmstatus
				+ ", cardiotach=" + cardiotach + ", drangstye=" + drangstye + ", onekeycall=" + onekeycall + ", pid="
				+ pid + ", sensortype=" + sensortype + ", status_change_time=" + status_change_time + ", wheelstyle="
				+ wheelstyle + ", gps=" + gps + ", longtitude=" + longtitude + ", latitude=" + latitude + ", pressure="
				+ pressure + ", node=" + node + "]";
	}

}
