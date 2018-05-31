package com.jtd.recharge.action.user;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.base.util.OSSUploadUtils;
import com.jtd.recharge.base.util.PropertiesUtils;
import com.jtd.recharge.dao.po.*;
import com.jtd.recharge.service.admin.OperateLogService;
import com.jtd.recharge.service.user.UserAuthService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lhm on 2017/4/11.
 * 认证信息
 */
@Controller
@RequestMapping("user")
public class UserAuthAction {

    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    private UserAuthService userAuthService;
    @Resource
    public OperateLogService operateLogService;

    /**
     * 商户认证审核页面
     * @return
     */
    @RequestMapping("authList")
    public String authList(){
        return "userAuth/authList";
    }

    /**
     * 商户认证审核列表页
     * @param pageNumber
     * @param pageSize
     * @param userAuth
     * @return
     */
    @RequestMapping("selectUserAuthList")
    @ResponseBody
    public Object selectUserAuthList(Integer pageNumber, Integer pageSize, UserAuth userAuth){
        PageInfo<UserAuth> list = userAuthService.selectUserAuthList(pageNumber,pageSize,userAuth);
        return list;
    }

    /**
     * 个人认证
     * @return
     */
    @RequestMapping("personageAttestation")
    public String personageAttestation(HttpServletRequest request,UserAuth userAuth){
        List<UserAuth> list = userAuthService.selectUserAuthByID(userAuth);
        request.setAttribute("list",list);
        return "userAuth/personageAttestation";
    }

    /**
     * 企业认证
     * @return
     */
    @RequestMapping("firmAttestation")
    public String firmAttestation(HttpServletRequest request,UserAuth userAuth){
        List<UserAuth> list = userAuthService.selectUserAuthByID(userAuth);
        request.setAttribute("list",list);
        return "userAuth/firmAttestation";
    }

    /**
     * 根据ID审核用户认证信息为审核通过
     * @param request
     * @return
     */
    @RequestMapping("approveOK")
    @ResponseBody
    public boolean updateUserAuthByID( HttpServletRequest request) {
        String authId = request.getParameter("id");
        UserAuth userAuth = new UserAuth();
        userAuth.setId(Integer.parseInt(authId));
        userAuth.setAuthState(UserAuth.ATTESTATION_STATUS.ATTESTATION_YES);
        userAuth.setUpdateTime(new Date());
        int i = userAuthService.updateUserAuthByID(userAuth);
        if(i>0){
            AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
            String adminName = user.getName();
            operateLogService.logInfo(adminName,"商户认证审核",adminName+"在商户认证审核审核了审核ID为："+authId+" 的信息为认证通过");
            return true;
        }else {
            return false;
        }
    }

    /**
     * 根据ID审核用户认证信息为审核不通过
     * @param request
     * @return
     */
    @RequestMapping("AuditNotThrough")
    @ResponseBody
    public boolean updateUserAuthTwo( HttpServletRequest request) {
        String authId = request.getParameter("id");
        String cause = request.getParameter("cause");
        UserAuth userAuth = new UserAuth();
        userAuth.setId(Integer.parseInt(authId));
        userAuth.setRemark(cause);
        userAuth.setUpdateTime(new Date());
        userAuth.setAuthState(UserAuth.ATTESTATION_STATUS.ATTESTATION_FAILED);
        int i = userAuthService.updateUserAuthByID(userAuth);
        if(i>0){
            AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
            String adminName = user.getName();
            operateLogService.logInfo(adminName,"商户认证审核",adminName+"在商户认证审核审核了审核ID为："+authId+" 的信息为认证未通过");
            return true;
        }else {
            return false;
        }
    }
}
