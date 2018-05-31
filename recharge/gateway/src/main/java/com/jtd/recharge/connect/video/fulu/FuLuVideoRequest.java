package com.jtd.recharge.connect.video.fulu;

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

/**
 * 福禄视频充值请求接口
 */
@Service
public class FuLuVideoRequest implements ConnectReqest {

    private final Logger logger = LoggerFactory.getLogger(FuLuVideoRequest.class);

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        Map supplyMap = (Map) SupplyConfig.getConfig().get(chargeRequest.getSupplyName());
        long start =System.currentTimeMillis();

        // 请求参数
        Map<String, String> param = new HashMap<>();
        param.put("method", "kamenwang.order.add");
        param.put("timestamp", DateUtil.Date2String(new Date(), "yyyy-MM-dd HH:mm:ss"));
        param.put("format", "json");
        param.put("v", "1.0");
        param.put("customerid", String.valueOf(supplyMap.get("user_id")));

        // 获取商品接口
        /*param.put("goodscatalogid", "8529");*/

        // 网游直充
        param.put("customerorderno", chargeRequest.getChannelNum());
        param.put("productid", chargeRequest.getPositionCode());
        param.put("buynum", "1");
        param.put("chargeaccount", chargeRequest.getMobile());
        param.put("notifyurl", String.valueOf(supplyMap.get("callback_url")));


        // 签名
        StringBuffer sb = new StringBuffer();
        Collection<String> keyset = param.keySet();
        List list = new ArrayList<>(keyset);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            if (i>0) sb.append("&");
            sb.append(list.get(i)).append("=").append(param.get(list.get(i)));
        }
        sb.append(String.valueOf(supplyMap.get("key")));
        param.put("sign", DigestUtils.md5Hex(sb.toString()));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent;
        // 充值请求
        try {
            logger.info("福禄视频会员充值请求参数：{}", param);
            resultContent = HttpTookit.doPost(String.valueOf(supplyMap.get("host")), param);
            logger.info("福禄视频会员充值请求成功，响应数据：{} 耗时:", resultContent, (System.currentTimeMillis()-start));
        } catch (Exception e) {
            logger.error("发送异常：发送供应商---福禄视频会员---充值号码{}--, 订单号:{}-失败原因{}",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), e.getLocalizedMessage());

            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }

        JSONObject object= JSON.parseObject(resultContent);
        if (null == object.getString("MessageCode")) {
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
            logger.info("8、发送流程：发送供应商---福禄视频会员----mobile ={} orderNum={} 提交到福禄视频会员成功",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum());
        } else {
            String massage = object.getString("StatusMsg");
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(massage);
            response.setStatusMsg(massage+"请咨询供应商！");
            logger.info("8、发送流程：发送供应商---福禄视频会员----mobile ={} orderNum={} 提交到福禄视频会员失败！原因：{}请咨询供应商！",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), massage);
        }

        return response;
    }
}
