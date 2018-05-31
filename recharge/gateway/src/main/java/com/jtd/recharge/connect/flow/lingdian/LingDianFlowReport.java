package com.jtd.recharge.connect.flow.lingdian;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @autor jipengkun
 * 容联回执
 */
@Controller
@RequestMapping("/return")
public class LingDianFlowReport {
    /*private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/lingdian")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);

        log.info("shangtong report---" + json);

        JSONObject jsonObject = JSON.parseObject(json);

        String respCode=jsonObject.getString("respCode");
        //返回的消息体
        String respMsg=jsonObject.getString("respMsg");
        String orderID =jsonObject.getString("orderno_ID");//商户流水
        String phoneNo=jsonObject.getString("phoneNo");

        ChargeReport report = new ChargeReport();

        report.setMobile(phoneNo);
        report.setChannelNum(orderID);
        report.setMessage(respMsg);

        if("0002".equals(respCode)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
        }

        log.info("添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("Success");
    }*/
}
