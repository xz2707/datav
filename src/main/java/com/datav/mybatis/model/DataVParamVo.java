package com.datav.mybatis.model;

/**
 * dataV 相关业务数据
 * 订单,代理商,客户行业
 * Created by xiaozhi on 2017/6/29.
 */
public class DataVParamVo extends OrderPo{
    private String client_name;     //客户名字 t_clientinfo表中
    private int industry_id;        //客户行业ID t_clientinfo表中
    private int industry_num;       //行业条数

    public int getIndustry_num() {
        return industry_num;
    }

    public void setIndustry_num(int industry_num) {
        this.industry_num = industry_num;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public int getIndustry_id() {
        return industry_id;
    }

    public void setIndustry_id(int industry_id) {
        this.industry_id = industry_id;
    }
}
