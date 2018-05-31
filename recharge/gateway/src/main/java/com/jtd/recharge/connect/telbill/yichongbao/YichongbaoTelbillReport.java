package com.jtd.recharge.connect.telbill.yichongbao;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @autor jipengkun
 * 易充宝
 */
@Controller
@RequestMapping("/return")
public class YichongbaoTelbillReport {
    private Log log = LogFactory.getLog(this.getClass());


    @RequestMapping("/telbill/yichongbao")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        //合作商订单号
        String orderId=request.getParameter("orderId");
        //系统订单号
        String serialId=request.getParameter("serialId");
        //订单状态(success：成功；fail：失败)
        String orderStatus=request.getParameter("orderStatus");
        //完成金额，单位：元
        String finishMon=request.getParameter("finishMon");
        //时间戳
        String finishTime=request.getParameter("finishTime");
        //MD5(orderId+ serialId+ orderStatus+ finishMon+ finishTime+key)
        String sign=request.getParameter("sign");
        log.info("1.易充宝成功接收到回调，订单状态:==="+ orderStatus+"===成功接收到订单号:==="+orderId);

        ChargeReport report = new ChargeReport();

        report.setChannelNum(orderId);

        if("success".equals(orderStatus)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.易充宝回执成功，订单状态:==="+ orderStatus+"===成功接收到订单号:==="+orderId);

        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(orderStatus+"请咨询供应商！");
            log.info("1.易充宝回执失败，订单状态:==="+ orderStatus+"===成功接收到订单号:==="+orderId+"请咨询供应商！");

        }

        log.info("2.易充宝成功添加回执消息队列" + JSON.toJSONString(report));
        
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.易充宝成功发送消息Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("OK");
    }
}
