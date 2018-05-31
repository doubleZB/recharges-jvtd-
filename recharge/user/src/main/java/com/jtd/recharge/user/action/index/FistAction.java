package com.jtd.recharge.user.action.index;

import com.jtd.recharge.dao.bean.UserBalances;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserApp;
import com.jtd.recharge.dao.po.UserAuth;
import com.jtd.recharge.service.user.UserAppService;
import com.jtd.recharge.service.user.UserAuthService;
import com.jtd.recharge.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.List;

/**
 * Created by lihuimin on 2016/11/30.
 */
@Controller
@RequestMapping("index")
public class FistAction {
    @Resource
    public UserService userService;
    @Resource
    private UserAuthService userAuthService;
    @Resource
    public UserAppService userAppService;
    /**
     * 开始进入云通信首页页面
     * @return
     */
    @RequestMapping("/fist")
    public String fistCommunication(HttpServletRequest request, UserBalances balance ) {
        User users =(User) request.getSession().getAttribute("users");
        Integer userId = users.getId();
        balance.setUserId(userId);
        //查询用户余额
        List<UserBalances> list = userService.selectUserBalanceList(balance);
        request.setAttribute("list",list);
        //查询用户认证信息
        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(userId);
        List<UserAuth> userAuthOne = userAuthService.selectUserAuthByID(userAuth);
        request.setAttribute("listTwo",userAuthOne);
        return "index/fist";
    }

    /**
     * 开始进入云通信首页页面
     * @return
     */
    @RequestMapping("/selectUserAppByUserId")
    @ResponseBody
    public Object fistCommunication(HttpServletRequest request) {
        User users =(User) request.getSession().getAttribute("users");
        Integer userId = users.getId();
        //根据用户主ID查看主用户应用
        UserApp userApp = new UserApp();
        userApp.setUserId(userId);
        List<UserApp> userApps = userAppService.selectUserAppByUserId(userApp);
        return userApps;
    }

    /**
     * 新增商户应用
     * @return
     */
    @RequestMapping("addUserApp")
    @ResponseBody
    public boolean addUserApp(HttpServletRequest request,UserApp userApp){
        User users =(User) request.getSession().getAttribute("users");
        Integer userId = users.getId();
        userApp.setUserId(userId);
        int i = userAppService.addUserApp(userApp);
        if(i>0){
            return true;
        }else{
            return false;
        }
    }

}
