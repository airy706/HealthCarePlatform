package com.nirvana.dal.po;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * 
 * @author Bin
 * 节点数据类（包含正常和异常）按照一定的频率上传
 */
@Entity
@Table(name = "nodedata")
public class NodeData {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer dataid;
	//身份证
	private String did;
	//传感器类型id
	private Integer sensortype;
	@Temporal(TemporalType.TIMESTAMP)
	private Date status_change_time;
	//节点id 无用 因为nodeid没有实现
	private Integer nodeid;
	//上传数据
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

	public Integer getSensortype() {
		return sensortype;
	}

	public void setSensortype(Integer sensortype) {
		this.sensortype = sensortype;
	}

	public Date getStatus_change_time() {
		return status_change_time;
	}

	public void setStatus_change_time(Date status_change_time) {
		this.status_change_time = status_change_time;
	}

	public Integer getNodeid() {
		return nodeid;
	}

	public void setNodeid(Integer nodeid) {
		this.nodeid = nodeid;
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
				+ status_change_time + ", nodeid=" + nodeid + ", data=" + data + "]";
	}

}
