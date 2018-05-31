package com.jtd.recharge.user.action.topUp;

import com.alibaba.fastjson.JSON;
import com.jtd.recharge.dao.po.*;
import com.jtd.recharge.service.charge.order.OrderService;
import com.jtd.recharge.service.user.BalanceService;
import com.jtd.recharge.service.user.UserService;
import com.jtd.recharge.user.action.admin.SendFlowThread;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lhm on 2018/2/24.
 * 视频会员
 */
@Controller
@RequestMapping("video")
public class VideoApplication {

    @Resource
    public UserService userService;
    @Resource
    public BalanceService balanceService;
    /**
     * 转到视频会员应用概览页面
     * @param request
     * @return
     */
    @RequestMapping("/videoOverview")
    public String videoOverview(HttpServletRequest request) {
        String businessType = request.getParameter("businessType");
        request.setAttribute("businessType", businessType);
        return "/video/videoOverview";
    }


    /**
     * 转到视频会员订单页面
     * @return
     */
    @RequestMapping("/videoOrder")
    public String videoOrder() {
        return "/video/videoHistory";
    }

    @RequestMapping("/videoRecharge")
    public String videoRecharge() {
        return "/video/videoRecharge";
    }

    /**
     * 手机号运营商判断
     * @param request
     * @return
     */
    @RequestMapping("/videoProduct")
    @ResponseBody
    public Object mobileJugement(HttpServletRequest request){
        User users =(User) request.getSession().getAttribute("users");
        int  operator=Integer.valueOf(request.getParameter("operator"));
        int  videoType=0;
        if(!"".equals(request.getParameter("videoType"))&&request.getParameter("videoType")!=null){
            videoType=Integer.valueOf(request.getParameter("videoType"));
        }
        int userId=users.getId();
        int appType=6;//固定视频会员
        int bussinessType=3;//视频会员
        List<UserMobileRecharge> list= selectUserProductCode(userId,bussinessType,operator,appType,videoType);
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("list",list);
        String jsonMap= JSON.toJSONString(map);
        return jsonMap;
    }


    /**
     * 查询产品编码
     * @param userId
     * @param bussinessType
     * @param operator
     * @return
     */
    public  List<UserMobileRecharge> selectUserProductCode(int userId,int bussinessType,int operator,int appType,int videoType){
        UserMobileRecharge userMobileRecharge=new UserMobileRecharge();
        userMobileRecharge.setUserId(userId);
        userMobileRecharge.setBusinessType(bussinessType);
        userMobileRecharge.setProvinceId(32);
        userMobileRecharge.setOperator(operator);
        userMobileRecharge.setAppType(appType);
        userMobileRecharge.setVideoType(videoType);
        return userService.selectUserMobileProductStore(userMobileRecharge);
    }

    /**
     * 批量充值
     * @param request
     * @return
     */
    @RequestMapping("/sendSumbitFlow")
    @ResponseBody
    public Object sendSumbitFlow(HttpServletRequest request, @RequestParam(value="mobileIds[]") String[] mobileIds){
        String result=null;
        List<String[]> list=new ArrayList<String[]>();//传递的数据格式
        String videoCode=request.getParameter("videoCode");//电信编码
        String money =request.getParameter("moneyAll");//扣费金额
        String urlName =request.getParameter("urlName");
        String operator=request.getParameter("operator");
        BigDecimal feeAmount=new BigDecimal(money);
        String remark="";
        User users =(User) request.getSession().getAttribute("users");
        int userId=users.getId();

        //获取帐户余额
        UserBalance userBalance = balanceService.queryBalanceByUserId(userId);
        BigDecimal borrowBalance = userBalance.getBorrowBalance();//借款额度
        BigDecimal creditBalance = userBalance.getCreditBalance();//信用额度
        BigDecimal balance = userBalance.getUserBalance();//用户余额
        //可用金额
        BigDecimal ableBlance = balance.add(creditBalance);
        if(feeAmount.compareTo(ableBlance) == 1) {
            result= "1010";//金额不足
            return result;
        }
        User user =userService.findUserByUserId(userId);
        String token=user.getToken();
       if(mobileIds.length>0){
                for(String mobileString:mobileIds){
                    String[] mobiles={mobileString,videoCode,token,urlName,operator};
                    list.add(mobiles);
                }
            }else{
                result= "1011";//没有移动手机号产品编码
                return result;
            }
        //调用线程往接口去发送程序
        SendFlowThread sendFlowThread=new SendFlowThread(list,remark);
        Thread thread = new Thread(sendFlowThread);
        thread.start();
        result="1000";//提交成功
        return result;
    }
}
