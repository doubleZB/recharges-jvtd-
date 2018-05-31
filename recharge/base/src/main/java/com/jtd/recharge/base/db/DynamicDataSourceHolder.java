package com.jtd.recharge.base.db;

/**
 * Created by liyabin on 2017/9/19.
 */
public class DynamicDataSourceHolder {
    private static final ThreadLocal<DynamicDataSourceGlobal> holder = new ThreadLocal<DynamicDataSourceGlobal>();

    public static final ThreadLocal<String> holders = new ThreadLocal<String>();

    private DynamicDataSourceHolder() {
        //
    }

    public static void putDataSource(DynamicDataSourceGlobal dataSource){
        holder.set(dataSource);
    }

    public static DynamicDataSourceGlobal getDataSource(){
        return holder.get();
    }

    public static void clearDataSource() {
        holder.remove();
    }

    public static void putDataSource(String name) {
        holders.set(name);
    }

    public static String getDataSouce() {
        return holders.get();
    }

}
