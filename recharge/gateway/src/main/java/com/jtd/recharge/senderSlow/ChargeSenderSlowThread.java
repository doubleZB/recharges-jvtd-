package com.jtd.recharge.senderSlow;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.jtd.recharge.base.cache.redis.RedisTemplate;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.ApplicationContextUtil;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.bean.ChargeMessage;
import com.jtd.recharge.bean.ChargeRequest;
import com.jtd.recharge.sender.ChargeSendService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by liyabin on 2017/9/13.
 */
public class ChargeSenderSlowThread implements  Runnable{
    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public void run() {
        while(1>0) {
            try {
                long start = System.currentTimeMillis();
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.SUBMIT_SLOW_QUEUE);
                Message popMsg = queue.popMessage();

                if(popMsg != null) {
                    String jsonMessage = popMsg.getMessageBodyAsString();
                    queue.deleteMessage(popMsg.getReceiptHandle());

                    log.info("6、从阿里云接收数据SubmitSlow " + jsonMessage);
                    ChargeMessage chargeMessage = JSON.parseObject(jsonMessage, ChargeMessage.class);

                    ChargeSenderSlowService chargeSendSlowService = (ChargeSenderSlowService) ApplicationContextUtil.getBean("chargeSendSlowService");
                    chargeSendSlowService.chargeSend(chargeMessage);
                    long time = System.currentTimeMillis() - start;
                    log.info("队列收到状态报告SubmitSlow：" + jsonMessage + " 处理耗时：" + time);
                }
                if(popMsg == null) {
                    //log.info("pushJsonNULL"+SysConstants.Queue.SUBMIT_SLOW_QUEUE);
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
