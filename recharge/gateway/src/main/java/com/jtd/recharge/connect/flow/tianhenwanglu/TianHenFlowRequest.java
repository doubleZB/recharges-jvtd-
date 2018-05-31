package com.jtd.recharge.connect.flow.tianhenwanglu;

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
 * Created by 李慧敏 on 2017/5/4.
 * 天痕网络通道请求
 */
@Service
public class TianHenFlowRequest implements ConnectReqest {
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
        log.info("8、发送流程：发送供应商---天痕网络----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");//充值地址
        String username = (String) supplyMap.get("username");//用户账号
        String password = (String) supplyMap.get("password");//密码
        String tel = chargeRequest.getMobile();//手机号码
        String goodsid = chargeRequest.getPositionCode();//档位编码
//        String channelNum = chargeRequest.getChannelNum();//订单号
        String hmac= DigestUtils.md5Hex(username+password);//加密串

        String data="username="+username+"&password="+password+"&tel="+tel+"&goodsid="+goodsid+"&hmac="+hmac;

        log.info("8、发送流程：发送供应商---天痕网络----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ data);

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,data);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---天痕网络----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到天痕网络！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程：发送供应商---天痕网络----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object= JSON.parseObject(resultContent);
        String Result=object.getString("Result");

        if(Result.equals("Success")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
            log.info("8、发送流程：发送供应商---天痕网络----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到天痕网络成功！");

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(Result);
            response.setStatusMsg(Result+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---天痕网络----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到天痕网络失败,原因:"+Result+"请咨询供应商！");

        }
        return response;
    }
}
