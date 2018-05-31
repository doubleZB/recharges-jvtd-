package com.jtd.recharge.service.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.base.util.CommonUtil;
import com.jtd.recharge.dao.bean.BalanceRecord;
import com.jtd.recharge.dao.mapper.BalanceRecordMapper;
import com.jtd.recharge.dao.mapper.UserBalanceDetailMapper;
import com.jtd.recharge.dao.mapper.UserBalanceMapper;
import com.jtd.recharge.dao.mapper.UserBalanceRecordMapper;
import com.jtd.recharge.dao.po.UserBalanceDetail;
import com.jtd.recharge.service.admin.OperateLogService;
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
 * Created by lihuimin on 2016/11/16.
 * 商户加款记录
 */
@Service
@Transactional(readOnly = true)
public class BalanceRecordService {

    @Resource
    UserBalanceRecordMapper userBalancerecordMapper;
    @Resource
    UserBalanceDetailMapper userBalanceDetailMapper;
    @Resource
    UserBalanceMapper userBalanceMapper;
    @Resource
    BalanceRecordMapper balanceRecordMapper;
    @Resource
    public OperateLogService operateLogService;

    /**
     * 商户加款审核
     * @param pageNumber
     * @param pageSize
     * @param bal
     * @return
     */
    public PageInfo<BalanceRecord> selectBalanceRecord(Integer pageNumber, Integer pageSize, BalanceRecord bal) {
        PageHelper.startPage(pageNumber,pageSize,"update_time desc");
        Map map = new HashMap();
        map.put("userName",bal.getUserName());
        map.put("addType",bal.getAddType());
        List<BalanceRecord> user = balanceRecordMapper.selectBalanceRecord(map);
        PageInfo<BalanceRecord> pageInfo = new PageInfo<>(user);
        return pageInfo;
    }


    /**
     * 商户加款记录
     * @param pageNumber
     * @param pageSize
     * @param bal
     * @return
     */
    public PageInfo<BalanceRecord> selectBalanceRecordH(Integer pageNumber, Integer pageSize, BalanceRecord bal) {
        PageHelper.startPage(pageNumber,pageSize,"update_time desc");
        Map map = new HashMap();
        map.put("timeStart",bal.getTimeStart());
        map.put("timeEnd",bal.getTimeEnd());
        map.put("userCnName",bal.getUserCnName());
        map.put("addType",bal.getAddType());
        List<BalanceRecord> user = balanceRecordMapper.selectBalanceRecordH(map);
        PageInfo<BalanceRecord> pageInfo = new PageInfo<>(user);
        return pageInfo;
    }


    /**
     * 审核不通过
     * @param bal
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public int DeleteBalanceRecord(BalanceRecord bal) {
        return balanceRecordMapper.deleteBalanceRecord(bal);
    }

    /**
     * 审核通过
     * @param
     * @param
     * @param ids  @return
     * @param adminId
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public synchronized String  AddRecordList(String ids, String adminId, String operate) {
        String[] split = ids.split(",");
        String content="";
        for(int i=0;i<split.length;i++) {
            BalanceRecord bal = new BalanceRecord();
            bal.setId(Integer.parseInt(split[i]));
            bal.setAuditorId(Integer.parseInt(adminId));
            bal.setStatus(BalanceRecord.statusType.UNAUDITED);
            //修改审核状态
            BalanceRecord record = balanceRecordMapper.selectRecord(bal);
            /**
             * 获取加款，借款，还款，金额
             */
            String amount =record.getAmount();
            BigDecimal borrows=new BigDecimal(amount);

            //获取用户id
            Integer userid = record.getUserId();

