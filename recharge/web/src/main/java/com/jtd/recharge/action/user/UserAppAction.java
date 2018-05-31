package com.jtd.recharge.action.user;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.ChargeProductGroup;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserApp;
import com.jtd.recharge.service.admin.OperateLogService;
import com.jtd.recharge.service.charge.store.StoreService;
import com.jtd.recharge.service.user.UserAppService;
import com.jtd.recharge.service.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lhm on 2017/1/22.
 * 用户应用
 */
@Controller
@RequestMapping("/user")
public class UserAppAction {
    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    public UserService userService;
    @Resource
    public UserAppService userAppService;
    @Resource
    public OperateLogService operateLogService;
    @Resource
    public StoreService storeService;

    /**
     * 到应用页面
     * @return
     */
    @RequestMapping("userApp")
    public String userApp(HttpServletRequest request,UserApp userApp) throws UnsupportedEncodingException {
        String userCnName = userApp.getUserCnName();
        Integer userId = userApp.getUserId();
        /*if(userCnName!=null && !"".equals(userCnName)){
            userCnName = new String(userCnName.getBytes("iso8859-1"),"utf-8");
        }*/
        request.setAttribute("userCnName",userCnName);
        request.setAttribute("userId",userId);
        return "/user/merchantApplication";
    }

    /**
     * 查询用户应用
     * @param pageNumber
     * @param pageSize
     * @param userApp
     * @return
     */
    @RequestMapping("userAppList")
    @ResponseBody
    public Object userAppList(Integer pageNumber, Integer pageSize, UserApp userApp){
        String groupCnName=userApp.getGroupCnName();
        if(groupCnName!=null){
            List<Integer> groupIds=new ArrayList<>();
            List<ChargeProductGroup> list=  getGroupId(userApp.getGroupCnName());
            for(ChargeProductGroup chargeProductGroup1:list){
                groupIds.add(chargeProductGroup1.getId());
            }
            userApp.setGroupIds(groupIds);
        }
        PageInfo<UserApp> list = userAppService.selectUserAppList(pageNumber,pageSize,userApp);
        return list;
    }

    /**
     * 新增商户应用
     * @param userApp
     * @return
     */
    @RequestMapping("addUserApp")
    @ResponseBody
    public boolean addUserApp(HttpServletRequest request,UserApp userApp){
        int i = userAppService.addUserApp(userApp);
        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        String adminName = user.getAdminName();
        if(i>0){
            operateLogService.logInfo(adminName,"商户应用管理",adminName+"在商户应用管理新增了商户id为："+userApp.getUserId()+" 的应用信息");

            //根据用户ID去查询用户表中pId
            Integer userId = userApp.getUserId();
            User users = new User();
            users.setpId(userId);
            List<User> userList = userService.selectUserListByPId(users);
            if(userList.size()>0){
                for(User userTwo : userList){
                    Integer id = userTwo.getId();
                    Integer appType = userApp.getAppType();
                    Integer groupId = userApp.getGroupId();
                    Integer isCache = userApp.getIsCache();
                    Integer chargeType = userApp.getChargeType();

                    //根据主账户的应用信息添加到子账户中
                    UserApp userAppTwo = new UserApp();
                    userAppTwo.setUserId(id);
                    userAppTwo.setAppType(appType);
                    userAppTwo.setGroupId(groupId);
                    userAppTwo.setIsCache(isCache);
                    userAppTwo.setChargeType(chargeType);
                    userAppService.addUserApp(userAppTwo);
                }
            }
            return true;
        }else{
            return false;
        }
    }

    /**
     * 验证商户应用是否存在
     * @param userApp
     * @return
     */
    @RequestMapping("userAppByUserAppType")
    @ResponseBody
    public Object UserAppByUserAppType(UserApp userApp){
        UserApp app = userAppService.selectUserAppByUserAppType(userApp);
        return app;
    }
    /**
     * 去追加货架
     * @return
     */
    @RequestMapping("/toAddGroup")
    @ResponseBody
    public List<ChargeProductGroup> selectProductGroup(){
        return userService.selectProductGroup();
    }

    /**
     * 开通应用跳转链接到修改页面
     * @return
     */
    @RequestMapping("application")
    public String application(HttpServletRequest request, UserApp userApp){
        request.setAttribute("userApp",userApp);
        return "/user/application";
    }

    /**
     * 根据id回显应用
     * @param userApp
     * @return
     */
    @RequestMapping("updateApplication")
    @ResponseBody
    public Map<String, Object> updateApplication(UserApp userApp){
        Map<String, Object> map = userAppService.selectUserAppListById(userApp);
        return map;
    }

