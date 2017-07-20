package com.datav.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.datav.common.pojo.HttpAddress;
import com.datav.common.util.JhttpUtil;
import com.datav.common.util.Symbol;
import com.datav.mybatis.mapper.DataVMapper;
import com.datav.mybatis.model.BusinessAmount;
import com.datav.mybatis.model.DataVParamVo;
import com.datav.service.DataVService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xiaozhi on 2017/6/27.
 */
@Service
public class DataVServiceImpl implements DataVService{
    private final static Logger log = LoggerFactory.getLogger(DataVServiceImpl.class);

    @Autowired
    private HttpAddress httpAddress;
    @Autowired
    private DataVMapper dataVMapper;

    @Override
    public JSONObject getCityMoneyInfo(String start_time,String end_time) throws IOException {
        List<DataVParamVo> dataVParamVos = dataVMapper.getShopNumInfo(start_time,end_time);
        if(dataVParamVos == null || dataVParamVos.isEmpty()){
            log.info("===数据库===>>数据为空或异常");
            return null;
        }
        JSONArray shopNumArray = new JSONArray();//符合条件的购物车ID集合
        double total_money = 0;//订单总金额
        for (DataVParamVo dp : dataVParamVos) {
            total_money += dp.getAd_contextprice().doubleValue();
            shopNumArray.add(dp.getAd_point());
        }
        JSONObject shopNumObject = new JSONObject();
        shopNumObject.put("orderId",shopNumArray);
        //向媒介中心请求订单城市相关信息
        log.info(httpAddress.getMediaAddress()+"/data/DataSelectOrderInfo");
        JSONObject jsonObject = JhttpUtil.instance.sendHttpPostRequestByJson(httpAddress.getMediaAddress()+"/data/DataSelectOrderInfo",shopNumObject.toString(),JSONObject.class,JSONObject.class);
        if(jsonObject == null || jsonObject.isEmpty()){
            log.info("===>>向媒介中心请求城市相关信息返回空");
            return null;
        }
        log.info("===媒介中心返回数据::"+jsonObject.toString());
        JSONArray cityArray = (JSONArray) jsonObject.get("data");
        //数组里包含多个{"adcode":"610400","amount":5219,"shopnum":"130@1498615673"}
        Map<String,Integer> adcodeMap = new HashMap<>();
        int total_amount = 0;
        for (Object o : cityArray) {
            JSONObject cityJson = (JSONObject) o;
            String adcode = String.valueOf(cityJson.get("adcode"));
            int amount = Integer.parseInt(String.valueOf(cityJson.get("amount")));
            String shopnum = String.valueOf(cityJson.get("shopnum"));
            total_amount += amount;//购物车总金额
            if(adcodeMap.containsKey(adcode)){
                int t_amount = adcodeMap.get(adcode)+amount;
                adcodeMap.put(adcode,t_amount);
            }else{
                adcodeMap.put(adcode,amount);
            }
        }
        Map<String,Double> adcode_float_map = new HashMap<>();
        for (Map.Entry<String, Integer> param : adcodeMap.entrySet()) {
            adcode_float_map.put(param.getKey(), (double)param.getValue() / total_amount);
        }
        JSONObject cityJson = new JSONObject();
        for (Map.Entry<String, Double> entry : adcode_float_map.entrySet()) {
            cityJson.put(entry.getKey(), entry.getValue() * total_money);
        }

        return cityJson;
    }

    @Override
    public JSONObject getOrderPoints(String start_time, String end_time) throws IOException {
        List<DataVParamVo> dataVParamVos = dataVMapper.getShopNumInfo(start_time,end_time);
        if(dataVParamVos == null || dataVParamVos.isEmpty()){
            log.info("===数据库===>>数据为空或异常");
            return null;
        }
        JSONArray shopNumArray = new JSONArray();//符合条件的购物车ID集合
        dataVParamVos.stream().forEach(dp -> shopNumArray.add(dp.getAd_point()));
        JSONObject shopNumObject = new JSONObject();
        shopNumObject.put("orderId",shopNumArray);
        //向媒介中心请求订单点位信息
        log.info(httpAddress.getMediaAddress()+"/data/DataSelectOrderPoint");
        JSONObject jsonObject = JhttpUtil.instance.sendHttpPostRequestByJson(httpAddress.getMediaAddress()+"/data/DataSelectOrderPoint",shopNumObject.toString(),JSONObject.class,JSONObject.class);
        if(jsonObject == null || jsonObject.isEmpty()){
            log.info("===>>向媒介中心请求订单点位信息返回空");
            return null;
        }
        JSONObject object = null;
        if("0".equals(String.valueOf(jsonObject.get("code")))){//请求成功
            object = (JSONObject) jsonObject.get("data");
        }
        return object;
    }

