package com.jtd.recharge.SubmitAlone;

import com.alibaba.fastjson.JSONArray;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.ApplicationContextUtil;
import com.jtd.recharge.bean.ChargeMessage;
import com.jtd.recharge.bean.MqReceive;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Created by liyabin on 2017/12/6.
 */
public class SubmitAloneListThread implements  Runnable{
    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public void run() {
        while (1>0) {
                try {
                    String jsonMessage= MqReceive.receive(SysConstants.Queue.SUBMIT_QUERE_ALONE_LIST);
                    if (!StringUtils.isBlank(jsonMessage)){
                        long start = System.currentTimeMillis();
                        log.info("消息队列submitAlone开始时间*******************"+start);
                            log.info("消息队列submitAlone执行*******************");
                            log.info("6、从阿里云接收数据submitAlonelist " + jsonMessage);
                            List<ChargeMessage> chargeMessageList = JSONArray.parseArray(jsonMessage, ChargeMessage.class);
                            SubmitAloneService submitAloneService = (SubmitAloneService) ApplicationContextUtil.getBean("submitAloneService");
                            String result= submitAloneService.chargeSend(chargeMessageList);
                            log.info("消息队列submitAlone结束时间*******************"+System.currentTimeMillis());
                            log.info("队列收到状态报告submitAlonelist：" + jsonMessage + result+" 处理耗时：" + (System.currentTimeMillis() - start));
                    }else{
                        Thread.sleep(300);
                    }
                } catch (Exception e) {
                    log.error("轮询线程异常" + e.getMessage(), e);
                    continue;
                }
        }
    }


}
