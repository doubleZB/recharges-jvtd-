package com.jtd.recharge.connect.flow.chengduchongxin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import com.jtd.recharge.connect.telbill.ziteng.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lhm on 2017/8/10
 * 成都众信
 */
@Controller
@RequestMapping("/return")
public class ChengDuZhongXinFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/chengduzhongxin")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream inputStream = request.getInputStream();
        String json = IOUtils.toString(inputStream);
        JSONObject jsonObject = JSON.parseObject(json);

        String status = jsonObject.getString("respCode");
        String respMsg = jsonObject.getString("respMsg");
        String channelNum = jsonObject.getString("orderNo");

        log.info("1.成都众信成功接收到回调，订单状态:==="+ status+"===成功接收到订单号:==="+channelNum);
        ChargeReport chargeReport = new ChargeReport();
        chargeReport.setChannelNum(channelNum);
        if("0002".equals(status)) {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.成都众信回执成功===订单号:==="+channelNum+"订单状态:==="+ status);
        }else{
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            chargeReport.setMessage(respMsg+"请咨询供应商！");
            log.info("1.成都众信回执成功===订单号:==="+channelNum+"订单状态:==="+ status+"请咨询供应商！");

        }

        log.info("2.成都众信成功添加回执消息队列" + JSON.toJSONString(chargeReport));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(chargeReport));
        Message putMsg = queue.putMessage(message);
        log.info("3.成都众信成功发送消息Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("Success");
    }
}
