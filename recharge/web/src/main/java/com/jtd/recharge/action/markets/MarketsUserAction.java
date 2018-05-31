
package com.jtd.recharge.action.markets;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.BalanceRecord;
import com.jtd.recharge.dao.bean.UserBalances;
import com.jtd.recharge.dao.bean.UserGroup;
import com.jtd.recharge.dao.bean.util.SHAUtil;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.dao.po.UserBalance;
import com.jtd.recharge.service.admin.OperateLogService;
import com.jtd.recharge.service.user.BalanceService;
import com.jtd.recharge.service.user.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by  on 2017/09/13.
 * 商户管理 merchant
 * lhm
 */
@Controller
@RequestMapping("/user")
public class MarketsUserAction {

    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    public UserService userservice;

    /**
     * 销售商户列表
     * @param request
     * @param pageNumber
     * @param pageSize
     * @param userOne
     * @return
     */
    @RequestMapping("/marketsUserList")
    public String marketsUserList(HttpServletRequest request,Integer pageNumber,Integer pageSize,User userOne){

        AdminUser adminUser = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        String marketName = adminUser.getName();
        userOne.setSells(marketName);
        PageInfo<BalanceRecord> list = null;
        String type = request.getParameter("type");
        int i = Integer.parseInt(type);
        if(i==0){
            list = userservice.getMarketsUserList(1,10,userOne);
        }else {
            list = userservice.getMarketsUserList(pageNumber,pageSize,userOne);
        }
        //对注册时间的格式进行处理（yyyy-MM-dd HH:mm:SS）
        for(BalanceRecord user :list.getList()){
            Date registerTime = user.getRegisterTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
            if(registerTime!=null){
                String s = format.format(registerTime);
                user.setRegisterTimeFormat(s);
            }
        }
        request.setAttribute("list", list);
        request.setAttribute("user",userOne);
        return "/market/marketsUser";
    }

}
