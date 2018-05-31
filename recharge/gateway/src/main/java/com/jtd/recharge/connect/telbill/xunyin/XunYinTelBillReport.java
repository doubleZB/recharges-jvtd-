package com.jtd.recharge.connect.telbill.xunyin;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lhm on 2017/9/27.
 * 迅银话费
 */
@Controller
@RequestMapping("/return")
public class XunYinTelBillReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/telbill/xunyin")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String channelNum = request.getParameter("id");
        String status = request.getParameter("status");
        String code = request.getParameter("code");
        String msg = request.getParameter("statemes");
        log.info("1.迅银话费成功接收到回调，订单状态:==="+ code+"===成功接收到订单号:==="+channelNum);

        ChargeReport chargeReport = new ChargeReport();
        chargeReport.setChannelNum(channelNum);
        if("8888".equals(code)) {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.迅银话费回执成功===订单号:==="+channelNum+"订单状态:==="+ status);

        }else{
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            chargeReport.setMessage(msg+"请咨询供应商！");
            log.info("1.迅银话费回执成功===订单号:==="+channelNum+"订单状态:==="+ msg+"请咨询供应商！");

        }

        log.info("2.迅银话费成功添加回执消息队列" + JSON.toJSONString(chargeReport));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(chargeReport));
        Message putMsg = queue.putMessage(message);
        log.info("3迅银话费成功发送消息Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("ok");
    }
}
