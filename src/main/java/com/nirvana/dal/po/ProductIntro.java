package com.nirvana.dal.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "productintro")
public class ProductIntro {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer productid;
	private String productimg;
	@Length(max=1000)
	private String productcontent;

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
		return "ProductIntro [productid=" + productid + ", productimg=" + productimg + ", productcontent="
				+ productcontent + "]";
	}

}