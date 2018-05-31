package com.jtd.recharge.connect.flow.weizu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lhm on 2017/3/30.
 * 南昌微族
 */
@Service
public class NanChangWeiZuFlowRequest  implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        long start =System.currentTimeMillis();
        log.info("8、发送流程：发送供应商---南昌微族----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");//充值地址
        String apiKey = (String) supplyMap.get("apikey");
        String username = (String) supplyMap.get("username");//用户账号
        String number = chargeRequest.getMobile();//手机号码
        String flowSize = chargeRequest.getPackageSize();//流量大小

        String sign= DigestUtils.md5Hex("username="+username+"&apikey="+apiKey).toUpperCase();//加密串 大写
        String user_order_id = chargeRequest.getChannelNum();//订单号

        Map param = new HashMap();
        param.put("username",username);
        param.put("number",number);
        param.put("flowsize",flowSize);
        param.put("user_order_id",user_order_id);
        param.put("sign",sign);

        String data="?username="+username+"&number="+number+"&flowsize="+flowSize+"&user_order_id="+user_order_id+"&sign="+sign;
        log.info("8、发送流程：发送供应商---南昌微族内容" + data);
        log.info("8、发送流程：发送供应商---南昌微族----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doGet(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---南昌微族----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到南昌微族！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---南昌微族----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object= JSON.parseObject(resultContent);
        String errorCode=object.getString("errcode");
        if(errorCode.equals("0")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(user_order_id);
            log.info("8、发送流程：发送供应商---南昌微族----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到南昌微族成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(errorCode);
            response.setStatusMsg(errorCode+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---南昌微族----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到南昌微族失败！原因："+errorCode+"请咨询供应商！");
        }
        return response;
    }
}
