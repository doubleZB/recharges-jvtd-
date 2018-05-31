package com.jtd.recharge.action.charge.store;


import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.po.*;
import com.jtd.recharge.service.charge.channel.SupplierListService;
import com.jtd.recharge.service.charge.store.StoreService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.json.JSONString;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Wangxiangping on 2016-11-14.
 * 货架以及货架组管理
 *
 */


@Controller
@RequestMapping("/store")
public class StoreAction {
    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    public StoreService service;
    @Resource
    private SupplierListService supplierListService;
    /**
     * 跳转到单个货架管理页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/storeManage")
    public String showHuojiaList(HttpServletRequest request, HttpServletResponse response){
        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        if(user==null){
            return "/admin/login";
        }
        List<ChargeProductGroup> groups = service.selectAllProductGroup();
        List<Dict> dicts = service.getAllPrivence();
        List<ChargePosition> flowPositionYD = service.getPositionByType(1,1);//移动流量档位
        List<ChargePosition> flowPositionLT = service.getPositionByType(1,2);//联通流量档位
        List<ChargePosition> flowPositionDX = service.getPositionByType(1,3);//电信流量档位
        List<ChargePosition> PhoneCostPositionYD = service.getPositionByType(2,1);//移动话费
        List<ChargePosition> PhoneCostPositionLT = service.getPositionByType(2,2);//联通话费
        List<ChargePosition> PhoneCostPositionDX = service.getPositionByType(2,3);//电信话费
        List<ChargePosition> PositionYK = service.getPositionByType(3,4);//优酷会员
        List<ChargePosition> PositionAQY = service.getPositionByType(3,5);//爱奇艺会员
        List<ChargePosition> PositionTX = service.getPositionByType(3,6);//腾讯会员
        List<ChargePosition> PositionSH = service.getPositionByType(3,7);//搜狐会员
        List<ChargePosition> PositionLS = service.getPositionByType(3,8);//乐视会员
        List<ChargeSupply> supplies = service.getChargeSupply(0);
        List<ChargeSupply> suppliesLL = service.getChargeSupply(1);
        List<ChargeSupply> suppliesHF = service.getChargeSupply(2);
        List<ChargeSupply> suppliesHY = service.getChargeSupply(3);
        String jsongroups = JSONObject.valueToString(groups);
        String jsondicts = JSONObject.valueToString(dicts);
        String jsonFlowPositionYD = JSONObject.valueToString(flowPositionYD);
        String jsonFlowPositionLT = JSONObject.valueToString(flowPositionLT);
        String jsonFlowPositionDX = JSONObject.valueToString(flowPositionDX);
        String jsonPhoneCostPositionYD = JSONObject.valueToString(PhoneCostPositionYD);
        String jsonPhoneCostPositionLT = JSONObject.valueToString(PhoneCostPositionLT);
        String jsonPhoneCostPositionDX = JSONObject.valueToString(PhoneCostPositionDX);

        String jsonPositionYK = JSONObject.valueToString(PositionYK);
        String jsonPositionAQY = JSONObject.valueToString(PositionAQY);
        String jsonPositionTX = JSONObject.valueToString(PositionTX);
        String jsonPositionSH = JSONObject.valueToString(PositionSH);
        String jsonPositionLS = JSONObject.valueToString(PositionLS);

        String jsonSupplies = JSONObject.valueToString(supplies);
        String jsonSuppliesLL = JSONObject.valueToString(suppliesLL);
        String jsonSuppliesHF = JSONObject.valueToString(suppliesHF);
        String jsonSuppliesHY = JSONObject.valueToString(suppliesHY);

        request.setAttribute("groups",jsongroups);//所有货架组
        request.setAttribute("dicts",jsondicts);//所有省份
        request.setAttribute("flowPositionYD",jsonFlowPositionYD);//所有移动流量档位
        request.setAttribute("flowPositionLT",jsonFlowPositionLT);//所有联通流量档位
        request.setAttribute("flowPositionDX",jsonFlowPositionDX);//所有电信流量档位
        request.setAttribute("PhonecostPositionYD",jsonPhoneCostPositionYD);//所有话费档位
        request.setAttribute("PhonecostPositionLT",jsonPhoneCostPositionLT);//所有话费档位
        request.setAttribute("PhonecostPositionDX",jsonPhoneCostPositionDX);//所有话费档位

        request.setAttribute("PositionYK",jsonPositionYK);//所有视频会员档位
        request.setAttribute("PositionAQY",jsonPositionAQY);//所有视频会员档位
        request.setAttribute("PositionTX",jsonPositionTX);//所有视频会员档位
        request.setAttribute("PositionSH",jsonPositionSH);//所有视频会员档位
        request.setAttribute("PositionLS",jsonPositionLS);//所有视频会员档位

        request.setAttribute("supplies",jsonSupplies);//所有流量供应商
        request.setAttribute("suppliesLL",jsonSuppliesLL);//所有流量供应商
        request.setAttribute("suppliesHF",jsonSuppliesHF);//所有话费供应商
        request.setAttribute("suppliesHY",jsonSuppliesHY);//所有视频会员供应商
        return "charge/store/storeManage";
    }

    /**
     * 跳转到货架组管理页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/storeGrouplist")
    public String storeGrouplist(HttpServletRequest request, HttpServletResponse response){
        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        if(user==null){
            return "/admin/login";
        }
        return "charge/store/storeGrouplist";
    }

    /**
     * 添加货架组
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/addStoreGrouplist")
    public Object addStoreGrouplist(HttpServletRequest request, HttpServletResponse response){
        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        if(user==null){
            return "/admin/login";
        }
        String name = request.getParameter("groupname");
        ReturnMsg msg = new ReturnMsg();
        msg.setSuccess(false);
        if(service.isStoreGroupNameExist(name)){//存在了，不能再添加
            msg.setMessage("货架组名称已存在，请换一个！");
        }else {
            if(service.saveStoreGroupNameExist(name,user)){
                msg.setSuccess(true);
                msg.setMessage("添加成功！");
            }else {
                msg.setMessage("添加失敗！");
            }
        }
        return msg;
    }

    /**
     * 分页查询货架组
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/findStoreGrouplist")
    public Object findStoreGrouplist(HttpServletRequest request, HttpServletResponse response,
                                     Integer pageNumber,
                                     Integer pageSize){
        PageInfo<ChargeProductGroup> list = service.selectProductGroup(pageNumber,pageSize);
        return list;
    }


    /**
     * 分页条件查询货架组 lyp
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectProductGroupCondition")
    public Object selectProductGroupCondition(HttpServletRequest request, HttpServletResponse response,
                                     Integer pageNumber,
                                     Integer pageSize){
        ChargeProductGroup chargeProductGroup=new ChargeProductGroup();
        String name=request.getParameter("name");
        if(StringUtil.isNotEmpty(name)){
            chargeProductGroup.setName("%"+name+"%");
        }
        String operators=request.getParameter("operator");
        List<Integer> operatorList=new ArrayList<>();
        if(StringUtil.isNotEmpty(operators)){
            String[] operatorArray=operators.split(",");
            for(String str:operatorArray){
                operatorList.add(Integer.parseInt(str));
            }
            chargeProductGroup.setOperators(operatorList);
        }
        String discountPrice=request.getParameter("discountPrice");
        List<BigDecimal> discountPriceList=new ArrayList<>();
        if(StringUtil.isNotEmpty(discountPrice)){
            String[] discountPriceArray=discountPrice.split(",");
            for(String str:discountPriceArray){
                BigDecimal bd=new BigDecimal(str);
                discountPriceList.add(bd);
            }
            chargeProductGroup.setDiscountPrices(discountPriceList);
        }
        String supplyName=request.getParameter("supplyName");
        if(StringUtil.isNotEmpty(supplyName)){
            List<Integer> suppltIdList=new ArrayList<>();
           List<ChargeSupply> list=getSupplierId("%"+supplyName+"%");
            if(list.size()==0){
                int i=0; //集合数组长度为0的时候设置一个变量
                suppltIdList.add(i);
            }else{
            for(ChargeSupply chargeSupply:list){
                suppltIdList.add(chargeSupply.getId());
            }
            }
            chargeProductGroup.setSupplyid(suppltIdList);
        }
        PageInfo<ChargeProductGroup> list = service.selectProductGroupCondition(pageNumber,pageSize,chargeProductGroup);
        return list;
    }

    /**
     * 修改货架组
     * @param request
     * @param response
     * @param groupId
     * @param groupName
     * @return
     */
    @ResponseBody
    @RequestMapping("/repareGroup")
    public Object repareGroup(HttpServletRequest request, HttpServletResponse response,
                              Integer groupId,
                              String groupName){
        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");

        ReturnMsg msg = new ReturnMsg();
        msg.setSuccess(false);
        if(service.isStoreGroupNameExist(groupName)){//存在了，不能再添加
            msg.setMessage("货架组名称已存在，请换一个！");
        }else {
            msg = service.repareGroup(groupId,groupName,user);
        }

        return msg;
    }


