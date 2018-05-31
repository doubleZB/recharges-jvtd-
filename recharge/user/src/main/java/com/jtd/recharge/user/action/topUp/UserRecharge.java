package com.jtd.recharge.user.action.topUp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.PropertiesUtils;
import com.jtd.recharge.dao.bean.util.SHAUtil;
import com.jtd.recharge.dao.po.NumSection;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserMobileRecharge;
import com.jtd.recharge.service.charge.common.NumSectionService;
import com.jtd.recharge.service.user.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lyp on 2017/1/3.
 */
@Controller
@RequestMapping("/Recharge")
public class UserRecharge {
    private static Properties prop = PropertiesUtils.loadProperties("config.properties");
    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    public UserService userService;

    @Resource
    public NumSectionService numSectionService;

    /**
     * 手机号运营商判断
     * @param request
     * @return
     */
    @RequestMapping("/mobileJugement")
    @ResponseBody
    public Object mobileJugement(HttpServletRequest request){
        User users =(User) request.getSession().getAttribute("users");

        int userId=users.getId();
        int identification=0;//是否有这个号段的表示符
        int appType=Integer.parseInt(request.getParameter("appType"));
        int bussinessType=Integer.parseInt(request.getParameter("bussinessType"));
        NumSection numSection=numSectionService.findNumSectionByMobile(request.getParameter("mobile"));
        List<UserMobileRecharge> list=null;
        if(numSection!=null){
            int  provinceId=numSection.getProvinceId();
            int operator=0;
            if(numSection.getMobileType()!=null){
            operator=numSection.getMobileType();
            }
            list= selectUserProductCode(userId,bussinessType,provinceId,operator,appType);
        }else{
            identification=1;
        }
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("list",list);
        map.put("numSection",numSection);
        map.put("identification",identification);
        String jsonMap=JSON.toJSONString(map);

        return jsonMap;
    }

    /**
     * 查询产品编码
     * @param userId
     * @param bussinessType
     * @param provinceId
     * @param operator
     * @return
     */
    public  List<UserMobileRecharge> selectUserProductCode(int userId,int bussinessType,int provinceId,int operator,int appType){
        UserMobileRecharge userMobileRecharge=new UserMobileRecharge();
        userMobileRecharge.setUserId(userId);
        userMobileRecharge.setBusinessType(bussinessType);
        userMobileRecharge.setProvinceId(provinceId);
        userMobileRecharge.setOperator(operator);
        userMobileRecharge.setAppType(appType);
        //List<UserMobileRecharge> list=userService.selectUserMobileProductStore(userMobileRecharge);
        return userService.selectUserMobileProductStore(userMobileRecharge);
}



    /**
     * 支付密码校验
     * @param request
     * @return
     */
    @RequestMapping("/payPassword")
    @ResponseBody
    public  Object payPassword (HttpServletRequest request)throws Exception{
        boolean result=true;
        String pagePayPassword =request.getParameter("pagePayPassword");//传进来页面输入的密码
        SHAUtil shaUtil = new SHAUtil();
        String shaPasswords = shaUtil.shaEncode(pagePayPassword);

        User user = (User) request.getSession().getAttribute("users");
        String payPassword =user.getPayPassword();//用户本来的支付密码

            if(shaPasswords.equals(payPassword)){
                result=true;//密码正确
            }else{
                result=false;//密码错误
            }
        return result;
    }



    /**
     * 单个手机号充值
     * @param request
     * @return
     */
    @RequestMapping("/send")
    @ResponseBody
    public Object userRecharge(HttpServletRequest request){
        String back="";
        String mobile=request.getParameter("mobile");
        String produtCode=request.getParameter("positionCode");
        String urlName=request.getParameter("urlName");
        String operator=request.getParameter("operator");
        User user = (User) request.getSession().getAttribute("users");
        String token =user.getToken();
        if(StringUtils.isEmpty(token)){
            back="该用户名无效";
            return back;
        }
        String result=sending(token,mobile,produtCode,urlName,operator);

        JSONObject object=JSON.parseObject(result);
        String statusCode=object.getString("statusCode");
        String statusMsg=object.getString("statusMsg");
        HashMap<String ,String> map=new HashMap<String ,String>();
        map.put("statusCode",statusCode);
        map.put("statusMsg",statusMsg);
        back=JSON.toJSONString(map);

        return back;
    }






    /**
     *向接口发送数据
     * @param key
     * @param num
     * @param product
     * @return
     */
    public String  sending (String key,String num,String product,String urlName,String operator){

        String token = key;//token
        String mobile = num;
        String customId = ""+System.currentTimeMillis();//商户订单号
        String code = product;//档位编码
        String callbackUrl = "http://116.62.28.7:8090/test/callback";//回调url
        String source = "2";//页面充值标识

        Map paramMap = new HashMap();
        paramMap.put("mobile", mobile);
        paramMap.put("customId", customId);
        paramMap.put("code", code);
        paramMap.put("callbackUrl", callbackUrl);
        paramMap.put("sign", DigestUtils.md5Hex(token + mobile + customId + code + callbackUrl));
        paramMap.put("token", token);
        paramMap.put("operator", operator);
        paramMap.put("source", source);

        String url = prop.getProperty(urlName);
        log.info("page request---" + JSON.toJSONString(paramMap));
        String contenet =null;
        try {
            contenet = HttpTookit.doPostParam(url, paramMap, "utf-8");
            log.info("page send request---" + contenet);
        }catch (Exception e){
            log.error("页面充值错误"+e);
        }
        return contenet;
    }

}
