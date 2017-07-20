package com.datav.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.datav.mybatis.model.BusinessAmount;
import com.datav.mybatis.model.DataVParamVo;

import java.io.IOException;
import java.util.List;

/**
 * Created by xiaozhi on 2017/6/27.
 */
public interface DataVService {
    JSONObject getIndustryInfo(String start_time,String end_time);
    JSONObject getClientInfo(String start_time,String end_time);
    JSONObject getAgentsOrUserInfo(String start_time, String end_time);
    JSONObject getTotalMoney(String start_time,String end_time);
    JSONObject getOrderPoints(String start_time,String end_time) throws IOException;

    JSONObject getCityMoneyInfo(String start_time,String end_time) throws IOException;
    List<DataVParamVo> gettest(String time1,String time2);
}
