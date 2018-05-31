package com.jtd.recharge.senderSlow;

import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.ApplicationContextUtil;
import com.jtd.recharge.sender.ChargeSenderThread;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * Created by liyabin on 2017/9/13.
 */
@Service
public class ChargeSenderSlowListenter  implements ApplicationListener<ContextRefreshedEvent> {

    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {


        //root application context 没有parent，则是Root WebApplicationContext
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            log.info(" ChargeSenderSlow开关："+SysConstants.Queue.SUBMIT_SLOW_QUEUE_SWITCH);
            if (new Integer(SysConstants.Queue.SUBMIT_SLOW_QUEUE_SWITCH) == SysConstants.Switch.NO) {
                int threadNum = new Integer(SysConstants.Queue.SUBMIT_SLOW_THREAD_NUM);//线程数
                for (int i = 0; i < threadNum; i++) {
                    log.info("senderSlow thread index---" + i);
                    ChargeSenderSlowThread chargeSenderSlow = new ChargeSenderSlowThread();
                    Thread thread = new Thread(chargeSenderSlow);
                    thread.setName("senderSlow thread index---" + i);
                    ThreadPoolTaskExecutor poolTaskExecutor = (ThreadPoolTaskExecutor) ApplicationContextUtil.getBean("taskExecutor");
                    poolTaskExecutor.execute(chargeSenderSlow);
                }

            }
        }
    }
}
