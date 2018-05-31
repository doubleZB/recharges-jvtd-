package com.jtd.recharge.connect.flow.lianxiang;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by liyabin on 2017/12/22.
 */
public class LianXiangFlowRequest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        long start =System.currentTimeMillis();
        log.info("8、发送流程---发送供应商---联想--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");
        String  memberId  = (String) supplyMap.get("MemberId");
        String  Secretkey  = (String) supplyMap.get("Secretkey");
        String  version  = (String) supplyMap.get("Version");
        String goodsId = chargeRequest.getPositionCode();
        String  memberOrderId  =  chargeRequest.getChannelNum();
        JSONObject head=new JSONObject();
        String sign= "Account"+chargeRequest.getMobile()+"GoodsId"+goodsId+"MemberId"+memberId+"MemberOrderId"+memberOrderId+"Secretkey"+ Secretkey;
        log.info("8、加密前="+sign);
        sign= DigestUtils.md5Hex("Account"+chargeRequest.getMobile()+"GoodsId"+goodsId+"MemberId"+memberId+"MemberOrderId"+memberOrderId+"Secretkey"+ Secretkey);
        head.put("MemberId",memberId);
        head.put("MemberOrderId",memberOrderId);
        head.put("Account",chargeRequest.getMobile());
        head.put("GoodsId",goodsId);
        head.put("Version",version);
        head.put("Hmac",sign);

        log.info("8、发送流程---发送供应商---联想--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" +head.toString());

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,head.toString());
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---联想----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到联想！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程--发送供应商---联想--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  封装应用参数json报文请求参数---" +head.toString()+"   ------返回数据：" + resultContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object= JSON.parseObject(resultContent);
        String respCode=object.getString("Result");
        String msg=object.getString("Message");
        if("Success".equals(respCode)){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
        }else{
            log.info("8.联想提交失败直接回调失败请咨询供应商！"+msg);
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
        }

        return response;
    }
}
