package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.po.UserPayOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserPayOrderMapper {
    int deleteByPrimaryKey(Integer id);

    /**
     * 添加用户支付账单信息
     * @param userPayOrder
     */
    int insertUserOrder(UserPayOrder userPayOrder);

    /**
     * 查询用户支付列表
     * @return
     */
    List<UserPayOrder> selectUserOrder();

    /**
     * 查询用户支付列表根据条件
     * @param userPayOrder
     * @return
     */
    List<UserPayOrder> selectUserOrderByName(UserPayOrder userPayOrder);

    /**
     * 根据用户id查数据
     * @param id
     * @return
     */
    List<UserPayOrder> selectUserOrderDetails(@Param("id") Integer id);

    /**
     * 根据用户id修改审核状态
     */
    int  updateUsercheckCauseById(UserPayOrder userPayOrder);
}