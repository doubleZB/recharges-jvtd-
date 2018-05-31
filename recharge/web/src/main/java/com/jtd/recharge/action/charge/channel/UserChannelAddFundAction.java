package com.jtd.recharge.action.charge.channel;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.base.util.OSSUploadUtils;
import com.jtd.recharge.base.util.PropertiesUtils;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.ChargeSupply;
import com.jtd.recharge.dao.po.UserChannelAddfund;
import com.jtd.recharge.service.admin.AdminService;
import com.jtd.recharge.service.admin.OperateLogService;
import com.jtd.recharge.service.charge.channel.SupplierListService;
import com.jtd.recharge.service.charge.channel.UserChannelAddfundService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lyp on 2017/4/11. 渠道加款审核查询
 */
@Controller
@RequestMapping("/channels")
public class UserChannelAddFundAction {
    @Resource
    public OperateLogService operateLogService;

    /**
     *
     * @lyp
     * 渠道管理页面用于左侧跳转渠道加款记录
     */
    @RequestMapping("/channelPlusRecord")
    public String channelPlusRecord(){

        return "/charge/channel/channelPlusRecord";
    }

    /**
     * 渠道管理页面用于左侧跳转渠道加款确认
     * @return
     */
    @RequestMapping("/channelPlusConfirmation")
    public String channelPlusConfirmation(){

        return "/charge/channel/channelPlusConfirmation";
    }

    /**
     * 渠道管理页面用于左侧跳转渠道加款申请
     * @return
     */
    @RequestMapping("/channelAdditional")
    public String channelAdditional(){

        return "/charge/channel/channelAddItional";
    }
    private Log log = LogFactory.getLog(this.getClass());
    private static final String PAPER_FILE = (String) PropertiesUtils.loadProperties("config.properties").get("FileName");
    @Resource
    private UserChannelAddfundService userChannelAddFundService;
    @Resource
    private SupplierListService supplierListService;
    @Resource
    private AdminService adminService;

    /**
     * 审核查询
     * @param request
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping("/selectUserChannelAddFund")
    @ResponseBody
    public Object selectUserChannelAddFund(HttpServletRequest request,Integer pageNumber,Integer pageSize){
        UserChannelAddfund userChannelAddfund=new UserChannelAddfund();

        String id = request.getParameter("id");
        if(StringUtil.isNotEmpty(id)){
            userChannelAddfund.setId(Integer.parseInt(id));
        }

        String supplyName = request.getParameter("supplyName");
        List<Integer> supplyIdList=new ArrayList<>();
        if(StringUtil.isNotEmpty(supplyName)){
            List<ChargeSupply> list=(List)selectSupplier("%"+supplyName+"%");
            if(list.size()>0){

                for(ChargeSupply supply:list){
                    supplyIdList.add(supply.getId());
                }

            }else {
                supplyIdList.add(UserChannelAddfund.ADDFUNDStatus.INITALIZE_VALUE);
            }
            userChannelAddfund.setSupplyIdList(supplyIdList);
        }
        String applyStartTime = request.getParameter("applyStartTime");
        String applyEndTime=request.getParameter("applyEndTime");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(StringUtil.isNotEmpty(applyStartTime)){
            try {
                Date dateStart=format.parse(applyStartTime);
                Date dateEnd=format.parse(applyEndTime);
                userChannelAddfund.setApplyTime(dateStart);
                userChannelAddfund.setApplyEndTime(dateEnd);
            }catch (ParseException e){
                e.printStackTrace();
            }
        }

        String auditTime = request.getParameter("auditTime");
        String auditEndTime = request.getParameter("auditEndTime");
        if(StringUtil.isNotEmpty(auditTime)){

            try {
                Date dateStart=format.parse(auditTime);
                Date dateEnd=format.parse(auditEndTime);
                userChannelAddfund.setAuditTime(dateStart);
                userChannelAddfund.setAuditEndTime(dateEnd);
            }catch (ParseException e){
                e.printStackTrace();
            }
        }

        String state = request.getParameter("state");
        if(StringUtil.isNotEmpty(state)){
            userChannelAddfund.setState(Integer.parseInt(state));
        }

        PageInfo<UserChannelAddfund> list = userChannelAddFundService.selecctUserChannelAddfund(userChannelAddfund,pageNumber,pageSize);
        updateTime(list.getList());
        return list;
    }

    /**
     * 时间格式化
     * @param details
     */
    public void updateTime(List<UserChannelAddfund> details){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(details!=null && !details.isEmpty()){
            for (int i = 0; i <details.size() ; i++) {
                UserChannelAddfund userChannelAddfund = details.get(i);
                userChannelAddfund.setApplyTimestr(DateUtil.Date2String(userChannelAddfund.getApplyTime(), DateUtil.SQL_TIME_FMT));
                userChannelAddfund.setAuditTimestr(DateUtil.Date2String(userChannelAddfund.getAuditTime(), DateUtil.SQL_TIME_FMT));
            }
        }
    }

    /**
     * 获取供应商
     * @param supplyName
     * @return
     */
    @RequestMapping("/selectSupplier")
    @ResponseBody
    public Object selectSupplier(String supplyName ){
        ChargeSupply chargeSupply=new ChargeSupply();
        if(StringUtil.isNotEmpty(supplyName)){
            chargeSupply.setName(supplyName);
        }

        return supplierListService.getSupplier(chargeSupply);
    }

