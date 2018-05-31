package com.jtd.recharge.cache;

import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.ApplicationContextUtil;
import com.jtd.recharge.push.Pushthread;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

/**
 * Created by liyabin on 2017/8/23.
 */
@Service
public class CacheListener implements ApplicationListener<ContextRefreshedEvent> {
    private Log log = LogFactory.getLog(this.getClass());

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("缓存执行");
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            log.info("缓存开关："+SysConstants.Queue.CACHE_QUEUE_SWITCH);
            if(new Integer(SysConstants.Queue.CACHE_QUEUE_SWITCH)==SysConstants.Switch.NO) {
            int threadNum = new Integer(SysConstants.Queue.CACHE_THREAD_NUM);//线程数
            for(int i=0;i<threadNum;i++) {
                log.info("cache thread index---" + i);

                CacheThread cacheThread = new CacheThread();
                Thread thread = new Thread(cacheThread);
                thread.setName("cache thread index---" + i);

                ThreadPoolTaskExecutor poolTaskExecutor = (ThreadPoolTaskExecutor) ApplicationContextUtil.getBean("taskExecutor");
                poolTaskExecutor.execute(cacheThread);
            }
            }
        }
    }
}
