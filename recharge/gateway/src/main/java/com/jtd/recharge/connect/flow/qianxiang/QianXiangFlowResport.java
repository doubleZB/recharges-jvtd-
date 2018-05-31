package com.jtd.recharge.connect.flow.qianxiang;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lhm on 2017/8/29.
 * 千像流量接口对接
 */
@Controller
@RequestMapping("/return")
public class QianXiangFlowResport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/flow/qianxiang")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        String json = IOUtils.toString(inputStream);
        log.info("1.千像流量返回的成功接收到回调------------------内容："+json);

        JSONObject jsonObject = JSON.parseObject(json);

        String data = jsonObject.getString("data");

        JSONArray jsonArray = JSONArray.parseArray(data);
        String mge = null;
        String resCode = null;
        String channelNum = null;
        for(int i=0 ; i < jsonArray.size() ;i++)
        {
            //获取每一个JsonObject对象
            JSONObject myjObject = jsonArray.getJSONObject(i);
            mge = myjObject.getString("message");
            resCode = myjObject.getString("code");
            channelNum = myjObject.getString("messageid");
            log.info("1.千像流量返回的成功接收到回调===状态="+resCode+"==订单号:==="+channelNum);

            ChargeReport chargeReport = new ChargeReport();
            chargeReport.setChannelNum(channelNum);
            if ("1".equals(resCode)) {
                chargeReport.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                log.info("1.千像流量回执成功===状态="+resCode+"==订单号:==="+channelNum);
            } else {
                chargeReport.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                chargeReport.setMessage(mge+"请咨询供应商！");
                log.info("1.千像流量回执失败===状态="+resCode+"==订单号:==="+channelNum+"请咨询供应商！");
            }

            log.info("2.千像流量成功添加回执消息队列" + JSON.toJSONString(chargeReport));
            MNSClient client = MessageClient.getClient();
            CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

            Message message = new Message();
            message.setMessageBody(JSON.toJSONString(chargeReport));
            Message putMsg = queue.putMessage(message);
            log.info("3.千像流量成功发送消息Send message id is: " + putMsg.getMessageId());

        }
        response.getWriter().print("200");
    }
}
