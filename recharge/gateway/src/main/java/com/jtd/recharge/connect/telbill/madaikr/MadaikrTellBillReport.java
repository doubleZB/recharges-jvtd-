package com.jtd.recharge.connect.telbill.madaikr;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 麻袋氪话费回执通道
 */
@Controller
@RequestMapping(value = "return")
public class MadaikrTellBillReport {

    private final Logger logger = LoggerFactory.getLogger(MadaikrTellBillReport.class);

    @RequestMapping(value = "/telbill/madaikr")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("GBK");
        logger.info("麻袋氪话费充值回执参数: {}", net.sf.json.JSONObject.fromObject(request.getParameterMap()));

        String orderId = request.getParameter("Orderid"); // 订单号
        String status = request.getParameter("Orderstatu_int"); // 状态
        String desc = request.getParameter("Orderstatu_text"); // 错误描述

        ChargeReport chargeReport = new ChargeReport();
        chargeReport.setChannelNum(orderId);


        String result = "fail";
        if ("16".equals(status)) {
            result = "OK";
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            logger.info("1.麻袋氪话费充值回执成功====订单状态:={}=", status);
        } else {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            chargeReport.setMessage(desc + "请咨询供应商: 状态:" + status);
            logger.info("1.麻袋氪话费充值失败===订单状态:={}=请咨询供应商！", status);
        }

        logger.info("2.麻袋氪话费成功添加回执消息队列{}", JSON.toJSONString(chargeReport));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(chargeReport));
        Message putMsg = queue.putMessage(message);
        logger.info("3麻袋氪话费成功发送消息Send message id is: {}", putMsg.getMessageId());

        response.getWriter().print(result);
    }
}
