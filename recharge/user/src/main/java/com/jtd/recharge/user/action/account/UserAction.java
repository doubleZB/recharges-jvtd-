package com.jtd.recharge.user.action.account;

import com.jtd.recharge.base.util.SmsUtil;
import com.jtd.recharge.dao.bean.UserBalances;
import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.bean.util.SHAUtil;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserApp;
import com.jtd.recharge.dao.po.UserAuth;
import com.jtd.recharge.dao.po.UserBalanceMonitor;

import com.jtd.recharge.service.user.UserAppService;
import com.jtd.recharge.service.user.UserAuthService;
import com.jtd.recharge.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @autor lhm
 * 云通信商户
 */
@Controller
@RequestMapping("/admin")
public class UserAction {


    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    public UserService userService;
    @Resource
    public UserAppService userAppService;
    @Resource
    private UserAuthService userAuthService;
       /**
     * 云通信首页
     * @return
     */
    @RequestMapping("/homePage")
    public String adminHomePage() {
        return "/homePage/index";
    }
       /**
     * 登录
     * @return
     */
    @RequestMapping("/login")
    public String adminLogin() {
        return "/admin/login";
    }

    /**
     * 判断用户是不是关闭状态登录的
     * @return
     */
    @RequestMapping("/checkUserNameStatus")
    @ResponseBody
    public Object checkUserNameStatus(HttpServletRequest request) throws Exception {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        //SHA密码加密
        SHAUtil shaUtil = new SHAUtil();
        String shaPasswords = shaUtil.shaEncode(password);
        User user = new User();
        user.setUserName(userName);
        user.setPassword(shaPasswords);
        User userOne = userService.UserList(user);
        if(userOne!=null){
            Integer status = userOne.getStatus();
            return status;
        }else{
            return 1;
        }
    }
    /**
     * 商户端登录页面后台
     * @param
     * @return
     */
    @RequestMapping("/loginOk")
    public String loginOk(HttpServletRequest request, User user) throws Exception {
        //第二次刷新再走一遍方法
        User users =(User) request.getSession().getAttribute("users");
        if (users!=null){
            UserApp userApp =new UserApp();
            //子账户
            if(users.getpId()!=null){
                Integer pId = users.getpId();
                userApp.setUserId(pId);
                log.info("userPId==============="+pId);
            }else{
                //主账户
                Integer id = users.getId();
                userApp.setUserId(id);
                log.info("userId==============="+id);
            }
            List<UserApp> userApps = userAppService.selectUserAppByUserId(userApp);
            request.setAttribute("userApp",userApps);
            request.getSession().setAttribute("users",users);
            request.getSession().setMaxInactiveInterval(60*60*3);
            request.getSession().setAttribute("userId",users.getId());
            return "index/index";
        }
        //密码为空进入登录页面
        String shaPassword = user.getPassword();
        if(shaPassword==null){
            return "/admin/login";
        }
        //SHA密码加密
        SHAUtil shaUtil = new SHAUtil();
        String shaPasswords = shaUtil.shaEncode(shaPassword);
        user.setPassword(shaPasswords);
        users = userService.UserList(user);

//        log.info("userPId==============="+users.getpId());
        if(users!=null){
            UserApp userApp =new UserApp();
            //子账户
            if(users.getpId()!=null){
                Integer pId = users.getpId();
                //商户应用
                userApp.setUserId(pId);
            }else{
               //主账户
                Integer id = users.getId();
                userApp.setUserId(id);
            }
            List<UserApp>  userApps = userAppService.selectUserAppByUserId(userApp);
            request.setAttribute("userApp",userApps);
            //设置session有效时间
            request.getSession().setAttribute("users",users);
            request.getSession().setMaxInactiveInterval(60*60*3);
            request.getSession().setAttribute("userId",users.getId());
            return "index/index";
        }
        request.setAttribute("msg","用户名密码错误！");
        return "/admin/login";
    }

    @RequestMapping("/register")
    public String register(){
        return "/admin/register";
    }

    /**
     * 忘记密码
     * @return
     */
    @RequestMapping("/forgetPassword")
    public String forgetPassword() {
        return "/admin/findPassword";
    }

    /**
     * 验证手机号码唯一
     * @param user
     * @return
     */
    @RequestMapping("checkMoble")
    @ResponseBody
    public Object checkMoble(User user){
        List<User> list =userService.selectUserNameMobile(user);
        return list;
    }

    /**
     * 获取手机验证码
     * @param request
     * @param user
     * @return
     */
    @RequestMapping("findCode")
    @ResponseBody
    public Object findCode(HttpServletRequest request,User user){
        int randNum = SmsUtil.getRandNum(1, 999999);
        SmsUtil.SendSms(user.getMobile(), "【聚通达】验证码：" + randNum);
        request.getSession().setAttribute("randNum",randNum);
        return randNum;
    }

