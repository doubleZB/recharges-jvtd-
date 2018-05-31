package com.jtd.recharge.action.user;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.po.ChargeOperateLogs;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.service.user.ChargeOperateLogsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/1/6.
 */
@Controller
@RequestMapping("/user")
public class ChargeOperateLogsAction {

    @Resource
    public ChargeOperateLogsService chargeOperateLogsService;
    /**
     * 操作日志
     * @return
     */
    @RequestMapping("operateLog")
    public String operateLog(){
        return "/user/merchanthandlelog";
    }


    /**
     * 查询日志
     * @param pageNumber
     * @param pageSize
     * @param operateLogs
     * @return
     * @throws ParseException
     */
    @RequestMapping("ChargeOperateLogs")
    @ResponseBody
    public Object ChargeOperateLogs(Integer pageNumber, Integer pageSize, ChargeOperateLogs operateLogs) throws ParseException {
        String startTime = operateLogs.getStartOperateTime();
        String endTime = operateLogs.getEndOperateTime();
        String st = "00:00:00";
        String et = "23:59:59";
        if(startTime!=null&&!"".equals(startTime) || endTime !=null &&!"".equals(endTime)) {
            String startTimes = startTime + " " + st ;
            operateLogs.setStartOperateTime(startTimes);

            String endTimes = endTime + " " + et ;
            operateLogs.setEndOperateTime(endTimes);
        }
        PageInfo<ChargeOperateLogs> list = chargeOperateLogsService.selectChargeOperateLogs(pageNumber,pageSize,operateLogs);
        return list;
    }

}
