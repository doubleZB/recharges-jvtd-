package com.jtd.recharge.connect.telbill.ziteng;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
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
 * Created by 紫藤话费回调 on 2017/6/19.
 */
@Controller
@RequestMapping("/return")
public class ZiTengBillReport {
    private Log log = LogFactory.getLog(this.getClass());


    @RequestMapping("/telbill/ziteng")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {

        String orderid   = request.getParameter("cp_order_no");//代理商商城订单
        String status     = request.getParameter("ret_result");//2：代表成功 3：代表部分成功 4：充值失败
        log.info("1。紫藤话费成功接收到回调，订单状态:==="+ status+"===成功接收到订单号:==="+orderid);

        ChargeReport report = new ChargeReport();

        report.setChannelNum(orderid);

        if("0001".equals(status)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.紫藤话费回执成功===手机号==="+status+"===订单号:==="+orderid);

        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(status+"请咨询供应商！");
            log.info("1.紫藤话费回执失败===手机号==="+status+"===订单号:==="+orderid+"请咨询供应商！");
        }

        log.info("2.紫藤话费成功添加回执消息队列" + JSON.toJSONString(report));

        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.紫藤话费成功发送消息Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("0000");
    }
}
