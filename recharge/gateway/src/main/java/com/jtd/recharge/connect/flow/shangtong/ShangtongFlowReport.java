package com.jtd.recharge.connect.flow.shangtong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * @autor jipengkun
 * 尚通回执
 */
@Controller
@RequestMapping("/return")
public class ShangtongFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/shangtong")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);

        log.info("1.尚通流量返回的成功接收到回调，内容"+json);

        JSONObject jsonObject = JSON.parseObject(json);

        String respCode=jsonObject.getString("respCode");
        //返回的消息体
        String respMsg=jsonObject.getString("respMsg");
        String orderID =jsonObject.getString("orderno_ID");//商户流水
        String phoneNo=jsonObject.getString("phoneNo");

        ChargeReport report = new ChargeReport();

        report.setMobile(phoneNo);
        report.setChannelNum(orderID);

        if("0002".equals(respCode)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.尚通流量回执成功====订单号:==="+orderID+"订单状态"+respCode);

        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(respMsg+"请咨询供应商！");
            log.info("1.尚通流量回执失败====订单号:==="+orderID+"订单状态"+respMsg+"请咨询供应商！");
        }

        log.info("2.尚通流量成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.尚通流量成功发送消息Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("Success");
    }
}
