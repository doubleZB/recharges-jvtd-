package com.jtd.recharge.connect.telbill.weicheng;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.SmsUtil;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import com.jtd.recharge.connect.video.SupplyConfig;
import com.jtd.recharge.dao.bean.util.Md5Util;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 唯诚话费充值--请求充值
 */
@Service
public class WeiChengTelbillRequest implements ConnectReqest{

    private final Logger logger = LoggerFactory.getLogger(WeiChengTelbillRequest.class);

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        long start =System.currentTimeMillis();
        logger.info("1、发送流程：发送供应商---唯诚话费充值----mobile ={} orderNum={}",
                chargeRequest.getMobile(), chargeRequest.getChannelNum());

        Map supplyMap = (Map) SupplyConfig.getConfig().get(chargeRequest.getSupplyName());

        String host=String.valueOf(supplyMap.get("host"));
        String  agent_id =String.valueOf(supplyMap.get("agent_id"));
        String  app_key= String.valueOf(supplyMap.get("app_key"));
        String app_secret=String.valueOf(supplyMap.get("app_secret"));
        String timestamp=String.valueOf(new Date().getTime());
        String order_agent_bill=chargeRequest.getChannelNum();
        String order_tel=chargeRequest.getMobile();
        // 请求参数
        Map<String, String> param = new HashMap<>();
        param.put("action","api_order_fee_submit");
        param.put("order_agent_id",agent_id);
        param.put("app_key", app_key);
        param.put("app_secret",app_secret);
        param.put("timestamp",timestamp); // 时间戳
        param.put("order_agent_bill", order_agent_bill);  // 订单号
        param.put("order_tel", order_tel);    // 充值手机号
        param.put("fee_face_price", chargeRequest.getPositionCode()); // 商品编码
        param.put("order_agent_back_url", String.valueOf(supplyMap.get("callback_url"))); // 回调地址
        //签名
        param.put("app_sign", Md5Util.MD5(app_key + app_secret +agent_id+timestamp+order_agent_bill+order_tel).toUpperCase()); // 回调地址

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent;
        // 充值请求
        try {
            logger.info("唯诚话费充值请求参数：{}", param);
            resultContent = HttpTookit.doGet(host+"/index.do", param);
        } catch (Exception e) {
            logger.error("发送异常：发送供应商---唯诚话费充值---充值号码{}--, 订单号:{}-失败原因{}",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), e.getLocalizedMessage());

            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        logger.info("唯诚话费充值请求成功，响应数据：{} 耗时: {}", resultContent, (System.currentTimeMillis()-start));

        JSONObject object= JSON.parseObject(resultContent);
        if ("0000".equals(object.getString("code"))) {
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
            logger.info("8、发送流程：发送供应商---唯诚话费充值----mobile ={} orderNum={} 提交到唯诚成功",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum());
        } else {
            String massage = object.getString("msg");
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(massage);
            response.setStatusMsg(massage+"请咨询供应商！");
            logger.info("8、发送流程：发送供应商---唯诚话费充值----mobile ={} orderNum={} 提交到唯诚话费充值失败！原因：{}请咨询供应商！",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), massage);
        }

        return response;
    }
}
