package com.jtd.recharge.service.iot;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jtd.recharge.base.util.CommonUtil;
import com.jtd.recharge.dao.mapper.UserBalanceDetailMapper;
import com.jtd.recharge.dao.mapper.UserBalanceMapper;
import com.jtd.recharge.dao.po.UserBalance;
import com.jtd.recharge.dao.po.UserBalanceDetail;

/**
 * Created by ${zyj} on 2018/4/2.
 */
@Service
public class UserBalanceService {
    /**
     * 根据userid获取userbalance信息
     */
    @Resource
    UserBalanceMapper userBalanceMapper;
    @Resource
    UserBalanceDetailMapper userBalanceDetailMapper;
    public UserBalance queryBalanceByUserId(Integer userId) {
        return   userBalanceMapper.selectUserBalanceByUserId(userId);
    }

    /**
     * 扣费操作
     * @param userId
     * @param feeAmount 扣费金额
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void updateBaleanceById(int userId,BigDecimal feeAmount,String orderNum) {
        //更新余额
        int i = userBalanceMapper.updateBalance(userId, feeAmount);
        if(i<=0){
            throw new IllegalStateException("订单"+orderNum+"扣费失败");
        }
        //添加帐单明细
        UserBalanceDetail userBalanceDetail = new UserBalanceDetail();
        userBalanceDetail.setUserId(userId);
        userBalanceDetail.setSequence(CommonUtil.getPayNum());
        userBalanceDetail.setBillType(UserBalanceDetail.BillType.CHARGE_OFF);
        userBalanceDetail.setCategory(UserBalanceDetail.Category.CONSUME);
        userBalanceDetail.setDescription(orderNum);
        userBalanceDetail.setAmount(feeAmount);

        UserBalance userBalance = userBalanceMapper.selectUserBalanceByUserId(userId);
        userBalanceDetail.setBalance(userBalance.getUserBalance());
        userBalanceDetail.setUpdateTime(new Date());

        userBalanceDetailMapper.insertSelective(userBalanceDetail);
    }

    /**
     * 退款
     * @param userId 用户id
     * @param amount 退款金额
     * @param orderNum 订单号
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void refund(int userId, BigDecimal amount, String orderNum) {
        //退款
        int i = userBalanceMapper.updateBalanceByRefund(userId, amount);
        if(i<=0){
            throw new IllegalStateException("订单"+orderNum+"退款失败");
        }
        //添加帐单明细
        UserBalanceDetail userBalanceDetail = new UserBalanceDetail();
        userBalanceDetail.setUserId(userId);
        userBalanceDetail.setSequence(CommonUtil.getPayNum());
        userBalanceDetail.setBillType(UserBalanceDetail.BillType.ENTRY);
        userBalanceDetail.setCategory(UserBalanceDetail.Category.REFUND);
        userBalanceDetail.setDescription(orderNum);
        userBalanceDetail.setAmount(amount);

        UserBalance userBalance = userBalanceMapper.selectUserBalanceByUserId(userId);
        userBalanceDetail.setBalance(userBalance.getUserBalance());
        userBalanceDetail.setUpdateTime(new Date());

        userBalanceDetailMapper.insertSelective(userBalanceDetail);
    }
}
