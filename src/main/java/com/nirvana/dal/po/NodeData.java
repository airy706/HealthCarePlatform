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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dataid;
	private String did;
	private String sensortype;
	@Temporal(TemporalType.TIMESTAMP)
	private Date status_change_time;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "nodeid")
	private Node node;
	private String data;

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

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "NodeData [dataid=" + dataid + ", did=" + did + ", sensortype=" + sensortype + ", status_change_time="
				+ status_change_time + ", node=" + node + ", data=" + data + "]";
	}

}
