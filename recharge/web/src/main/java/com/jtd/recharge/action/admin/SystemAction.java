package com.jtd.recharge.action.admin;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.bean.util.SHAUtil;
import com.jtd.recharge.dao.po.AdminMenuShow;
import com.jtd.recharge.dao.po.AdminRole;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.AdminUserF;
import com.jtd.recharge.service.admin.OperateLogService;
import com.jtd.recharge.service.admin.SysService;

/**
 * Created by WXP 2016-11-24 14:41:49.
 */
@Controller
@RequestMapping("/sys")
public class SystemAction {

    @Resource
    SysService sysService;
    @Resource
    public OperateLogService operateLogService;

    /**
     * 跳转到权限管理页面
     * @return
     */
    @RequestMapping("/permitManagement")
    public String permitManagement(){

        return "/admin/permitManage";
    }


    /**
     * 跳转到角色管理页面
     * @return
     */
    @RequestMapping("/roleManagement")
    public String roleManagement(HttpServletRequest request){
        List<AdminMenuShow> menus= sysService.getAllMenu();
        List<AdminRole> roles = sysService.getAllRole(null);
        String mnus = JSON.toJSONString(menus);
        request.setAttribute("menus",mnus);
        request.setAttribute("roles",JSON.toJSONString(roles));
        return "/admin/roleManagement";
    }

    /**
     * 根据角色名查询角色
     * @param request
     * @param roleName
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectRoleByname")
    public Object selectRoleByname(HttpServletRequest request,String roleName){
        List<AdminRole> roles = null;
        if(roleName!=null && !"".equals(roleName)){
            roles = sysService.getAllRole(roleName);
        }else {
            roles = sysService.getAllRole(null);
        }
        return roles;
    }

    /**
     * 查询角色权限
     * @param roleID
     * @return
     */
    @ResponseBody
    @RequestMapping("/getRoleMenus")
    public Object getRoleMenus(int roleID){
//        System.out.println(roleID);
        List<AdminMenuShow> menus= sysService.getAllMenubyRoleid(roleID);
        return  menus;
    }

    /**
     * 判断角色名是否存在
     * @param rolename
     * @return
     */
    @ResponseBody
    @RequestMapping("/isExistRole")
    public Object isExistRole(String rolename){
        return sysService.isExistRole(rolename);
    }

    /**
     * 添加角色
     * @param rolename
     * @return
     */
    @ResponseBody
    @RequestMapping("/addRole")
    public Object addRole(String rolename,String menusId,HttpServletRequest request){
        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        String msg = "F";
        try {
            sysService.addRole(rolename,menusId);
            operateLogService.logInfo(user.getName(),"角色管理","新增角色："+rolename);
            msg = "T";
        }catch (Exception e){

        }
        return msg;
    }

    /**
     * 修改角色
     * @param roleID
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateRole")
    public Object updateRole(int roleID,String menusId,HttpServletRequest request){
        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        String msg = "F";
        try {
            sysService.updateRole(roleID,menusId);
            operateLogService.logInfo(user.getName(),"角色管理","修改角色id为"+roleID+"的权限为"+menusId);
            msg = "T";
        }catch (Exception e){

        }
        return msg;
    }

    /**
     * 删除角色
     * @param roleID
     * @return
     */
    @ResponseBody
    @RequestMapping("/delRole")
    public Object delRole(int roleID,HttpServletRequest request){
        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        String msg = null;
        try {
            msg =sysService.delRole(roleID);
            operateLogService.logInfo(user.getName(),"角色管理","删除了角色id为"+roleID+"的角色");
        }catch (Exception e){
            msg="F";
        }
        return msg;
    }

    /**
     * 后台用户管理
     * @return
     */
    @RequestMapping("/adminUserList")
    public String adminUserList(HttpServletRequest request){
        List<AdminRole> roles = sysService.getAllRole(null);
        request.setAttribute("roles",roles);
        return "/admin/adminUserList";
    }

    /**
     * 检查后台用户名是否存在
     * @param loginName
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkAdminUserName")
    public String checkAdminUserName(String loginName){
        String res = sysService.checkAdminUserName(loginName);
        return res;
    }

    /**
     * 添加后台用户
     * @param loginName
     * @return
     */
    @ResponseBody
    @RequestMapping("/addAdminUser")
    public Object addAdminUser(String loginName,String userName,String password,String passwordag,int roleId,HttpServletRequest request){
        AdminUser adminUser = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        ReturnMsg msg = new ReturnMsg();
        msg.setSuccess(false);
        if(userName==null||"".equals(userName)||
                loginName==null||"".equals(loginName)||
                password==null||"".equals(password)||
                passwordag==null||"".equals(passwordag)){
            msg.setMessage("参数有空值，请检查后再提交！");
        }else {
            String res = sysService.checkAdminUserName(loginName);
            if (res.equals("T")){
                msg.setMessage("登录名已存在，请换一个！");
            }else {
                if (!password.equals(passwordag)){
                    msg.setMessage("两次输入密码不一致！");
                }else {
                    AdminUser user = new AdminUser();
                    user.setAdminName(loginName);
                    user.setName(userName);
                    user.setPassword(password);
                    user.setUpdateTime(new Date());
                    try{
                        String shapassword = SHAUtil.shaEncode(password);
                        user.setPassword(shapassword);
                        sysService.addAdminUser(user,roleId);
                        operateLogService.logInfo(adminUser.getName(),"用户管理","添加后台用户，用户名"+user.getName()+",登录名"+user.getAdminName());
                        msg.setMessage("用户添加成功！");
                        msg.setSuccess(true);
                    }catch (Exception e){
//                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        msg.setMessage("添加失败！");
                    }
                }
            }
        }
        return msg;
    }


