package com.jtd.recharge.connect.flow.jvtd;

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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * 老平台流量状态报告接收接口
 * Created by wangxiangping on 2017-02-06.
 */

@Controller
@RequestMapping("/return")
public class JvtdFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/jvtdFlow")
    @ResponseBody
    public Object getReport(HttpServletRequest request, HttpServletResponse response){
        InputStream in = null;
        try {
            in = request.getInputStream();
            String json = IOUtils.toString(in);
            log.info("1.老流量平台成功接收回调，返回内容---" + json);

            JSONObject report = JSON.parseObject(json);
            String statusCode = report.getString("status");
            String orderNum = report.getString("transId");
            String msg = report.getString("message");
            ChargeReport chargeReport = new ChargeReport();
            chargeReport.setChannelNum(orderNum);
            chargeReport.setMessage(statusCode);
            if(statusCode.equals("0")){
                chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                log.info("1.老流量平台回执成功===订单号:==="+orderNum+"订单状态:==="+ statusCode);
            }else {
                chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                chargeReport.setMessage(msg+"请咨询供应商！");
                log.info("1.老流量平台回执成功====订单号:==="+orderNum+"订单状态:==="+ statusCode+"请咨询供应商！");
            }
            MNSClient client = MessageClient.getClient();
            CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

            Message message = new Message();
            message.setMessageBody(JSON.toJSONString(chargeReport));
            Message messageReturn =queue.putMessage(message);
            log.info("2.老流量平台状态报告"+json+"，状态" + JSON.toJSONString(chargeReport)+",消息队列"+
                    JSON.toJSONString(message)+",返回message="+JSON.toJSONString(messageReturn));

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }
}
