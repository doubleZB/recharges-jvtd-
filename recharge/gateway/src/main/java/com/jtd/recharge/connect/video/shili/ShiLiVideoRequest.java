package com.jtd.recharge.connect.video.shili;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.HttpTookit;
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
public class ShiLiVideoRequest implements ConnectReqest {

    private final Logger logger = LoggerFactory.getLogger(ShiLiVideoRequest.class);

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        Map supplyMap = (Map) SupplyConfig.getConfig().get(chargeRequest.getSupplyName());
        long start =System.currentTimeMillis();

        // 请求参数
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("agentId", String.valueOf(supplyMap.get("agentId")));
        paramMap.put("agentOrderNo", chargeRequest.getChannelNum());
        paramMap.put("phoneNo", chargeRequest.getMobile());
        paramMap.put("skuId", chargeRequest.getPositionCode());
        paramMap.put("notifyUrl", String.valueOf(supplyMap.get("notifyUrl")));
        paramMap.put("timestamp", DateUtil.Date2String(new Date(), "yyyyMMddHHmmss"));
        // 签名
        StringBuffer sb = new StringBuffer();
        Collection<String> keyset = paramMap.keySet();
        List list = new ArrayList<>(keyset);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            sb.append(list.get(i)).append(paramMap.get(list.get(i)));
        }
        sb.append(String.valueOf(supplyMap.get("key")));
        logger.info("签名排序后的字符串:{}", sb.toString());
        paramMap.put("sign", DigestUtils.md5Hex(sb.toString()));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent;
        // 充值请求
        try {
            logger.info("实力视频会员请求参数：{}", paramMap);
            resultContent = HttpTookit.doPost(String.valueOf(supplyMap.get("host")), paramMap);
        } catch (Exception e) {
            logger.error("发送异常：发送供应商---实力视频会员---充值号码{}--, 订单号:{}-失败原因{}",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), e.getLocalizedMessage());

            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        logger.info("实力视频会员充值请求成功，响应数据：{} 耗时:", resultContent, (System.currentTimeMillis()-start));

        JSONObject object= JSON.parseObject(resultContent);
        if ("T".equals(object.getString("isSuccess"))) {
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
            logger.info("8、发送流程：发送供应商---实力视频会员----mobile ={} orderNum={} 提交到实力视频会员成功",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum());
        } else {
            String massage = object.getString("errorCode");
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(massage);
            response.setStatusMsg(massage+"请咨询供应商！");
            logger.info("8、发送流程：发送供应商---实力视频会员----mobile ={} orderNum={} 提交到实力视频会员失败！原因：{}请咨询供应商！",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), massage);
        }

        return response;
    }
}
