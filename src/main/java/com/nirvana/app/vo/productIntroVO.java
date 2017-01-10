package com.nirvana.app.vo;

import com.nirvana.dal.po.Community;
import com.nirvana.dal.po.ProductIntro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andeper on 2017/1/10.
 */
public class ProductIntroVO {
    private String producttitle;
    private String productimg;
    private String productcontent;

    public ProductIntroVO(ProductIntro productIntro) {
        this.producttitle = productIntro.getProducttitle();
        this.productimg = productIntro.getProductimg();
        this.productcontent = productIntro.getProductcontent();
    }

    public static List<ProductIntroVO> toListVO(List<ProductIntro> polist) {
        List<ProductIntroVO> list = new ArrayList<ProductIntroVO>();
        for (int i = 0; i < polist.size(); i++) {
            list.add(new ProductIntroVO(polist.get(i)));
        }
        return list;
    }
    public String getProducttitle() {
        return producttitle;
    }

    public void setProducttitle(String producttitle) {
        this.producttitle = producttitle;
    }

    public String getProductcontent() {
        return productcontent;
    }

    public void setProductcontent(String productcontent) {
        this.productcontent = productcontent;
    }

    public String getProductimg() {
        return productimg;
    }

    public void setProductimg(String productimg) {
        this.productimg = productimg;
    }


    @Override
    public String toString() {
        return "ProductIntroVO{" +
                "producttitle='" + producttitle + '\'' +
                ", productimg='" + productimg + '\'' +
                ", productcontent='" + productcontent + '\'' +
                '}';
    }
}
