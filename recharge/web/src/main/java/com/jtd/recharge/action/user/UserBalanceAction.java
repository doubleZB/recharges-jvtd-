package com.jtd.recharge.action.user;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.BalanceRecord;
import com.jtd.recharge.dao.bean.Order;
import com.jtd.recharge.dao.bean.UserBalances;
import com.jtd.recharge.dao.po.ChargeOperateLogs;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.service.admin.OperateLogService;
import com.jtd.recharge.service.user.BalanceService;
import com.jtd.recharge.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by lihuimin on 2016/11/16.
 * 商户余额
 */
@Controller
@RequestMapping("/user")
public class UserBalanceAction {
    @Resource
    public BalanceService balanceService;
    @Resource
    public OperateLogService operateLogService;
    @Resource
    public UserService userService;
    /**
     * 商户账户余额
     * @param request
     * @param pageNumber
     * @param pageSize
     * @param balance
     * @param type1
     * @return
     */
    @RequestMapping("/balance")
    public String merchantbalance(HttpServletRequest request,Integer pageNumber, Integer pageSize, UserBalances balance, String type1){
        PageInfo<UserBalances> list = null;
        List<UserBalances> listTwo=null;
        int i = Integer.parseInt(type1);
        if(i==0){
            list = balanceService.getBalanceList(1,10,balance);
            //计算交易额
            listTwo =  balanceService.selectBalanceStatisticsBalance(balance);
        }else {
            list = balanceService.getBalanceList(pageNumber,pageSize,balance);
            //计算交易额
            listTwo =  balanceService.selectBalanceStatisticsBalance(balance);
        }
        request.setAttribute("list", list);
        request.setAttribute("listTwo", listTwo);
        request.setAttribute("balance",balance);
        return "/user/merchantbalance";
    }

    /**
     * 加款时验证用户账户名
     * @param pageNumber
     * @param pageSize
     * @param balance
     * @param type1
     * @return
     */
    @RequestMapping("/balance1")
    @ResponseBody
    public PageInfo<UserBalances> balance1(Integer pageNumber, Integer pageSize, UserBalances balance, String type1){
        PageInfo<UserBalances> list = null;
        int i = Integer.parseInt(type1);
        if(i==0){
            list = balanceService.getBalanceList(1,10,balance);
        }else {
            list = balanceService.getBalanceList(pageNumber,pageSize,balance);
        }
        return list;
    }

    /**
     * 根据用户ID去查询加款审核表中未审核的信息
     * @param balanceRecord
     * @return
     */
    @RequestMapping("/selectUserBalanceRecordByUserId")
    @ResponseBody
    public Object selectUserBalanceRecordByUserId(BalanceRecord balanceRecord){
        balanceRecord.setStatus(BalanceRecord.statusType.UNAUDITED);
        BalanceRecord balance = balanceService.selectUserBalanceRecordByUserId(balanceRecord);
        if(balance!=null && !balance.equals("")){
            return 1;
        }else{
            return 2;
        }
    }
    /**
     * 商户加款
     * @param balanceRecord
     * @return
     */
    @RequestMapping("/toBalance")
    @ResponseBody
    public boolean toBalance(BalanceRecord balanceRecord, HttpServletRequest request){
        Integer addType = balanceRecord.getAddType();

        String operate = request.getParameter("operate");
        String userName = balanceRecord.getUserName();

        //获取凭证
//        String receive_voucher = request.getParameter("receiveVoucher");
//        becord.setReceive_voucher(receive_voucher);
        String proposer = request.getParameter("adminId");
        balanceRecord.setProposerId(Integer.parseInt(proposer));
        //加款
        String amountOne = request.getParameter("amount");
        String amountTwo = request.getParameter("borrowMoney");
        String amountThree = request.getParameter("deductionsMoney");
        if(addType==1){
            balanceRecord.setAmount(amountOne);
            int i = balanceService.AddRecordList(balanceRecord);
            if(i>0){
                //添加日志
                operateLogService.logInfo(operate,"商户账户余额",operate+"为账户名："+userName+" 用户加款："+amountOne+"元");
                return true;
            }else{
                return false;
            }
        }else if(addType==2){
            //借款
            balanceRecord.setAmount(amountTwo);
            int i =balanceService.AddRecordList(balanceRecord);
            if(i>0){
                //添加日志
                operateLogService.logInfo(operate,"商户账户余额",operate+"为账户名："+userName+" 用户借款："+amountTwo+"元");
                return true;
            }else{
                return false;
            }
        }else{
            //减款
            balanceRecord.setAmount(amountThree);
            int i =balanceService.AddRecordList(balanceRecord);
            if(i>0){
                //添加日志
                operateLogService.logInfo(operate,"商户账户余额",operate+"为账户名："+userName+" 用户减款："+amountThree+"元");
                return true;
            }else{
                return false;
            }
        }
    }


    /**
     * 商户借款判断
     * @param becord
     * @return
     */
    @RequestMapping("/lookBalance")
    @ResponseBody
    public boolean lookBalance(BalanceRecord becord, HttpServletRequest request){
        String str = request.getParameter("amount");
        Integer id = becord.getId();
        becord.setUserId(id);
        BalanceRecord b = balanceService.selectBlanceList(becord);
        BigDecimal user_balance = b.getUserBalance();
        BigDecimal bigDecimal = new BigDecimal(str);
        if(bigDecimal.compareTo(user_balance)==-1){
            return true;
        }else{
            return false;
        }
    }


    /**
     *  根据用户ID去查看用户余额
     *  * @param userBalances
     * @return
     */
    @RequestMapping("selectUserMasterAccount")
    @ResponseBody
    public Object selectUserMasterAccount(UserBalances userBalances){
        List<UserBalances> balances = userService.selectUserBalanceList(userBalances);
        return balances;
    }


    /**
     * 商户结算管理
     * @return
     */
    @RequestMapping("/settlement")
    public String merchantsettlement(){
        return "/user/merchantsettlement";
    }


    /**
     * 商户结算配置时验证用户账户名
     * @param balance
     * @return
     */
    @RequestMapping("/checkeBalance")
    @ResponseBody
    public List<UserBalances> checkeBalance( UserBalances balance){
        List<UserBalances> list = balanceService.selectBalanceLists(balance);
        return list;
    }

    /**
     * 商户结算管理
     * @return
     */
    @RequestMapping("/empowerlist")
    @ResponseBody
    public boolean empowerlist(UserBalances user_balance){
        String operate = user_balance.getUserAllName();
        String userName = user_balance.getUserName();
        Integer isCredit = user_balance.getIsCredit();
        if(isCredit==1){
            balanceService.UpdateByUser(user_balance);
            int i = balanceService.UpdateByBalance(user_balance);
            if(i>0){
                //添加日志
                operateLogService.logInfo(operate,"商户结算管理",operate+"为账户名："+userName+" 用户配置授信金额："+user_balance.getCreditBalance()+"元");
                return true;
            }
            return true;
        }else{
            balanceService.UpdateByUser(user_balance);
            operateLogService.logInfo(operate,"商户结算管理",operate+"为账户名："+userName+" 用户配置：不允许授信");
            return true;
        }


    }

}
