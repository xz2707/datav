package com.datav.mybatis.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 业务员或代理商业绩
 * Created by xiaozhi on 2017/6/30.
 */
public class BusinessAmount implements Serializable{
    private static final long serialVersionUID = -139650862056368834L;
    private int role_id;//1:代理商 2:业务员
    private String people_num;   //业务员或代理商人数
    private BigDecimal ad_contextprice;//总金额

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getPeople_num() {
        return people_num;
    }

    public void setPeople_num(String people_num) {
        this.people_num = people_num;
    }

    public BigDecimal getAd_contextprice() {
        return ad_contextprice;
    }

    public void setAd_contextprice(BigDecimal ad_contextprice) {
        this.ad_contextprice = ad_contextprice;
    }
}
