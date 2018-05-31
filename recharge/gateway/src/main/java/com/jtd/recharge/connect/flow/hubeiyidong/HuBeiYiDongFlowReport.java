package com.jtd.recharge.connect.flow.hubeiyidong;

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
import org.dom4j.Document;
import org.dom4j.DocumentException;
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
 * @autor lyp
 * 湖北移动回执
 */
@Controller
@RequestMapping("/return")
public class HuBeiYiDongFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/hubeiyidong")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException,DocumentException {

        InputStream in = request.getInputStream();
        String xmlStrig = IOUtils.toString(in);

        log.info("1.湖北移动成功接收到回调，订单内容:==="+ xmlStrig);

        Document document = DocumentHelper.parseText(xmlStrig);
        Element root = document.getRootElement();
        String TransIDO = root.elementText("TransIDO");
        String RspCode = root.element("Response").elementText("RspCode");
        SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = sdf.format(new Date());
        ChargeReport report = new ChargeReport();
        log.info("1.湖北移动成功接收到回调，订单状态:==="+ RspCode+"===成功接收到订单号:==="+TransIDO);

        report.setChannelNum(TransIDO);

        if("00".equals(RspCode)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.湖北移动回执成功===订单号:==="+TransIDO+"订单状态:==="+ RspCode);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            report.setMessage(xmlStrig+"请咨询供应商！");
            log.info("1.湖北移动回执失败===订单号:==="+TransIDO+"订单状态:==="+ RspCode+"请咨询供应商！");
        }


        log.info("2.湖北移动成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.湖北移动成功发送消息：Send message id is: " + putMsg.getMessageId());
        String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                "<InterBOSS>\n" +
                "<BIPCode>LLF8F006</BIPCode>\n" +
                "<ActivityCode>T8012006</ActivityCode>\n" +
                "<ActionCode>1</ActionCode>\n" +
                "<TransIDO>"+TransIDO+"</TransIDO>\n" +
                "<ProcessTime>"+dateString+"</ProcessTime>\n" +
                "<Response>\n" +
                "<RspCode>00</RspCode>\n" +
                "<RspDesc>请求信息接收成功</RspDesc>\n" +
                "</Response>\n" +
                "</InterBOSS>";
        response.getWriter().print(result);
    }
}
