package com.jtd.recharge.connect.flow.toushi;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lyp on 2017/3/30.
 * 投石回执
 */
@Controller
@RequestMapping("/return")
public class TouShiFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/toushi")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String userid = request.getParameter("userid");//用户账号
        String orderid = request.getParameter("orderid");//订单号
        String state = request.getParameter("state");//状态
        String userkey = request.getParameter("userkey");//加密穿

        log.info("1.投石成功接收到回调，订单状态:==="+ state+"===成功接收到订单号:==="+orderid);


        ChargeReport report = new ChargeReport();
        report.setChannelNum(orderid);

        if("1".equals(state)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.投石流量回执成功===订单状态:==="+ state+"===成功接收到订单号:==="+orderid);

        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(state+"请咨询供应商！");
            log.info("1.投石流量回执失败===订单状态:==="+ state+"===成功接收到订单号:==="+orderid+"请咨询供应商！");

        }

        log.info("2.投石成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.投石成功成功发送消息Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("OK");
    }
}
