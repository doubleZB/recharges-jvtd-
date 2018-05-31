package com.jtd.recharge.report;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.ApplicationContextUtil;
import com.jtd.recharge.bean.ChargeReport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @autor jipengkun
 */
public class ReportThread implements  Runnable{
    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public void run() {

        long start = System.currentTimeMillis();

        while(1>0) {

            try {
                start = System.currentTimeMillis();
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.REPORT_QUEUE);
                Message popMsg = queue.popMessage();
                if(popMsg != null) {
                    String jsonMessage = popMsg.getMessageBodyAsString();
                    log.info("reportJson:"+jsonMessage);
                    queue.deleteMessage(popMsg.getReceiptHandle());

                    ChargeReport chargeReport = JSON.parseObject(jsonMessage, ChargeReport.class);

                    ReportService reportService = (ReportService) ApplicationContextUtil.getBean("reportService");
                    reportService.reportHandle(chargeReport);
                    log.info("队列收到状态报告Report：" + jsonMessage+" 处理耗时："+(System.currentTimeMillis()-start));
                }
                if(popMsg == null) {
                    //log.info("pushJsonNULL"+SysConstants.Queue.REPORT_QUEUE);
                    Thread.sleep(2000);
                    continue;
                }
            } catch (Exception e) {
                log.error("轮询线程异常",e);
                continue;
            }
        }
    }
}
