package com.datav.mybatis.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单相关业务数据,与数据库 t_order 表中字段对应
 * Created by xiaozhi on 2017/6/29.
 */
public class OrderPo implements Serializable{
    private static final long serialVersionUID = -2199501070000519194L;

    private int id;//订单ID
    private int client_id;//客户ID
    private int user_id;//用户ID(业务员)
    private int agent_id;//代理商ID
    private int role_id;//1:代理商 2:业务员
    private BigDecimal ad_contextprice;//订单金额

    private String ad_point;//购物车编号(新加)
    private String retain_2;//签订合同时间(新加)

    @Override
    public String toString() {
        return "OrderPo{" +
                "id=" + id +
                ", client_id=" + client_id +
                ", user_id=" + user_id +
                ", agent_id=" + agent_id +
                ", role_id=" + role_id +
                ", ad_contextprice=" + ad_contextprice +
                ", ad_point='" + ad_point + '\'' +
                ", retain_2='" + retain_2 + '\'' +
                '}';
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(int agent_id) {
        this.agent_id = agent_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public BigDecimal getAd_contextprice() {
        return ad_contextprice;
    }

    public void setAd_contextprice(BigDecimal ad_contextprice) {
        this.ad_contextprice = ad_contextprice;
    }

    public String getAd_point() {
        return ad_point;
    }

    public void setAd_point(String ad_point) {
        this.ad_point = ad_point;
    }

    public String getRetain_2() {
        return retain_2;
    }

    public void setRetain_2(String retain_2) {
        this.retain_2 = retain_2;
    }
}
