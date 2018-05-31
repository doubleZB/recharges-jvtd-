package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.bean.UserBalances;

import java.util.List;
import java.util.Map;

/**
 * 商户余额公用接口
 * Created by Administrator on 2016/12/19.
 */
public interface UserBalancesMapper {

    //账户余额查询
    List<UserBalances> selectBalanceList(Map map);

    int updateBybalance(UserBalances userbalance);

    //商户结算配置时验证用户账户名
    List<UserBalances> selectBalanceLists(UserBalances balance);

    int updateByUser(UserBalances userbalance);

    List<UserBalances> selectUserBalance(UserBalances balance);

    List<UserBalances> selectUserBalanceByUserId(UserBalances balance);

    //子账户列表页面
    List<UserBalances> selectUserBalanceList(UserBalances userBalances);

    /**
     * 商户端修改主账户调款金额    ( 商户端修改调款金额从主账户划入子账户)
     * @param userBalances
     * @return
     */
    int updateUserBalanceByUserId(UserBalances userBalances);

    /**
     * 商户端修改子账户调款金额
     * @param userBalances
     * @return
     */
    int updateUserBalanceByUserIdSon(UserBalances userBalances);

    /**
     * 商户账户余额统计
     * @param balance
     * @return
     */
    List<UserBalances> selectBalanceStatisticsBalance(UserBalances balance);
}
