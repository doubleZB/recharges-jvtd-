package com.jtd.recharge.push;

import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.ApplicationContextUtil;
import com.jtd.recharge.report.ReportThread;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * Created by liyabin on 2017/7/31.
 */
@Service
public class PushListener  implements ApplicationListener<ContextRefreshedEvent> {
    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        log.info("推送执行-队列push");
        //root application context 没有parent，则是Root WebApplicationContext
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            log.info("push开关："+SysConstants.Queue.PUSH_QUEUE_SWITCH);
            if(new Integer(SysConstants.Queue.PUSH_QUEUE_SWITCH)==SysConstants.Switch.NO) {
                int threadNum = new Integer(SysConstants.Queue.PUSH_THREAD_NUM);//线程数
                log.info("线程数-" + threadNum);
                for (int i = 0; i < threadNum; i++) {
                    log.info("push thread index---" + i);

                    Pushthread pushThread = new Pushthread();
                    Thread thread = new Thread(pushThread);
                    thread.setName("push thread index---" + i);

                    ThreadPoolTaskExecutor poolTaskExecutor = (ThreadPoolTaskExecutor) ApplicationContextUtil.getBean("taskExecutor");
                    poolTaskExecutor.execute(pushThread);
                }
            }
        }
    }
}
