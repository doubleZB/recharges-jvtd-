package com.jtd.recharge.connect.video.changyu;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import com.jtd.recharge.connect.video.SupplyConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ChangYuVideoRequest implements ConnectReqest {

    private final Logger logger = LoggerFactory.getLogger(ChangYuVideoRequest.class);

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        Map supplyMap = (Map) SupplyConfig.getConfig().get(chargeRequest.getSupplyName());
        long start =System.currentTimeMillis();
        String qq="";
        String host=String.valueOf(supplyMap.get("host"));
        String channel=String.valueOf(supplyMap.get("channel"));
        String version=String.valueOf(supplyMap.get("version"));
        String secret=String.valueOf(supplyMap.get("secret"));
        // 请求参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phone", chargeRequest.getMobile());
        paramMap.put("outorderid", chargeRequest.getChannelNum());
        paramMap.put("channel",channel);
        paramMap.put("productid",chargeRequest.getPositionCode());
        if(chargeRequest.getOperator()==6){//腾讯
            qq=chargeRequest.getMobile();
            paramMap.put("qq",qq);
        }else{
            paramMap.put("qq","");
        }
        paramMap.put("version",version);
        // 签名
        String sign= DigestUtils.md5Hex(chargeRequest.getMobile()+"&"+channel+"&"+chargeRequest.getChannelNum()+"&"+chargeRequest.getPositionCode()+"&"+qq+"&"+version+"&"+secret);
        logger.info("签名排序后的字符串:{}", chargeRequest.getMobile()+"&"+channel+"&"+chargeRequest.getChannelNum()+"&"+chargeRequest.getPositionCode()+"&"+qq+"&"+version+"&"+secret);
        paramMap.put("sign",sign);
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent;
        // 充值请求
        try {
            logger.info("畅域视频会员请求参数：{}", JSONObject.toJSONString(paramMap));
            resultContent = HttpTookit.doPost(host,JSONObject.toJSONString(paramMap));
        } catch (Exception e) {
            logger.error("发送异常：发送供应商---畅域视频会员---充值号码{}--, 订单号:{}-失败原因{}",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), e.getLocalizedMessage());

            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        logger.info("畅域视频会员充值请求成功，响应数据：{} 耗时:", resultContent, (System.currentTimeMillis()-start));

        ChargeReport chargeReport = new ChargeReport();
        chargeReport.setChannelNum(chargeRequest.getChannelNum());
        JSONObject object= JSON.parseObject(resultContent);
        if ("0".equals(object.getString("code"))) {
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());

            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            logger.info("8、发送流程：发送供应商---畅域视频会员----mobile ={} orderNum={} 提交到畅域视频会员成功",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum());
        } else {
            String massage = object.getString("desc");
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(massage);
            response.setStatusMsg(massage+"请咨询供应商！");

            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            chargeReport.setMessage(massage+"请咨询供应商！");

            logger.info("8、发送流程：发送供应商---畅域视频会员----mobile ={} orderNum={} 提交到畅域视频会员失败！原因：{}请咨询供应商！",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), massage);
        }
        logger.info("2.实力视频成功添加回执消息队列{}", JSON.toJSONString(chargeReport));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(chargeReport));
        Message putMsg = queue.putMessage(message);
        logger.info("3实力视频成功发送消息Send message id is: {}", putMsg.getMessageId());
        return response;
    }
}
