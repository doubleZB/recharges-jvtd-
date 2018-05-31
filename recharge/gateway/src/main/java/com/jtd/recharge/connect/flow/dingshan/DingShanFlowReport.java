package com.jtd.recharge.connect.flow.dingshan;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lihuimin on 2017/5/8.
 * 鼎山科技通道
 */
@Controller
@RequestMapping("/return")
public class DingShanFlowReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/dingshan")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {



        // 以流的方式接受数据
        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);
        log.info("1.鼎山科技成功接收到回调，内容："+json);

        JSONObject jsonObject = JSON.parseObject(json);

        String status=jsonObject.getString("status");
        String massage=jsonObject.getString("desc");
        String channelNum=jsonObject.getString("order_id");
        log.info("1.鼎山科技成功接收到回调，订单状态:==="+ status+"===成功接收到订单号:==="+channelNum);
        ChargeReport chargeReport = new ChargeReport();
        chargeReport.setChannelNum(channelNum);
        if("S".equals(status)) {
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
            log.info("1.鼎山科技回执成功===订单号:==="+channelNum+"订单状态:==="+ status);
        }else{
            chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
            chargeReport.setMessage(massage);
            log.info("1.鼎山科技回执失败==订单号:==="+channelNum+"订单状态:==="+ massage);
        }

        log.info("2.鼎山科技成功添加回执消息队列" + JSON.toJSONString(chargeReport));
        MNSClient client = MessageClient.getClient();
        CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

        Message message = new Message();
        message.setMessageBody(JSON.toJSONString(chargeReport));
        Message putMsg = queue.putMessage(message);
        log.info("3.鼎山科技成功发送消息：Send message id is: " + putMsg.getMessageId());

        response.getWriter().print("success");
    }
}
