
package com.jtd.recharge.action.user;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.UserBalances;
import com.jtd.recharge.dao.bean.UserGroup;
import com.jtd.recharge.dao.bean.util.SHAUtil;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserBalance;
import com.jtd.recharge.service.admin.OperateLogService;
import com.jtd.recharge.service.user.BalanceService;
import com.jtd.recharge.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by lihuimin on 2016/11/11.
 * 商户管理 merchant
 */
@Controller
@RequestMapping("/user")
public class UserAction {

    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    public UserService userservice;
    @Resource
    public OperateLogService operateLogService;
    @Resource
    public BalanceService balanceService;
    /**
     * 商户列表
     * @param request
     * @param pageNumber
     * @param pageSize
     * @param u
     * @return
     */
    @RequestMapping("/list")
    public String merchantList(HttpServletRequest request,Integer pageNumber,Integer pageSize,User u){
        PageInfo<User> list = null;
        String type = request.getParameter("type");
        int i = Integer.parseInt(type);
        if(i==0){
            list = userservice.getUserList(1,10,u);
        }else {
            list = userservice.getUserList(pageNumber,pageSize,u);
        }
        //对注册时间的格式进行处理（yyyy-MM-dd HH:mm:SS）
        for(User user :list.getList()){
            Date registerTime = user.getRegisterTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
            if(registerTime!=null){
                String s = format.format(registerTime);
                user.setRegisterTimeFormat(s);
            }
        }
        request.setAttribute("list", list);
        request.setAttribute("user",u);
        return "/user/merchantlist";
    }

    /**
     * 查询商户列表
     * @param user 查询条件
     * @param pageNumber 页码
     * @param pageSize 每页最大记录数
     * @return 商户列表
     */
    @ResponseBody
    @RequestMapping("query-user-list")
    @CrossOrigin(methods = {RequestMethod.POST})
    public PageInfo<User> queryUserList(User user, Integer pageNumber, Integer pageSize) {
        PageHelper.startPage(pageNumber == null ? 0 : pageNumber, pageSize == null ? 1000 : pageSize);
        return new PageInfo<>(userservice.queryUserList(user));
    }

    /**
     * 子账户列表
     * @return
     */
    @RequestMapping("userSun")
    public String userSun(HttpServletRequest request) throws Exception {
        String userId = request.getParameter("userId");
        String userCnName = request.getParameter("userCnName");
        // userCnName = new String(userCnName.getBytes("iso8859-1"),"utf-8");
        request.setAttribute("userId",userId);
        request.setAttribute("userCnName",userCnName);
        return "/user/subAccount";
    }

    /**
     * 子账户列表页面
     * @param pageNumber
     * @param pageSize
     * @param userBalances
     * @return
     */
    @RequestMapping("/selectUserSunList")
    @ResponseBody
    public Object selectUserList(Integer pageNumber,Integer pageSize, UserBalances userBalances){
        userBalances.setpId(userBalances.getUserId());
        PageInfo<UserBalances> list = balanceService.getUserBalanceList(pageNumber,pageSize,userBalances);
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
     * 验证唯一
     * @param
     * @return
     */

    @RequestMapping("/OnlyName")
    @ResponseBody
    public Object verifyUserName(User user){
        List<User> list = userservice.selectUserNameMobile(user);
        return list;
    }

    /**
     * add新增商户
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public boolean add(UserGroup usergroup) throws Exception {
        //添加中自动生成一个32位的字符串
        String usersid = UUID.randomUUID().toString().trim().replaceAll("-", "");
        usergroup.setUserSid(usersid);

//        Integer groupid = usergroup.getId();
        //添加中自动生成一个32位的字符串
        String token = UUID.randomUUID().toString().trim().replaceAll("-", "");
        usergroup.setToken(token);
        //SHA密码加密
        SHAUtil shaUtil = new SHAUtil();
        String shpassword = usergroup.getPassword();
        String shapassword = shaUtil.shaEncode(shpassword);
        usergroup.setPassword(shapassword);

        usergroup.setUserCnName(usergroup.getUserCnName().trim());

        int i =  userservice.addUser(usergroup);
        if(i>0){
            //添加余额表
            UserBalance balance = new UserBalance();
            Integer ids = usergroup.getId();
            balance.setUserId(ids);
            userservice.addBalance(balance);

            //添加日志
            String operate = usergroup.getName();
            operateLogService.logInfo(operate,"商户列表",operate+"在商户列表新增了用户名："+usergroup.getUserName()+" 的信息");

            return true;
        }else{
            return false;
        }
    }
    /**
     * 去修改
     * @return
     */
    @RequestMapping("/toUpdate")
    @ResponseBody
    public Map<String, Object> toUpdate(UserGroup usergroup){
        Map<String, Object> map = userservice.toUpdateUser(usergroup);
        return map;
    }

    /**
     * 修改
     * @return
     */
    @RequestMapping("/updateList")
    @ResponseBody
    public Boolean updateList(UserGroup usergroup,HttpServletRequest request) throws Exception {
        String passwords = request.getParameter("password");
        usergroup.setSells(usergroup.getSells().trim());
        usergroup.setContacts(usergroup.getContacts().trim());
        if (passwords == null || passwords.equals("")) {
            int i = userservice.updateUserLists(usergroup);
            if (i > 0) {

                //添加日志
                String operate = usergroup.getName();
                operateLogService.logInfo(operate,"商户列表",operate+"在商户列表修改了用户名："+usergroup.getUserName()+" 的信息");

            }else{
                return false;
            }
        } else {
            //SHA密码加密
            SHAUtil shaUtil = new SHAUtil();
            String shpassword = usergroup.getPassword();
            String shapassword = shaUtil.shaEncode(shpassword);
            usergroup.setPassword(shapassword);
            int i = userservice.updateUserList(usergroup);
            if (i > 0) {

                //添加日志
                String operate = usergroup.getName();
                operateLogService.logInfo(operate,"商户列表",operate+"在商户列表修改了用户名："+usergroup.getUserName()+" 的信息");
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 查询所有主账户
     * @return
     */
    @RequestMapping("/selectUserList")
    @ResponseBody
    public Object selectUserList(){
        List<User> list = userservice.selectUser();
        return list;
    }
}
