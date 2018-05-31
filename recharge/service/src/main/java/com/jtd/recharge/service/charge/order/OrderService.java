package com.jtd.recharge.service.charge.order;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.base.util.CommonUtil;
import com.jtd.recharge.dao.bean.Order;
import com.jtd.recharge.dao.mapper.ChargeOrderDetailMapper;
import com.jtd.recharge.dao.mapper.ChargeOrderMapper;
import com.jtd.recharge.dao.mapper.OrderDetailMapper;
import com.jtd.recharge.dao.mapper.UserMapper;
import com.jtd.recharge.dao.po.ChargeOrder;
import com.jtd.recharge.dao.po.User;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 订单
 * lihuimin on 2016/11/22.
 */
@Service
public class OrderService {
    @Resource
    ChargeOrderMapper chargeOrderMapper;

    @Resource
    public UserMapper userMapper;

    @Resource
    OrderDetailMapper orderDetailMapper;
    @Resource
    ChargeOrderDetailMapper chargeOrderDetailMapper;
    /**
     * 流量话费交易列表
     * @param pageNumber
     * @param pageSize
     * @param order
     * @return
     */
    public PageInfo<Order> selectOrder(String subData,Integer pageNumber, Integer pageSize, Order order) {
        List<Integer> moneyList = new ArrayList<>();
        List<String> mobileList = new ArrayList<>();
        if(subData!=null && !subData.equals("")){
            JSONObject jsonObject = new JSONObject(subData);
            String moneyIds = jsonObject.get("mz").toString();
            moneyIds = moneyIds.replace("[","").replace("]","").trim();
            String ids[] = null;
            if(!"".equals(moneyIds)){
                ids = moneyIds.split(",");
                for (int i = 0; i <ids.length ; i++) {
                    moneyList.add(Integer.parseInt(ids[i].substring(1,ids[i].length()-1)));
                }
            }
        }
        PageHelper.startPage(pageNumber,pageSize,"order_time desc");
        HashMap map = new HashMap();
        map.put("orderTimeOne",order.getOrderTimeOne());
        map.put("orderTimeTwo",order.getOrderTimeTwo());
        if(order.getRechargeMobile()!=null && !"".equals(order.getRechargeMobile())){
            String[] m = order.getRechargeMobile().split(",");
            for (String mo:m){
                mobileList.add(mo);
            }
            map.put("rechargeMobile",mobileList);
        }
        map.put("userCnName",order.getUserCnName());
        map.put("customId",order.getCustomId());
        map.put("orderNum",order.getOrderNum());
        map.put("supplyName",order.getSupplyName());
        map.put("operator",order.getOperator());
        map.put("val1",order.getVal1());
        map.put("businessType",order.getBusinessType());
        map.put("source",order.getSource());
        map.put("status",order.getStatus());
        map.put("pushStatus",order.getPushStatus());
        map.put("artificialRefund",order.getArtificialRefund());
        if(moneyList!=null && !moneyList.isEmpty()){
            map.put("positionCodes",moneyList);
        }
        map.put("order",order.getOrder());
        List<Order> orders =  orderDetailMapper.selectOrder(map);
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        return pageInfo;
    }

