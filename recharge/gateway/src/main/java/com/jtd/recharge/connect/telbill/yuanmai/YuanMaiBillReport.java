package com.jtd.recharge.connect.telbill.yuanmai;

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
 * 容联回执
 */
@Controller
@RequestMapping("/return")
public class YuanMaiBillReport {
    private Log log = LogFactory.getLog(this.getClass());


    @RequestMapping("/telbill/yuanmai")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {

        String orderid   = request.getParameter("orderid");//代理商商城订单
        String status     = request.getParameter("status");//2：代表成功 3：代表部分成功 4：充值失败
        String sign    = request.getParameter("sign");//校验值，md5(appkey+sporder_id+orderid) 32位小写，用于校验请求合法性

        log.info("1.元迈话费成功接收到回调，订单状态:==="+ status+"===成功接收到订单号:==="+orderid);

        ChargeReport report = new ChargeReport();

        report.setChannelNum(orderid);

        if("2".equals(status)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.元迈话费回执成功，订单状态:==="+ status+"===成功接收到订单号:==="+orderid);

        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(status+"请咨询供应商！");
            log.info("1.元迈话费回执失败，订单状态:==="+ status+"===成功接收到订单号:==="+orderid+"请咨询供应商！");

        }

        log.info("2.元迈话费添加回执消息队列" + JSON.toJSONString(report));

        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.元迈话费发送消息成功Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("0000");
    }
}
