package com.nirvana.dal.po;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "solutioncase")
public class SolutionCase {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer caseid;
	private String casetitle;
	private String caseimg;
	@Length(max=10000)
	private String casecontent;

	public Integer getCaseid() {
		return caseid;
	}

	public void setCaseid(Integer caseid) {
		this.caseid = caseid;
	}

	public String getCasetitle() {
		return casetitle;
	}

	public void setCasetitle(String casetitle) {
		this.casetitle = casetitle;
	}

	public String getCaseimg() {
		return caseimg;
	}

	public void setCaseimg(String caseimg) {
		this.caseimg = caseimg;
	}

	public String getCasecontent() {
		return casecontent;
	}

	public void setCasecontent(String casecontent) {
		this.casecontent = casecontent;
	}

	@Override
	public String toString() {
		return "SolutionCase [caseid=" + caseid + ", casetitle=" + casetitle + ", caseimg=" + caseimg + ", casecontent="
				+ casecontent + "]";
	}

}
