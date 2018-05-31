package com.jtd.recharge.user.action.account;

import com.jtd.recharge.base.util.OSSUploadUtils;
import com.jtd.recharge.base.util.PropertiesUtils;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserAuth;
import com.jtd.recharge.service.user.UserAuthService;
import com.jtd.recharge.service.user.UserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by lhm on 2017/4/10.
 * 商户上传认证
 */
@Controller
@RequestMapping("customer")
public class AttestationAction {


    private Log log = LogFactory.getLog(this.getClass());
    private static final String PAPER_FILE = (String) PropertiesUtils.loadProperties("config.properties").get("FileName");
    @Resource
    private UserAuthService userAuthService;
    @Resource
    public UserService userService;

    /**
     * 图片附件上传
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "customerUpload", method = RequestMethod.POST)
    public String customerUpload(@RequestParam(value = "file", required = true) MultipartFile file) {
        String url = "";
        try {
            url = OSSUploadUtils.uploadFile(PAPER_FILE, file);
        } catch (IOException e) {
            log.error(e.getMessage() + "图片文件上传失败");
        }
        return "{\"status\":1,\"reurl\":\"" + url + "\"}";
    }

    /**
     * 添加用户认证信息
     * @param request
     * @return
     */
    @RequestMapping("insertUserAuthIdentification")
    @ResponseBody
    public boolean insertUserAuthIdentification( HttpServletRequest request) {
        String businessLicenseNum = request.getParameter("businessLicenseNum");
        String businessLicenseImage = request.getParameter("businessLicenseImage");

        String authName = request.getParameter("name");
        String identityCardNum = request.getParameter("identityCardNum");
        String identityCardFront = request.getParameter("identityCardFront");
        String identityCardBack = request.getParameter("identityCardBack");
        User users =(User) request.getSession().getAttribute("users");
        Integer usersId = users.getId();

        String userAllName = request.getParameter("userAllName");
        //修改用户信息
        if(userAllName!=null && !"".equals(userAllName)){
            User user = new User();
            user.setUserAllName(userAllName);
            user.setId(usersId);
            userService.updateUser(user);
        }
        UserAuth userAuth = new UserAuth();
        userAuth.setUserId(usersId);
        //现根据用户ID查询认证信息
        List<UserAuth> userAuthOne = userAuthService.selectUserAuthByID(userAuth);

        userAuth.setUpdateTime(new Date());
        userAuth.setAuthState(UserAuth.ATTESTATION_STATUS.CHECK_PENDING);
        //企业
        String userType = request.getParameter("userType");

        Integer id = null;
        if(userAuthOne.size()>0){
            //修改用户认证信息
            for(UserAuth userAuthTwo:userAuthOne){
                id = userAuthTwo.getId();
            }
            userAuth.setId(id);
            if(Integer.parseInt(userType)==UserAuth.MERCHANTS_TYPE.FIRM){
                userAuth.setUserType(UserAuth.MERCHANTS_TYPE.FIRM);
            }else if(Integer.parseInt(userType)==UserAuth.MERCHANTS_TYPE.PERSONAGE){
                userAuth.setUserType(UserAuth.MERCHANTS_TYPE.PERSONAGE);
            }
            userAuth.setName(authName);
            userAuth.setBusinessLicenseImage(businessLicenseImage);
            userAuth.setBusinessLicenseNum(businessLicenseNum);
            userAuth.setIdentityCardNum(identityCardNum);
            userAuth.setIdentityCardFront(identityCardFront);
            userAuth.setIdentityCardBack(identityCardBack);
            int i = userAuthService.updateUserAuthByID(userAuth);
            if(i>0){
                return true;
            }else {
                return false;
            }
        }else{
            //添加用户认证信息
            if(Integer.parseInt(userType)==UserAuth.MERCHANTS_TYPE.FIRM){
                userAuth.setBusinessLicenseImage(businessLicenseImage);
                userAuth.setUserType(UserAuth.MERCHANTS_TYPE.FIRM);
                userAuth.setBusinessLicenseNum(businessLicenseNum);
                int i = userAuthService.insertUsersAuth(userAuth);
                if(i>0){
                    return true;
                }else {
                    return false;
                }
            }else if(Integer.parseInt(userType)==UserAuth.MERCHANTS_TYPE.PERSONAGE){
                userAuth.setName(authName);
                userAuth.setUserType(UserAuth.MERCHANTS_TYPE.PERSONAGE);
                userAuth.setIdentityCardNum(identityCardNum);
                userAuth.setIdentityCardFront(identityCardFront);
                userAuth.setIdentityCardBack(identityCardBack);
                int i = userAuthService.insertUsersAuth(userAuth);
                if(i>0){
                    return true;
                }else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 根据用户ID查询已有的认证信息
     * @param userAuth
     * @param request
     * @return
     */
    @RequestMapping("selectUserAuthByID")
    @ResponseBody
    public Object selectUserAuthByID(UserAuth userAuth,HttpServletRequest request){
        User users =(User) request.getSession().getAttribute("users");
        Integer usersId = users.getId();
        userAuth.setUserId(usersId);
        List<UserAuth> list = userAuthService.selectUserAuthByID(userAuth);
        if(list.size()>0){
            return 1;
        }else{
            return 0;
        }
    }

}
