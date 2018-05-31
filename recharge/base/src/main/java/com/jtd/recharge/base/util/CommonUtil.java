package com.jtd.recharge.base.util;

import org.apache.commons.lang.RandomStringUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @autor jipengkun
 */
public class CommonUtil {

    /**
     * 手机号是否合法
     * @param input
     * @return
     */
    public static boolean isPhoneNum(String input){
        Pattern p = Pattern.compile("^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(16[6])|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))[0-9]{8}$");
        return p.matcher(input).matches();
    }

    /**
     * 生成定单号
     * @return
     */
    public static String getOrderNum() {
        String orderNum = "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String datestr =formatter.format(new Date());

        String random  = RandomStringUtils.randomNumeric(5);

        orderNum = "o" + datestr + random;
        return orderNum;
    }

    /**
     * 生成定单号
     * @return
     */
    public static String getChannelNum() {
        String orderNum = "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String datestr =formatter.format(new Date());

        String random  = RandomStringUtils.randomNumeric(5);

        orderNum = "c" + datestr + random;
        return orderNum;
    }

    /**
     * 规定生成定单号
     * @return
     */
    public static String getChannelNumTwo() {
        String orderNum = "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String datestr =formatter.format(new Date());

        String random  = RandomStringUtils.randomNumeric(4);

        orderNum = "W" + datestr + random;
        return orderNum;
    }
    /**
     * 生成支付流水
     * @return
     */
    public static String getPayNum() {
        String orderNum = "";

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String datestr =formatter.format(new Date());

        String random  = RandomStringUtils.randomNumeric(5);

        orderNum = "p" + datestr + random;
        return orderNum;
    }

    /**
     * 根据订单号获取,订单所在的表名.charge_order_201611
     * @param orderNum 订单号
     * @return
     */
    public static String getOrderTableName(String orderNum) {
        String tableName = "charge_order";

        String orderDate =  orderNum.substring(1,7);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        String currentDate =  dateFormat.format(new Date());
        if(currentDate.equals(orderDate)) {
            return tableName;
        } else {
            return tableName + "_" + orderDate;
        }
    }

    /**
     * 根据流水号获取所在所在的表名.charge_order_detail_201611
     * @param channelNum 流水号
     * @return
     */
    public static String getOrderDetailTableName(String channelNum) {
        String tableName = "charge_order_detail";

        String orderDate =  channelNum.substring(1,7);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMM");
        String currentDate =  dateFormat.format(new Date());
        if(currentDate.equals(orderDate)) {
            return tableName;
        } else {
            return tableName + "_" + orderDate;
        }
    }

    /**
     * 获取请求ip
     * @param request
     * @return
     */
    public static String getRemortIP(HttpServletRequest request) {
        /*if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");*/

        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static void main(String[] args) {
        System.out.println(getOrderNum());
    }


}