    /**
     * 找回密码第二步
     * @param user
     * @param request
     * @return
     */
    @RequestMapping("nextOne")
    public String findCode(User user,HttpServletRequest request){
        List<User> list =userService.selectUserNameMobile(user);
        request.setAttribute("list",list);
        return "/admin/findPasswordTwo";
    }

    /**
     * 登出系统
     * @param request
     * @return
     */
    @RequestMapping("/LoginOut")
    public String LoginOut(HttpServletRequest request){
        //清空
        request.getSession().removeAttribute("users");
        return "/admin/login";
    }

    /**
     * 找回密码
     * @param user
     */
    @RequestMapping("/findPassword")
    @ResponseBody
    public Object findPassword(HttpServletRequest request,User user) throws Exception {
        String userName = request.getParameter("userName");
        String newPassword = request.getParameter("password");
        //加密
        SHAUtil shaUtil = new SHAUtil();
        user.setPassword(newPassword);
        user.setUserName(userName);
        String shaPassword = shaUtil.shaEncode(newPassword);
        user.setPassword(shaPassword);
        //修改密码
        userService.updatePassword(user);
        return true;
    }

    /**
     * 修改密码页面
     * @return
     */
    @RequestMapping("/administrate")
    public String administrate(){
        return "/admin/administrate";
    }

