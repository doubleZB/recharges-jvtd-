package com.jtd.recharge.connect.flow.xuanjie;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ibm.icu.text.SimpleDateFormat;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by lhm on 2017/9/21.
 * 炫捷流量省网查询接口
 */
@Service
public class XuanJieFlowQueryRequest implements ConnectReqest {
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
        log.info("1、查询流程：供应商---炫捷流量省网----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());

        String host = (String) supplyMap.get("host");//充值地址
        String key = (String) supplyMap.get("key");
        String szAgentId = (String) supplyMap.get("szAgentId");
        String szOrderId=chargeRequest.getChannelNum();
        String szFormat="JSON";
        String szVerifyString = DigestUtils.md5Hex("szAgentId="+szAgentId+"&szOrderId="+szOrderId+"&szKey="+key);

        Map param = new HashMap();
        param.put("szAgentId",szAgentId);
        param.put("szOrderId",szOrderId);
        param.put("szVerifyString",szVerifyString);
        param.put("szFormat",szFormat);

        log.info("1、查询流程：供应商---炫捷流量省网----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"封装应用参数json报文请求参数:"+ JSON.toJSONString(param));
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,param);
        } catch (Exception e) {
            log.error("1、查询异常：供应商---炫捷流量省网----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到炫捷流量省网！原因："+ e.getLocalizedMessage());
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        log.info("1、查询流程：供应商---炫捷流量省网----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object= JSON.parseObject(resultContent);
        String code=object.getString("nRtn");
        String massage=object.getString("szRtnCode");
        if(code.equals("5012")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(szOrderId);
            log.info("1、查询流程：供应商---炫捷流量省网----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 订单到炫捷流量省网处理成功！");
        }else if(code.equals("5013")){
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(code);
            response.setStatusMsg(massage+"请咨询供应商！");
            log.info("1、查询流程：供应商---炫捷流量省网----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 订单到炫捷流量省网处理失败！原因："+massage+"请咨询供应商！");
        }
        return response;
    }
}
