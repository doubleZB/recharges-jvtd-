package com.jtd.recharge.user.action.statistics;

import com.alibaba.fastjson.JSON;
import com.jtd.recharge.base.util.ExportExcelUtil;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserIdAndSelectTime;
import com.jtd.recharge.dao.po.UserStatisticsDay;
import com.jtd.recharge.dao.po.UserStatisticsMonth;
import com.jtd.recharge.service.statistics.UserStatisticsMonthService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/3/11.
 * 商户端流量/话费应用概览，根据年查询当年每月的页面功能
 */
@Controller
@RequestMapping("/userStatisticsMonth")
public class UserStatisticsMonthAction {
    private Logger log = Logger.getLogger(this.getClass());
    @Resource
    private UserStatisticsMonthService userStatisticsMonthService;

    /**
     *根据年份查该年每月份的table表格数据
     * @param request
     * @param updateTime 参数年
     * @param businessType 业务类型 1流量 2话费
     * @return
     */
    @RequestMapping("/userSelectByYear")
    @ResponseBody
    public Object userSelectByYear(HttpServletRequest request, String updateTime, Integer businessType){
        User users =(User) request.getSession().getAttribute("users");
        int userId=users.getId();
        UserIdAndSelectTime userIdAndSelectTime=new UserIdAndSelectTime();
        userIdAndSelectTime.setUserId(userId);
        userIdAndSelectTime.setUpdateTime(updateTime);
        userIdAndSelectTime.setBusinessType(businessType);

        List<UserStatisticsMonth> list= userStatisticsMonthService.userSelectByYear(userIdAndSelectTime);
        List<Object>  selectOutTimeList=new ArrayList<Object>();
        List<Object>  successOrderNumList=new ArrayList<Object>();
        List<Object>  amountList=new ArrayList<Object>();
        String[] years={"01","02","03","04","05","06","07","08","09","10","11","12"};//每年12个月份
        for(String months:years){
            boolean flag=false;
            for ( UserStatisticsMonth userStatisticsMonth:list){
                if(months.equals(userStatisticsMonth.getSelectOutTime().substring(5,7))){
                    selectOutTimeList.add(userStatisticsMonth.getSelectOutTime());
                    successOrderNumList.add(userStatisticsMonth.getSuccessOrderNum());
                    amountList.add(userStatisticsMonth.getAmount());
                    flag=true;
                    break;
                }
            }
            if (!flag) {
                selectOutTimeList.add(updateTime+"-"+months);
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
     * 交易金额根据年查询折线图表格
     * @param request
     * @param updateTime
     * @param businessType
     * @return
     */
    @RequestMapping("/userGraphSelectByYear")
    @ResponseBody
    public Object userGraphSelectByYear(HttpServletRequest request, String updateTime, Integer businessType){
        User users =(User) request.getSession().getAttribute("users");
        int userId=users.getId();
        UserIdAndSelectTime userIdAndSelectTime=new UserIdAndSelectTime();
        userIdAndSelectTime.setUserId(userId);
        userIdAndSelectTime.setUpdateTime(updateTime);
        userIdAndSelectTime.setBusinessType(businessType);

        List<UserStatisticsMonth> list= userStatisticsMonthService.userSelectByYear(userIdAndSelectTime);
        List<Object>  count=new ArrayList<Object>();
        List<Object>  month=new ArrayList<Object>();
        String[] years={"01","02","03","04","05","06","07","08","09","10","11","12"};//每年12个月份
        for(String s:years){
            boolean flag=false;
        for ( UserStatisticsMonth userStatisticsMonth:list){
            if(s.equals(userStatisticsMonth.getSelectOutTime().substring(5,7))){
                count.add(userStatisticsMonth.getAmount());
                month.add(userStatisticsMonth.getSelectOutTime().substring(5,7));
                flag=true;
                break;
            }
        }
            if (!flag) {
                count.add(0);
                month.add(s);
            }
        }
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("count",count);
        map.put("month",month);

        return JSON.toJSONString(map);
    }

    /**
     * 订单数根据年查询折线图形
     * @param request
     * @param updateTime
     * @param businessType
     * @return
     */
    @RequestMapping("/userGraphSelectByYearSumOrderNum")
    @ResponseBody
    public Object userGraphSelectByYearSumOrderNum(HttpServletRequest request, String updateTime, Integer businessType){
        User users =(User) request.getSession().getAttribute("users");
        int userId=users.getId();
        UserIdAndSelectTime userIdAndSelectTime=new UserIdAndSelectTime();
        userIdAndSelectTime.setUserId(userId);
        userIdAndSelectTime.setUpdateTime(updateTime);
        userIdAndSelectTime.setBusinessType(businessType);

        List<UserStatisticsMonth> list= userStatisticsMonthService.userSelectByYear(userIdAndSelectTime);
        List<Object>  count=new ArrayList<Object>();
        List<Object>  month=new ArrayList<Object>();
        String[] years={"01","02","03","04","05","06","07","08","09","10","11","12"};//每年12个月份
        for(String s:years){
            boolean flag=false;
            for ( UserStatisticsMonth userStatisticsMonth:list){
                if(s.equals(userStatisticsMonth.getSelectOutTime().substring(5,7))){
                    count.add(userStatisticsMonth.getSumOrderNum());
                    month.add(userStatisticsMonth.getSelectOutTime().substring(5,7));
                    flag=true;
                    break;
                }
            }
            if (!flag) {
                count.add(0);
                month.add(s);
            }
        }
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("count",count);
        map.put("month",month);

        return JSON.toJSONString(map);
    }

    /**
     * 成功率根据年查询折线图数据
     * @param request
     * @param updateTime
     * @param businessType
     * @return
     */
    @RequestMapping("/userGraphSelectByYearSuccessRate")
    @ResponseBody
    public Object userGraphSelectByYearSuccessRate(HttpServletRequest request, String updateTime, Integer businessType){
        User users =(User) request.getSession().getAttribute("users");
        int userId=users.getId();
        UserIdAndSelectTime userIdAndSelectTime=new UserIdAndSelectTime();
        userIdAndSelectTime.setUserId(userId);
        userIdAndSelectTime.setUpdateTime(updateTime);
        userIdAndSelectTime.setBusinessType(businessType);

        List<UserStatisticsMonth> list= userStatisticsMonthService.userSelectByYear(userIdAndSelectTime);
        List<Object>  count=new ArrayList<Object>();
        List<Object>  month=new ArrayList<Object>();

        String[] years={"01","02","03","04","05","06","07","08","09","10","11","12"};//每年12个月份
        for(String s:years){
            boolean flag=false;
            for ( UserStatisticsMonth userStatisticsMonth:list){
                if(s.equals(userStatisticsMonth.getSelectOutTime().substring(5,7))){
                    month.add(userStatisticsMonth.getSelectOutTime().substring(5,7));
                    if(userStatisticsMonth.getSuccessRate()==null){
                        count.add(0);
                    }else{
                        count.add(userStatisticsMonth.getSuccessRate());
                    }
                    flag=true;
                    break;
                }
            }
            if (!flag) {
                count.add(0);
                month.add(s);
            }
        }
        HashMap<String,Object> map=new HashMap<String,Object>();
        map.put("count",count);
        map.put("month",month);

        return JSON.toJSONString(map);
    }

    /**
     * 根据年查询月的表格下载数据
     * @param response
     * @param request
     * @param updateTime
     * @param businessType
     */
    @RequestMapping("/downloadExcelYear")
    @ResponseBody
    public void downloadExcel(HttpServletResponse response, HttpServletRequest request, String updateTime, Integer businessType){
        User users =(User) request.getSession().getAttribute("users");
        int userId=users.getId();
        UserIdAndSelectTime userIdAndSelectTime=new UserIdAndSelectTime();
        userIdAndSelectTime.setUserId(userId);
        userIdAndSelectTime.setUpdateTime(updateTime);
        userIdAndSelectTime.setBusinessType(businessType);

        List<UserStatisticsMonth> list=userStatisticsMonthService.userSelectByYear(userIdAndSelectTime);
        String businessTypeName=(businessType==1?"流量":"话费");
        String fileNameStart="用户"+businessTypeName+"订单";
        String fileName= fileNameStart+".xls";
        String[] userHeaders={"selectOutTime:订单时间","successOrderNum:成功订单数","amount:订单金额(元)"};
        try {
            ExportExcelUtil.exportExcel(response,fileName,userHeaders,list);
        } catch (Exception e) {
            log.error("年数据表格下载失败");
            //e.printStackTrace();
        }
    }

}
