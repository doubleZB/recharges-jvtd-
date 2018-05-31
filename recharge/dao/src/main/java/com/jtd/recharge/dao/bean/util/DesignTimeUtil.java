package com.jtd.recharge.dao.bean.util;

import com.jtd.recharge.dao.bean.Order;
import com.jtd.recharge.dao.po.ChargeOrder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lhm on 2017/1/22.
 */
public class DesignTimeUtil{

    public static void submeter(String startTime,Order orders){
        //重置表名
        orders.setOrder("charge_order");
        //订单详情表
        orders.setOrder_detail("charge_order_detail");
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
        if(startTime!=null&&!"".equals(startTime)) {
            try {
                //转换格式并且付给开始时间
                Date parse = fmt.parse(startTime);
                //赋值开始时间
                fmt = new SimpleDateFormat("yyyyMM");
                String starTimes = fmt.format(parse);
                String now = fmt.format(new Date());
                //传进来的开始时间如果不等于系统当期那日期
                if(!starTimes.equals(now)){
                    orders.setOrder("charge_order_"+starTimes);
                    orders.setOrder_detail("charge_order_detail_"+starTimes);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void Time(String startTime,String endTime,ChargeOrder chargeOrder){
        String st = "00:00:00";
        String et = "23:59:59";
        chargeOrder.setTable("charge_order");
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dateStart=null;
        Date dateEnd=null;
        //根据时间日期 分表
        if(startTime!=null&&!"".equals(startTime)) {
            String startime = startTime + " " + st ;
            try {
                dateStart= fmt.parse(startime);
            }catch (ParseException e){
                e.printStackTrace();
            }
            chargeOrder.setOrderTime(dateStart);
            Date date=new Date();
            DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
            String time=format.format(date);//当前时间
            String times=time.substring(0,7);
            String satartimes = startime.substring(0,7);
            if (times.equals(satartimes)) {
                chargeOrder.setTable("charge_order");
            } else {
                String []str=satartimes.split("-");
                chargeOrder.setTable("charge_order" + "_"+str[0]+str[1]);
            }
        }else{
            chargeOrder.setTable("charge_order");
        }

        //结束时间
        if(endTime!=null&&!"".equals(endTime)){
            String endtime = endTime + " " + et ;
            try {
                dateEnd =  fmt.parse(endtime);
            }catch (ParseException e){
                e.printStackTrace();
            }
            chargeOrder.setOrderTimeend(dateEnd);
        }
    }

    public static void orderTimeTwo(String sTime,String eTime,Order order){

        String st = "00:00:00";
        String et = "23:59:59";
        //重置表名
        order.setOrder("charge_order");
        //转换格式
        SimpleDateFormat  fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //设置开始时间
        Date dateStart=null;
        //设置结束时间
        Date dateEnd=null;
        //根据时间日期 分表
        if(sTime!=null&&!"".equals(sTime)) {
//            String starTime = sTime + st ;
            String starTime = sTime ;
            try {
                //转换格式并且付给开始时间
                dateStart = fmt.parse(starTime);
                //赋值开始时间
                order.setOrderTimeOne(dateStart);
                System.out.println(dateStart);
                fmt = new SimpleDateFormat("yyyyMM");
                String starTimes = fmt.format(dateStart);
                String now = fmt.format(new Date());
                //传进来的开始时间如果不等于系统当期那日期
                if(!starTimes.equals(now)){
                    order.setOrder("charge_order_"+starTimes);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //结束时间
        if(eTime!=null && !"".equals(eTime)){
//            String endTime = eTime  + et ;
            String endTime = eTime;

            try {
                fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateEnd =  fmt.parse(endTime);
                order.setOrderTimeTwo(dateEnd);
                System.out.println(dateEnd);
            }catch (ParseException e){
                e.printStackTrace();
            }
        }

        //时间为空时查询当天
        else{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date date=new Date();
            String format = sdf.format(date);

            String starTime = format + " " + st ;
            String endTime = format + " " + et ;

            try {
                order.setOrderTimeOne(fmt.parse(starTime));
                order.setOrderTimeTwo(fmt.parse(endTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
