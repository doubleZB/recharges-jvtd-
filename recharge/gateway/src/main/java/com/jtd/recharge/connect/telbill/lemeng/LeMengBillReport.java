package com.jtd.recharge.connect.telbill.lemeng;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lhm 乐盟话费 on 2017/6/21.
 */
@Controller
@RequestMapping("/return")
public class LeMengBillReport {
    private Log log = LogFactory.getLog(this.getClass());


    @RequestMapping("/telbill/lemeng")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        // 以流的方式接受数据
        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);
        log.info("1.乐盟话费成功接收到回调，内容:==="+ json);

        JSONObject jsonObject = JSON.parseObject(json);

        String orderId=jsonObject.getString("merorderid");
        String orderStatus=jsonObject.getString("result");

        log.info("1.乐盟话费成功接收到回调，订单状态:==="+ orderStatus+"===成功接收到订单号:==="+orderId);

        ChargeReport report = new ChargeReport();

        report.setChannelNum(orderId);

        if("70".equals(orderStatus)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            report.setMessage("回执成功");
            log.info("1.乐盟话费回执成功===订单状态:==="+ orderStatus+"===成功接收到订单号:==="+orderId);

        } else if("15".equals(orderStatus)) {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(orderStatus+"请咨询供应商！");
            log.info("1.乐盟话费回执失败===订单状态:==="+ orderStatus+"===成功接收到订单号:==="+orderId+"请咨询供应商！");

        }

        log.info("2.乐盟话费成功添加回执消息队列" + JSON.toJSONString(report));

        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.乐盟话费成功发送消息Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("SUCCESS");
    }
}
