package com.jtd.recharge.connect.video.xuanjie;

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

@Controller
@RequestMapping(value = "/return")
public class XuanjieVideoReport {

    private final Logger logger = LoggerFactory.getLogger(XuanjieVideoReport.class);

    @RequestMapping("/video/xuanjie")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        logger.info("炫捷视频会员充值回执参数：{}", net.sf.json.JSONObject.fromObject(request.getParameterMap()));
        String status = request.getParameter("nFlag");
        String msg = request.getParameter("szRtnMsg");
        String channelNum = request.getParameter("szOrderId");
        String szAgentId = request.getParameter("szAgentId");
        String szOrderId = request.getParameter("szOrderId");
        String szPhoneNum = request.getParameter("szPhoneNum");
        String nDemo = request.getParameter("nDemo");
        String fSalePrice = request.getParameter("fSalePrice");
        logger.info("炫捷视频会员充值回执参数:szAgentId={},szOrderId={},szPhoneNum={},nDemo={},fSalePrice={},nFlag={},szRtnMsg={}",
                szAgentId,szOrderId,szPhoneNum,nDemo,fSalePrice,status,msg);


        ChargeReport chargeReport = new ChargeReport();
        chargeReport.setChannelNum(channelNum);

        if("2".equals(status)) {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            logger.info("1.炫捷视频会员充值回执成功===订单号:==={}订单状态:==={}", channelNum, status);
        } else {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            chargeReport.setMessage(msg+"请咨询供应商！");
            logger.info("1.炫捷视频会员充值回执成功===订单号:==={}订单状态:==={}请咨询供应商！", channelNum, msg);
        }

        logger.info("2.炫捷视频成功添加回执消息队列{}", JSON.toJSONString(chargeReport));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(chargeReport));
        Message putMsg = queue.putMessage(message);
        logger.info("3炫捷视频成功发送消息Send message id is: {}", putMsg.getMessageId());

        response.getWriter().print("ok");
    }
}
