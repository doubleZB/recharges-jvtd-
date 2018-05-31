package com.jtd.recharge.service.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.CommonUtil;
import com.jtd.recharge.dao.bean.BalanceRecord;
import com.jtd.recharge.dao.bean.UserBalances;
import com.jtd.recharge.dao.mapper.BalanceRecordMapper;
import com.jtd.recharge.dao.mapper.UserBalanceDetailMapper;
import com.jtd.recharge.dao.mapper.UserBalanceMapper;
import com.jtd.recharge.dao.mapper.UserBalancesMapper;
import com.jtd.recharge.dao.po.UserBalance;
import com.jtd.recharge.dao.po.UserBalanceDetail;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @autor lihuimin
 * 余额service,
 */
@Service
@Transactional(readOnly = true)
public class BalanceService {

    @Resource
    UserBalanceMapper userBalanceMapper;

    @Resource
    UserBalancesMapper userBalancesMapper;
    @Resource
    UserBalanceDetailMapper userBalanceDetailMapper;
    @Resource
    BalanceRecordMapper balanceRecordMapper;

    public UserBalance queryBalanceByUserId(Integer userId) {
        return userBalanceMapper.selectUserBalanceByUserId(userId);
    }

    /**
     * 账户余额查询
     * @param pageNumber
     * @param pageSize
     * @param balance
     * @return
     */
    public PageInfo<UserBalances> getBalanceList(Integer pageNumber, Integer pageSize, UserBalances balance) {
        PageHelper.startPage(pageNumber,pageSize);
        Map map = new HashMap();
        map.put("userCnName",balance.getUserCnName());
        map.put("userName",balance.getUserName());
        map.put("status",balance.getStatus());
        map.put("orderBalance",balance.getOrderBalance());
        map.put("orderBorrow",balance.getOrderBorrow());
        map.put("orderCredit",balance.getOrderCredit());
        List<UserBalances> bal = userBalancesMapper.selectBalanceList(map);
        PageInfo<UserBalances> pageInfo = new PageInfo<>(bal);
        return pageInfo;
    }

    /**
     * 商户加款
     * @param becord
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int AddRecordList(BalanceRecord becord) {
        return balanceRecordMapper.insertRecordList(becord);
    }

    /**
     * 退款
     * @param userId 用户id
     * @param amount 退款金额
     * @param businessType 业务类型
     * @param orderNum 订单号
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public void refund(int userId, BigDecimal amount, int businessType, String orderNum) {
        //退款
        userBalanceMapper.updateBalanceByRefund(userId, amount);

        //添加帐单明细
        UserBalanceDetail userBalanceDetail = new UserBalanceDetail();
        userBalanceDetail.setUserId(userId);
        userBalanceDetail.setSequence(CommonUtil.getPayNum());
        userBalanceDetail.setBillType(UserBalanceDetail.BillType.ENTRY);
        userBalanceDetail.setCategory(UserBalanceDetail.Category.REFUND);
        if(SysConstants.BusinessType.flow == businessType) {
            userBalanceDetail.setDescription("流量退款-" + orderNum);
        } else {
            userBalanceDetail.setDescription("话费退款-" + orderNum);
        }
        userBalanceDetail.setAmount(amount);

        UserBalance userBalance = userBalanceMapper.selectUserBalanceByUserId(userId);
        userBalanceDetail.setBalance(userBalance.getUserBalance());
        userBalanceDetail.setUpdateTime(new Date());

        userBalanceDetailMapper.insertSelective(userBalanceDetail);
    }

    /**
     * 商户结算管理修改balance
     * @param userbalance
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int UpdateByBalance(UserBalances userbalance) {
        return userBalancesMapper.updateBybalance(userbalance);
    }

    /**
     * 商户结算配置时验证用户账户名
     * @param balance
     * @return
     */
    public List<UserBalances> selectBalanceLists(UserBalances balance) {
        return userBalancesMapper.selectBalanceLists(balance);
    }

    /**
     * 商户结算管理修改user
     * @param userbalance
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int UpdateByUser(UserBalances userbalance) {
       return userBalancesMapper.updateByUser(userbalance);
    }

    /**
     * 商户借款判断
     * @param becord
     * @return
     */

    public BalanceRecord selectBlanceList(BalanceRecord becord) {
       return balanceRecordMapper.selectBlanceList(becord);
    }

    /**
     * 子账户列表页面
     * @param pageNumber
     * @param pageSize
     * @param userBalances
     * @return
     */
    public PageInfo<UserBalances> getUserBalanceList(Integer pageNumber,  Integer pageSize,  UserBalances userBalances) {
        PageHelper.startPage(pageNumber,pageSize,"id desc");
        List<UserBalances> bal = userBalancesMapper.selectUserBalanceList(userBalances);
        PageInfo<UserBalances> pageInfo = new PageInfo<>(bal);
        return pageInfo;
    }

    /**
     * 根据用户ID去查询加款审核表中未审核的信息
     * @param balanceRecord
     * @return
     */
    public BalanceRecord selectUserBalanceRecordByUserId(BalanceRecord balanceRecord) {
        return balanceRecordMapper.selectRecord(balanceRecord);
    }
    /**
     * 商户账户余额统计
     * @param balance
     * @return
     */
    public List<UserBalances> selectBalanceStatisticsBalance(UserBalances balance) {
        return userBalancesMapper.selectBalanceStatisticsBalance(balance);
    }
}
