package com.jtd.recharge.user.action.statistics;

import com.alibaba.fastjson.JSON;
import com.jtd.recharge.base.util.ExportExcelUtil;
import com.jtd.recharge.dao.po.*;
import com.jtd.recharge.service.statistics.UserStatisticsDayService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by lyp on 2017/3/7.
 *商户端流量/话费/视频会员应用概览，根据月查询当月每天的页面功能
 */

@Controller
@RequestMapping("/userStatisticsDay")
public class UserStatisticsDayAction {
    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private UserStatisticsDayService userStatisticsDayService;

    /**
     * 获取昨天关键数据通过用户id
     * @param request
     * @return
     */
    @RequestMapping("/userStatisticsByUserId")
    @ResponseBody
    public Object userStatisticsByUserId(HttpServletRequest request,Integer businessType){
        User users =(User) request.getSession().getAttribute("users");
        HashMap<String,Object> map=new HashMap<String,Object>();
        int userId=users.getId();
        UserStatisticsDay userStatisticsDay=new UserStatisticsDay();
        userStatisticsDay.setUserId(userId);
        userStatisticsDay.setBusinessType(businessType);

        List<UserStatisticsDay> list = userStatisticsDayService.userStatisticsByUserId(userStatisticsDay);

        for (UserStatisticsDay user:list){
            if(user==null){
                Integer  successOrderNum= 0;
                Integer sumOrderNum=0;
                BigDecimal a=new BigDecimal(0);
                Double amount= a.doubleValue();
                String successRate="0";
                map.put("successOrderNum",successOrderNum);
                map.put("sumOrderNum",sumOrderNum);
                map.put("amount",amount);
                map.put("successRate",successRate);
            }else{
                Integer  successOrderNum= user.getSuccessOrderNum();
                Integer sumOrderNum=user.getSumOrderNum();
                BigDecimal amount=user.getAmount();
                String successRate=user.getSuccessRate();
                if (successRate==null){
                    successRate="0";
                }
                System.out.println(successRate);
                map.put("successOrderNum",successOrderNum);
                map.put("sumOrderNum",sumOrderNum);
                map.put("amount",amount);
                map.put("successRate",successRate);
            }
        }
        return map;
    }

    /**
     *通过月份查询本月的数据table表格
     * @param request
     * @param updateTime 从页面参数获取的时间
     * @return
     */
    @RequestMapping("/userSelectByMonth")
    @ResponseBody
    public String userSelectByMonth(HttpServletRequest request, String updateTime, Integer businessType){
        User users =(User) request.getSession().getAttribute("users");
        int userId=users.getId();
        UserIdAndSelectTime userIdAndSelectTime=new UserIdAndSelectTime();
        userIdAndSelectTime.setUserId(userId);
        userIdAndSelectTime.setUpdateTime(updateTime);
        userIdAndSelectTime.setBusinessType(businessType);
        List<String> listDate=dateConversion(updateTime);
        List<UserStatisticsDay> list= userStatisticsDayService.userSelectByMonth(userIdAndSelectTime);
        List<Object>  selectOutTimeList=new ArrayList<Object>();
        List<Object>  successOrderNumList=new ArrayList<Object>();
        List<Object>  amountList=new ArrayList<Object>();
        for(String day:listDate){
            boolean flag=false;
            for ( UserStatisticsDay userStatisticsDay:list){
                if(day.equals(userStatisticsDay.getSelectOutTime())){
                    selectOutTimeList.add(userStatisticsDay.getSelectOutTime());
                    successOrderNumList.add(userStatisticsDay.getSuccessOrderNum());
                    amountList.add(userStatisticsDay.getAmount());
                    flag=true;
                    break;
                }
            }
            if (!flag) {
                selectOutTimeList.add(day);
                successOrderNumList.add(0);
                amountList.add(0);
            }
        }
        HashMap<String,Object> maps=new HashMap<String,Object>();
        maps.put("selectOutTimeList",selectOutTimeList);
        maps.put("successOrderNumList",successOrderNumList);
        maps.put("amountList",amountList);
        return JSON.toJSONString(maps);
    }

    /**
     * 通过月份查询折线图的数据订单金额
     * @param request
     * @param updateTime
     * @param businessType
     * @return
     */
    @RequestMapping("/userGraphSelectByMonth")
    @ResponseBody
    public Object userGraphSelectByMonth(HttpServletRequest request, String updateTime, Integer businessType){
        User users =(User) request.getSession().getAttribute("users");
        int userId=users.getId();
        UserIdAndSelectTime userIdAndSelectTime=new UserIdAndSelectTime();
        userIdAndSelectTime.setUserId(userId);
        userIdAndSelectTime.setUpdateTime(updateTime);
        userIdAndSelectTime.setBusinessType(businessType);
        List<String> listDate=dateConversion(updateTime);
        List<UserStatisticsDay> list= userStatisticsDayService.userSelectByMonth(userIdAndSelectTime);
        List<Object>  count=new ArrayList<Object>();
        List<Object>  month=new ArrayList<Object>();
        for(String day:listDate){
            boolean flag=false;
        for ( UserStatisticsDay userStatisticsDay:list){
            if(day.equals(userStatisticsDay.getSelectOutTime())){
                count.add(userStatisticsDay.getAmount());
                month.add(userStatisticsDay.getSelectOutTime().substring(8,10));
                flag=true;
                break;
            }
        }
            if (!flag) {
                count.add(0);
                month.add(day.substring(8,10));
            }
        }
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("count",count);
        map.put("month",month);

        return JSON.toJSONString(map);
    }

