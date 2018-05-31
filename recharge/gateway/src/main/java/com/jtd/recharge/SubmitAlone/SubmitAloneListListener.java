package com.jtd.recharge.SubmitAlone;

import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.ApplicationContextUtil;
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
public class SubmitAloneListListener implements ApplicationListener<ContextRefreshedEvent> {

    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        //root application context 没有parent，则是Root WebApplicationContext
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            log.info("submitAloneList开关："+SysConstants.Queue.SUBMIT_QUERE_ALONE_LIST_SWITCH);
            if(new Integer(SysConstants.Queue.SUBMIT_QUERE_ALONE_LIST_SWITCH)==SysConstants.Switch.NO) {
                int threadNum = new Integer(SysConstants.Queue.SUBMIT_ALONE_LIST_NUM);//线程数
                for (int i = 0; i < threadNum; i++) {
                    log.info("submitAloneList thread index---" + i);
                    SubmitAloneListThread submitAloneListThread = new SubmitAloneListThread();
                    Thread thread = new Thread(submitAloneListThread);
                    thread.setName("submitAloneList");
                    System.out.println(thread.getName()+" 存活:" +thread.isAlive()+" 状态:" + thread.getState() );
                    ThreadPoolTaskExecutor poolTaskExecutor = (ThreadPoolTaskExecutor) ApplicationContextUtil.getBean("taskExecutor");
                    poolTaskExecutor.execute(submitAloneListThread);
                }
            }
        }

    }

}

