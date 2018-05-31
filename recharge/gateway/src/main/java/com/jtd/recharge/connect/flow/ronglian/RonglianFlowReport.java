package com.jtd.recharge.connect.flow.ronglian;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @autor jipengkun
 * 容联回执
 */
@Controller
@RequestMapping("/return")
public class RonglianFlowReport {
    private Log log = LogFactory.getLog(this.getClass());


    @RequestMapping("/flow/ronglian")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        ServletInputStream in = request.getInputStream();
        InputStreamReader reader = new InputStreamReader(in, "utf-8");
        BufferedReader br = new BufferedReader(reader);
        String str = br.readLine();
        StringBuffer sb = new StringBuffer();

        while (str != null) {
            sb.append(str);
            str = br.readLine();
        }
        in.close();
        reader.close();
        br.close();

        log.info("1.容连返回的成功接收到回调，内容"+sb);

        Document document = DocumentHelper.parseText(sb.toString());
        Element root = document.getRootElement();
        //应用id
        String appId = root.elementText("appId");
        //订单号
        String rechargeId = root.elementText("rechargeId");
        //第三方交易id
        String customId = root.elementText("customId");
        //流量充值号码
        String mobile = root.elementText("phoneNum");
        //充值结果
        String status = root.elementText("status");
        //消息描述
        String msg = root.elementText("msg");

        ChargeReport report = new ChargeReport();

        report.setMobile(mobile);
        report.setChannelNum(customId);
        if("3".equals(status)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.容连流量回执成功====订单号:==="+customId+"订单状态"+status);

        } else if("4".equals(status)) {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(status+"请咨询供应商！"+msg);
            log.info("1.容连流量回执失败====订单号:==="+customId+"订单状态"+status+"请咨询供应商！");

        }


        log.info("2.容连流量成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.容连流量成功提添加消息Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<Response>" +
                "<Result>SUCC</Result>" +
                "</Response>");
    }
}
