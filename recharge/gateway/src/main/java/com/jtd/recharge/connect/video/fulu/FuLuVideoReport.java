package com.jtd.recharge.connect.video.fulu;

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
 * 福禄视频会员回执处理
 */
@Controller
@RequestMapping(value = "return")
public class FuLuVideoReport {

    private final Logger logger = LoggerFactory.getLogger(FuLuVideoReport.class);

    @RequestMapping("/video/fulu")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        logger.info("福禄视频会员充值回执参数: {}", net.sf.json.JSONObject.fromObject(request.getParameterMap()));
        String CustomerOrderNo = request.getParameter("CustomerOrderNo");
        String Status = request.getParameter("Status");
        String ReMark = request.getParameter("ReMark");

        ChargeReport chargeReport = new ChargeReport();
        chargeReport.setChannelNum(CustomerOrderNo);
        if ("True".equals(Status)) {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            logger.info("1.福禄视频会员充回执成功====订单状态:={}=", Status);
        } else {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            chargeReport.setMessage(ReMark + "请咨询供应商！状态:" + Status);
            logger.info("1.福禄视频会员充失败===订单状态:={}=请咨询供应商！", ReMark);
        }

        logger.info("2.福禄视频成功添加回执消息队列{}", JSON.toJSONString(chargeReport));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(chargeReport));
        Message putMsg = queue.putMessage(message);
        logger.info("3福禄视频成功发送消息Send message id is: {}", putMsg.getMessageId());

        // 响应
        response.getWriter().print("<?xml version=\"1.0\" encoding=\"utf-8\"?><root><ret><status>True</status></ret></root>");
    }
}
