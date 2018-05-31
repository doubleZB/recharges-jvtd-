package com.jtd.recharge.connect.flow.huaxin;

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
import java.util.HashMap;

/**
 * @autor jipengkun
 * 容联回执
 */
@Controller
@RequestMapping("/return")
public class HuaXinFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/huaxinFlow")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        InputStream in = request.getInputStream();

        String strdecode = IOUtils.toString(in);//因上游给的URLD加密的所以需要转格式
        String json= java.net.URLDecoder.decode(strdecode,   "utf-8");
        log.info("1.华信流量成功接收到回调，订单内容:==="+ json);

        JSONObject jsonObject = JSON.parseObject(json);

        //返回的消息体
        String reqId=jsonObject.getString("reqId");//自己的流水号
        String status =jsonObject.getString("status");
        String phone=jsonObject.getString("phone");
        String msg=jsonObject.getString("msg");
        log.info("1.华信流量成功接收到回调，订单状态:==="+ status+"===成功接收到订单号:==="+reqId+"===手机号==="+phone);

        ChargeReport report = new ChargeReport();

        report.setMobile(phone);
        report.setChannelNum(reqId);
        report.setMessage(msg);

        if("3".equals(status)) {
            report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.华信流量回执成功===手机号==="+phone+"===订单号:==="+reqId);
        } else {
            report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            log.info("1.华信流量回执失败===手机号==="+phone+"===订单号:==="+reqId+"请咨询供应商！");
        }

        log.info("2.华信流量成功添加回执消息队列" + JSON.toJSONString(report));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(report));
        Message putMsg = queue.putMessage(message);
        log.info("3.华信流量成功发送消息：Send message id is: " + putMsg.getMessageId());
        String result="SUCC";
        HashMap<String, String> map=new HashMap<String, String>();
        map.put("result", result);
        response.getWriter().print(JSON.toJSONString(map));
    }
}
