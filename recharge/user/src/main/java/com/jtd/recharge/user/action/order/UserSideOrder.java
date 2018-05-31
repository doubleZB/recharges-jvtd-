package com.jtd.recharge.user.action.order;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.dao.po.ChargeOrder;
import com.jtd.recharge.dao.po.ChargePosition;
import com.jtd.recharge.dao.po.Dict;
import com.jtd.recharge.dao.po.User;
import com.jtd.recharge.service.charge.channel.ChannelListService;
import com.jtd.recharge.service.charge.order.OrderService;
import com.jtd.recharge.service.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lyp on 2016/12/1.
 */
@Controller
@RequestMapping("/userclient")
public class UserSideOrder {
    @Resource
    private OrderService orderService;
    @Resource
    private ChannelListService channelListService;
    @Resource
    private UserService userService;


    /**
     * 商户端订单记录查询
     * @param request
     * @return
     */
    @RequestMapping("/usersideorder")
    @ResponseBody
    public Object getUserOrderList(HttpServletRequest request){
        //设开始时间
        String stime = request.getParameter("startime");
        //设结束时间
        String etime = request.getParameter("endtime");
        String st = "00:00:00";
        String et = "23:59:59";

        String  mobile=request.getParameter("mobile");
        int pageNum=Integer.parseInt(request.getParameter("pageNum"));
        int bussinessType=Integer.parseInt(request.getParameter("bussinessType"));
        int  userid=Integer.parseInt(request.getParameter("userid"));
        int  source=Integer.parseInt(request.getParameter("source"));//来源
        int  provinceid=Integer.parseInt(request.getParameter("provinceid"));//省份
        int  operator=Integer.parseInt(request.getParameter("operator"));//运营商
        int  status=Integer.parseInt(request.getParameter("status"));//状态
        String  merchantOrderNumber=request.getParameter("merchantOrderNumber");//状态

        String remark=null;//备注
        if( !"".equals( request.getParameter("remark"))) {
             remark = "%" + request.getParameter("remark") + "%";
        }

        ChargeOrder chargeOrder=new ChargeOrder();
        chargeOrder.setBusinessType(bussinessType);
        chargeOrder.setTable("charge_order");
        chargeOrder.setRemark(remark);
        if(!"".equals(request.getParameter("merchantOrderNumber"))){
            chargeOrder.setCustomid(merchantOrderNumber);
        }

        if(!"".equals(request.getParameter("childAccount"))){//获取子账号ID
            chargeOrder.setUserId(Integer.parseInt(request.getParameter("childAccount")));
        }else {
            chargeOrder.setUserId(userid);
        }

        if(!"".equals(mobile)){
            chargeOrder.setRechargeMobile(mobile);
        }

        if(source>0){
            chargeOrder.setSource(source);
        }

        if(provinceid>0){
            chargeOrder.setProvinceId(provinceid);
        }

        if(operator>0){
            chargeOrder.setOperator(operator);
        }

        if(status>0){
            if(status==2){
                List<Integer> statuslist=new ArrayList<Integer>();
                statuslist.add(ChargeOrder.OrderStatus.NO_STORE);
                statuslist.add(ChargeOrder.OrderStatus.NO_CHANNEL);
                chargeOrder.setStatusList(statuslist);
            }else {
                chargeOrder.setStatus(status);
            }
        }

        SimpleDateFormat  fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datestart=null;
        Date dateend=null;
        //根据时间日期 分表
        if(stime!=null&&!"".equals(stime)) {
            String startime = stime + " " + st ;
            try {
                datestart= fmt.parse(startime);
            }catch (ParseException e){
                e.printStackTrace();
            }
            chargeOrder.setOrderTime(datestart);
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
        if(etime!=null&&!"".equals(etime)){
            String endtime = etime + " " + et ;
            try {
                dateend =  fmt.parse(endtime);
            }catch (ParseException e){
                e.printStackTrace();
            }
            chargeOrder.setOrderTimeend(dateend);
        }
        PageInfo<ChargeOrder> list=orderService.getUserOrder(pageNum,10,chargeOrder);
        updataTime(list.getList());
        return list;
    }

    public void updataTime(List<ChargeOrder> details){
        if(details!=null && !details.isEmpty()){
            for (int i = 0; i <details.size() ; i++) {
                ChargeOrder chargeOrder = details.get(i);
                chargeOrder.setDateStr(DateUtil.Date2String(chargeOrder.getOrderTime(),DateUtil.SQL_TIME_FMT));
            }
        }
    }


    /**
     * 获取省份lyp
     * @return
     */
    @RequestMapping("/getprovince")
    @ResponseBody
    public Object getProvince(){
        Dict dict=new Dict();
        dict.setModule("province");
        List<Dict> list=channelListService.getProvince(dict);
        return list;
    }

    /**
     * 计算消费金额的
     * @param request
     * @return
     */
    @RequestMapping("/getamountcount")
    @ResponseBody
    public Object getamountcount(HttpServletRequest request){
        //设开始时间
        String stime = request.getParameter("startime");
        //设结束时间
        String etime = request.getParameter("endtime");
        String st = "00:00:00";
        String et = "23:59:59";
        String  mobile=request.getParameter("mobile");

        int bussinessType=Integer.parseInt(request.getParameter("bussinessType"));
        int  userid=Integer.parseInt(request.getParameter("userid"));
        int  source=Integer.parseInt(request.getParameter("source"));//来源
        int  provinceid=Integer.parseInt(request.getParameter("provinceid"));//省份
        int  operator=Integer.parseInt(request.getParameter("operator"));//运营商
        int  status=Integer.parseInt(request.getParameter("status"));//状态
        String remark=null;//备注
        if( !"".equals( request.getParameter("remark"))) {
            remark = "%" + request.getParameter("remark") + "%";
        }
        ChargeOrder chargeOrder=new ChargeOrder();
        chargeOrder.setBusinessType(bussinessType);
        chargeOrder.setUserId(userid);
//        chargeOrder.setTable("charge_order");
        chargeOrder.setRemark(remark);


        if(!"".equals(request.getParameter("childAccount"))){//获取子账号ID
            chargeOrder.setUserId(Integer.parseInt(request.getParameter("childAccount")));
        }else {
            chargeOrder.setUserId(userid);
        }

        if(!"".equals(mobile)){
            chargeOrder.setRechargeMobile(mobile);
        }

        if(source>0){
            chargeOrder.setSource(source);
        }

        if(provinceid>0){
            chargeOrder.setProvinceId(provinceid);
        }

        if(operator>0){
            chargeOrder.setOperator(operator);
        }

        if(status>0){
            chargeOrder.setStatus(status);
        }

        SimpleDateFormat  fmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date datestart=null;
        Date dateend=null;
        //根据时间日期 分表
        if(stime!=null&&!"".equals(stime)) {
            String startime = stime + " " + st ;
            try {
                datestart= fmt.parse(startime);
            }catch (ParseException e){
                e.printStackTrace();
            }
            chargeOrder.setOrderTime(datestart);
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
        if(etime!=null&&!"".equals(etime)){
            String endtime = etime + " " + et ;
            try {
                dateend =  fmt.parse(endtime);
            }catch (ParseException e){
                e.printStackTrace();
            }
            chargeOrder.setOrderTimeend(dateend);

        }
        String str = orderService.getamountcount(chargeOrder);
        if(str==null){
            str="0";

        }
        return str;
    }

    //获取产品编码
    @RequestMapping("/getproductcode")
    @ResponseBody
    public Object getProduct(){
        ChargePosition chargePosition=new ChargePosition();
        List<ChargePosition> product=channelListService.getProduct(chargePosition);
        return product;
    }

    /**
     * 查询子账号
     * @param request
     * @return
     */
    @RequestMapping("/getChildAccount")
    @ResponseBody
    public Object getChildAccount(HttpServletRequest request){
        User users =(User) request.getSession().getAttribute("users");
        User user=new User();
        user.setpId(users.getId());
        return userService.selectUserListByPId(user);
    }

    /**
     * 查询所有用户信息
     * @return
     */
    @RequestMapping("/selectAllUser")
    @ResponseBody
    public Object selectAllUser(){
        return userService.selectUser();
    }
}
