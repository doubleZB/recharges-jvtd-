package com.jtd.recharge.connect.flow.liuliangfengbao;

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
 * 流量风暴 话费 回执
 */
@Controller
@RequestMapping(value = "return")
public class LiuLiangFengBaoTelBillReport {

    private final Logger logger = LoggerFactory.getLogger(LiuLiangFengBaoTelBillReport.class);

    @RequestMapping("/flow/liuliangfengbao")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String tradeOrder = request.getParameter("trade_order"); // 用户订单号
        String retresult = request.getParameter("retresult");   // 流量充值结果 success=成功，error=失败
        logger.info("收到流量风暴回执参数：trade_order：{}，retresult：{}",tradeOrder, retresult);

        ChargeReport report = new ChargeReport();
        report.setChannelNum(tradeOrder);

        if ("success".equals(retresult)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            logger.info("2.流量风暴回执成功===手机号={}=订单状态:={}=", tradeOrder, retresult);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(retresult+"请咨询供应商！");
            logger.info("3.流量风暴回执失败===订单状态:={}=请咨询供应商！", retresult);
        }

        logger.info("3.流量风暴成功添加回执消息队列{}", JSON.toJSONString(report));

        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        logger.info("4.流量风暴成功发送消息Send message id is:{} ", putMsg.getMessageId());

        response.getWriter().print("success");
    }
}
