package com.jtd.recharge.base.db;

import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by liyabin on 2017/10/12.
 */
public class DataSourceAspect {

    protected static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);
//    private static final Map<String, DynamicDataSourceGlobal> cacheMap = new ConcurrentHashMap<>();

    public void before(JoinPoint point)
    {
        DynamicDataSourceGlobal dynamicDataSourceGlobal = null;
//        Object target = point.getTarget();
        String method = point.getSignature().getName();

//        Class<?>[] classz = target.getClass().getInterfaces();
//
//        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
//                .getMethod().getParameterTypes();
        try {
//            Method m = classz[0].getMethod(method, parameterTypes);
//           if(m.getName().indexOf("select")!=-1||m.getName().indexOf("get")!=-1) {
        	if(method.indexOf("select")!=-1||method.indexOf("get")!=-1) {
               dynamicDataSourceGlobal = DynamicDataSourceGlobal.READ;
            } else {
               dynamicDataSourceGlobal = DynamicDataSourceGlobal.WRITE;
            }
            DynamicDataSourceHolder.putDataSource(dynamicDataSourceGlobal);
            logger.info("设置方法[{}] use [{}] Strategy",dynamicDataSourceGlobal, method);

        } catch (Exception e) {
        	logger.error(e.getMessage(),e);
        }
    }
}
