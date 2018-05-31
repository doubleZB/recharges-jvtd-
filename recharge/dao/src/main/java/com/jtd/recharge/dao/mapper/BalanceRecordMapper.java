package com.jtd.recharge.dao.mapper;

import com.jtd.recharge.dao.bean.BalanceRecord;
import com.jtd.recharge.dao.po.User;

import java.util.List;
import java.util.Map;

/**
 * 商户加款,余额
 * Created by Administrator on 2016/12/19.
 */
public interface BalanceRecordMapper {

    //商户加款
    int insertRecordList(BalanceRecord becord);

    //商户借款判断
    BalanceRecord selectBlanceList(BalanceRecord becord);

    List<BalanceRecord> selectBalanceRecord(Map map);

    int deleteBalanceRecord(BalanceRecord bal);

    List<BalanceRecord> selectBalanceRecordH(Map map);

    int updateRecordList(BalanceRecord bal);

    int updateBalanceList(BalanceRecord bal2);

    BalanceRecord selectRecord(BalanceRecord record);

    //借款
    int updateBalanceList1(BalanceRecord record);

    //查询用户余额表中的信息根据用户id
    BalanceRecord selectBlance(BalanceRecord balance);


    //如果借款额度>收款额度
    int updateBalanceList2(BalanceRecord bal2);

    int insertRecordAddBorrows(BalanceRecord balanceRecord);

    //还款
    int updateBalanceListTwo(BalanceRecord record);
    //还款额度>=借款额度
    int updateBalanceListThree(BalanceRecord record);

    /**
     * 销售商户列表
     * @param map
     * @return
     */
    List<BalanceRecord> getMarketsUserList(Map map);
}
