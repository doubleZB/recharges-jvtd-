package com.jtd.recharge.user.action.finance;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserBalanceDetail;
import com.jtd.recharge.service.user.BillService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by anna on 2016-12-12.
 */
@RequestMapping("/bill")
@Controller
public class BillDetailsAction {

    @Resource
    BillService billService;


    @RequestMapping({"/billDetails"})
    public String billDetails(){
        return "bill/billDetails";
    }


    @ResponseBody
    @RequestMapping("/getBillDetails")
    public Object getBillDetails(HttpServletRequest request,Integer pageNum,Integer pageSize){
        String flowNum = request.getParameter("flowNum");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String inOrOut = request.getParameter("inOrOut");
        String costType = request.getParameter("costType");
        User users =(User) request.getSession().getAttribute("users");
        PageInfo<UserBalanceDetail> userBalanceDetailPageInfo = new PageInfo<>();
        if(users==null){
            return userBalanceDetailPageInfo;
        }

        UserBalanceDetail detail = new UserBalanceDetail();
        //设置当前登录用户主键ID
        detail.setUserId(users.getId());

        if (flowNum!=null && flowNum.length()!=0){
            detail.setSequence(flowNum);
        }
        if (startTime!=null && startTime.length()!=0){
            detail.setUpdateTime(DateUtil.String2Date(startTime));
        }
        if (endTime!=null && endTime.length()!=0){
            detail.setUpdateTimeEnd(DateUtil.String2Date(endTime));
        }
        if (inOrOut!=null && inOrOut.length()!=0 && !"0".equals(inOrOut)){
            detail.setBillType(Integer.parseInt(inOrOut));
        }
        if (costType!=null && costType.length()!=0 && !"0".equals(costType)){
            detail.setCategory(Integer.parseInt(costType));
        }

        userBalanceDetailPageInfo = billService.getBillDetails(detail,pageNum,pageSize);
        updataTime(userBalanceDetailPageInfo.getList());
        return userBalanceDetailPageInfo;
    }

    public void updataTime(List<UserBalanceDetail> details){
        if(details!=null && !details.isEmpty()){
            for (int i = 0; i <details.size() ; i++) {
                UserBalanceDetail detail = details.get(i);
                detail.setDateStr(DateUtil.Date2String(detail.getUpdateTime(),DateUtil.SQL_TIME_FMT));
            }
        }
    }
}
