package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.bean.BalanceRecord;
import com.jtd.recharge.dao.bean.UserBalances;
import com.jtd.recharge.dao.po.UserBalance;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface UserBalanceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBalance record);

    int insertSelective(UserBalance record);

    UserBalance selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBalance record);

    int updateByPrimaryKey(UserBalance record);

    UserBalance selectUserBalanceByUserId(Integer userId);


    /**
     * 扣费
     * @param userId
     * @param feeAmount
     * @return
     */
    int updateBalance(@Param("userId") int userId,
                      @Param("feeAmount") BigDecimal feeAmount);
    /**
     * 退款
     * @param userId 用户id
     * @param refundAmount 退款金额
     * @return
     */
    int updateBalanceByRefund(@Param("userId") int userId,
                              @Param("refundAmount") BigDecimal refundAmount);

    //新增balance表
    int insertBalance(UserBalance balance);
}