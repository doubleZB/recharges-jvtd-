package com.jtd.recharge.base.util;

/**
 * Created by liyabin on 2017/9/12.
 */
public class HandleDataSource {
    public static final ThreadLocal<String> holder = new ThreadLocal<String>();

    public static void putDataSource(String datasource) {
        holder.set(datasource);
    }

    public static String getDataSource() {
        return holder.get();
    }
}
