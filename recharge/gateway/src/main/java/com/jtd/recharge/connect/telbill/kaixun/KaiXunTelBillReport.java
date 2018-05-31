package com.jtd.recharge.connect.telbill.kaixun;

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
 * 锴汛话费充值回调处理
 */
@Controller
@RequestMapping(value = "/return")
public class KaiXunTelBillReport {

    private final Logger logger = LoggerFactory.getLogger(KaiXunTelBillReport.class);

    @RequestMapping("/telbill/kaixun")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        logger.info("锴汛话费充值回执参数: {}", net.sf.json.JSONObject.fromObject(request.getParameterMap()));

        String downstreamSerialno = request.getParameter("downstreamSerialno");
        String status = request.getParameter("status");

        ChargeReport chargeReport = new ChargeReport();
        chargeReport.setChannelNum(downstreamSerialno);

        if ("00".equals(status)) {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            logger.info("1.锴汛话费充回执成功====订单状态:={}=", status);
        } else {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            chargeReport.setMessage("状态2是成功3是失败: 状态:" + status + " 请咨询供应商！");
            logger.info("1.锴汛话费充失败===订单状态:={}=请咨询供应商！", status);
        }

        logger.info("2.锴汛话费成功添加回执消息队列{}", JSON.toJSONString(chargeReport));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(chargeReport));
        Message putMsg = queue.putMessage(message);
        logger.info("3锴汛话费成功发送消息Send message id is: {}", putMsg.getMessageId());

        // 响应
        response.getWriter().print("success");
    }
}
