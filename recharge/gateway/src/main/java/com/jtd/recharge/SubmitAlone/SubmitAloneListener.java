package com.jtd.recharge.SubmitAlone;

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
 * Created by Administrator on 2017/8/26.
 */
@Service
public class SubmitAloneListener implements ApplicationListener<ContextRefreshedEvent> {

    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        //root application context 没有parent，则是Root WebApplicationContext
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            log.info("submitAlone开关："+SysConstants.Queue.SUBMIT_ALONE_SWITCH);
            if(new Integer(SysConstants.Queue.SUBMIT_ALONE_SWITCH)==SysConstants.Switch.NO) {
                int threadNum = new Integer(SysConstants.Queue.SUBMIT_ALONE_NUM);//线程数
                for (int i = 0; i < threadNum; i++) {
                    log.info("submitAlone thread index---" + i);

                    SubmitAloneThread submitAloneThread = new SubmitAloneThread();
                    Thread thread = new Thread(submitAloneThread);
                    thread.setName("submitAlone" + i);
                    ThreadPoolTaskExecutor poolTaskExecutor = (ThreadPoolTaskExecutor) ApplicationContextUtil.getBean("taskExecutor");
                    poolTaskExecutor.execute(submitAloneThread);
                }
            }
        }
    }

}

