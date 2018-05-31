package com.jtd.recharge.connect.flow.ningxiaxunda;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by 宁夏迅达 on 2017/8/9
 * lhm
 */
@Service
public class NingXiaXunDaFlowRequest implements ConnectReqest {
    private Log log = LogFactory.getLog(this.getClass());
    private static Properties properties = null;
    private static final CloseableHttpClient httpClient = null;
    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) {

        log.info("8、发送流程--发送供应商---宁夏迅达---mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum());
        long start =System.currentTimeMillis();

        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);

        String host = (String) supplyMap.get("host");
        String account = (String) supplyMap.get("account");
        String bisCode = (String) supplyMap.get("bisCode");
        String serviceCode = (String) supplyMap.get("serviceCode");
        String discntType = (String) supplyMap.get("discntType");
        String effectiveWay = (String) supplyMap.get("effectiveWay");
        String provinceCode = (String) supplyMap.get("provinceCode");
        String effectiveCycle = (String) supplyMap.get("effectiveCycle");
        String source = (String) supplyMap.get("source");
        String apiKey = (String) supplyMap.get("key");
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        String mobile = chargeRequest.getMobile();
        String discntCode = chargeRequest.getPositionCode();
        String orderID = chargeRequest.getChannelNum();

        String sign= DigestUtils.md5Hex(orderID+account+bisCode+timestamp+apiKey);

        Map param = new HashMap();
        param.put("serialNumber",orderID);
        param.put("account",account);
        param.put("discntType",discntType);
        param.put("effectiveCycle",effectiveCycle);
        param.put("source",source);
        param.put("bisCode",bisCode);
        param.put("timestamp",timestamp);
        param.put("key",apiKey);
        param.put("serviceCode",serviceCode);
        param.put("mobile",mobile);
        param.put("discntCode",discntCode);
        param.put("effectiveWay",effectiveWay);
        param.put("sign",sign);
        param.put("provinceCode",provinceCode);

        log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"  发送供应商---宁夏迅达； 封装应用参数json报文请求参数---" +JSON.toJSONString(param));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent = "";
        try {
            resultContent = HttpTookit.doPost(host,JSON.toJSONString(param));
        } catch (Exception e) {
            log.error("8、发送异常：发送供应商---宁夏迅达----mobile="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到宁夏迅达流量！原因："+ e.getLocalizedMessage());
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            return response;
        }
        log.info("8、发送流程：发送供应商---宁夏迅达----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+"返回数据：" + resultContent+"***************发送请求耗时："+
                (System.currentTimeMillis()-start));

        JSONObject object=JSON.parseObject(resultContent);
        String respCode=object.getString("resultCode");
        String Message=object.getString("resultMsg");
        if(respCode.equals("0000")){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(orderID);

            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到宁夏迅达成功！");
        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(respCode);
            response.setStatusMsg(Message);
            log.info("8、发送流程----mobile ="+chargeRequest.getMobile()+" orderNum=" + chargeRequest.getChannelNum()+" 提交到宁夏迅达失败！原因："+Message);
        }
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.DIRECT_QUEUE);
        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(param));
        Message putMsg = queue.putMessage(message);
        log.info("8.收到结果宁夏迅达成功发送消息，Send message id is: " + putMsg.getMessageId());

        return response;
    }

}