    /**
     *通过月份查询折线图订单的总数
     * @param request
     * @param updateTime
     * @param businessType
     * @return
     */
    @RequestMapping("/userGraphSelectByMonthSumOrderNum")
    @ResponseBody
    public Object userGraphSelectByMonthSumOrderNum(HttpServletRequest request, String updateTime, Integer businessType){
        User users =(User) request.getSession().getAttribute("users");
        int userId=users.getId();
        UserIdAndSelectTime userIdAndSelectTime=new UserIdAndSelectTime();
        userIdAndSelectTime.setUserId(userId);
        userIdAndSelectTime.setUpdateTime(updateTime);
        userIdAndSelectTime.setBusinessType(businessType);

        List<String> listDate=dateConversion(updateTime);
        List<UserStatisticsDay> list= userStatisticsDayService.userSelectByMonth(userIdAndSelectTime);
        List<Object>  count=new ArrayList<Object>();
        List<Object>  month=new ArrayList<Object>();
        for(String day:listDate) {
            boolean flag = false;
            for (UserStatisticsDay userStatisticsDay : list) {
                if(day.equals(userStatisticsDay.getSelectOutTime())) {
                    count.add(userStatisticsDay.getSumOrderNum());
                    month.add(userStatisticsDay.getSelectOutTime().substring(8, 10));
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                count.add(0);
                month.add(day.substring(8,10));
            }
        }
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("count",count);
        map.put("month",month);
        return JSON.toJSONString(map);
    }

    /**
     * 通过月数据查询折线图的成功率
     * @param request
     * @param updateTime
     * @param businessType
     * @return
     */
    @RequestMapping("/userGraphSelectByMonthSuccessRate")
    @ResponseBody
    public Object userGraphSelectByMonthSuccessRate(HttpServletRequest request, String updateTime, Integer businessType){
        User users =(User) request.getSession().getAttribute("users");
        int userId=users.getId();
        UserIdAndSelectTime userIdAndSelectTime=new UserIdAndSelectTime();
        userIdAndSelectTime.setUserId(userId);
        userIdAndSelectTime.setUpdateTime(updateTime);
        userIdAndSelectTime.setBusinessType(businessType);

        List<String> listDate=dateConversion(updateTime);
        List<UserStatisticsDay> list= userStatisticsDayService.userSelectByMonth(userIdAndSelectTime);
        System.out.println(list);
        List<Object>  count=new ArrayList<Object>();
        List<Object>  month=new ArrayList<Object>();
        for(String day:listDate) {
            boolean flag = false;
            for (UserStatisticsDay userStatisticsDay : list) {
                if(day.equals(userStatisticsDay.getSelectOutTime())) {
                    if (day.equals(userStatisticsDay.getSelectOutTime())) {
                        if (userStatisticsDay.getSuccessRate() == null) {
                            count.add(0);
                        } else {
                            count.add(userStatisticsDay.getSuccessRate());
                        }
                        month.add(userStatisticsDay.getSelectOutTime().substring(8, 10));
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                count.add(0);
                month.add(day.substring(8,10));
            }
        }
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("count",count);
        map.put("month",month);
        return JSON.toJSONString(map);
    }

    /**
     * 报表导出，根据月数据查询每天的
     * @param request
     * @param updateTime
     * @param businessType
     * @return
     */
    @RequestMapping("/downloadExcelMonth")
    @ResponseBody
    public void downloadExcel(HttpServletResponse response, HttpServletRequest request,String updateTime,Integer businessType){
        User users =(User) request.getSession().getAttribute("users");
        int userId=users.getId();
        UserIdAndSelectTime userIdAndSelectTime=new UserIdAndSelectTime();
        userIdAndSelectTime.setUserId(userId);
        userIdAndSelectTime.setUpdateTime(updateTime);
        userIdAndSelectTime.setBusinessType(businessType);

        List<UserStatisticsDay> list= userStatisticsDayService.userSelectByMonth(userIdAndSelectTime);
        String businessTypeName=(businessType==1?"流量":"话费");
        String fileNameStart="用户"+businessTypeName+"订单";
        String fileName= fileNameStart+".xls";
        String[] userHeaders={"selectOutTime:订单时间","successOrderNum:成功订单数","amount:订单金额(元)"};
        try {
            ExportExcelUtil.exportExcel(response,fileName,userHeaders,list);
        } catch (Exception e) {
            log.error("月数据表格下载失败");
           // e.printStackTrace();
        }
    }

    /**
     * 首页昨天数据
     * @param request
     * @return
     */
    @RequestMapping("/countUserYesterday")
    @ResponseBody
    public Object countUserYesterday(HttpServletRequest request){
        User users =(User) request.getSession().getAttribute("users");
        int userId=users.getId();
        UserStatisticsDay userStatisticsDay=new UserStatisticsDay();
        userStatisticsDay.setUserId(userId);


        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
        String timeStr= df.format(date.getTime())+"%";
        userStatisticsDay.setUpdateTime(timeStr);
        return userStatisticsDayService.countUserYesterday(userStatisticsDay);
    }

    /**
     * 日期转换
     * @param time
     * @return
     */
    private List dateConversion(String time) {
        String[] times=time.split("-");
        int year = Integer.parseInt(times[0]);
        int month = Integer.parseInt(times[1])-1;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.set(year, month, 1);
        List<String> list=new ArrayList<String>();
        int totalDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= totalDays; i++) {
            c.set(Calendar.DAY_OF_MONTH, i);
            Date date = c.getTime();
            list.add(df.format(c.getTime()));
        }
        return list;
    }
}