    /**
     * 获取操作人员
     * @param request
     * @return
     */
    @RequestMapping("/selectAdminUser")
    @ResponseBody
    public Object selectAdminUser(HttpServletRequest request){
        // AdminUser adminUser=(AdminUser)request.getSession().getAttribute("adminLoginUser");
        AdminUser adminUser=new AdminUser();
        return adminService.selectAdminUser(adminUser);
    }

    /**
     * 渠道加款取消操作
     * @param request
     * @return
     */
    @RequestMapping("/deleteUserChannelAddFund")
    @ResponseBody
    public Object deleteUserChannelAddFund(HttpServletRequest request){
        UserChannelAddfund userChannelAddfund=new UserChannelAddfund();
        int id = Integer.parseInt(request.getParameter("id"));
        userChannelAddfund.setId(id);

        String state = request.getParameter("state");
        if(StringUtil.isNotEmpty(state)){
            userChannelAddfund.setId(Integer.parseInt(state));
        }

        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        String adminName = user.getName();
        operateLogService.logInfo(adminName,"渠道加款申请(运营)",adminName+"在渠道加款申请(运营)取消了加款ID为："+id+" 的信息！");

        return userChannelAddFundService.deleteUserChannelAddfund(userChannelAddfund);
    }

    /**
     * 渠道加款确认
     * @param request
     * @return
     */
    @RequestMapping("/updateUserChannelAddfund")
    @ResponseBody
    public Object updateUserChannelAddfund(HttpServletRequest request){
        UserChannelAddfund userChannelAddfund=new UserChannelAddfund();
        int id = Integer.parseInt(request.getParameter("id"));
        userChannelAddfund.setId(id);
        userChannelAddfund.setState(UserChannelAddfund.ADDFUNDStatus.SUCCESS_ADDFUND);
//        String state = request.getParameter("state");
//        if(StringUtil.isNotEmpty(state)){}

        String certificate = request.getParameter("certificate");
        if(StringUtil.isNotEmpty(certificate)){
            userChannelAddfund.setCertificate(certificate);
        }

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String auditTime = request.getParameter("auditTime");
        try {
            Date dateStart=format.parse(auditTime);
            userChannelAddfund.setAuditTime(dateStart);
        }catch (ParseException e){
            e.printStackTrace();
        }

        AdminUser adminUser = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        userChannelAddfund.setAuditUserid(adminUser.getId());
        String adminName = adminUser.getName();
        operateLogService.logInfo(adminName,"渠道加款审核(财务)",adminName+"在渠道加款审核(财务)为ID为："+id+" 的数据申请加款！");

        return userChannelAddFundService.updateUserChannelAddfund(userChannelAddfund);
    }

    /**
     * 添加渠道加款信息
     * @param request
     * @return
     */
    @RequestMapping("/insertUserChannelAddfund")
    @ResponseBody
    public Object insertUserChannelAddfund(HttpServletRequest request){
        UserChannelAddfund userChannelAddfund=new UserChannelAddfund();

        String supplyName = request.getParameter("supplyName");
        if(StringUtil.isNotEmpty(supplyName)){
            List<ChargeSupply> list=(List)selectSupplier(supplyName);
            int supplyId=0;
            for(ChargeSupply supply:list){
                supplyId=supply.getId();
            }
            userChannelAddfund.setSupplyId(supplyId);
        }

        String money = request.getParameter("money");
        if(StringUtil.isNotEmpty(money)){
            BigDecimal bd=new BigDecimal(money);
            userChannelAddfund.setAmount(bd);
        }

        String remark = request.getParameter("remark");
        if(StringUtil.isNotEmpty(remark)){
            userChannelAddfund.setRemark(remark);
        }

        userChannelAddfund.setState(UserChannelAddfund.ADDFUNDStatus.CREATE_ADDFUND);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = sdf.parse(sdf.format(new Date()));
            userChannelAddfund.setApplyTime(date);
        }catch (Exception e){
            log.error("申请加款异常:"+e);
        }

        AdminUser adminUser = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        userChannelAddfund.setApplyUserid(adminUser.getId());

        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        String adminName = user.getName();
        operateLogService.logInfo(adminName,"渠道加款申请(运营)",adminName+"在渠道加款申请(运营)为供应商："+supplyName+" 的加款："+money+"元！");

        return userChannelAddFundService.insertUserChannelAddfund(userChannelAddfund);
    }

    /**
     * 获取图片url名字
     * @param request
     * @return
     */
    @RequestMapping("/selectImgUrlNameI")
    @ResponseBody
    public Object selectImgUrlNameI(HttpServletRequest request){
        UserChannelAddfund userChannelAddfund=new UserChannelAddfund();
        String id = request.getParameter("id");
        if(StringUtil.isNotEmpty(id)){
            userChannelAddfund.setId(Integer.parseInt(id));
        }
        return userChannelAddFundService.selectImgUrlNameI(userChannelAddfund);
    }

    /**
     * 图片附件上传
     * @param file
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "customerUpload", method = RequestMethod.POST)
    public String customerUpload(@RequestParam(value = "file", required = true) MultipartFile file) {
        String url = "";
        try {
            url = OSSUploadUtils.uploadFile(PAPER_FILE, file);
        } catch (IOException e) {
            log.error(e.getMessage() + "图片文件上传失败");
        }
        return "{\"status\":1,\"reurl\":\"" + url + "\"}";
    }

}