    /**
     * 修改应用
     * @param userApp
     * @return
     */
    @RequestMapping("updateApp")
    @ResponseBody
    public boolean updateApp(HttpServletRequest request,UserApp userApp){
        //修改主账户应用
        int i = userAppService.updateApp(userApp);

        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        String adminName = user.getAdminName();

        if(i>0){
            //根据应用ID确定用户信息
            UserApp app = userAppService.selectUserAppListByIdTwo(userApp);
            Integer userId = app.getUserId();
            Integer appType = userApp.getAppType();

            //根据用户ID去查询子类信息
            User userOne = new User();
            userOne.setpId(userId);
            List<User> userTwo = userService.selectUserListByPId(userOne);
            if(userTwo.size()>0){
                for(User users : userTwo){
                    Integer id = users.getId();
                    UserApp userAppOne = new UserApp();
                    userAppOne.setUserId(id);
                    userAppOne.setAppType(appType);
                    //根据用户ID和应用类型确定应用信息
                    List<UserApp> userAppTwo = userAppService.selectUserAppByUserId(userAppOne);
                    for(UserApp userApps : userAppTwo){
                        //批量修改子账户应用
                        Integer appId = userApps.getId();
                        Integer chargeType = userApp.getChargeType();
                        Integer groupId = userApp.getGroupId();
                        Integer isCache = userApp.getIsCache();

                        UserApp userAppThree = new UserApp();
                        userAppThree.setId(appId);
                        userAppThree.setChargeType(chargeType);
                        userAppThree.setGroupId(groupId);
                        userAppThree.setIsCache(isCache);
                        userAppService.updateApp(userAppThree);
                    }
                }
            }
            //添加日志信息
            operateLogService.logInfo(adminName,"商户应用管理",adminName+"在商户应用管理修改了商户应用id为："+userApp.getId()+" 的应用信息");
            return true;
        }else{
            return false;
        }
    }

    /**
     * 查询货架id
     * @param supplyName
     * @return
     */
    @RequestMapping("/getGroupId")
    @ResponseBody
    public  List<ChargeProductGroup> getGroupId(String supplyName){
        ChargeProductGroup chargeProductGroup=new ChargeProductGroup();
        if(StringUtils.isNotEmpty(supplyName)){chargeProductGroup.setName("%"+supplyName+"%");}
        return storeService.selectProductGroupList(chargeProductGroup);
    }

    /**
     * 批量修改应用到制定的货架
     * @param request
     * @return
     */
    @RequestMapping("/updateUserApp")
    @ResponseBody
    public boolean updateUserApp(HttpServletRequest request){

        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        String adminName = user.getAdminName();

        //修改账户应用货架
        UserApp userApp = new UserApp();
        String groupId = request.getParameter("groupId");
        String appId = request.getParameter("appId");
        String appType = request.getParameter("appType");
        String[] split = appId.split(",");
        int i = 0;
        if(split!=null && !split.equals("")){
            for(String str :split) {
                userApp.setId(Integer.parseInt(str));
                userApp.setGroupId(Integer.parseInt(groupId));
                i = userAppService.updateApp(userApp);
                if(i>0){
                    log.info("批量修改用户应用货架成功！");
                    //根据应用ID确定用户信息
                    UserApp app = userAppService.selectUserAppListByIdTwo(userApp);
                    Integer userId = app.getUserId();
//                    Integer appType = userApp.getAppType();

                    //根据用户ID去查询子类信息
                    User userOne = new User();
                    userOne.setpId(userId);
                    List<User> userTwo = userService.selectUserListByPId(userOne);
                    if(userTwo.size()>0){
                        for(User users : userTwo){
                            Integer id = users.getId();
                            UserApp userAppOne = new UserApp();
                            userAppOne.setUserId(id);
                            userAppOne.setAppType(Integer.parseInt(appType));
                            //根据用户ID和应用类型确定应用信息
                            List<UserApp> userAppTwo = userAppService.selectUserAppByUserId(userAppOne);
                            for(UserApp userApps : userAppTwo){
                                //批量修改子账户应用
                                Integer appIdOne = userApps.getId();
                                Integer chargeType = userApp.getChargeType();
                                Integer groupIdOne= userApp.getGroupId();
                                Integer isCache = userApp.getIsCache();

                                UserApp userAppThree = new UserApp();
                                userAppThree.setId(appIdOne);
                                userAppThree.setChargeType(chargeType);
                                userAppThree.setGroupId(groupIdOne);
                                userAppThree.setIsCache(isCache);
                                userAppService.updateApp(userAppThree);
                                log.info("批量修改子用户应用成功！");
                            }
                        }
                    }
                }
                //添加日志信息
                operateLogService.logInfo(adminName,"商户应用管理",adminName+"在商户应用管理修改了商户应用id为："+userApp.getId()+" 的应用信息");
            }
            return true;
        }else{
            return false;
        }
    }
}
