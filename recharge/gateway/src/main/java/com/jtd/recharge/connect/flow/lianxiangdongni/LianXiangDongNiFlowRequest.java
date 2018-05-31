package com.jtd.recharge.connect.flow.lianxiangdongni;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by on 2017/3/30.
 */
@Service
public class LianXiangDongNiFlowRequest implements ConnectReqest {
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
        log.info("8、发送流程--发送供应商---联想--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");//充值地址
        String MemberId = (String) supplyMap.get("MemberId");//商户编号
        String Secretkey = (String) supplyMap.get("Secretkey");//秘钥
        String Version = (String) supplyMap.get("Version");//版本号
        String mobile = chargeRequest.getMobile();
        String MemberOrderId = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String GoodsId=chargeRequest.getChannelNum();

        String Hmac = "Account" + mobile + "GoodsId" + GoodsId + "MemberId" + MemberId + "MemberOrderId" + MemberOrderId + "Secretkey" + Secretkey ;
        String sign= DigestUtils.md5Hex(Hmac);

        String user_order_id = chargeRequest.getChannelNum();//订单号

        Map param = new HashMap();
        param.put("MemberId",MemberId);
        param.put("MemberOrderId",MemberOrderId);
        param.put("GoodsId",GoodsId);
        param.put("account",mobile);
        param.put("sign",sign);
        param.put("Version",Version);
        String data = "MemberId=" + MemberId + "&MemberOrderId=" + MemberOrderId + "&GoodsId=" + GoodsId+ "&account=" + mobile + "&Hmac=" + sign + "&Version=" + Version;
        log.info("8、发送流程---发送供应商---联想--mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 封装应用参数json报文请求参数---" +JSON.toJSONString(param)+"data"+data);
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---联想----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到联想！原因："+ e.getLocalizedMessage());
            //提交失败
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---联想---" +JSON.toJSONString(param)+"   ------返回数据：" + resultContent+"*******发送请求耗时："+
                (System.currentTimeMillis()-start));


        JSONObject object= JSON.parseObject(resultContent);
        String errorCode=object.getString("errcode");
        if(errorCode.equals("0")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(user_order_id);
            log.info("8、发送流程：发送供应商---联想----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到联想成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(errorCode);
            response.setStatusMsg(errorCode+"请咨询供应商！");
            log.info("8、发送流程：发送供应商---联想----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到联想失败！原因："+errorCode+"请咨询供应商！");
        }
        return response;
    }


}
