package com.jtd.recharge.action.charge.channel;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.ChargeSupply;
import com.jtd.recharge.service.admin.OperateLogService;
import com.jtd.recharge.service.charge.channel.SupplierListService;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by lyp on 2016/11/14.
 * 供应商列表
 */
@Controller
@RequestMapping("/channels")
public class SupplierListAction {
   @Resource
    private SupplierListService supplierListService;
    @Resource
    public OperateLogService operateLogService;

    /**供应商跳转
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getsupplierList")
    public String getSupplierList(HttpServletRequest request, HttpServletResponse response){

        return "/charge/channel/supplierList";
    }



    //添加供应商 lyp
    @RequestMapping("/addsupplier")
    @ResponseBody
    public Object  addSupplier(HttpServletRequest request, HttpServletResponse response, ChargeSupply chargeSupply){
        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        String adminName = user.getName();
        operateLogService.logInfo(adminName,"供应商列表",adminName+"在供应商列表成功添加了供应商名称为："+chargeSupply.getName()+" 的信息！");

        return  supplierListService.addSupplier(chargeSupply);
    }

    /**
     * 分页查询方法 lyp
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/quertsuppleir")
    @ResponseBody
    public Object quertSuppleir(Model model,HttpServletRequest request, HttpServletResponse response){
        String name=request.getParameter("name");
        String pageNum=request.getParameter("pageNumber");
        String type=request.getParameter("leixing");
        int pageNumint;
        if(pageNum==null||"".equals(pageNum)){
            pageNumint=1;
        }else{
            pageNumint=Integer.parseInt(pageNum);
        }
        PageInfo<ChargeSupply> list=supplierListService.getSupplierPage(name,type,pageNumint,10);
        return list;
    }

    /**
     * 供应商修改 lyp
     * @param request
     * @return
     */
    @RequestMapping("/editorsupplier")
    @ResponseBody
    public Object editorSupplier(HttpServletRequest request){
        String userId=request.getParameter("userId");
        String sid=request.getParameter("id");
        String name=request.getParameter("name");
        String allName=request.getParameter("allName");
        String people=request.getParameter("people");
        String mobile=request.getParameter("mobile");
        String semlla=request.getParameter("semlla");
        String suaddress=request.getParameter("address");
//        String stype=request.getParameter("stype");
        operateLogService.logInfo(userId,"供应商列表",userId+"在供应商列表修改了供应商："+name+"的信息");
        int id=Integer.parseInt(sid);
//        int stypeid=Integer.parseInt(stype);
        ChargeSupply chargeSupply=new ChargeSupply();
        chargeSupply.setId(id);
        chargeSupply.setName(name);
        chargeSupply.setAllName(allName);
        chargeSupply.setContactName(people);
        chargeSupply.setContactMobile(mobile);
        chargeSupply.setSellman(semlla);
        chargeSupply.setChannelAddress(suaddress);
//        chargeSupply.setBusinessType(stypeid);
        int i=supplierListService.editorSupplier(chargeSupply);
        return  i;
    }

}
