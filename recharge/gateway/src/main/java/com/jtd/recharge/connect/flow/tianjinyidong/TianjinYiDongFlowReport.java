package com.jtd.recharge.connect.flow.tianjinyidong;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @autor lyb
 * 天津移动回执
 */
@Controller
@RequestMapping("/return")
public class TianjinYiDongFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/tianjinyd")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        InputStream in = request.getInputStream();
        String xml = IOUtils.toString(in);
        log.info("1.天津移动成功接收到回调，订单内容:==="+ xml);

        String respCode="";
        //返回的消息体
        String respMsg="";
        String orderID ="";//商户流水
        String phoneNo="";
        try {
            Document document = DocumentHelper.parseText(xml);
            Element root = document.getRootElement();

            orderID= root.element("Record").elementText("SerialNum");
            respCode= root.element("Record").elementText("Status");
            respMsg= root.element("Record").elementText("Description");
            phoneNo= root.element("Record").elementText("Mobile");

        }catch (Exception e){
            log.error("天津移动回调异常",e);
        }



        ChargeReport report = new ChargeReport();

        report.setMobile(phoneNo);
        report.setChannelNum(orderID);
        report.setMessage(respMsg);

        if("3".equals(respCode)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.天津移动回执成功===手机号==="+phoneNo+"===订单号:==="+orderID+"订单状态"+respCode);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            log.info("1.天津移动回执失败===手机号==="+phoneNo+"===订单号:==="+orderID+"订单状态"+respCode+"请咨询供应商！");
        }

        log.info("2.天津移动成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.天津移动成功发送消息Send message id is: " + putMsg.getMessageId());


        String timeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").format(new Date());
        String requestXml="<Response>\n" +
                " <Datetime>"+timeStamp+"</Datetime>\n" +
                "  <Code>10000</Code>\n" +
                "  <Message>成功</Message>\n" +
                "</Response>";
        response.getWriter().print(requestXml);
    }
}
