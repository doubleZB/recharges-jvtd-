package com.jtd.recharge.user.action.admin;

import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 过滤器
 */
public class UserFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 判断是否是http请求
        if (!(servletRequest instanceof HttpServletRequest)
                || !(servletResponse instanceof HttpServletResponse)) {
            throw new ServletException("只支持 HTTP 请求！");
        }
        // 获得在下面代码中要用的request,response,session对象
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        String[] strs = {"loginOk.do","homePage","messageAgreement.do","checkUserNameStatus.do","register.do","checkUserName.do","add.do","static","forgetPassword.do","checkMoble.do","findCode.do","nextOne.do","findPassword.do"}; // 路径中包含这些字符串的,可以不用登录直接访问
        StringBuffer url = httpRequest.getRequestURL();
        /**
         * 过滤掉根目录
         */
        String path = httpRequest.getContextPath();
        String protAndPath = httpRequest.getServerPort() == 80 ? "" : ":"
                + httpRequest.getServerPort();
        String basePath = httpRequest.getScheme() + "://"
                + httpRequest.getServerName() + protAndPath + path + "/";
        if (basePath.equalsIgnoreCase(url.toString())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        User users =(User) httpRequest.getSession().getAttribute("users");
        if(users!=null){
            String uri = httpRequest.getRequestURI();
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            // 特殊用途的路径可以直接访问
            if (strs != null && strs.length > 0) {
                for (String str : strs) {
                    if (url.indexOf(str) >= 0) {
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                }
            }
            // 用户不存在,踢回登录页面
            httpRequest.setAttribute("msg","登录过期！");
            httpRequest.getRequestDispatcher("/admin/login.do").forward(servletRequest, servletResponse);
            return;
        }
    }

    @Override
    public void destroy() {

    }
}
