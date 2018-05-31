package com.jtd.recharge.sender;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.cache.redis.RedisTemplate;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.ApplicationContextUtil;
import com.jtd.recharge.bean.ChargeMessage;
import com.jtd.recharge.bean.ChargeRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * @autor jipengkun
 */
public class ChargeSenderThread implements  Runnable{
    private Log log = LogFactory.getLog(this.getClass());


    @Override
    public void run() {
        while(1>0) {
            try {
                long start = System.currentTimeMillis();
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.SUBMIT_QUEUE);
                Message popMsg = queue.popMessage();

                if(popMsg != null) {
                    String jsonMessage = popMsg.getMessageBodyAsString();
                    queue.deleteMessage(popMsg.getReceiptHandle());

                    log.info("6、从阿里云接收数据Submit " + jsonMessage);
                    ChargeMessage chargeMessage = JSON.parseObject(jsonMessage, ChargeMessage.class);
                    ChargeSendService chargeSendService = (ChargeSendService) ApplicationContextUtil.getBean("chargeSendService");
                    chargeSendService.chargeBase(chargeMessage);
                    long time = System.currentTimeMillis() - start;
                    log.info("队列收到状态报告Submit：" + jsonMessage + " 处理耗时：" + time);
                }
                if(popMsg == null) {
                    //log.info("pushJsonNULL"+SysConstants.Queue.SUBMIT_QUEUE);
                    Thread.sleep(2000);
                    continue;
                }
            } catch (Exception e) {
                log.error("轮询线程异常"+e.getMessage(),e);
                continue;
            }
        }
    }
}
