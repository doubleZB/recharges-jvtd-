package com.jtd.recharge.connect.flow.chuangzhi;

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
 * Created by lhm on 2017-08-24.
 * 创智流量
 */
@Service
public class ChuangZhiFlowRequest implements ConnectReqest {

    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();



    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }


    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {
        long start =System.currentTimeMillis();
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        response.setStatus(ChargeSubmitResponse.Status.FAIL);
        log.info("8、发送流程：发送供应商---创智流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String url = (String)supplyMap.get("host");
        String appSecret = (String)supplyMap.get("appSecret");
        String backUrl = (String) supplyMap.get("callback_url");
        String appKey = (String) supplyMap.get("key");
        String ts = String.valueOf(System.currentTimeMillis());
        String phoneNo = chargeRequest.getMobile();
        String prodCode =chargeRequest.getPositionCode();
        String transNo = chargeRequest.getChannelNum();
        //排序得出paramStr
        String paramStr ="appKey="+appKey+"&backUrl="+backUrl+"&phoneNo="+phoneNo+"&prodCode="+prodCode+"&transNo="+transNo+"&ts="+ts;

        String sign = DigestUtils.md5Hex(paramStr+appSecret);

        Map param = new HashMap();
        param.put("appKey",appKey);
        param.put("ts",ts);
        param.put("phoneNo",phoneNo);
        param.put("prodCode",prodCode);
        param.put("backUrl",backUrl);
        param.put("transNo",transNo);
        param.put("sign",sign);

        String resultContent = "";
        log.info("8、发送流程：发送供应商---创智流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));

        try {
            resultContent = HttpTookit.doPost(url,param);
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---创智流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到创智流量！原因："+ e.getLocalizedMessage());
           // response.setStatusMsg("提交订单时候，http访问出错"+e.getMessage()+"；订单参数："+JSON.toJSONString(param));
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程：向创智流量发送数据-mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String statusCode = object.getString("respCode");
        String desc = object.getString("respMsg");
        response.setStatusMsg(resultContent);
        if(statusCode.equals("jx0000")){
            response.setStatusMsg(desc);
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(transNo);
            log.info("8、发送流程：发送供应商---创智流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到创智流量成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(statusCode);
            response.setStatusMsg(desc+"请咨询供应商！");
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到创智流量失败！原因："+desc+"请咨询供应商！");
        }
        return response;
    }
}