    /**
     * 计算交易额
     * @param
     * @return
     */
    public List<Order> selectOrderList(String subData,Order order) {
        List<Integer> moneyList = new ArrayList<>();
        List<String> mobileList = new ArrayList<>();
        if(subData!=null && !subData.equals("")) {
            JSONObject jsonObject = new JSONObject(subData);
            String moneyIds = jsonObject.get("mz").toString();
            moneyIds = moneyIds.replace("[", "").replace("]", "").trim();
            String ids[] = null;
            if (!"".equals(moneyIds)) {
                ids = moneyIds.split(",");
                for (int i = 0; i < ids.length; i++) {
                    moneyList.add(Integer.parseInt(ids[i].substring(1, ids[i].length() - 1)));
                }
            }
        }
        HashMap map = new HashMap();
        map.put("orderTimeOne",order.getOrderTimeOne());
        map.put("orderTimeTwo",order.getOrderTimeTwo());
        if(order.getRechargeMobile()!=null && !"".equals(order.getRechargeMobile())){
            String[] m = order.getRechargeMobile().split(",");
            for (String mo:m){
                mobileList.add(mo);
            }
            map.put("rechargeMobile",mobileList);
        }
        if(order.getArtificialRefund()!=null && !"".equals(order.getArtificialRefund())){
            map.put("artificialRefund",order.getArtificialRefund());
        }
        map.put("userCnName",order.getUserCnName());
        map.put("customId",order.getCustomId());
        map.put("orderNum",order.getOrderNum());
        map.put("supplyName",order.getSupplyName());
        map.put("operator",order.getOperator());
        map.put("val1",order.getVal1());
        map.put("businessType",order.getBusinessType());
        map.put("source",order.getSource());
        map.put("status",order.getStatus());
        map.put("pushStatus",order.getPushStatus());
        if(moneyList!=null && !moneyList.isEmpty()){
            map.put("positionCodes",moneyList);
        }
        map.put("order",order.getOrder());
        return orderDetailMapper.selectOrderList(map);
    }


    /**
     * 流量话费销售交易列表
     * @param pageNumber
     * @param pageSize
     * @param order
     * @return
     */
    public PageInfo<Order> selectMarketOrder(Integer pageNumber, Integer pageSize, Order order) {
        HashMap map = new HashMap();
        HashMap hashMap = new HashMap();
        List<Integer> userMarketOrder = new ArrayList<>();
        List<String> mobileList = new ArrayList<>();
        PageInfo<Order> pageInfo = null;
        //全称代替销售名字进行查询
        if(order.getUserAllName()!=null && !"".equals(order.getUserAllName())){
            map.put("sells",order.getUserAllName());
        }
        //获取用户id
        List<User> userList = userMapper.getUserList(map);
        Integer id ;
        for (int i=0;i<userList.size();i++){
            User user = userList.get(i);
            id = user.getId();
            //根据用户id去查询子账户信息
            User userTwo = new User();
            userTwo.setpId(id);
            userMarketOrder.add(id);
            List<User> userListTwo = userMapper.selectUserListByPId(userTwo);
            if(userListTwo!=null&&userListTwo.size()>0) {
                for (int j = 0; j < userListTwo.size(); j++) {
                    User userThree = userListTwo.get(j);
                    id = userThree.getId();
                    userMarketOrder.add(id);
                }
            }
        }
        if(userMarketOrder!=null && !userMarketOrder.isEmpty()){
            hashMap.put("userMarketOrderId", userMarketOrder);
        }else{
            userMarketOrder.add(0);
            hashMap.put("userMarketOrderId", userMarketOrder);
        }
        PageHelper.startPage(pageNumber, pageSize, "order_time desc");
        hashMap.put("orderTimeOne", order.getOrderTimeOne());
        hashMap.put("orderTimeTwo", order.getOrderTimeTwo());
        if (order.getRechargeMobile() != null && !"".equals(order.getRechargeMobile())) {
            String[] m = order.getRechargeMobile().split(",");
            for (String mo : m) {
                mobileList.add(mo);
            }
            hashMap.put("rechargeMobile", mobileList);
        }
        hashMap.put("userCnName", order.getUserCnName());
        hashMap.put("customId", order.getCustomId());
        hashMap.put("orderNum", order.getOrderNum());
        hashMap.put("operator", order.getOperator());
        hashMap.put("val1", order.getVal1());
        hashMap.put("businessType", order.getBusinessType());
        hashMap.put("status", order.getStatus());
        hashMap.put("order", order.getOrder());
        List<Order> orders = orderDetailMapper.selectMarketOrder(hashMap);
        pageInfo = new PageInfo<>(orders);
        return pageInfo;
    }


