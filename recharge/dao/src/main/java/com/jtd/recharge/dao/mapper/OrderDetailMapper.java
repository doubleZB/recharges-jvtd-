package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.bean.Order;
import com.jtd.recharge.dao.po.ChargeOrder;
import com.jtd.recharge.dao.po.ChargeOrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderDetailMapper {
    /**
     *  流量话费交易列表
     * @param map
     * @return
     */
    List<Order> selectOrder(HashMap map);
    /**
     * 充值流水详情
     * @param
     * @return
     */
    List<Order> selectDatail(Map map);
    /**
     * 充值流水查询
     * @param
     * @return
     */
    List<Order> selectOrders(Map map);

    /**
     * 查询结果未知订单查询
     * @param map
     * @return
     */
    List<Order> selectResultUnknownOrderList(HashMap map);

    /**
     * 设置成功 更改订单状态
     * @param orders
     */
    int updateOrderByStatus(Order orders);

    /**
     * 计算交易额
     * @param map
     * @return
     */
    List<Order> selectOrderList(HashMap map);

    /**
     * 根据订单号查询订单及用户信息
     * @param order
     * @return
     */
    Order selectOrderByOrderNum(Order order);

    /**
     * 更改详情订单状态
     * @param order
     * @return
     */
    int updateOrderDetailByStatus(Order order);

    List<Order> selectMarketOrder(HashMap hashMap);

    List<Order> selectMarketOrderMoney(HashMap map);
}