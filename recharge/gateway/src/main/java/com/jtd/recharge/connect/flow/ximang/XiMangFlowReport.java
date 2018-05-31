package com.jtd.recharge.connect.flow.ximang;

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
import java.util.List;

/**
 * Created by 西芒流量 on 2018/03/07.
 */
@Controller
@RequestMapping("/return")
public class XiMangFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/ximang")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);
        try{
            log.info("1.西芒流量 成功收到回調report---" + json);
            Document document = DocumentHelper.parseText(json);
            Element rootBody = document.getRootElement();
            List<Element> itemList=rootBody.element("body").elements("item");
            for (int i = 0; i < itemList.size(); i++) {
                Element item = (Element) itemList.get(i);
                String RspCode =item.elementText("result");
                String orderId =item.elementText("orderId");
                String desc =item.elementText("desc");
                ChargeReport report = new ChargeReport();
                log.info("1.西芒流量成功接收到回调，订单状态:==="+ RspCode+"===成功接收到订单号:==="+orderId);
                report.setChannelNum(orderId);
                if("0000".equals(RspCode)) {
                    report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                    log.info("1.西芒流量回执成功===订单号:==="+orderId+"订单状态:==="+ RspCode);
                } else {
                    report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                    report.setMessage(desc+"请咨询供应商！");
                    log.info("1.西芒流量回执失败===订单号:==="+orderId+"订单状态:==="+ RspCode+"请咨询供应商！");
                }
                log.info("2.西芒流量成功添加回执消息队列" + JSON.toJSONString(report));
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

                Message message = new Message();
                message.setMessageBody(JSON.toJSONString(report));
                Message putMsg = queue.putMessage(message);
                log.info("3.西芒流量成功发送消息：Send message id is: " + putMsg.getMessageId());
            }
            String result = "<response>\n" +
                    "<result>0000</result>\n" +
                    "<desc>回执成功</desc>\n" +
                    "</response>";
            response.getWriter().print(result);
        } catch (Exception e) {
            log.error(e);
        }
    }

}
