package com.jtd.recharge.action.user;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.BalanceRecord;
import com.jtd.recharge.service.admin.OperateLogService;
import com.jtd.recharge.service.user.BalanceRecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by lihuimin on 2016/11/16.
 * 商户加款记录 审核
 */
@Controller
@RequestMapping("/user")
public class BalanceRecordAction {

    @Resource
    public BalanceRecordService balanceRecordService;
    @Resource
    public OperateLogService operateLogService;

    /**
     * 商户加款记录
     */
    @RequestMapping("/record")
    public String merchantrecord(){
        return "/user/merchantrecord";
    }

    /**
     * 商户加款记录
     * @param pageNumber
     * @param pageSize
     * @param bal
     * @return
     */
    @RequestMapping("/recordlistH")
    @ResponseBody
    public Object recordlistH(Integer pageNumber, Integer pageSize,BalanceRecord bal) throws ParseException {
        String starttime = bal.getTimeStart();
        String endtime = bal.getTimeEnd();
        String st = "00:00:00";
        String et = "23:59:59";
        if(starttime!=null&&!"".equals(starttime) || endtime !=null &&!"".equals(endtime)) {
            String stimes = starttime + " " + st ;
            bal.setTimeStart(stimes);

            String etimes = endtime + " " + et ;
            bal.setTimeEnd(etimes);
        }
        PageInfo<BalanceRecord> list = balanceRecordService.selectBalanceRecordH(pageNumber,pageSize,bal);
        return list;
    }


    /**
     * 商户加款审核
     * @return
     */
    @RequestMapping("/addfunds")
    public String merchantaddfunds(){
        return "/user/merchantaddfunds";
    }

    /**
     * 商户加款审核
     * @param pageNumber
     * @param pageSize
     * @param bal
     * @return
     */
    @RequestMapping("/recordlist")
    @ResponseBody
    public Object recordlist(Integer pageNumber, Integer pageSize,BalanceRecord bal){
        PageInfo<BalanceRecord> list = balanceRecordService.selectBalanceRecord(pageNumber,pageSize,bal);
        return list;
    }



    /**
     * 审核不通过
     * @param
     * @return
     */
    @RequestMapping("/Deletefundslist")
    @ResponseBody
    public boolean Deletefundslist(HttpServletRequest request){
        boolean res = false;
        String operate = request.getParameter("operate");
        try{
            String id = request.getParameter("id");
            String[] split = id.split(",");
            for(int i=0;i<=split.length;i++){
                BalanceRecord bal = new BalanceRecord();
                bal.setId(Integer.parseInt(split[i]));
                balanceRecordService.DeleteBalanceRecord(bal);
                operateLogService.logInfo(operate,"商户加款审核",operate+"修改：id="+Integer.parseInt(split[i])+"  审核信息为审核不通过");
            }
            res = true;
        }catch (Exception e){
            res = false;
        }
        return res;
    }

    /**
     * 审核通过
     * @param
     * @return
     */
    @RequestMapping("/Addrecordlist")
    @ResponseBody
    public String Addrecordlist(String ids,String adminId,HttpServletRequest request){
        String operate = request.getParameter("operate");
        try{
            String content = balanceRecordService.AddRecordList(ids, adminId, operate);
            return content;
        }catch (Exception e){
            return "网络异常！";
        }
    }


}
