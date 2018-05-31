package com.jtd.recharge.connect.telbill.ronghuizhongtong;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by liyabin on 2017/9/5.
 */
@Controller
@RequestMapping("/return")
public class RhZhongTongTelbillReport {
    private Log log = LogFactory.getLog(this.getClass());

    @RequestMapping("/telbill/ronghuizhongtong")
    public void report(HttpServletRequest request, HttpServletResponse response) throws IOException {
        InputStream in = request.getInputStream();
        String json = IOUtils.toString(in);
        try{
            log.info("1.融汇众通 成功收到回調report---" + json);
            JSONObject jobs = JSONObject.parseObject(json);
            String serialId=jobs.getString("serialId");
            JSONArray jsonArray = JSONArray.parseArray(jobs.getString("orderList"));
            log.info("1.融汇众通 jsonArray report---" + jsonArray);
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject job = jsonArray.getJSONObject(i);
                log.info("融汇众通收到回調----" + job);
                String respCode = job.getString("orderStatus");
                //返回的消息体
                String OutTradeNo = job.getString("customerId");//商户流水
                String ReportCode = job.getString("orderRspMsg");

                log.info("1.融汇众通 成功收到回調report---訂單狀態" + respCode + "訂單號:" + OutTradeNo);
                ChargeReport report = new ChargeReport();
                report.setChannelNum(OutTradeNo);

                if ("4".equals(respCode)) {
                    report.setStatus(ChargeReport.ChargeReportStatus.SUCCESS);
                    log.info("1.融汇众通回执成功---訂單狀態 ：" + respCode + "訂單號:" + OutTradeNo);
                } else {
                    report.setStatus(ChargeReport.ChargeReportStatus.FAIL);
                    report.setMessage(ReportCode + "请咨询供应商！");
                    log.info("1.融汇众通回执失败---訂單狀態 ：" + respCode + "訂單號:" + OutTradeNo + "请咨询供应商！状态：" + ReportCode + "请咨询供应商！");

                }

                log.info("2.融汇众通成功添加回执消息队列" + JSON.toJSONString(report));
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);

                Message message = new Message();
                message.setMessageBody(JSON.toJSONString(report));
                Message putMsg = queue.putMessage(message);
                log.info("3.融汇众通成功发送消息Send message id is: " + putMsg.getMessageId());
            }
            Map map=new HashedMap();
            map.put("serialId ",serialId);
            map.put("rspCode","SUCCESS");
            response.getWriter().print("ok");
        } catch (Exception e) {
            log.error(e);
        }
    }
}
