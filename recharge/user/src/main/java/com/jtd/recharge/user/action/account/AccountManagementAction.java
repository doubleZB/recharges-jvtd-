package com.jtd.recharge.user.action.account;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.base.util.CommonUtil;
import com.jtd.recharge.dao.bean.Order;
import com.jtd.recharge.dao.bean.UserBalances;
import com.jtd.recharge.dao.bean.util.SHAUtil;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserApp;
import com.jtd.recharge.dao.po.UserBalance;
import com.jtd.recharge.dao.po.UserBalanceDetail;
import com.jtd.recharge.service.user.BalanceService;
import com.jtd.recharge.service.user.UserAppService;
import com.jtd.recharge.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lhm on 2017/3/8.
 * 主子账户
 */
@Controller
@RequestMapping("/user")
public class AccountManagementAction {
    @Resource
    public UserService userService;
    @Resource
    public UserAppService userAppService;
    @Resource
    public BalanceService balanceService;

    private Log log = LogFactory.getLog(this.getClass());

    /**
     * 子账户管理
     * @return
     */
    @RequestMapping("/subAccountManagement")
    public String subAccountManagement() {
        return "/admin/subAccountManagement";
    }

    /**
     * 子账户列表页面
     * @param pageNumber
     * @param pageSize
     * @param userBalances
     * @return
     */
    @RequestMapping("/selectAccountUserList")
    @ResponseBody
    public Object selectUserList(HttpServletRequest request,Integer pageNumber, Integer pageSize, UserBalances userBalances) throws ParseException {
        User users =(User) request.getSession().getAttribute("users");
        Integer userId = users.getId();
        userBalances.setpId(userId);
        PageInfo<UserBalances> list = balanceService.getUserBalanceList(pageNumber,10,userBalances);
        for(UserBalances balances :list.getList()){
            Date registerTime = balances.getRegisterTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
            if(registerTime!=null){
                String formatOne = format.format(registerTime);
                balances.setRegisterTimeFormat(formatOne);
            }
        }
        return list;
    }

    /**
     * 验证用户名唯一
     * @param
     * @return
     */
    @RequestMapping("/checkOnlyName")
    @ResponseBody
    public List<User> selectUserName(User user){
        return userService.selectUserNameMobile(user);
    }

    /**
     * 添加子账户
     * @param user
     * @return
     */
    @RequestMapping("insertUserSon")
    @ResponseBody
    public boolean insertUserSon(HttpServletRequest request,User user) throws Exception {

        String newPassword = request.getParameter("password");
        //密码加密
        String shaPassword = SHAUtil.shaEncode(newPassword);
        user.setPassword(shaPassword);

        //获取用户登录ID
        User users =(User) request.getSession().getAttribute("users");
        Integer userId = users.getId();
        user.setpId(userId);

        //添加中自动生成一个32位的字符串userSid
        String userSid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        user.setUserSid(userSid);

        //添加中自动生成一个32位的字符串userToken
        String token = UUID.randomUUID().toString().trim().replaceAll("-", "");
        user.setToken(token);

        //时间
        user.setRegisterTime(new Date());
        user.setPushSum(User.pushSumStatus.STATUS_THREE);
        //添加子账户
        int i =userService.insertUserSon(user);
        if(i>0){
            //添加余额表
            UserBalance balance = new UserBalance();
            Integer userID = user.getId();
            balance.setUserId(userID);
            userService.addBalance(balance);


            //根据用户主ID查看主用户应用
            UserApp userApp = new UserApp();
            userApp.setUserId(userId);
            List<UserApp> userApps = userAppService.selectUserAppByUserId(userApp);
            for(UserApp app : userApps){

                Integer appType = app.getAppType();
                Integer groupId = app.getGroupId();
                Integer isCache = app.getIsCache();
                Integer chargeType = app.getChargeType();

                //根据主账户的应用信息添加到子账户中
                UserApp userAppTwo = new UserApp();
                userAppTwo.setUserId(userID);
                userAppTwo.setAppType(appType);
                userAppTwo.setGroupId(groupId);
                userAppTwo.setIsCache(isCache);
                userAppTwo.setChargeType(chargeType);
                userAppService.addUserApp(userAppTwo);
            }
            return true;
        }else {
            return false;
        }
}

    /**
     * 修改子账户
     * @param request
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping("updateUserSon")
    @ResponseBody
    public boolean updateUserSon(HttpServletRequest request,User user) throws Exception {
        String newPassword = request.getParameter("password");
        if(newPassword!=null && !"".equals(newPassword)){
            //密码加密
            SHAUtil shaUtil = new SHAUtil();
            String shaPassword = shaUtil.shaEncode(newPassword);
            user.setPassword(shaPassword);
        }

        //时间
        user.setRegisterTime(new Date());

        int i = userService.updateUserSon(user);
        if(i>0){
            return true;
        }else {
            return false;
        }
    }

    /**
     *  调拨款项从主账户划入子账户
     * @param userBalances
     * @return
     */
    @RequestMapping("selectUserMasterAccount")
    @ResponseBody
    public Object selectUserMasterAccount(HttpServletRequest request,UserBalances userBalances){
        //获取用户登录ID
        User users =(User) request.getSession().getAttribute("users");
        Integer userId = users.getId();
        userBalances.setUserId(userId);

        List<UserBalances> balances = userService.selectUserBalanceList(userBalances);
        return balances;
    }

    /**
     *  调拨款项从子账户划入主账户
     * @param userBalances
     * @return
     */
    @RequestMapping("selectUserChildAccount")
    @ResponseBody
    public Object selectUserChildAccount(UserBalances userBalances){
        List<UserBalances> balances = userService.selectUserBalanceList(userBalances);
        return balances;
    }

    /**
     *  商户端修改调款金额从主账户划入子账户
     * @param userBalances
     * @return
     */
    @RequestMapping("updateUserBalance")
    @ResponseBody
    public boolean updateUserBalanceByUserId(UserBalances userBalances){
        boolean tru = false;
        try{
            //修改主账户余额
            userService.updateUserBalanceByUserId(userBalances);
            tru = true;
        }catch (Exception e){
            tru = false;
        }
        return tru;
    }

    /**
     *  商户端修改调款金额从子账户划入主账户
     * @param userBalances
     * @return
     */
    @RequestMapping("updateUserBalanceSon")
    @ResponseBody
    public boolean updateUserBalanceSonByUserId(UserBalances userBalances){
        BigDecimal userBalance = userBalances.getUserBalance();
        //父级userId
        Integer userId = userBalances.getUserId();
        //子级userId
        Integer pId = userBalances.getpId();
        log.info("userBalance======================="+userBalance);
        log.info("主serId======================="+userId);
        log.info("子userId======================="+pId);

        userBalances.setUserId(pId);
        userBalances.setpId(userId);

        boolean tru = false;
        try{
            //修改主账户余额
            userService.updateUserBalanceByUserIdSon(userBalances);
            tru = true;
        }catch (Exception e){
            tru = false;
        }
        return tru;
    }

}
