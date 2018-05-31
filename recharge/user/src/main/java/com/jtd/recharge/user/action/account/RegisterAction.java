package com.jtd.recharge.user.action.account;

import com.jtd.recharge.dao.bean.util.SHAUtil;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserBalance;
import com.jtd.recharge.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户注册
 * Created by Administrator on 2017/4/10.
 */
@Controller
@RequestMapping("/register")
public class RegisterAction {
    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    public UserService userService;

    /**
     * 云通信协议
     * @return
     */
    @RequestMapping("/messageAgreement")
    public String checkUserName(){
        return "/admin/messageAgreement";
    }
    /**
     * 检查用户唯一性
     * @param user
     * @return
     */
    @RequestMapping("/checkUserName")
    @ResponseBody
    public Object checkUserName(User user){
        List<User> list =userService.selectUserNameMobile(user);
        return list;
    }

    /**
     * 添加注册用户
     * @param user
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Object addUser(User user) {
        //添加中自动生成一个32位的字符串
        String userSid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        user.setUserSid(userSid);
        //添加中自动生成一个32位的字符串
        String token = UUID.randomUUID().toString().trim().replaceAll("-", "");
        user.setToken(token);
        try {
            //SHA密码加密
            SHAUtil shaUtil = new SHAUtil();
            String shaPassword = user.getPassword();
            String shaPasswordOne = shaUtil.shaEncode(shaPassword);
            user.setPassword(shaPasswordOne);
            user.setStatus(User.pushSumStatus.STATUS_ONE);
            user.setRegisterTime(new Date());
            user.setPushSum(User.pushSumStatus.STATUS_THREE);
            int i =  userService.insertUserSon(user);
            if(i>0){
                //添加余额表
                UserBalance balance = new UserBalance();
                Integer ids = user.getId();
                balance.setUserId(ids);
                userService.addBalance(balance);
                //日志
                String userName = user.getUserName();
                log.info("添加用户"+userName+"成功");
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
           log.error("加密失败");
        }
        return false;
    }

}
