package com.jtd.recharge.connect.flow.rongjing;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpClients;
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
 * Created by 荣景流量 on 2017/10/16
 * lhm
 */
@Service
public class RongJingFlowRequest implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {

        log.info("8、发送流程--发送供应商---荣景流量---mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());
        long start =System.currentTimeMillis();

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String host = (String) supplyMap.get("host");
        String SecretKey = (String) supplyMap.get("SecretKey");
        String cp_id = (String) supplyMap.get("cp_id");
        String method = (String) supplyMap.get("method");
        String belong = (String) supplyMap.get("belong");
        String prorange = (String) supplyMap.get("prorange");
        String usermobile = chargeRequest.getMobile();
        String flow = chargeRequest.getPositionCode();
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        String orderNum = chargeRequest.getChannelNum();

        JSONObject paramJson = new JSONObject();
        // 订购相关参数
        paramJson.put("cp_id", cp_id);
        paramJson.put("flow", flow);
        paramJson.put("prorange", prorange);//流量的使用范围0全国1省内
        paramJson.put("belong", belong);//流量包归属地0全国1省内
        paramJson.put("usermobile", usermobile);

        Map<String, String> signatureMap = new HashMap<String, String>();
        signatureMap.put("cp_id", cp_id); // 接口分配
        signatureMap.put("SecretKey", SecretKey); // 接口分配，不参与排序
        signatureMap.put("method", method); // 请求结果参数
        signatureMap.put("timestamp", timestamp); // 请求结果参数

        String url = ToolsUtil.getUrl(ToolsUtil.jsonToMapString(paramJson.toString()), signatureMap, host);

        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---荣景流量； 封装应用参数json报文请求参数---" +JSON.toJSONString(paramJson));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpClients.doPost(url,JSON.toJSONString(paramJson),"text/plain;charset=utf-8;");
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---荣景流量----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到荣景流量流量！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程：发送供应商---荣景流量----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("status");
        String Message=object.getString("message");
        String result=object.getString("result");
        JSONObject resultJson=JSON.parseObject(result);
        String orderid = resultJson.getString("orderid");
        if(respCode.equals("0000")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderNum);
            response.setSupplyChannelNum(orderid);
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到荣景流量成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(Message);
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到荣景流量失败！原因："+Message);
        }
        return response;
    }

}