    /**
     * 根据条件查询产品
     * @param request
     * @param response
     * @param subData
     * @return
     */
    @ResponseBody
    @RequestMapping("/findProductions")
    public Object findProductions(HttpServletRequest request, HttpServletResponse response,String subData){
        ReturnMsg msg = new ReturnMsg();
        subData = subData.replace(" ","");
        List<ChargeProduct> products = service.getChargeProduct(subData);
        if (products!=null&& !products.isEmpty()){
            msg.setSuccess(true);
            msg.setObject(service.getProductF(products));
        }else {
            msg.setSuccess(false);
        }
        return msg;
    }

    /**
     * 添加货架
     * @param request
     * @param response
     * @param addStoreData
     * @return
     */
    @ResponseBody
    @RequestMapping("/addStore")
    public Object addStore(HttpServletRequest request, HttpServletResponse response,String addStoreData) {
        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        addStoreData = addStoreData.replace(" ","");
        ReturnMsg msg = null;
        try {
            msg = service.addStore(addStoreData,user);
        }catch (Exception e){
            log.error("添加货架出现异常",e);
            msg = new ReturnMsg();
            msg.setSuccess(false);
            if(e.getMessage().equals("1")){
                msg.setMessage("含有重复货架，请检查后再提交！");
            }else {
                msg.setMessage("操作失败");
            }
        }
        return msg;
    }

