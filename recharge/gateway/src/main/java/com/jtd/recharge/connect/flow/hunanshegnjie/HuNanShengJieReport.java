package com.jtd.recharge.connect.flow.hunanshegnjie;

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
 * Created by liyabin on 2017/9/28.
 * 湖南胜杰
 */
@Controller
@RequestMapping("/return")
public class HuNanShengJieReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/hunanshengjie")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException,DocumentException {

        String orderNum=request.getParameter("id");
        String code=request.getParameter("st");
        ChargeReport report = new ChargeReport();
        log.info("1.湖南胜杰成功接收到回调，订单状态:==="+ code+"===成功接收到订单号:==="+orderNum);

        report.setChannelNum(orderNum);

        if("2".equals(code)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.湖南胜杰回执成功===订单号:==="+orderNum+"订单状态:==="+ code);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            log.info("1.湖南胜杰回执失败===订单号:==="+orderNum+"订单状态:==="+ code+"请咨询供应商！");
        }


        log.info("2.湖南胜杰成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);
        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.湖南胜杰成功发送消息：Send message id is: " + putMsg.getMessageId());
        response.getWriter().print("OK");
    }
}