    /**
     * 修改密码
     * @param user
     * @return
     */
    @RequestMapping("/updatePassword")
    @ResponseBody
    public Object updatePassword(HttpServletRequest request, User user) throws Exception {
        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        String againPassword = request.getParameter("againPassword");
        String id = request.getParameter("id");
        ReturnMsg msg = new ReturnMsg();
        msg.setMessage("抱歉修改失败！");
        msg.setSuccess(false);
        User users =(User) request.getSession().getAttribute("users");
        String password = users.getPassword();
        log.info("用户原密码============="+password);
        SHAUtil shaUtil = new SHAUtil();
        if(oldPassword==null || "".equals(oldPassword)){
            msg.setMessage("参数有空值！");
        }else {
            oldPassword = shaUtil.shaEncode(oldPassword);
            if (oldPassword==null || "".equals(oldPassword)||
                    newPassword==null || "".equals(newPassword)||
                    againPassword==null || "".equals(againPassword)){
                    msg.setMessage("参数有空值！");
            }else if (!password.equals(oldPassword)){
                msg.setMessage("原始密码不正确！");
            }else if (!newPassword.equals(againPassword)){
                msg.setMessage("两次新密码输入不一致！");
            }else {
                user.setPassword(newPassword);
                user.setId(Integer.parseInt(id));
                try {
                    String shaPassword = shaUtil.shaEncode(newPassword);
                    user.setPassword(shaPassword);
                    userService.updatePassword(user);
                    users.setPassword(shaPassword);
                    request.getSession().setAttribute("users",users);
                    msg.setMessage("恭喜你修改成功！");
                    msg.setSuccess(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return msg;
    }

    /**
     * 首次支付密码修改
     * @param request
     * @param user
     * @return
     */
    @RequestMapping("updatePayPassword")
    @ResponseBody
    public boolean updatePayPassword(HttpServletRequest request,User user) throws Exception {
        String payPassword = request.getParameter("againPayPassword");
        String id = request.getParameter("id");
        User users =(User) request.getSession().getAttribute("users");
        //加密
        SHAUtil shaUtil = new SHAUtil();
        user.setId(Integer.parseInt(id));
        String shaPassword = shaUtil.shaEncode(payPassword);
        user.setPayPassword(shaPassword);
        int i = userService.updateUserPayPassword(user);
        users.setPayPassword(shaPassword);
        request.getSession().setAttribute("users",users);
        if(i>0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 再次支付密码修改
     * @param request
     * @param user
     * @return
     */
    @RequestMapping("/updateTwoPassword")
    @ResponseBody
    public Object updateTwoPassword(HttpServletRequest request, User user) throws Exception {
        String oldPayPassword = request.getParameter("oldPayPassword");
        String newPayPassword = request.getParameter("newPayPassword");
        String twoPayPassword = request.getParameter("twoPayPassword");
        String id = request.getParameter("id");
        ReturnMsg msg = new ReturnMsg();
        msg.setMessage("抱歉修改失败！");
        msg.setSuccess(false);
        User users =(User) request.getSession().getAttribute("users");
        SHAUtil shaUtil = new SHAUtil();
        if(oldPayPassword==null || "".equals(oldPayPassword)){
            msg.setMessage("参数有空值！");
        }else {
            oldPayPassword = shaUtil.shaEncode(oldPayPassword);
            if (oldPayPassword==null || "".equals(oldPayPassword)||
                    newPayPassword==null || "".equals(newPayPassword)||
                    twoPayPassword==null || "".equals(twoPayPassword)){
                msg.setMessage("参数有空值！");
            }else if (!users.getPayPassword().equals(oldPayPassword)){
                msg.setMessage("原始密码不正确！");
            }else if (!newPayPassword.equals(twoPayPassword)){
                msg.setMessage("两次新密码输入不一致！");
            }else {
                user.setPayPassword(newPayPassword);
                user.setId(Integer.parseInt(id));
                try {
                    String shaPassword = shaUtil.shaEncode(newPayPassword);
                    user.setPayPassword(shaPassword);
                    userService.updateUserPayPassword(user);
                    users.setPayPassword(shaPassword);
                    request.getSession().setAttribute("users",users);
                    msg.setMessage("恭喜你修改成功！");
                    msg.setSuccess(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return msg;
    }

    /**
     *余额不足提醒
     * @param balance
     * @return
     */
    @RequestMapping("/insufficientBalance")
    public String insufficientBalance(HttpServletRequest request,UserBalances balance){
        User users =(User) request.getSession().getAttribute("users");
        Integer userId = users.getId();
        balance.setUserId(userId);
        List<UserBalances> list = userService.selectUserBalanceByUserId(balance);

        UserBalanceMonitor userBalanceMonitor = new UserBalanceMonitor();
        userBalanceMonitor.setUserId(userId);
        List<UserBalanceMonitor> monitor = userService.selectUserBalanceMonitorByUserId(userBalanceMonitor);
        request.setAttribute("listTwo",monitor);
        request.setAttribute("list",list);
        return "/admin/insufficientBalance";
    }

    /**
     * 修改余额提示开关
     * @param userBalanceMonitor
     * @return
     */
    @RequestMapping("/userBalanceMonitor")
    @ResponseBody
    public boolean userBalanceMonitor(HttpServletRequest request,UserBalanceMonitor userBalanceMonitor){
        Integer status = userBalanceMonitor.getStatus();
        String id = request.getParameter("id");
        if(id==null || "".equals(id)){
            userService.insertUserBalanceMonitor(userBalanceMonitor);
            return true;
        }else{
            if(status==2){
                userService.updateUserBalanceMonitors(userBalanceMonitor);
            }else {
                userService.updateUserBalanceMonitor(userBalanceMonitor);
            }
            return true;
        }
    }

    /**
     * 账户信息详情
     * @return
     */
    @RequestMapping("userMessage")
    public String userMessage(HttpServletRequest request,User user){
        User users =(User) request.getSession().getAttribute("users");
        Integer id = users.getId();
        user.setId(id);
        users = userService.selectUserById(user);
        if(users!=null){
            //设置session有效时间
            request.getSession().setAttribute("users",users);
            UserAuth userAuth = new UserAuth();
            userAuth.setUserId(id);
            List<UserAuth> list = userAuthService.selectUserAuthByID(userAuth);
            if(list.size()>0) {
                for (UserAuth userAuthOne : list) {
                    Integer userType = userAuthOne.getUserType();
                    String businessLicenseImage = userAuthOne.getBusinessLicenseImage();
                    String businessLicenseNum = userAuthOne.getBusinessLicenseNum();
                    Integer authState = userAuthOne.getAuthState();
                    String identityCardBack = userAuthOne.getIdentityCardBack();
                    String identityCardFront = userAuthOne.getIdentityCardFront();
                    String identityCardNum = userAuthOne.getIdentityCardNum();
                    String name = userAuthOne.getName();
                    String remark = userAuthOne.getRemark();

                    UserAuth userAuthTwo = new UserAuth();
                    userAuthTwo.setRemark(remark);
                    userAuthTwo.setName(name);
                    userAuthTwo.setBusinessLicenseImage(businessLicenseImage);
                    userAuthTwo.setUserType(userType);
                    userAuthTwo.setBusinessLicenseNum(businessLicenseNum);
                    userAuthTwo.setAuthState(authState);
                    userAuthTwo.setIdentityCardBack(identityCardBack);
                    userAuthTwo.setIdentityCardFront(identityCardFront);
                    userAuthTwo.setIdentityCardNum(identityCardNum);

                    request.getSession().setAttribute("userAuth", userAuthTwo);
                    request.getSession().setMaxInactiveInterval(60 * 60 * 3);
                }
            }
        }
        return "/admin/userMessage";
    }

    /**
     *修改绑定用户状态
     * @param user
     * @return
     */
    @RequestMapping("updateUserMobile")
    @ResponseBody
    public boolean updateUserMobile(User user){
        int i = userService.updateUserMobile(user);
        if(i>0){
            return true;
        }else{
            return false;
        }
    }

}