    /**
     * 分页查询货架
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/findStoreList")
    public Object findStoreList(HttpServletRequest request, HttpServletResponse response,
                                     String subData,
                                     Integer pageNumber,
                                     Integer pageSize,
                                     Integer zk_type,
                                     BigDecimal zk_num,
                                     Integer supplierID
                                ){
        PageInfo<ChargeProductStoreF> list = service.selectProductStore(subData,pageNumber,pageSize,zk_type,zk_num,supplierID);
        service.addInfoToChargeProductStoreF(list.getList());
        return list;
    }

    /**
     * 批量修改货架组上下架状态
     * @param request
     * @param response
     * @param subData
     * @param status
     * @return
     */
    @ResponseBody
    @RequestMapping("/editStatus")
    public Object editStatus(HttpServletRequest request, HttpServletResponse response,
                                                         String subData,
                                                         int status){
        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        ReturnMsg msg = new ReturnMsg();
        msg.setMessage("修改失败！");
        msg.setSuccess(false);
        subData = subData.replace(" ","");
        subData = subData.replace("[","");
        subData = subData.replace("]","");
        subData = subData.replace("\"","");
        subData = subData.replace("\n","");
        try {
            service.editStatus(subData,status,user);
            msg.setMessage("修改成功！");
            msg.setSuccess(true);
        }catch (Exception e){
            log.error("修改货架上下架状态出现异常",e);
            e.printStackTrace();
        }
        return msg;
    }

