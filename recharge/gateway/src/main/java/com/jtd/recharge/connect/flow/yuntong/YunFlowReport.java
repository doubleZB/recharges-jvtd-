package com.jtd.recharge.connect.flow.yuntong;

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
 * @autor
 * 云通通信
 */
@Controller
@RequestMapping("/return")
public class YunFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/yuntong")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);

        log.info("1.云通通信成功接收到回调，内容"+json);

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
            log.info("1.云通通信回执成功===手机号==="+phoneNo+"===订单号:==="+orderID+"订单状态:==="+ respCode);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            log.info("1.云通通信回执失败===手机号==="+phoneNo+"===订单号:==="+orderID+"订单状态:==="+ respCode+"请咨询供应商！");

        }

        log.info("2.云通通信成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.云通通信成功发送消息Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("Success");
    }
}
