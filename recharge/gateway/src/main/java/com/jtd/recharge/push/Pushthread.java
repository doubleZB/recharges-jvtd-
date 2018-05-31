package com.jtd.recharge.push;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.ApplicationContextUtil;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import com.jtd.recharge.dao.bean.CallbackReport;
import com.jtd.recharge.report.ReportService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by liyabin on 2017/7/31.
 */
public class Pushthread implements  Runnable{
    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public void run() {

        long start = System.currentTimeMillis();

        while(1>0) {
            try {
                start = System.currentTimeMillis();
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.PUSH_QUEUE);
                Message popMsg = queue.popMessage();
                if(popMsg != null) {
                    String jsonMessage = popMsg.getMessageBodyAsString();
                    log.info("pushJson:"+jsonMessage);
                    queue.deleteMessage(popMsg.getReceiptHandle());

                    CallbackReport callbackReport = JSON.parseObject(jsonMessage, CallbackReport.class);

                    PushService pushService = (PushService) ApplicationContextUtil.getBean("pushService");
                    pushService.pushCallback(callbackReport);
                    log.info("队列收到状态报告Push：" + jsonMessage+" 处理耗时："+(System.currentTimeMillis()-start));
                }

                if(popMsg == null) {
                    //log.info("pushJsonNULL"+SysConstants.Queue.PUSH_QUEUE);
                    Thread.sleep(2000);
                    continue;
                }
                //Thread.sleep(1000);
            } catch (Exception e) {
                log.error("轮询线程异常",e);
                continue;
            }
        }
    }
}