    /**
     * 分页查询后台用户
     * @param longName
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAdminUser")
    public Object getAdminUser(String longName,Integer pageNumber, Integer pageSize){
        PageInfo<AdminUserF> pageInfo = sysService.getAdminUser(longName,pageNumber,pageSize);
        return pageInfo;
    }

    /**
     * 删除后台用户
     * @param uid
     * @return
     */
    @ResponseBody
    @RequestMapping("/delAdminUser")
    public Object delAdminUser(Integer uid,HttpServletRequest request){
        AdminUser adminUser = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        ReturnMsg msg = new ReturnMsg();
        msg.setSuccess(false);
        msg.setMessage("删除失败");
        if(uid==null){
            msg.setMessage("用户id为空！");
        }else {
            try {
                sysService.delAdminUser(uid);
                operateLogService.logInfo(adminUser.getName(),"用户管理","删除后台用户，用户id"+uid);
                msg.setSuccess(true);
                msg.setMessage("删除成功");
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return msg;
    }


    /**
     * 获取单个用户信息用于编辑
     * @param uid
     * @return
     */
    @ResponseBody
    @RequestMapping("/getAdminUserByid")
    public Object getAdminUserByid(Integer uid){
        AdminUserF adminUserF = sysService.getAdminUserByid(uid);

        return adminUserF;
    }


    /**
     * 更新后台用户信息
     * @param userId
     * @param userName
     * @param password
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping("/toEditAdminUser")
    public Object toEditAdminUser(int userId,String userName,String password,int roleId,HttpServletRequest request){
        AdminUser adminUser = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        ReturnMsg msg = new ReturnMsg();
        msg.setSuccess(false);
        msg.setMessage("更新失败！");
        try {
            if(password!=null &&!"".equals(password)){
                password = SHAUtil.shaEncode(password);
            }else{
                password=null;
            }
            sysService.toEditAdminUser(userId,userName,password,roleId);
            operateLogService.logInfo(adminUser.getName(),"用户管理","修改后台用户，用户id"+userId);
            msg.setSuccess(true);
            msg.setMessage("更新成功！");
        }catch (Exception e){
            e.printStackTrace();
        }

        return msg;
    }


    /**
     * 跳转到用户密码修改界面
     * @param request
     * @return
     */
    @RequestMapping("/updateUserPswd")
    public String updateUserPswd(HttpServletRequest request){
        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        request.setAttribute("userInfo",user);
        return "/admin/updateUserPswd";
    }


    /**
     * 后台用户修改自己的密码
     * @param request
     * @param oldPswd
     * @param newPswd
     * @param newPswdag
     * @return
     */
    @ResponseBody
    @RequestMapping("/updatepswd")
    public Object updatepswd(HttpServletRequest request,String  oldPswd,String newPswd ,String newPswdag ) throws Exception{
        ReturnMsg msg = new ReturnMsg();
        msg.setMessage("修改失败");
        msg.setSuccess(false);
        AdminUser user = (AdminUser)request.getSession().
                            getAttribute("adminLoginUser");
        if(oldPswd==null || "".equals(oldPswd)){
            msg.setMessage("参数有空值！");
        }else {
            oldPswd = SHAUtil.shaEncode(oldPswd);
            if (oldPswd==null || "".equals(oldPswd)||
                    newPswd==null || "".equals(newPswd)||
                    newPswdag==null || "".equals(newPswdag)){
                msg.setMessage("参数有空值！");
            }else if (!user.getPassword().equals(oldPswd)){
                msg.setMessage("原始密码不正确！");
            }else if (!newPswd.equals(newPswdag)){
                msg.setMessage("两次新密码输入不一致！");
            }else {
                AdminUser adminUser = new AdminUser();
                adminUser.setPassword(newPswd);
                adminUser.setId(user.getId());
                try {
                    String shapassword = SHAUtil.shaEncode(newPswd);
                    adminUser.setPassword(shapassword);
                    sysService.updatepswd(adminUser);
                    operateLogService.logInfo(user.getName(),"用户管理","修改自己登陆密码，用户id"+user.getId());
                    user.setPassword(shapassword);
                    request.getSession().setAttribute("adminLoginUser",user);
                    msg.setMessage("修改成功！");
                    msg.setSuccess(true);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return msg;
    }


}
