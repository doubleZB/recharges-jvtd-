package com.jtd.recharge.connect.telbill.jusutong;

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
 * @autor lhm
 * 贝贝 话费回执
 */
@Controller
@RequestMapping("/return")
public class JusutongTellBillReport {

    private final Logger logger = LoggerFactory.getLogger(JusutongTellBillReport.class);

    @RequestMapping("/telbill/jusutong")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        /*String customerId=request.getParameter("downstreamSerialno");
        String status=request.getParameter("status");
        log.info("1.聚速通 成功收到回調report---訂單狀態" + status + "訂單號:" + customerId);
        ChargeReport report = new ChargeReport();
        report.setChannelNum(customerId);

        if ("2".equals(status)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.聚速通回执成功---訂單狀態 ：" + status + "訂單號:" + customerId);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(status + "请咨询供应商！");
            log.info("1.聚速通回执失败---訂單狀態 ：" + status + "訂單號:" + customerId + "请咨询供应商！状态：" + status + "请咨询供应商！");

        }

        log.info("2.聚速通成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.聚速通成功发送消息Send message id is: " + putMsg.getMessageId());
        response.getWriter().print("success");*/

        String userId = request.getParameter("userId"); // 合作方用户编号(充值平台方提供)
        String bizId = request.getParameter("bizId"); // 业务编号
        String ejId = request.getParameter("ejId"); // 充值平台方订单号
        String downstreamSerialno = request.getParameter("downstreamSerialno"); // 合作方商户系统的流水号
        String status = request.getParameter("status"); // 状态 2 是成功 3 是失败

        logger.info("1.聚合话费成功接收到回调，订单状态:==={}===成功接收到订单号:==={}充值平台方订单号：{}业务编号：{}合作方用户编号：{}"
                , status, downstreamSerialno,ejId, bizId, userId);

        ChargeReport report = new ChargeReport();
        report.setChannelNum(downstreamSerialno);

        if("2".equals(status)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            logger.info("1.聚合话费回执成功===手机号={}=订单状态:={}=", downstreamSerialno, status);

        } else if("3".equals(status)) {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(status+"请咨询供应商！");
            logger.info("1.聚合话费回执失败===订单状态:={}=请咨询供应商！", status);
        }

        logger.info("2.聚合话费成功添加回执消息队列{}", JSON.toJSONString(report));

        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        logger.info("3.聚合话费成功发送消息Send message id is:{} ", putMsg.getMessageId());

        response.getWriter().print("success");
    }
}
