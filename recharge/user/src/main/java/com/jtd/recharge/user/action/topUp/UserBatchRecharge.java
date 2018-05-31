package com.jtd.recharge.user.action.topUp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.cache.redis.RedisTemplate;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.ApplicationContextUtil;
import com.jtd.recharge.base.util.PropertiesUtils;
import com.jtd.recharge.dao.po.NumSection;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserBalance;
import com.jtd.recharge.dao.po.UserMobileRecharge;
import com.jtd.recharge.service.charge.common.NumSectionService;
import com.jtd.recharge.service.user.BalanceService;
import com.jtd.recharge.service.user.UserService;
import com.jtd.recharge.user.action.admin.SendFlowThread;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by lyp on 2017/1/11.
 * 商户端页面批量充值功能
 */

@Controller
@RequestMapping("/BatchRecharge")
@Scope("session")
public class UserBatchRecharge {

    private static Properties prop = PropertiesUtils.loadProperties("config.properties");
    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    public UserService userService;

    @Resource
    public NumSectionService numSectionService;
    @Resource(name="commRedisTemplate")
    RedisTemplate redisTemplate;
    @Resource
    public BalanceService balanceService;

    List<String> chinaMobilList=new ArrayList<String>();
    List<String> chinaUnicomList=new ArrayList<String>();
    List<String> chinaTelecomList=new ArrayList<String>();

    /**
     * 批量充值手机号运营商判断个数统计
     * @param request
     * @return
     */
    @RequestMapping("/mobileJudge")
    @ResponseBody
    public Object mobileJudge(HttpServletRequest request){
        log.info("提交的手机号"+request.getParameter("mobiles"));
        String mobiles=null;
        int chinaMobil=0;
        int chinaUnicom=0;
        int chinaTelecom =0;
        int identification=0;//是否有这个号段的表示符
        Map map=new HashMap();
        mobiles=request.getParameter("mobiles");
        String [] mobilePhone=mobiles.split(",");
        chinaMobilList=new ArrayList<String>();
        chinaUnicomList=new ArrayList<String>();
        chinaTelecomList=new ArrayList<String>();
        List<String> list=new ArrayList<String>();//存放库里没有的手机号
        list=new ArrayList<String>();

        for (String str:mobilePhone) {
            String mobileRedis=numSectionService.findNumSection(str);
            JSONObject object=JSON.parseObject(mobileRedis);

            if (object != null) {
                if (object.getString("mobile_type") != null) {
                    if (object.getString("mobile_type").equals("1")) {
                        chinaMobil = chinaMobil + 1;
                        chinaMobilList.add(str);
                    }
                    if (object.getString("mobile_type").equals("2")) {
                        chinaUnicom = chinaUnicom + 1;
                        chinaUnicomList.add(str);
                    }
                    if (object.getString("mobile_type").equals("3")) {
                        chinaTelecom = chinaTelecom + 1;
                        chinaTelecomList.add(str);
                    }
                } else {
                    identification = identification + 1;
                    list.add(str);
                }
            }else {
                identification = identification + 1;
                list.add(str);
            }
        }

        map.put("chinaMobil",chinaMobil);
        map.put("chinaUnicom",chinaUnicom);
        map.put("chinaTelecom",chinaTelecom);
        map.put("identification",identification);
        map.put("list",list);
        chinaMobil=0;
        chinaUnicom=0;
        chinaTelecom=0;

        return map;
    }


    /**
     * 批量充值
     * @param request
     * @return
     */
    @RequestMapping("/sendSumbitFlow")
    @ResponseBody
    public Object sendSumbitFlow(HttpServletRequest request){
        String result=null;
        List<String[]> list=new ArrayList<String[]>();//传递的数据格式
        String chinaMobilCode=request.getParameter("chinaMobilCode");//移动编码
        String chinaUnicomCode=request.getParameter("chinaUnicomCode");//联通编码
        String chinaTelecomCode=request.getParameter("chinaTelecomCode");//电信编码
        String money =request.getParameter("money");//扣费金额
        String urlName =request.getParameter("urlName");//扣费金额
        String remark = request.getParameter("remark");//备注
        String operator=request.getParameter("operator");
        BigDecimal feeAmount=new BigDecimal(money);
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
        if(chinaMobilList.size()>0){
            if(StringUtils.isNotEmpty(chinaMobilCode)){
            for(String mobileString:chinaMobilList){
                String[] mobiles={mobileString,chinaMobilCode,token,urlName,operator};
                list.add(mobiles);
            }
            }else{
                result= "1011";//没有移动手机号产品编码
                return result;
            }
        }
        if(chinaUnicomList.size()>0){
            if(StringUtils.isNotEmpty(chinaUnicomCode)){
            for(String mobileString:chinaUnicomList){
                String[] mobiles={mobileString,chinaUnicomCode,token,urlName,operator};
                list.add(mobiles);
            }
        }else{
                result= "1012";//没有联通手机号产品编码
                return result;
            }
        }
        if(chinaTelecomList.size()>0){
            if(StringUtils.isNotEmpty(chinaTelecomCode)){
            for(String mobileString:chinaTelecomList){
                String[] mobiles={mobileString,chinaTelecomCode,token,urlName,operator};
                list.add(mobiles);
            }
            }else{
                result= "1013";//没有电信手机号产品编码
                return result;
            }
        }
        //调用线程往接口去发送程序
        SendFlowThread sendFlowThread=new SendFlowThread(list,remark);
        Thread thread = new Thread(sendFlowThread);
        thread.start();
        result="1000";//提交成功
        return result;
    }


}