    /**
     * 计算销售订单交易额
     * @param
     * @return
     */
    public List<Order> selectMarketOrderListMoney(Order order) {
        HashMap map = new HashMap();
        HashMap hashMap = new HashMap();
        List<Integer> userMarketOrderMoney = new ArrayList<>();
        List<String> mobileList = new ArrayList<>();
        //全称代替销售名字进行查询
        if(order.getUserAllName()!=null && !"".equals(order.getUserAllName())){
            map.put("sells",order.getUserAllName());
        }
        List<User> userList = userMapper.getUserList(map);
        //获取用户id
        Integer id;
        for (int i=0;i<userList.size();i++){
            User user = userList.get(i);
            id = user.getId();
            //根据用户id去查询子账户信息
            User userTwo = new User();
            userTwo.setpId(id);
            userMarketOrderMoney.add(id);
            List<User> userListTwo = userMapper.selectUserListByPId(userTwo);
            if(userListTwo!=null&&userListTwo.size()>0) {
                for (int j = 0; j < userListTwo.size(); j++) {
                    User userThree = userListTwo.get(j);
                    id = userThree.getId();
                    userMarketOrderMoney.add(id);
                }
            }
        }
        if(userMarketOrderMoney!=null && !userMarketOrderMoney.isEmpty()){
            hashMap.put("userMarketOrderId",userMarketOrderMoney);
        }else{
            userMarketOrderMoney.add(0);
            hashMap.put("userMarketOrderId", userMarketOrderMoney);
        }
        hashMap.put("orderTimeOne",order.getOrderTimeOne());
        hashMap.put("orderTimeTwo",order.getOrderTimeTwo());
        if(order.getRechargeMobile()!=null && !"".equals(order.getRechargeMobile())){
            String[] m = order.getRechargeMobile().split(",");
            for (String mo:m){
                mobileList.add(mo);
            }
            hashMap.put("rechargeMobile",mobileList);
        }
        hashMap.put("userCnName",order.getUserCnName());
        hashMap.put("customId",order.getCustomId());
        hashMap.put("orderNum",order.getOrderNum());
        hashMap.put("operator",order.getOperator());
        hashMap.put("val1",order.getVal1());
        hashMap.put("businessType",order.getBusinessType());
        hashMap.put("status",order.getStatus());
        hashMap.put("order",order.getOrder());
        return orderDetailMapper.selectMarketOrderMoney(hashMap);
    }



    /**
     * 充值流水查询
     * @param order
     * @return
     */
    public List<Order> selectOrders(Order order) {
        Map map = new HashMap();
        map.put("order",order.getOrder());
        map.put("orderNum",order.getOrderNum());
        return orderDetailMapper.selectOrders(map);
    }
    /**
     * 充值流水详情
     * @param order
     * @return
     */
    public List<Order> selectDatail(Order order) {
        Map map = new HashMap();
        map.put("order",order.getOrder());
        map.put("orderNum",order.getOrderNum());
        map.put("orderTime",order.getOrderTimeOne());
        map.put("order_detail",order.getOrder_detail());

        return orderDetailMapper.selectDatail(map);
    }



    /**
     * 商户端列表查询
     * @param pageNumber
     * @param pageSize
     * @param chargeorder
     * @return
     */
    public PageInfo<ChargeOrder> getUserOrder(Integer pageNumber, Integer pageSize, ChargeOrder chargeorder) {
        PageHelper.startPage(pageNumber,pageSize,"id desc");
        List<ChargeOrder> orders =  chargeOrderMapper.getUserOrder(chargeorder);
        PageInfo<ChargeOrder> pageInfo = new PageInfo<>(orders);
        return pageInfo;
    }

