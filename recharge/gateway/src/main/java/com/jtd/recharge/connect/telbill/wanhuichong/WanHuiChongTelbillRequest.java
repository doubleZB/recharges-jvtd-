package com.jtd.recharge.connect.telbill.wanhuichong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.SmsUtil;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import com.jtd.recharge.connect.video.SupplyConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 万惠充话费充值--请求充值
 */
@Service
public class WanHuiChongTelbillRequest implements ConnectReqest{

    private final Logger logger = LoggerFactory.getLogger(WanHuiChongTelbillRequest.class);

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        long start =System.currentTimeMillis();
        logger.info("1、发送流程：发送供应商---万惠充话费充值----mobile ={} orderNum={}",
                chargeRequest.getMobile(), chargeRequest.getChannelNum());

        Map supplyMap = (Map) SupplyConfig.getConfig().get(chargeRequest.getSupplyName());
        // 请求参数
        Map<String, String> param = new HashMap<>();
        param.put("name", String.valueOf(supplyMap.get("userId"))); // 用户名
        param.put("nonceStr", String.valueOf(SmsUtil.getRandNum(0,8)));  // 随机字符串
        param.put("timeStamp", String.valueOf(new Date().getTime())); // 时间戳
        param.put("outId", chargeRequest.getChannelNum());  // 订单号
        param.put("phoneNumber", chargeRequest.getMobile());    // 充值手机号
        param.put("productId", chargeRequest.getPositionCode()); // 商品编码
        param.put("faceValue",  chargeRequest.getAmount().toString());  //商品面值
        param.put("callbackUrl", String.valueOf(supplyMap.get("callback_url"))); // 回调地址
        param.put("version", "1.2");    // 版本号,固定值1.2
        // 签名
        StringBuffer sb = new StringBuffer();
        Collection<String> keyset = param.keySet();
        List list = new ArrayList<>(keyset);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            if (i > 0) sb.append("&");
            sb.append(list.get(i)).append("=").append(param.get(list.get(i)));
        }
        logger.info("待签名字符串: {}", sb.toString());
        sb.append("&key=").append(String.valueOf(supplyMap.get("key")));
        param.put("sign", DigestUtils.md5Hex(sb.toString()).toUpperCase());

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent;
        // 充值请求
        try {
            logger.info("万惠充话费充值请求参数：{}", param);
            resultContent = HttpTookit.doPost(String.valueOf(supplyMap.get("host")), param);
        } catch (Exception e) {
            logger.error("发送异常：发送供应商---万惠充话费充值---充值号码{}--, 订单号:{}-失败原因{}",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), e.getLocalizedMessage());

            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }
        logger.info("万惠充话费充值请求成功，响应数据：{} 耗时: {}", resultContent, (System.currentTimeMillis()-start));

        JSONObject object= JSON.parseObject(resultContent);
        if ("0".equals(object.getString("code"))) {
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
            logger.info("8、发送流程：发送供应商---万惠充话费充值----mobile ={} orderNum={} 提交到万惠充成功",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum());
        } else {
            String massage = object.getString("msg");
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(massage);
            response.setStatusMsg(massage+"请咨询供应商！");
            logger.info("8、发送流程：发送供应商---万惠充话费充值----mobile ={} orderNum={} 提交到万惠充话费充值失败！原因：{}请咨询供应商！",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), massage);
        }

        return response;
    }
}
