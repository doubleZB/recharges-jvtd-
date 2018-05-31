package com.jtd.recharge.action.admin;

import com.alibaba.fastjson.JSON;
import com.jtd.recharge.dao.bean.util.SHAUtil;
import com.jtd.recharge.dao.po.AdminMenu;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.service.admin.AdminService;
import com.jtd.recharge.service.admin.SysService;
import com.jtd.recharge.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @autor jipengkun
 */
@Controller
@RequestMapping("/admin")
public class AdminAction {

    @Resource
    public AdminService adminService;
    @Resource
    public SysService sysService;
    @Resource
    public UserService userService;

    /**
     * 登录
     * @return
     */
    @RequestMapping("/login")
    public String adminLogin() {
        //AdminUser adminUser = adminService.queryUserByUserId(new Integer("1"));
        return "/admin/login";
    }

    /**
     * 登录页面后台
     * @param adminuser
     * @return
     */
    @RequestMapping("/LoginOk")
    public String LoginOk(HttpServletRequest request, AdminUser adminuser) throws Exception {

        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        if (user!=null){
            assembleMenu(request, user);
            request.getSession().setAttribute("admin_id",user.getId());
            request.getSession().setAttribute("admin_name",user.getAdminName());
            return "index/index";
        }
        //SHA密码加密
        String shpassword = adminuser.getPassword();
        if(shpassword==null){
            return "/admin/login";
        }
        String shapassword = SHAUtil.shaEncode(shpassword);
        adminuser.setPassword(shapassword);
        user = adminService.queryUser(adminuser);
        if(user!=null && !user.equals("")){
            //查询用户表中的信息
            assembleMenu(request, user);
            request.getSession().setMaxInactiveInterval(60*60*3);
            request.getSession().setAttribute("admin_id",user.getId());
            request.getSession().setAttribute("adminLoginUser",user);
            request.getSession().setAttribute("admin_name",user.getAdminName());
            return "index/index";
        }
        request.setAttribute("msg","用户名密码错误！");
        return "/admin/login";
    }

	private void assembleMenu(HttpServletRequest request, AdminUser user) {
		List<AdminMenu> adminMenus =sysService.getMenusByUid(user.getId());
		com.jtd.recharge.service.admin.NavData navData = sysService.getMenuJson(adminMenus);
		@SuppressWarnings("unchecked")
		Map<String,String> data = (Map<String, String>) navData.getData();
		String menuJson =JSON.toJSONString(data);
		request.setAttribute("menuJson",menuJson);
		Set<String> menuSet = data.keySet();
		menuSet.remove("lst");
		request.setAttribute("menuSet",menuSet);
	}

    /**
     * 登出系统
     * @param request
     * @return
     */
    @RequestMapping("/LoginOut")
    public String LoginOut(HttpServletRequest request){
        request.getSession().invalidate();
//        request.getSession().setAttribute(request.getSession().getId(),null);
        return "/admin/login";
    }

}
