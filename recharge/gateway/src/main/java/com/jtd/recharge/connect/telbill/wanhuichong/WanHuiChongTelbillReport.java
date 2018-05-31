package com.jtd.recharge.connect.telbill.wanhuichong;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 万惠充话费充值--回执
 */
@Controller
@RequestMapping(value = "return")
public class WanHuiChongTelbillReport {

    private final Logger logger = LoggerFactory.getLogger(WanHuiChongTelbillReport.class);

    @RequestMapping("/telbill/wanhuichong")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        logger.info("万惠充话费充值-回执参数: {}", JSONObject.fromObject(request.getParameterMap()));
        String outId = request.getParameter("outId");   // 订单号
        String state = request.getParameter("state");   // 状态 10 已完成(充值成功)

        ChargeReport chargeReport = new ChargeReport();
        chargeReport.setChannelNum(outId);

        if ("10".equals(state)) {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            logger.info("1.万惠充话费充值回执充值成功===状态:==={}", state);
        } else {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            chargeReport.setMessage(state +" 请咨询供应商！");
            logger.info("1.万惠充话费充值回执--充值失败===订单状态:==={}请咨询供应商！", state);
        }

        logger.info("2.万惠充话费成功添加回执消息队列{}", JSON.toJSONString(chargeReport));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(chargeReport));
        Message putMsg = queue.putMessage(message);
        logger.info("3万惠充话费成功发送消息Send message id is: {}", putMsg.getMessageId());
    }
}
