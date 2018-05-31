package com.jtd.recharge.connect.flow.henanyidong;

import com.asiainfo.openplatform.common.util.SecurityUtils;
import com.asiainfo.openplatform.common.util.SignUtil;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.GroovyConfigUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 河南移动全国
 */
@Service
public class HeNanYiDongRequest implements ConnectReqest {

    private final Logger logger = LoggerFactory.getLogger(HeNanYiDongRequest.class);

    private static Map config = new HashMap();

    static {
        config = GroovyConfigUtil.init("config/supply.groovy");
    }

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        long start =System.currentTimeMillis();
        String supplyName = chargeRequest.getSupplyName();
        Map supplyMap = (Map) config.get(supplyName);
        // 业务参数
        JSONObject busiParam = new JSONObject();
        busiParam.put("GBILL_ID", supplyMap.get("gbill_id"));
        busiParam.put("FLAG", "2"); // 操作标识
        busiParam.put("BILL_ID", chargeRequest.getMobile());
        busiParam.put("VALID_MONTH", "1"); // 赠送流量有效期 固定值 1
        busiParam.put("VALID_TYPE", "1"); // 赠送流量有效期 填1代表立即生效，2代表次月生效。
        busiParam.put("MEM_SRVPKG", chargeRequest.getPositionCode()); // 成员资费包。
        busiParam.put("CUST_ORDER_ID", chargeRequest.getChannelNum()); // 订单流水。
        busiParam.put("SMS_TEMPLATE", "0"); // 短信模板。

        logger.info("河南移动流量充值，签名钱字符串：{}", busiParam.toString());
        // 签名
        String entity = SecurityUtils.encodeAES256HexUpper(busiParam.toString(),
                        SecurityUtils.decodeHexUpper(String.valueOf(supplyMap.get("app_key"))));
        String accessToken = TokenSingleton
                .getInstance(String.valueOf(supplyMap.get("access_token_url"))).getMap().get("access_token");

        // 系统参数
        Map<String, String> sysParam = new HashMap<>();
        sysParam.put("method", "SO_MEMBER_DEAL_OPER");
        sysParam.put("format", "json");
        sysParam.put("appId", String.valueOf(supplyMap.get("app_id")));
        sysParam.put("version", "1.0");
        sysParam.put("accessToken", accessToken);
        sysParam.put("timestamp", DateUtil.Date2String(new Date(), "yyyyMMddHHmmss"));
        sysParam.put("busiSerial", "1");

        String sign = SignUtil.sign(sysParam, entity, "HmacSHA256", String.valueOf(supplyMap.get("app_key")));
        logger.info("签名后的字符串:{}", sign);

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        StringBuffer host = new StringBuffer(supplyMap.get("host").toString());
        host.append("?method=SO_MEMBER_DEAL_OPER").append("&format=json").append("&appId=")
                .append(String.valueOf(supplyMap.get("app_id"))).append("&version=1.0").append("&accessToken=")
                .append(accessToken).append("&sign=").append(sign).append("&timestamp=")
                .append(sysParam.get("timestamp")).append("&busiSerial=1");
        logger.info("请求地址host值: {}", host);

        String resultContent;
        try {
            resultContent = HttpTookit.doPost(host.toString(), entity, "utf-8");
        }catch (Exception e) {
            logger.error("8、发送流程----mobile ={} orderNum={} 提交到海航失败！原因：{}请咨询供应商！",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(),e.getMessage());

            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setStatusCode(e.getMessage());
            response.setStatusMsg(e.getMessage()+"请咨询供应商！");
            return response;
        }
        logger.info("8、发送流程----mobile ={} orderNum={} 发送供应商---河南移动全国； 封装应用参数json报文请求参数--{}返回数据：{}****发送请求耗时：{}"
                ,chargeRequest.getMobile(),chargeRequest.getChannelNum(), busiParam,resultContent,(System.currentTimeMillis()-start));

        JSONObject responseInfo= net.sf.json.JSONObject.fromObject(resultContent);
        if ("00000".equals(responseInfo.get("respCode"))) {
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            response.setChannelNum(chargeRequest.getChannelNum());
            logger.info("4、发送流程：发送供应商---河南移动全国----mobile ={} orderNum={} 提交到河南移动全国流量话费成功！",
                    chargeRequest.getMobile(),chargeRequest.getChannelNum());
        } else {
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(responseInfo.getString("respCode"));
            response.setStatusMsg(responseInfo.getString("respDesc") + "请咨询供应商！");
            logger.info("5、发送流程：发送供应商---河南移动全国----mobile ={} orderNum={} 提交到战鼓分省流量失败！原因：{}请咨询供应商！",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), responseInfo.getString("respDesc"));
        }

        return response;
    }
}
