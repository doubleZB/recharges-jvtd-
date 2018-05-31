package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.ChargeChannel;
import com.jtd.recharge.dao.po.ChargePosition;
import com.jtd.recharge.dao.po.ChargeSupply;
import com.jtd.recharge.dao.po.ChargeSupplyPosition;

import java.util.List;

public interface ChargeChannelMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChargeChannel record);

    int insertSelective(ChargeChannel record);

    ChargeChannel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChargeChannel record);

    int updateByPrimaryKey(ChargeChannel record);

    List<ChargeChannel> selectChannelByCondition(ChargeChannel chargeChannel);
    //获取供应商
    List<ChargeSupply> getSupplier();
    //获取产品
    List<ChargePosition>getProduct(ChargePosition chargePosition);
    //防止重复添加
    List<ChargeChannel> selectChannelInsert(ChargeChannel chargeChannel);

    /**
     * 渠道一键修改
     * @param record
     * @return
     */
    int updateAllSelective(ChargeChannel record);
    //获取相应渠道产品
    List<ChargeSupplyPosition>getSupplierProduct(ChargeSupplyPosition chargeSupplyPosition);


    int updateAllSwitch(ChargeChannel record);
}