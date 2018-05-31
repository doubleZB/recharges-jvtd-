package com.jtd.recharge.connect.video.shili;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "return")
public class ShiLiVideoReport {

    private final Logger logger = LoggerFactory.getLogger(ShiLiVideoReport.class);

    @RequestMapping("/video/shili")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("实力视频充值回调1, 参数:{}", net.sf.json.JSONObject.fromObject(request.getParameterMap()));

        String orderNo = request.getParameter("orderNo"); // 实立订单号
        String agentOrderNo = request.getParameter("agentOrderNo"); // 商户平台订单号
        String fillStatus = request.getParameter("fillStatus"); // 充值状态
        String succAmount = request.getParameter("succAmount"); // 充值金额

        logger.info("实力视频充值回调2, 充值状态:{}, 实立订单号{},充值金额{}, 商户平台订单号{}",
                fillStatus, orderNo, succAmount, agentOrderNo);

        String result = "{\"isSuccess\":\"T\",\"errorCode\":\"\"}";
        if (StringUtils.isBlank(fillStatus)) {
            result = "{\"isSuccess\":\"F\",\"errorCode\":\"1000\"}";
        }
        ChargeReport chargeReport = new ChargeReport();
        if (!"2".equals(fillStatus)) {
            chargeReport.setChannelNum(agentOrderNo);
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            logger.info("1.实力视频会员充值回执成功===错误码:==={}", fillStatus);
        } else {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            chargeReport.setMessage(fillStatus+"请咨询供应商！");
            logger.info("1.实力视频会员充值回执成功===错误码:==={}请咨询供应商！", fillStatus);
        }

        logger.info("2.实力视频成功添加回执消息队列{}", JSON.toJSONString(chargeReport));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(chargeReport));
        Message putMsg = queue.putMessage(message);
        logger.info("3实力视频成功发送消息Send message id is: {}", putMsg.getMessageId());

        response.getWriter().print(result);
    }
}