    /**
     * 批量修改货架组折扣
     * @param request
     * @param response
     * @param subData
     * @param discount
     * @return
     */
    @ResponseBody
    @RequestMapping("/editZk")
    public Object editZk(HttpServletRequest request, HttpServletResponse response,
                                                         String subData,
                                                         BigDecimal discount){
        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        ReturnMsg msg = new ReturnMsg();
        subData = subData.replace(" ","");
        subData = subData.replace("[","");
        subData = subData.replace("]","");
        subData = subData.replace("\"","");
        subData = subData.replace("\n","");
        try {
            service.editZk(subData,discount,user);
            msg.setMessage("修改成功！");
            msg.setSuccess(true);
        }catch (Exception e){
            log.error("修改货架折扣出现异常",e);
            msg.setMessage("修改失败！");
            msg.setSuccess(false);
        }
        return msg;
    }

    /**
     * 一键修改折扣  王相平 2017年7月6日13:27:56
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/editZk_one_key")
    public Object editZk_one_key(HttpServletRequest request, HttpServletResponse response,
                                 String subData,
                                 String status,
                                 BigDecimal zk_to_update,
                                 Integer zk_type,
                                 BigDecimal zk_num,
                                 Integer supplierID){
        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        ReturnMsg msg = new ReturnMsg();
        msg.setMessage("一键修改货架失败！");
        msg.setSuccess(false);
        try {
            service.edit_one_key(subData,zk_type,zk_num,supplierID,status,zk_to_update,user);
            msg.setMessage("一键修改货架折扣成功！");
            if(status!=null && !"".equals(status)){
                msg.setMessage("一键修改货架上下架状态成功！");
                log.info(user.getName()+"一键修改货架上下架状态成功！");
            }else {
                log.info(user.getName()+"一键修改货架折扣成功！");
            }
            msg.setSuccess(true);
        }catch (Exception e){
            log.info(user.getName()+"一键修改货架信息失败！原因："+e.getLocalizedMessage());
            e.printStackTrace();
        }
        return msg;
    }





    /**
     * 批量修改货架发送时候选择渠道方式
     * @param subData
     * @return
     */
    @ResponseBody
    @RequestMapping("/editSendType")
    public Object editSendType(String subData,String storeIds,HttpServletRequest request){
        AdminUser user = (AdminUser)request.getSession().getAttribute("adminLoginUser");
        ReturnMsg msg = null;
        subData = subData.replace(" ","");
        storeIds = storeIds.replace(" ","");
        storeIds = storeIds.replace("[","");
        storeIds = storeIds.replace("]","");
        storeIds = storeIds.replace("\"","");
        storeIds = storeIds.replace("\n","");
        try {
            msg = service.editSendType(subData,storeIds,user);
        }catch (Exception e){
            msg = new ReturnMsg();
            if(e.getMessage().equals("1")){
                msg.setMessage("所选货架与渠道不匹配，请仔细检查！");
                msg.setSuccess(false);
            }else{
                log.error("修改货架渠道出现异常",e);
                msg.setMessage("修改失败！");
                msg.setSuccess(false);
            }
        }

        return msg;
    }

    /**
     * 根据货架id查询其指定供应商
     * @param storeId
     * @return
     */
    @ResponseBody
    @RequestMapping("/watchSupply")
    public Object watchSupply(int storeId){
        List<ChargeSupply> supplies = service.watchSupply(storeId);
        return supplies;
    }


    /**
     * 根据货架类型（流量，话费）、运营商（移动联通电信）查询档位
     * @param storeType
     * @return
     */
    @ResponseBody
    @RequestMapping("/getPositionCard")
    public Object getPositionCard(int storeType,int operator){
        List<ChargePosition> chargePositions = service.getPositionCard(storeType,operator);
        List<Map<String,String>> cards =service.getCard(chargePositions);
        return cards;
    }

    @ResponseBody
    @RequestMapping("getSuppliersByName")
    public Object getSuppliersByName(String name){
        List<Map<String,String>> suppliers = service.getSuppliersByName(name);
        return suppliers;
    }

    /**
     * 查询供应商id
     * @param name
     * @return
     */
    public List <ChargeSupply> getSupplierId(String name){
        ChargeSupply chargeSupply=new ChargeSupply();
        chargeSupply.setName(name);
        return supplierListService.getSupplier(chargeSupply);
    }


}
