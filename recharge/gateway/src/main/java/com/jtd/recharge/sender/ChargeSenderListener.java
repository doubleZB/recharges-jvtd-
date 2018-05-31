package com.jtd.recharge.sender;

import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.ApplicationContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;


/**
 * @autor jipengkun
 */

@Service
public class ChargeSenderListener implements ApplicationListener<ContextRefreshedEvent> {

    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {


        //root application context 没有parent，则是Root WebApplicationContext
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            log.info("submit开关："+SysConstants.Queue.SUBMIT_QUEUE_SWITCH);
            if(new Integer(SysConstants.Queue.SUBMIT_QUEUE_SWITCH)==SysConstants.Switch.NO) {
                int threadNum = new Integer(SysConstants.Queue.SUBMIT_THREAD_NUM);//线程数
                for (int i = 0; i < threadNum; i++) {
                    log.info("sender thread index---" + i);
                    ChargeSenderThread chargeSender = new ChargeSenderThread();
                    Thread thread = new Thread(chargeSender);
                    thread.setName("sender thread index---" + i);
                    ThreadPoolTaskExecutor poolTaskExecutor = (ThreadPoolTaskExecutor) ApplicationContextUtil.getBean("taskExecutor");
                    poolTaskExecutor.execute(chargeSender);
                }
            }
        }

    }

}
