package com.jtd.recharge.connect.flow.yanyanda;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

/**
 * Created by 妍妍达 on 2017/7/21.
 */
@Controller
@RequestMapping("/return")
public class YanYanDaFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/yanyanda")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try{
                String ReportCode = request.getParameter("status");
                String OutTradeNo =  request.getParameter("downstreamSerialno");
                //返回的消息体
                log.info("1.妍妍达 成功收到回調report---訂單狀態" + ReportCode + "訂單號:" + OutTradeNo);
                ChargeReport report = new ChargeReport();
                report.setChannelNum(OutTradeNo);

                if ("2".equals(ReportCode)) {
                    report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                    log.info("1.妍妍达回执成功---訂單狀態 ：" + ReportCode + "訂單號:" + OutTradeNo);
                } else {
                    report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                    report.setMessage(ReportCode + "请咨询供应商！");
                    log.info("1.妍妍达回执失败---訂單狀態 ：" + ReportCode + "訂單號:" + OutTradeNo + "请咨询供应商！状态：" + ReportCode + "请咨询供应商！");

                }

                log.info("2.妍妍达成功添加回执消息队列" + JSON.toJSONString(report));
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

                Message message = new Message();
                message.setMessageBody(JSON.toJSONString(report));
                Message putMsg = queue.putMessage(message);
                log.info("3.妍妍达成功发送消息Send message id is: " + putMsg.getMessageId());
            response.getWriter().print("success");
            } catch (Exception e) {
                 log.error(e);
        }
    }

}
