package com.jtd.recharge.connect.flow.danyuan;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.base.util.XMLUtil;
import com.jtd.recharge.bean.ChargeReport;
import groovy.util.NodeList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by lhm on 2017/4/19.
 * 单元信息
 */
@Controller
@RequestMapping("/return")
public class DanYuanFlowReport {
    private Log log = LogFactory.getLog(this.getClass());
    @RequestMapping("/flow/danyuan")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        ServletInputStream in = request.getInputStream();
        InputStreamReader reader = new InputStreamReader(in, "utf-8");
        BufferedReader br = new BufferedReader(reader);
        String str = br.readLine();
        StringBuffer sb = new StringBuffer();

        while (str != null) {
            sb.append(str);
            str = br.readLine();
        }
        in.close();
        reader.close();
        br.close();


        String linkid =null;
        String message =null;
        String code =null;
        log.info("1.单元流量成功接收到回调内容:==="+sb.toString());
        try {
            Document document = XMLUtil.parseStringToXml(sb.toString());
            Element element = document.getRootElement();
            List<Element> childElements = element.elements();

            for (Element child : childElements) {
                linkid = child.attribute("linkid").getValue();
                message = child.attribute("message").getValue();
                code = child.attribute("code").getValue();
                log.info("1.单元流量成功接收到回调，订单状态:==="+ code+"===成功接收到订单号:==="+linkid);

                ChargeReport report = new ChargeReport();

                report.setChannelNum(linkid);
                report.setMessage(message);
                if("0".equals(code)) {
                    report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                    log.info("1.单元流量回执成功，订单状态:==="+ code+"===成功接收到订单号:==="+linkid);
                } else {
                    report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                    log.info("1.单元流量回执失败，订单状态:==="+ code+"===成功接收到订单号:==="+linkid+"请咨询供应商");
                }


                log.info("2.单元流量成功添加回执消息队列" + JSON.toJSONString(report));
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

                Message messageOne = new Message();
                messageOne.setMessageBody(JSON.toJSONString(report));
                Message putMsg = queue.putMessage(messageOne);
                log.info("3.单元流量成功发送消息，Send message id is: " + putMsg.getMessageId());

                response.getWriter().print("success");
            }
            }catch (Exception e){
            log.info("1.单元流量接收到回调异常:==="+e.getLocalizedMessage());
        }

    }
}
