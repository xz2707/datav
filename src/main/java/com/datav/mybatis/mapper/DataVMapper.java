package com.datav.mybatis.mapper;

import com.datav.mybatis.model.BusinessAmount;
import com.datav.mybatis.model.DataVParamVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by xiaozhi on 2017/6/28.
 */
public interface DataVMapper {
    //行业占比
    List<DataVParamVo> getIndustryInfo(@Param("start_time")String start_time, @Param("end_time")String end_time);

    List<DataVParamVo> getShopNumInfo(@Param("start_time")String start_time,@Param("end_time")String end_time);
    //客户销售额
    List<DataVParamVo> getClientInfo(@Param("start_time")String start_time,@Param("end_time")String end_time);
    //业务员,代理商,人数及总金额
    List<BusinessAmount> getAgentsOrUserInfo(@Param("start_time")String start_time, @Param("end_time")String end_time);
    //上季度累计销售额
    DataVParamVo getTotalSaleMoney(@Param("start_time")String start_time, @Param("end_time")String end_time);


    List<DataVParamVo> gettest(@Param("time1")String time1,@Param("time2")String time2);
}
