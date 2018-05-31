package com.jtd.recharge.connect.flow.fengwo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import com.jtd.recharge.connect.video.SupplyConfig;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 蜂窝科技全国流量充值回执处理
 */
@Controller
@RequestMapping("/return")
public class FengWoFlowReport {

    private final Logger logger = LoggerFactory.getLogger(FengWoFlowReport.class);

    @RequestMapping("/flow/fengwo")
    public JSONObject report(HttpServletRequest request,
                             HttpServletResponse response, @RequestBody JSONObject param) throws IOException {
        logger.info("蜂窝科技流量充值回执参数: {}", net.sf.json.JSONObject.fromObject(request.getParameterMap()));
        logger.info("蜂窝科技流量充值回执参数 json 格式: {}", param);

        Map supplyMap = (Map) SupplyConfig.getConfig().get("fengwoflow");

        JSONObject content = param.getJSONObject("MSGBODY").getJSONObject("CONTENT");
        String status = content.getString("CODE");
        String desc = content.getString("STATUS");
        String orderNum = content.getString("EXTORDER");

        ChargeReport chargeReport = new ChargeReport();
        chargeReport.setChannelNum(orderNum);

        if ("00".equals(status)) {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            logger.info("1.蜂窝科技流量充值回执成功====订单状态:={}=", status);
        } else {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            chargeReport.setMessage(desc + "请咨询供应商: 状态:" + status);
            logger.info("1.蜂窝科技流量充值失败===订单状态:={}=请咨询供应商！", status);
        }

        logger.info("2.蜂窝科技流量成功添加回执消息队列{}", JSON.toJSONString(chargeReport));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(chargeReport));
        Message putMsg = queue.putMessage(message);
        logger.info("3蜂窝科技流量成功发送消息Send message id is: {}", putMsg.getMessageId());

        // 系统参数
        JSONObject sysParam = new JSONObject();
        sysParam.put("VERSION", "V1.1");        // 协议版本号
        sysParam.put("TIMESTAMP", DateUtil.Date2String(new Date(), "yyyyMMddHHmmssSSS"));
        sysParam.put("SEQNO", orderNum);       // 流水号
        sysParam.put("APPID", String.valueOf(supplyMap.get("app_id")));       //应用标识号

        sysParam.put("SECERTKEY", DigestUtils.md5Hex(sysParam.getString("VERSION")
                + sysParam.getString("APPID") + String.valueOf(supplyMap.get("key"))));       // MD5签名
        logger.info("蜂窝流量充值:系统参数: {}", sysParam);

        // 请求参数
        JSONObject contentParam = new JSONObject();
        contentParam.put("RCODE", "00");       // 分发对象标识（手机号码/UserID）
        contentParam.put("RMSG", "成功");     // 流量包ID

        JSONObject msgbody = new JSONObject();
        msgbody.put("RESP", contentParam);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("HEADER", sysParam);
        jsonObject.put("MSGBODY", msgbody);
        logger.info("蜂窝流量回执响应参数: {}", jsonObject.toJSONString());
        return jsonObject;
    }
}
