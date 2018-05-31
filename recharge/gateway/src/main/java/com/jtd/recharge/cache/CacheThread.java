package com.jtd.recharge.cache;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.ApplicationContextUtil;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeReport;
import com.jtd.recharge.dao.po.CacheOrder;
import com.jtd.recharge.push.PushService;
import com.jtd.recharge.report.ReportService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Created by liyabin on 2017/8/23.
 */
public class CacheThread implements  Runnable {
    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public void run() {
        while (1 > 0) {
            try {
                long  start = System.currentTimeMillis();
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.CACHE_QUEUE);
                Message popMsg = queue.popMessage();
                if(popMsg != null) {
                    String jsonMessage = popMsg.getMessageBodyAsString();
                    log.info("reportJson:"+jsonMessage);
                    queue.deleteMessage(popMsg.getReceiptHandle());
                    CacheOrder cacheOrder = JSON.parseObject(jsonMessage, CacheOrder.class);
                    CacheService cacheService = (CacheService) ApplicationContextUtil.getBean("cacheService");
                    cacheService.cacheOrderSubmit(cacheOrder);
                    log.info("队列收到缓存订单信息cache：" + jsonMessage+" 处理耗时："+(System.currentTimeMillis()-start));
                }

                if(popMsg == null) {
                    Thread.sleep(1000);
                    continue;
                }
        } catch (Exception e) {
            log.error("轮询线程异常", e);
            continue;
        }
        }
    }
}
