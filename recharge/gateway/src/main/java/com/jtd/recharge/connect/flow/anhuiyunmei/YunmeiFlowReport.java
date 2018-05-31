package com.jtd.recharge.connect.flow.anhuiyunmei;

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
 * @autor lyp
 * 云媒回执
 */
@Controller
@RequestMapping("/return")
public class YunmeiFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/yunmei")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {


        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);
        log.info("1.云媒流量成功接收到回调，内容:==="+ json);
        JSONObject jsonObject = JSON.parseObject(json);
        String respCode=jsonObject.getString("Status");
        //返回的消息体
        String orderID =jsonObject.getString("OrderID");//商户流水
        String phoneNo=jsonObject.getString("Mobile");
        log.info("1.云媒流量成功接收到回调，订单状态:==="+ respCode+"===成功接收到订单号:==="+orderID+"===手机号==="+phoneNo);

        ChargeReport report = new ChargeReport();

        report.setMobile(phoneNo);
        report.setChannelNum(orderID);

        if("1".equals(respCode)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.云媒流量回执成功===手机号==="+phoneNo+"===订单号:==="+orderID+"状态:==="+ respCode);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(respCode+"请咨询供应商");
            log.info("1.云媒流量回执失败===手机号==="+phoneNo+"===订单号:==="+orderID+"状态:==="+ respCode+"请咨询供应商");
        }

        log.info("2.云媒流量成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.云媒流量成功发送消息，Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("Success");
    }
}