    /**
     * 流量概况
     * @param chargeOrder
     * @return
     */
    public List<ChargeOrder> floworderList(ChargeOrder chargeOrder) {
        return chargeOrderMapper.floworderList(chargeOrder);
    }

    /**
     * 商户端消费统计查询
     * @param chargeOrder
     * @return
     */
    public String getamountcount(ChargeOrder chargeOrder){

        return chargeOrderMapper .getamountcount(chargeOrder);
    }
    /**
     * 话费概况
     * @param chargeOrder
     * @return
     */
    public List<ChargeOrder> chargeorderList(ChargeOrder chargeOrder) {
        return chargeOrderMapper.chargeorderList(chargeOrder);
    }
    /**
     * 流量发送概况
     * @param chargeOrder
     * @return
     */
    public List<ChargeOrder> floworderListup(ChargeOrder chargeOrder) {
        return chargeOrderMapper.floworderListup(chargeOrder);
    }
    /**
     * 话费发送概况
     * @param chargeOrder
     * @return
     */
    public List<ChargeOrder> chargeorderListup(ChargeOrder chargeOrder) {
        return chargeOrderMapper.chargeorderListup(chargeOrder);
    }

    /**
     * 结果未知查询
     * @param pageNumber
     * @param pageSize
     * @param order
     * @return
     */
    public PageInfo<Order> selectResultUnknownOrderList(String subData,Integer pageNumber, Integer pageSize, Order order) {
        JSONObject jsonObject = new JSONObject(subData);
        String moneyIds = jsonObject.get("mz").toString();
        moneyIds = moneyIds.replace("[","").replace("]","").trim();
        List<Integer> moneyList = new ArrayList<>();
        String ids[] = null;
        if(!"".equals(moneyIds)){
            ids = moneyIds.split(",");
            for (int i = 0; i <ids.length ; i++) {
                moneyList.add(Integer.parseInt(ids[i].substring(1,ids[i].length()-1)));
            }
        }
        PageHelper.startPage(pageNumber,pageSize,"submit_time desc");
        HashMap map = new HashMap();
        map.put("orderTimeOne",order.getOrderTimeOne());
        map.put("orderTimeTwo",order.getOrderTimeTwo());
        map.put("rechargeMobile",order.getRechargeMobile());
        map.put("userCnName",order.getUserCnName());
        map.put("supplyName",order.getSupplyName());
        map.put("operator",order.getOperator());
        map.put("val1",order.getVal1());
        map.put("businessType",order.getBusinessType());
        map.put("order",order.getOrder());
        if(moneyList!=null && !moneyList.isEmpty()){
            map.put("positionCodes",moneyList);
        }
        map.put("order_detail",order.getOrder_detail());
        List<Order> orders =  orderDetailMapper.selectResultUnknownOrderList(map);
        PageInfo<Order> pageInfo = new PageInfo<>(orders);
        return pageInfo;
    }

    /**
     * 设置成功
     * @param orders
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateOrderByStatus(Order orders) {
        return orderDetailMapper.updateOrderByStatus(orders);
    }

    /**
     * 更改详情订单状态
     * @param order
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateOrderDetailByStatus(Order order) {
        return orderDetailMapper.updateOrderDetailByStatus(order);
    }

    /**
     * 根据订单号查询订单及用户信息
     * @param
     * @return
     */
    public Order selectOrderByOrderNum(Order order ) {
        return orderDetailMapper.selectOrderByOrderNum(order);
    }

    /**
     * 根据时间查询相对应的订单，导出
     * @param chargeOrder
     * @return
     */
    public List<ChargeOrder> selectUserOrder(ChargeOrder chargeOrder) {
        return chargeOrderMapper.selectUserOrder(chargeOrder);
    }

    public  ChargeOrder getChargeOrder(String orderNum){
        ChargeOrder chargeOrder = chargeOrderMapper.selectOrderByOrderNum(CommonUtil.getOrderTableName(orderNum),orderNum);
        return chargeOrder;
    }

}
