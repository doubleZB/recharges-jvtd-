package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.ChargeOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChargeOrderMapper {

    int insert(ChargeOrder record);

    int insertSelective(ChargeOrder record);

    int updateStatusByOrderNum(@Param("orderNum") String orderNum,
                               @Param("status") Integer status,
                               @Param("supplyId") Integer supplyId);

    int updateStatusByOrderNumList(@Param("orderNums") List orderNums,
                               @Param("status") Integer status,
                               @Param("supplyId") Integer supplyId);

    int updateStatusByOrderNums(@Param("orderNum") String orderNum,
                               @Param("status") Integer status);


    /**
     * 更新回执状态
     * @param chargeOrder
     * @return
     */
    int updateReturnStatusByOrderNum(ChargeOrder chargeOrder);

    /**
     * 更新订单根据订单号,必须传订单号
     * @param chargeOrder
     * @return
     */
    int updateOrderByOrderNum(ChargeOrder chargeOrder);


    ChargeOrder selectOrderByOrderNum(@Param("table") String tableName,@Param("orderNum") String orderNum);


    ChargeOrder selectOrderByOrderNumAndCustomId(@Param("orderNum") String orderNum,
                                                 @Param("customId") String customId, @Param("phone") String phone);

    ChargeOrder selectOrderByMobileAndCustomId(@Param("mobile") String mobile,@Param("customId") String customId);

    /**
     * 商户端列表查询
     * @param chargeOrder
     * @return
     */
    List<ChargeOrder> getUserOrder(ChargeOrder chargeOrder);

    /**
     * 流量概况
     * @param chargeOrder
     * @return
     */
    List<ChargeOrder> floworderList(ChargeOrder chargeOrder);

    /**
     * 流量发送概况
     * @param chargeOrder
     * @return
     */
    List<ChargeOrder> floworderListup(ChargeOrder chargeOrder);

    /**
     * 话费概况
     * @param chargeOrder
     * @return
     */
    List<ChargeOrder> chargeorderList(ChargeOrder chargeOrder);

    /**
     * 话费发送概况
     * @param chargeOrder
     * @return
     */
    List<ChargeOrder> chargeorderListup(ChargeOrder chargeOrder);

    String getamountcount(ChargeOrder chargeOrder);

    /**
     * 根据时间查询相对应的订单，导出
     * @param chargeOrder
     * @return
     */
    List<ChargeOrder> selectUserOrder(ChargeOrder chargeOrder);
}