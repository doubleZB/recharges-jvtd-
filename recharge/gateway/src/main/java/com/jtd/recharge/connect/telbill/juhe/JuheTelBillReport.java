package com.jtd.recharge.connect.telbill.juhe;

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
 * 聚合话费
 */
@Controller
@RequestMapping("/return")
public class JuheTelBillReport {
    private Log log = LogFactory.getLog(this.getClass());


    @RequestMapping("/telbill/juhe")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {

        String juhe_sporder_id   = request.getParameter("sporder_id");//聚合订单号
        String juhe_orderid = request.getParameter("orderid");//用户自定义的单号
        String juhe_sta     = request.getParameter("sta");//充值状态1：成功 9：失败
        String juhe_sign    = request.getParameter("sign");//校验值，md5(appkey+sporder_id+orderid) 32位小写，用于校验请求合法性

        log.info("1.聚合话费成功接收到回调，订单状态:==="+ juhe_sta+"===成功接收到订单号:==="+juhe_orderid);

        ChargeReport report = new ChargeReport();

        report.setChannelNum(juhe_orderid);

        if("1".equals(juhe_sta)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.聚合话费回执成功===手机号==="+juhe_orderid+"订单状态:=="+ juhe_sta);

        } else if("9".equals(juhe_sta)) {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(juhe_sta+"请咨询供应商！");
            log.info("1.聚合话费回执失败===手机号==="+juhe_orderid+"订单状态:=="+ juhe_sta+"请咨询供应商！");

        }

        log.info("2.聚合话费成功添加回执消息队列" + JSON.toJSONString(report));

        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.聚合话费成功发送消息Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("success");
    }
}
