package com.nirvana.app.vo;

import com.nirvana.dal.po.SolutionCase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andeper on 2017/1/10.
 */
public class SolutionCaseVO {
    private String casetitle;
    private String caseimg;
    private String casecontent;

    public SolutionCaseVO(SolutionCase solutionCase) {
        this.casetitle = solutionCase.getCasetitle();
        this.caseimg = solutionCase.getCaseimg();
        this.casecontent = solutionCase.getCasecontent();
    }

    public static List<SolutionCaseVO> toListVO(List<SolutionCase> polist) {
        List<SolutionCaseVO> list = new ArrayList<SolutionCaseVO>();
        for (int i = 0; i < polist.size(); i++) {
            list.add(new SolutionCaseVO(polist.get(i)));
        }
        return list;
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
        return "SolutionCaseVO{" +
                "casetitle='" + casetitle + '\'' +
                ", caseimg='" + caseimg + '\'' +
                ", casecontent='" + casecontent + '\'' +
                '}';
    }
}
