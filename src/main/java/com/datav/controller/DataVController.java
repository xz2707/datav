package com.datav.controller;

import com.datav.common.pojo.MessageRsp;
import com.datav.common.pojo.StaticValue;
import com.datav.mybatis.model.DataVParamVo;
import com.datav.service.DataVService;
import com.datav.service.impl.DataVServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by xiaozhi on 2017/6/27.
 */
@Controller
@RequestMapping(value = "/datav")
public class DataVController {
//    @Autowired
//    RestTemplate restTemplate;

private final static Logger log = LoggerFactory.getLogger(DataVServiceImpl.class);

    @Autowired
    private DataVService dataVService;

    @RequestMapping(value = "/cityMoney", method = RequestMethod.GET)
    @ResponseBody
    public MessageRsp getCityMoneyInfo(HttpServletRequest req) {
        String start_time = String.valueOf(req.getParameter("start_time"));
        String end_time = String.valueOf(req.getParameter("end_time"));
        MessageRsp rsp = new MessageRsp();
        try{
            rsp.setData(dataVService.getCityMoneyInfo(start_time,end_time));
        }catch (Exception e){
            log.error("获取时间段内:城市销售额失败", e);
            rsp.setErrorcode(StaticValue.ERROR);
            rsp.setErrormsg("获取时间段内:城市销售额失败");
        }

        return rsp;
    }

    //每个月订单的点位
    @RequestMapping(value = "/orderPoints", method = RequestMethod.GET)
    @ResponseBody
    public MessageRsp getOrderPoints(HttpServletRequest req){
        String start_time = String.valueOf(req.getParameter("start_time"));
        String end_time = String.valueOf(req.getParameter("end_time"));
        MessageRsp rsp = new MessageRsp();
        try{
            rsp.setData(dataVService.getOrderPoints(start_time,end_time));
        }catch (Exception e){
            log.error("获取时间段内:订单点位失败", e);
            rsp.setErrorcode(StaticValue.ERROR);
            rsp.setErrormsg("获取时间段内:订单点位失败");
        }

        return rsp;
    }

    //广告组行业占比,行业+数量
    @RequestMapping(value = "/industry", method = RequestMethod.GET)
    @ResponseBody
    public MessageRsp getIndustryInfo(HttpServletRequest req) {
        String start_time = String.valueOf(req.getParameter("start_time"));
        String end_time = String.valueOf(req.getParameter("end_time"));
        MessageRsp rsp = new MessageRsp();
        try{
            rsp.setData(dataVService.getIndustryInfo(start_time,end_time));
        }catch (Exception e){
            log.error("获取时间段内:行业占比失败", e);
            rsp.setErrorcode(StaticValue.ERROR);
            rsp.setErrormsg("获取时间段内:行业占比失败");
        }

        return rsp;
    }

    //客户销售额
    @RequestMapping(value = "/clientMoney", method = RequestMethod.GET)
    @ResponseBody
    public MessageRsp getClientInfo(HttpServletRequest req) {
        String start_time = String.valueOf(req.getParameter("start_time"));
        String end_time = String.valueOf(req.getParameter("end_time"));
        MessageRsp rsp = new MessageRsp();
        try{
            rsp.setData(dataVService.getClientInfo(start_time,end_time));
        }catch (Exception e){
            log.error("获取时间段内客户销售失败", e);
            rsp.setErrorcode(StaticValue.ERROR);
            rsp.setErrormsg("获取时间段内客户销售失败");
        }

        return rsp;
    }

    //业务员,代理商业绩
    @RequestMapping(value = "/agentOrSale", method = RequestMethod.GET)
    @ResponseBody
    public MessageRsp getAgentsOrUserInfo(HttpServletRequest req) {
        String start_time = String.valueOf(req.getParameter("start_time"));
        String end_time = String.valueOf(req.getParameter("end_time"));
        MessageRsp rsp = new MessageRsp();
        try{
            rsp.setData(dataVService.getAgentsOrUserInfo(start_time,end_time));
        }catch (Exception e){
            log.error("获取时间段内客户及业务员信息失败", e);
            rsp.setErrorcode(StaticValue.ERROR);
            rsp.setErrormsg("获取时间段内客户及业务员信息失败");
        }

        return rsp;
    }

    //季度累计销售额
    @RequestMapping(value = "/totalMoney", method = RequestMethod.GET)
    @ResponseBody
    public MessageRsp getTotalMoneyInfo(HttpServletRequest req) {
        String start_time = String.valueOf(req.getParameter("start_time"));
        String end_time = String.valueOf(req.getParameter("end_time"));
        MessageRsp rsp = new MessageRsp();
        try{
            rsp.setData(dataVService.getTotalMoney(start_time,end_time));
        }catch (Exception e){
            log.error("获取时间段内累计销售额失败", e);
            rsp.setErrorcode(StaticValue.ERROR);
            rsp.setErrormsg("获取时间段内累计销售额失败");
        }

        return rsp;
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public List<DataVParamVo> gettest() {
        String time1 = "1496332800";
        String time2 = "1498800249";
//        restTemplate.getForEntity("http://192.168.3.194:9591/data/DataSelectOrderInfo?orderId=1",String.class,JSONObject.class);
        return dataVService.gettest(time1,time2);
    }
}
