package com.jtd.recharge.connect.video.xuanjie;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 炫捷视屏会员
 */
@Service
public class XuanJieVideoRequest implements ConnectReqest {

    private final Logger logger = LoggerFactory.getLogger(XuanJieVideoRequest.class);

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        logger.info("炫捷视屏会员");
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        long start =System.currentTimeMillis();

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("szAgentId", String.valueOf(supplyMap.get("szAgentId")));
        paramMap.put("szOrderId", chargeRequest.getChannelNum());
        paramMap.put("szPhoneNum", chargeRequest.getMobile());
        paramMap.put("nMoney", chargeRequest.getPackageSize()); // 充值金额
        paramMap.put("nSortType", String.valueOf(chargeRequest.getPositionCode())); // 运营商
        paramMap.put("nProductClass", "1"); //
        paramMap.put("nProductType", "302"); // 视频会员
        paramMap.put("szTimeStamp", DateUtil.Date2String(new Date(), DateUtil.SQL_TIME_FMT)); // 时间戳
        paramMap.put("szVerifyString",
                DigestUtils.md5Hex(getUrlParamsByMap(paramMap, String.valueOf(supplyMap.get("key")))));
        paramMap.put("szNotifyUrl", String.valueOf(supplyMap.get("callback_url"))); //
        paramMap.put("szFormat", "JSON"); //
        // paramMap.put("ip", String.valueOf("192.168.4.167")); //

        logger.info("8、发送流程：发送供应商---炫捷视频充值参数--{}", paramMap);
        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent;
        JSONObject object;
        // 充值请求
        try {
            resultContent = HttpTookit.doPost(String.valueOf(supplyMap.get("host")), paramMap);
            logger.info("请求成功，响应数据：{} 耗时: {}", resultContent, (System.currentTimeMillis()-start));
            object = JSON.parseObject(resultContent);
        } catch (Exception e) {
            logger.error("发送异常：发送供应商---炫捷视频会员---充值号码{}--, 订单号:{}-失败原因{}",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), e.getLocalizedMessage());

            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }

        String code=object.getString("nRtn");
        String massage=object.getString("szRtnCode");
        if("0".equals(code)){
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
            logger.info("8、发送流程：发送供应商---炫捷视频会员----mobile ={} orderNum={} 提交到炫捷视频会员成功",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum());

        }else{
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(code);
            response.setStatusMsg(massage+"请咨询供应商！");
            logger.info("8、发送流程：发送供应商---炫捷视频会员----mobile ={} orderNum={} 提交到炫捷视频会员失败！原因：{}请咨询供应商！",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), massage);
        }
        return response;
    }

    /**
     * 将map转换成url
     * @param map map参数
     * @return url参数串
     */
    private String getUrlParamsByMap(Map<String, String> map, String key) {
        StringBuffer sb = new StringBuffer();
        sb.append("szAgentId=").append(map.get("szAgentId"))
                .append("&szOrderId=").append(map.get("szOrderId"))
                .append("&szPhoneNum=").append(map.get("szPhoneNum"))
                .append("&nMoney=").append(map.get("nMoney"))
                .append("&nSortType=").append(map.get("nSortType"))
                .append("&nProductClass=").append(map.get("nProductClass"))
                .append("&nProductType=").append(map.get("nProductType"))
                .append("&szTimeStamp=").append(map.get("szTimeStamp"))
                .append("&szKey=").append(key);
        logger.info("待加密字符串: {}", sb.toString());
        return sb.toString();
    }
}
