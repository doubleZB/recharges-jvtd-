package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.ChargeOrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChargeOrderDetailMapper {


    int insertSelective(ChargeOrderDetail record);

    int updateByChannelNum(ChargeOrderDetail chargeOrderDetail);

    /**
     * 动态查询
     * @param tableName
     * @param channelNum
     * @return
     */
    ChargeOrderDetail selectOrderNumByChannelNum(@Param("charge_order_detail") String tableName,
                                                 @Param("channelNum") String channelNum);


    /**
     * 动态查询
     * @param channelNum
     * @return
     */
    ChargeOrderDetail selectOrderNumBySupplyChannelNumMobile(@Param("supplyChannelNum") String channelNum,@Param("mobile") String mobile);

    /**
     * 动态查询
     * @param channelNum
     * @return
     */
    List<ChargeOrderDetail> selectOrderNumBySupplyChannelNum(@Param("supplyChannelNum") String channelNum);

}