package com.nirvana.dal.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;
/**
 * 
 * @author Bin
 * 产品介绍类
 */
@Entity
@Table(name = "productintro")
public class ProductIntro {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer productid;
	//介绍标题
	private String producttitle;
	//产品照片
	private String productimg;
	//介绍内容
	@Length(max = 1000)
	private String productcontent;
	//是否显示
	private boolean isshow;

	public String getProducttitle() {
		return producttitle;
	}

	public void setProducttitle(String producttitle) {
		this.producttitle = producttitle;
	}

	public boolean isIsshow() {
		return isshow;
	}

	public void setIsshow(boolean isshow) {
		this.isshow = isshow;
	}

	public Integer getProductid() {
		return productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}

	public String getProductimg() {
		return productimg;
	}

	public void setProductimg(String productimg) {
		this.productimg = productimg;
	}

	public String getProductcontent() {
		return productcontent;
	}

	public void setProductcontent(String productcontent) {
		this.productcontent = productcontent;
	}

	@Override
	public String toString() {
		return "ProductIntro{" +
				"productid=" + productid +
				", producttitle='" + producttitle + '\'' +
				", productimg='" + productimg + '\'' +
				", productcontent='" + productcontent + '\'' +
				", isshow=" + isshow +
				'}';
	}
}