            //查询用户余额表中的信息根据用户id
            BalanceRecord bal3 = balanceRecordMapper.selectBlance(record);
            //查询出借款额度 //构造以字符串内容为值的BigDecimal类型的变量
            BigDecimal borrowbalance = bal3.getBorrowBalance();
            //修改用户金额
            if(record.getAddType()==BalanceRecord.statusAddType.GATHERING){
                /**
                 * 加款部分
                 */
                //收款额度>=借款额度  返回的结果是int类型,-1表示小于,0是等于,1是大于.
                int i1 = borrows.compareTo(borrowbalance);
                //重新赋值
                record.setUserBalance(borrows);
                BalanceRecord balance;
                if(i1>=0){
                    //借款金额就为0
                    balanceRecordMapper.updateBalanceList(record);

                    /**
                     * 根据用户id在去查询balance表余额
                     */
                    balance = balanceRecordMapper.selectBlance(record);

                    /**
                     * 加款之后的余额，余额变动,变动后余额，借款变动金额,当前借款金额,进行修改
                     */
                    //加款金额-账户借款金额
                    bal.setBalanceChange(borrows.subtract(borrowbalance));
                    bal.setBalanceNow(balance.getUserBalance());
                    bal.setBorrowChange(borrowbalance);
                    bal.setBorrowNow(balance.getBorrowBalance());
                    bal.setStatus(BalanceRecord.statusType.CHECKED);
                    balanceRecordMapper.updateRecordList(bal);
                    operateLogService.logInfo(operate,"商户加款审核",operate+"修改：id="+Integer.parseInt(split[i])+"  审核信息为审核通过");
                }else{
                    //如果收款额度小于借款额度
                    balanceRecordMapper.updateBalanceList2(record);

                    /**
                     * 根据用户id在去查询balance表余额
                     */
                    balance = balanceRecordMapper.selectBlance(record);

                    /**
                     * 加款之后的余额，余额变动,变动后余额，借款变动金额,当前借款金额,进行修改
                     */
                    bal.setBalanceChange(new BigDecimal(0));
                    bal.setBalanceNow(balance.getUserBalance());
                    bal.setBorrowChange(borrows);
                    bal.setBorrowNow(balance.getBorrowBalance());
                    bal.setStatus(BalanceRecord.statusType.CHECKED);
                    balanceRecordMapper.updateRecordList(bal);
                    operateLogService.logInfo(operate,"商户加款审核",operate+"修改：id="+Integer.parseInt(split[i])+"  审核信息为审核通过");
                }


                //根据用户id添加user_balance_detail表
                UserBalanceDetail detail = new UserBalanceDetail();
                //收款存入详情表中 重新赋值
                detail.setCategory(UserBalanceDetail.Category.RECHARGE);
                //入帐
                detail.setBillType(UserBalanceDetail.BillType.ENTRY);
                //帐单流水
                CommonUtil commonUtil = new CommonUtil();
                String payNum = commonUtil.getPayNum();
                //付给账单流水
                detail.setSequence(payNum);
                //账单描述
                detail.setDescription("充值");
                //转换类型
                BigDecimal bigamount = new BigDecimal(amount);
                //赋值给detail表中金额
                detail.setAmount(bigamount);
                //赋值给detail表中userid
                detail.setUserId(userid);
                //获取账户余额
                BigDecimal userbalance = balance.getUserBalance();
                //赋值给detail表中用户余额
                detail.setBalance(userbalance);
                //系统时间
                detail.setUpdateTime(new Date());

                /**
                 * 添加balance_detail表中数据
                 */
                userBalanceDetailMapper.insertBalanceDetail(detail);

                content="Success";
            }else if(record.getAddType()==BalanceRecord.statusAddType.BORROW_MONEY){
                /**
                 * 借款部分
                 */
                record.setBorrowBalance(borrows);
                balanceRecordMapper.updateBalanceList1(record);

                //根据用户id在去查询balance表余额
                BalanceRecord balance = balanceRecordMapper.selectBlance(record);

                /**
                 * 借款之后的余额，余额变动,变动后余额，借款变动金额,当前借款金额,进行修改
                 */
                bal.setBalanceChange(borrows);
                bal.setBalanceNow(balance.getUserBalance());
                bal.setBorrowChange(borrows);
                bal.setBorrowNow(balance.getBorrowBalance());
                bal.setStatus(BalanceRecord.statusType.CHECKED);
                balanceRecordMapper.updateRecordList(bal);
                operateLogService.logInfo(operate,"商户加款审核",operate+"修改：id="+Integer.parseInt(split[i])+"  审核信息为审核通过");

                //获取用户余额
                BigDecimal balancet = balance.getUserBalance();
                //根据用户id添加user_balance_detail表
                UserBalanceDetail detail = new UserBalanceDetail();
                //借款存入详情表中 重新赋值
                detail.setCategory(UserBalanceDetail.Category.BORROW);
                //出帐
                detail.setBillType(UserBalanceDetail.BillType.CHARGE_OFF);
                //帐单流水
                CommonUtil commonUtil = new CommonUtil();
                String payNum = commonUtil.getPayNum();
                //付给账单流水
                detail.setSequence(payNum);
                //账单描述
                detail.setDescription("借款");
                //转换类型
                BigDecimal bigamount = new BigDecimal(amount);
                //赋值给detail表中金额
                detail.setAmount(bigamount);
                //赋值给detail表中userid
                detail.setUserId(userid);
                //赋值给detail表中用户余额
                detail.setBalance(balancet);
                //系统时间
                detail.setUpdateTime(new Date());
                /**
                 * 添加balance_detail表中数据
                 */
                userBalanceDetailMapper.insertBalanceDetail(detail);

                content="Success";
            }else if(record.getAddType()==BalanceRecord.statusAddType.REDUCE_MONEY){
                /**
                 * 还款部分
                 */
                //查询余额
                BigDecimal userBalanceThree = bal3.getUserBalance();

                //账户余额>=还款款额度  返回的结果是int类型,-1表示小于,0是等于,1是大于.
                int Three = userBalanceThree.compareTo(borrows);
                if(Three>=0){
                    //收款额度>=原借款款额度  返回的结果是int类型,-1表示小于,0是等于,1是大于.
                    int Two = borrows.compareTo(borrowbalance);

                    //重新赋值还款
                    record.setUserBalance(borrows);
                    BalanceRecord balance = null;
                    //如果还款额度大于借款额度（借款为0，余额-还款）
                    if(Two>=0){
                        //借款金额就为0
                        balanceRecordMapper.updateBalanceListThree(record);
                        /**
                         * 根据用户id在去查询balance表余额
                         */
                        balance = balanceRecordMapper.selectBlance(record);

                        /**
                         * 还款之后的余额，余额变动,变动后余额，借款变动金额,当前借款金额,进行修改
                         */
                        //加款金额-账户借款金额
                        bal.setBalanceChange(borrows);
                        bal.setBalanceNow(balance.getUserBalance());
                        bal.setBorrowChange(borrowbalance);
                        bal.setBorrowNow(balance.getBorrowBalance());
                        bal.setStatus(BalanceRecord.statusType.CHECKED);
                        balanceRecordMapper.updateRecordList(bal);
                        operateLogService.logInfo(operate,"商户加款审核",operate+"修改：id="+Integer.parseInt(split[i])+"  审核信息为审核通过");
                    }else{
                        //如果还款额度小于借款额度（-借款-余额）
                        balanceRecordMapper.updateBalanceListTwo(record);
                        /**
                         * 根据用户id在去查询balance表余额
                         */
                        balance = balanceRecordMapper.selectBlance(record);

                        /**
                         * 还款之后的余额，余额变动,变动后余额，借款变动金额,当前借款金额,进行修改
                         */
                        bal.setBalanceChange(borrows);
                        bal.setBalanceNow(balance.getUserBalance());
                        bal.setBorrowChange(borrows);
                        bal.setBorrowNow(balance.getBorrowBalance());
                        bal.setStatus(BalanceRecord.statusType.CHECKED);
                        balanceRecordMapper.updateRecordList(bal);
                        operateLogService.logInfo(operate,"商户加款审核",operate+"修改：id="+Integer.parseInt(split[i])+"  审核信息为审核通过");
                    }
                    //获取用户余额
                    BigDecimal balancet = balance.getUserBalance();
                    //根据用户id添加user_balance_detail表
                    UserBalanceDetail detail = new UserBalanceDetail();
                    //借款存入详情表中 重新赋值
                    detail.setCategory(UserBalanceDetail.Category.REDUCE_MONEY);
                    //出帐
                    detail.setBillType(UserBalanceDetail.BillType.CHARGE_OFF);
                    //帐单流水
                    CommonUtil commonUtil = new CommonUtil();
                    String payNum = commonUtil.getPayNum();
                    //付给账单流水
                    detail.setSequence(payNum);
                    //账单描述
                    detail.setDescription("还款");
                    //转换类型
                    BigDecimal bigamount = new BigDecimal(amount);
                    //赋值给detail表中金额
                    detail.setAmount(bigamount);
                    //赋值给detail表中userid
                    detail.setUserId(userid);
                    //赋值给detail表中用户余额
                    detail.setBalance(balancet);
                    //系统时间
                    detail.setUpdateTime(new Date());
                    //添加balance_detail表中数据
                    userBalanceDetailMapper.insertBalanceDetail(detail);
                    content="Success";
                }else{
                    content="序号:"+Integer.parseInt(split[i])+"还款金额大于账户余额";
                }
            }
        }
        return content;
    }

}