    /**
     * 获取行业及其数目
     * @param start_time
     * @param end_time
     * @return
     */
    @Override
    public JSONObject getIndustryInfo(String start_time,String end_time){
        List<DataVParamVo> industryInfo= dataVMapper.getIndustryInfo(start_time,end_time);
        if(industryInfo == null || industryInfo.isEmpty()){
            log.info("===数据库===>>数据为空或异常");
            return null;
        }
        JSONObject jsonObject = new JSONObject();
        industryInfo.stream().forEach( param ->{
            try {
                log.info(httpAddress.getLabelAddress()+"/saletag/label/"+param.getIndustry_id());
                String result = JhttpUtil.instance.sendHttpGetRequest(httpAddress.getLabelAddress()+"/saletag/label/"+param.getIndustry_id());
                JSONObject jsonParam = JSON.parseObject(result);
                JSONObject industryJson = (JSONObject) jsonParam.get("data");
                String labelName = String.valueOf(industryJson.get("labelName"));
                jsonObject.put(labelName,param.getIndustry_num());
            } catch (IOException e) {
                log.info("===>>获取标签名字异常");
                e.printStackTrace();
            }
        });

        return jsonObject;
    }

    /**
     * 获取客户及销售额
     * @param start_time
     * @param end_time
     * @return
     */
    @Override
    public JSONObject getClientInfo(String start_time, String end_time) {
        List<DataVParamVo> clientList = dataVMapper.getClientInfo(start_time,end_time);
        JSONObject jsonObject = new JSONObject();
        if(clientList == null || clientList.isEmpty()){
            log.info("===数据库===>>数据为空或异常");
            return jsonObject;
        }
        clientList.stream().forEach( cl -> jsonObject.put(cl.getClient_name(),cl.getAd_contextprice()));

        return jsonObject;
    }

    @Override
    public JSONObject getAgentsOrUserInfo(String start_time, String end_time) {
        List<BusinessAmount> list = dataVMapper.getAgentsOrUserInfo(start_time,end_time);
        JSONObject jsonObject = new JSONObject();
        if(list == null || list.isEmpty()){
            log.info("===数据库===>>数据为空或异常");
            return jsonObject;
        }
        list.stream().forEach( param ->{
            JSONObject agentJson = new JSONObject();
            agentJson.put("people_num",param.getPeople_num());
            agentJson.put("ad_contextprice",param.getAd_contextprice());
            if(param.getRole_id() == 1){//代理商
                jsonObject.put("agent",agentJson);
            }else if(param.getRole_id() == 2){//业务员
                jsonObject.put("sale_man",agentJson);
            }
        });

        return jsonObject;
    }

    @Override
    public JSONObject getTotalMoney(String start_time, String end_time) {
        DataVParamVo mongey = dataVMapper.getTotalSaleMoney(start_time,end_time);
        JSONObject jsonObject = new JSONObject();
        if (mongey == null){
            log.info("===数据库===>>数据为空或异常");
            return jsonObject;
        }
        jsonObject.put("data",mongey.getAd_contextprice());
        return jsonObject;
    }



    @Override
    public List<DataVParamVo> gettest(String time1,String time2) {
        return dataVMapper.gettest(time1,time2);
    }

    /**
     * 当前日期上月时间 毫秒
     * @return
     */
    private long getLastMonthTime(){
        //当前日期上月1号 2017-5-1 00:00:00
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);//今年
        int month = now.get(Calendar.MONTH);//上个月
        int day = 1;
        if(month == 0){
            year = year - 1;
            month = 12;
        }
        String lastMonth = Symbol.Empty +year+ Symbol.MINUS_LINE +month+ Symbol.MINUS_LINE +day+ Symbol.SPACE +"00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long time = 0;
        try {
            time = sdf.parse(lastMonth).getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
