package com.jtd.recharge.connect.telbill.kaixun;


import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.HttpTookit;
import com.jtd.recharge.base.util.XMLUtil;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.bean.ChargeSubmitResponse;
import com.jtd.recharge.connect.base.ConnectReqest;
import com.jtd.recharge.connect.video.SupplyConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 锴汛话费充值接口
 */
public class KaiXunTelBillRequest implements ConnectReqest {

    private final Logger logger = LoggerFactory.getLogger(KaiXunTelBillRequest.class);

    @Override
    public ChargeSubmitResponse chargeRequest(ChargeRequest chargeRequest) throws Exception {

        Map supplyMap = (Map) SupplyConfig.getConfig().get(chargeRequest.getSupplyName());
        long start =System.currentTimeMillis();

        // 请求参数
        Map<String, String> param = new HashMap<>();
        param.put("userId", String.valueOf(supplyMap.get("userId")));
        param.put("itemId", chargeRequest.getPositionCode());
        // param.put("itemPrice", String.valueOf(chargeRequest.getAmount().doubleValue()));
        param.put("uid", chargeRequest.getMobile());
        param.put("serialno", chargeRequest.getChannelNum());
        param.put("dtCreate", DateUtil.Date2String(new Date(), "yyyyMMddHHmmss"));
        // 签名
        StringBuffer sb = new StringBuffer();
        Collection<String> keyset = param.keySet();
        List list = new ArrayList<>(keyset);
        Collections.sort(list);
        for(int i=0;i<list.size();i++){
            // if (i>0) sb.append("&");
            sb.append(param.get(list.get(i)));
        }
        sb.append(String.valueOf(supplyMap.get("key")));
        param.put("sign", DigestUtils.md5Hex(sb.toString()));

        ChargeSubmitResponse response = new ChargeSubmitResponse();
        String resultContent;

        // 充值请求
        try {
            logger.info("锴汛话费充值请求参数：{}", param);
            resultContent = HttpTookit.doGet(String.valueOf(supplyMap.get("host")), param);
            logger.info("锴汛话费充值请求成功，响应数据：{} 耗时: {}", resultContent, (System.currentTimeMillis()-start));
        } catch (Exception e) {
            logger.error("发送异常：发送供应商---锴汛话费充值---充值号码{}--, 订单号:{}-失败原因{}",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), e.getLocalizedMessage());

            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusMsg(e.getLocalizedMessage()+"请咨询供应商！");
            return response;
        }

        Document document = XMLUtil.parseStringToXml(resultContent);
        if(document==null){
            throw new Exception("8.锴汛话费充值xml解析异常，返回的内容是----"+resultContent);
        }

        Element order = document.getRootElement();
        String status = order.element("code").getText();
        String desc  = order.element("desc").getText();

        if ("00".equals(status)) {
            response.setStatusCode(status);
            response.setStatus(ChargeSubmitResponse.Status.SUCCESS);
            logger.info("4、发送流程：发送供应商---锴汛话费充值----mobile ={} orderNum={} 提交到锴汛话费成功！",
                    chargeRequest.getMobile(),chargeRequest.getChannelNum());
        } else {
            response.setStatus(ChargeSubmitResponse.Status.FAIL);
            response.setStatusCode(status);
            response.setStatusMsg(desc + "请咨询供应商！");
            logger.info("5、发送流程：发送供应商---锴汛话费充值----mobile ={} orderNum={} 提交到锴汛话费充值失败！原因：{}请咨询供应商！",
                    chargeRequest.getMobile(), chargeRequest.getChannelNum(), desc);
        }

        return response;
    }
}
