package com.jtd.recharge.user.action.admin;

import com.jtd.recharge.dao.po.ChargePosition;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserApp;
import com.jtd.recharge.service.charge.position.ChargePostionService;
import com.jtd.recharge.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lyp on 2016/12/9.
 */
@Controller
@RequestMapping("/developer")
public class DeveloperInformation {
    @Resource
    public UserService userService;
    @Resource
    public ChargePostionService ChargePostionService;

    @RequestMapping("/developeraccount")
    public String developerAccount() {
        return "/document/developerAccount";
    }

    /**
     * 流量接口文档
     * @return
     */
    @RequestMapping("/flowDocument")
    public String flowDocument(){
       return "/document/flowDocument";
    }
    /**
     * 物联网卡接口文档
     * @return
     */
    @RequestMapping("/cardDocument")
    public String cardDocument(){
       return "/document/cardDocument";
    }
    /**
     * 话费接口文档
     * @return
     */
    @RequestMapping("/telephoneDocument")
    public String telephoneDocument(){
        return "/document/telephoneDocument";
    }
    /**
     * 视频会员接口文档
     * @return
     */
    @RequestMapping("/videoDocument")
    public String videoDocument(){
        return "/document/videoDocument";
    }

    /**
     * 流量示例代码
     * @return
     */
    @RequestMapping("/flowJavaCode")
    public String flowJavaCode(){
        return "/document/flowJavaCode";
    }
    /**
     * 物理网示例代码
     * @return
     */
    @RequestMapping("/cardJavaCode")
    public String cardJavaCode(){
        return "/document/cardJavaCode";
    }

    /**
     * 话费示例代码
     * @return
     */
    @RequestMapping("/telbillJavaCode")
    public String telbillJavaCode(){
        return "/document/telbillJavaCode";
    }

    /**
     * 视频会员示例代码
     * @return
     */
    @RequestMapping("/videoJavaCode")
    public String videoJavaCode(){
        return "/document/videoJavaCode";
    }

    @RequestMapping("/phpcode")
    public String phpCode(){
        return "/document/phpCode";
    }


    @RequestMapping("/balanceEnquiry")
    public String balanceEnquiry(){
        return "/document/balanceEnquiry";
    }

    /**
     * 获取用户token
     * @param request
     * @return
     */
    @RequestMapping("/getusertokenandsid")
    @ResponseBody
    public Object getUserToken(HttpServletRequest request){
        String userId = request.getParameter("userid");
        User user =userService.findUserByUserId(new Integer(userId));
        return user;
    }


    @RequestMapping("/updateuseripaddress")
    @ResponseBody
    public Object updateUserIpAddressS(HttpServletRequest request){
        User user=new User();
        user.setId(Integer.parseInt(request.getParameter("userid")));
        user.setIpAddress(request.getParameter("ipaddress"));
        user.setPushSum(Integer.parseInt(request.getParameter("pushSum")));
        int i=userService.updateUserIpAddress(user);
        return i;
    }

    /**
     * 获取用户对应流量包档位编码
     * @param request
     * @return
     */
    @RequestMapping("/flowdocuments")
    @ResponseBody
    public Object flowDocuments(HttpServletRequest request){
        String userId = request.getParameter("userId");
        String appType = request.getParameter("appType");
        UserApp userApp=new UserApp();
        userApp.setAppType(Integer.parseInt(appType));
        userApp.setUserId(Integer.parseInt(userId));
        String businessType = request.getParameter("businessType");
        List<ChargePosition> list = ChargePostionService.findUserPosition(userApp,Integer.parseInt(businessType));
        return list;
    }
    /**
     * 获取用户对应话费包档位编码
     * @param request
     * @return
     */
    @RequestMapping("/teldocuments")
    @ResponseBody
    public Object telDocuments(HttpServletRequest request){
        String userId = request.getParameter("userId");
        String appType = request.getParameter("appType");
        UserApp userApp=new UserApp();
        userApp.setAppType(Integer.parseInt(appType));
        userApp.setUserId(Integer.parseInt(userId));
        String businessType = request.getParameter("businessType");
        List<ChargePosition> list = ChargePostionService.findUserPosition(userApp,Integer.parseInt(businessType));
        return list;
    }

    /**
     * 获取用户对应话视频会员档位编码
     * @param request
     * @return
     */
    @RequestMapping("/videoDocuments")
    @ResponseBody
    public Object videoDocuments(HttpServletRequest request){
        String userId = request.getParameter("userId");
        String appType = request.getParameter("appType");
        UserApp userApp=new UserApp();
        userApp.setAppType(Integer.parseInt(appType));
        userApp.setUserId(Integer.parseInt(userId));
        String businessType = request.getParameter("businessType");
        List<ChargePosition> list = ChargePostionService.findUserPosition(userApp,Integer.parseInt(businessType));
        return list;
    }
}
