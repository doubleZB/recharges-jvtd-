package com.jtd.recharge.action.charge.channel;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.jtd.recharge.dao.po.*;
import com.jtd.recharge.service.admin.OperateLogService;
import com.jtd.recharge.service.charge.channel.ChannelListService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lyp on 2016/11/14.
 */
@Controller
@RequestMapping("/channels")
public class ChannelAction {

    @Resource
    private ChannelListService channelListService;
    @Resource
    public OperateLogService operateLogService;
    /**
     *
     * @lyp
     * 渠道管理页面用于左侧跳转
     */
    @RequestMapping("/channellist")
    public String ChannelList(){
        return "/charge/channel/channelList";
    }

    /**
     * 获取供应商 lyp
     * @return
     */
    @RequestMapping("/getsupplierandchannles")
    @ResponseBody
    public Object  GetSupplier(){
        List<ChargeSupply> chargeSupply=channelListService.getSupplier();
        return chargeSupply;
    }

    /**
     * 获取产品编码
     * @param request
     * @return
     */
    @RequestMapping("/getproduct")
    @ResponseBody
    public Object getProduct(HttpServletRequest request){
        ChargePosition chargePosition=new ChargePosition();
        String type=request.getParameter("type");
        if(type!=null&&!"".equals(type)){
            int typeId=Integer.parseInt(type);
            chargePosition.setBusinessType(typeId);
        }

        String operator=request.getParameter("operator");
        if(operator!=null&&!"".equals(operator)){
            int operatorId=Integer.parseInt(operator);
            chargePosition.setOperator(operatorId);
        }

        List<ChargePosition> product=channelListService.getProduct(chargePosition);
        return product;
    }

    /**
     * 添加通道
     * @param request
     * @return
     */
    @RequestMapping("/insertChannels")
    @ResponseBody
    public String addChannel(HttpServletRequest request){
        String boo = "";
        String resultMsg="";
        String userId=request.getParameter("userId");
        String channelName=request.getParameter("channelName");//通道名称
        String channelType=request.getParameter("channelType");//通道类型
        String operator=request.getParameter("operator");
        String supplier=request.getParameter("supplier");
        String province=request.getParameter("province");
        String product=request.getParameter("product");
        String productType=request.getParameter("productType");
        String discount=request.getParameter("discount");
        String splitRatio=request.getParameter("splitRatio");//分流比
        String state=request.getParameter("state");
        //拼接通道名称的

        int channelTypeId=Integer.parseInt(channelType);
        int operatorId=Integer.parseInt(operator);
        int supplierId=Integer.parseInt(supplier);
        int productTypeId=Integer.parseInt(productType);
        int stateId=Integer.parseInt(state);
        int splitRatioInt=Integer.parseInt(splitRatio);
        BigDecimal bd=new BigDecimal(discount);
        String[] proId=province.split(",");
        String[] prodId=product.split(",");

        ChargeChannel chargeChannel=new ChargeChannel();
        for(int i=0;i<proId.length;i++) {
            for (String p : prodId) {
                int provinceId = Integer.parseInt(proId[i]);//省份id
                chargeChannel.setBusinessType(channelTypeId);//渠道类型
                chargeChannel.setOperatorId(operatorId);//运营商
                chargeChannel.setSupplyId(supplierId);//供应商
                chargeChannel.setProvinceId(provinceId);//省份id
                chargeChannel.setChannelScope(productTypeId);//产品通道类型
                chargeChannel.setPositionCode(p);//自己公司的产品编码code
                //防止渠道重复提交
                List<ChargeChannel> list=channelListService.selectChannelInsert(chargeChannel);
                if(list.size()==0) {
                    ChargeChannel chargeChannelTwo=new ChargeChannel();
                    chargeChannelTwo.setChannelName(channelName); //渠道名称
                    chargeChannelTwo.setCostDiscount(bd);//折扣
                    chargeChannelTwo.setWeight(splitRatioInt);//分流比
                    chargeChannelTwo.setStatus(stateId);//通道状态
                    chargeChannelTwo.setUpdateTime(new Date());

                    chargeChannelTwo.setBusinessType(chargeChannel.getBusinessType());//渠道类型
                    chargeChannelTwo.setOperatorId(chargeChannel.getOperatorId());//运营商
                    chargeChannelTwo.setSupplyId(chargeChannel.getSupplyId());//供应商
                    chargeChannelTwo.setProvinceId(chargeChannel.getProvinceId());//省份id
                    chargeChannelTwo.setChannelScope(chargeChannel.getChannelScope());//产品通道类型
                    chargeChannelTwo.setPositionCode(chargeChannel.getPositionCode());

                    int result = channelListService.addChannels(chargeChannelTwo);
                    if (result == 1) {
                        operateLogService.logInfo(userId,"渠道列表",userId+"在渠道列表增加了渠道："+channelName+"的信息");
                        boo = "添加成功";
                    } else {
                        boo = "添加失败";
                    }
                }else {
                    resultMsg += "存在重复渠道---卡品："+chargeChannel.getPositionCode()+"---省份："+chargeChannel.getProvinceId()+"\n";
                }
            }
        }
        if(resultMsg!=null && !("").equals(resultMsg)){
            return resultMsg;
        }else{
            return boo;
        }
    }



    /**
     * 渠道列表查询
     * @param request
     * @return
     */
    @RequestMapping("/getChannelDetail")
    @ResponseBody
    public Object getChannelDetail(HttpServletRequest request){
        ChargeChannel chargeChannel=new ChargeChannel();
        String type=request.getParameter("lx");
        if(type!="0"&&!"0".equals(type)){
            int lxId=Integer.parseInt(type);
            chargeChannel.setBusinessType(lxId);
        }
        String  operator=request.getParameter("yys");
        if(operator!="0"&&!"0".equals(operator)){
            int operatorId=Integer.parseInt(operator);
            chargeChannel.setOperatorId(operatorId);
        }
        //省份多选
        String  provinceIds=request.getParameter("sf");
        List<Integer> provinceIdList = new ArrayList<>();
        if(!"".equals(provinceIds)){
            String ids[] = provinceIds.split(",");
            for (int i = 0; i <ids.length ; i++) {
                provinceIdList.add(Integer.parseInt(ids[i]));
            }
        }
        if(provinceIdList!=null && !provinceIdList.isEmpty()){
            chargeChannel.setPrivanceIds(provinceIdList);
        }
        //面值多选
        String parValue = request.getParameter("parvalue");
        if(StringUtil.isNotEmpty(parValue)){
            List<String> parValueList = new ArrayList<>();
            String ids[] = parValue.split(",");
            for (String str:ids){
                parValueList.add(str);
            }
            chargeChannel.setParValue(parValueList);
        }

        String  jc = request.getParameter("jc");
        if(StringUtil.isNotEmpty(jc)) {
            chargeChannel.setChannelName(jc);
        }
        String supplyName=request.getParameter("gys");
        if(supplyName!="0"&&!"0".equals(supplyName)){
            int supplyId=Integer.parseInt(supplyName);
            chargeChannel.setSupplyId(supplyId);
        }
        String  status=request.getParameter("zt");

        if(status!="0"&&!"0".equals(status)){
            int statusId=Integer.parseInt(status);
            chargeChannel.setStatus(statusId);
        }

        String pageNum=request.getParameter("pageNum");
        int pageNumInt;
        if(pageNum==null||"".equals(pageNum)){
            pageNumInt=1;
        }else{
            pageNumInt=Integer.parseInt(pageNum);
        }
        PageInfo<ChargeChannel> list=channelListService.getChannelDetil(chargeChannel ,pageNumInt ,10);
        return  list;
    }

    /**
     * 渠道列表删除
     * @param request
     * @return
     */
    @RequestMapping("/deleteChannel")
    @ResponseBody
    public Object deleteChannel(HttpServletRequest request){
        String id=request.getParameter("id");
        String userId=request.getParameter("userId");
        operateLogService.logInfo(userId,"渠道列表",userId+"在渠道列表删除了渠道："+id+"的信息");
        int idInt=Integer.parseInt(id);
        return channelListService.deleteChannels(idInt);
    }

    /**
     * 渠道修改
     * @param chargeChannel
     * @param request
     * @return
     */
    @RequestMapping("/updateChannel")
    @ResponseBody
    public Object updateChannel(ChargeChannel chargeChannel,HttpServletRequest request){
        Integer id = chargeChannel.getId();
        String userId=request.getParameter("userId");
        operateLogService.logInfo(userId,"渠道列表",userId+"在渠道列表修改了渠道："+id+"的信息");
        int i=channelListService.updateChannel(chargeChannel);
        return i;
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
     * 渠道页面批量修改
     * @param request
     * @return
     */
    @RequestMapping("/updatePageChannel")
    @ResponseBody
    public Object updatePageChannel(HttpServletRequest request){
        int i=0;
        String checkID=request.getParameter("checkID");
        String discount=request.getParameter("discount");
        String weight=request.getParameter("weith");
        String[] checkIDs=checkID.split(",");
        String userId=request.getParameter("userId");
        String status=request.getParameter("status");

        BigDecimal bd=new BigDecimal(discount);
        int weightTwo=Integer.parseInt(weight);
        ChargeChannel chargeChannel=new ChargeChannel();
        for(String s:checkIDs){
            int id=Integer.parseInt(s);
            chargeChannel.setId(id);
            chargeChannel.setCostDiscount(bd);
            chargeChannel.setWeight(weightTwo);
            chargeChannel.setStatus(Integer.parseInt(status));
            i=channelListService.updateChannel(chargeChannel);
            operateLogService.logInfo(userId,"渠道列表",userId+"在渠道列表批量修改了渠道id:"+id);
        }
        return i;
    }

    /**
     * 一键批量修改
     * @param request
     * @return
     */
    @RequestMapping("/updateAllChannel")
    @ResponseBody
    public Object updateAllChannel(HttpServletRequest request){
        ChargeChannel chargeChannel=new ChargeChannel();
        BigDecimal bd=new BigDecimal(request.getParameter("discount"));
        int weightTwo=Integer.parseInt(request.getParameter("weith"));
        String chooseStatusThree=request.getParameter("chooseStatusThree");
        chargeChannel.setCostDiscount(bd);
        chargeChannel.setWeight(weightTwo);
        chargeChannel.setUpdateStatus(Integer.parseInt(chooseStatusThree));

        String channelName=request.getParameter("channelName");


        String userId=request.getParameter("userId");
        String provinceId = request.getParameter("provinceId");
        String[] split = provinceId.split(",");
        if(provinceId.equals(",")){
            if(channelName!=null&&!"".equals(channelName)) {
                chargeChannel.setChannelName(channelName);
            }
            int businessTypeId=Integer.parseInt(request.getParameter("businessType"));
            if(businessTypeId >0){
                chargeChannel.setBusinessType(businessTypeId);
            }
            int supplyId=Integer.parseInt(request.getParameter("supplyId"));
            if(supplyId >0){
                chargeChannel.setSupplyId(supplyId);
            }
            int operatorId=Integer.parseInt(request.getParameter("operatorId"));
            if(operatorId >0){
                chargeChannel.setOperatorId(operatorId);
            }
            int status=Integer.parseInt(request.getParameter("status"));
            if(status >0){
                chargeChannel.setStatus(status);
            }
            //面值多选
            String parValue = request.getParameter("parvalue");
            if(StringUtil.isNotEmpty(parValue)){
                List<String> parValueList = new ArrayList<>();
                String ids[] = parValue.split(",");
                for (String str:ids){
                    parValueList.add(str);
                }
                chargeChannel.setParValue(parValueList);
            }

            channelListService.updateAllChannel(chargeChannel);
            operateLogService.logInfo(userId,"渠道列表",userId+"在渠道列表一键修改了渠道供应商id:"+supplyId+"的信息");
        } else{
            for(String str:split){
                if(channelName!=null&&!"".equals(channelName)) {
                    chargeChannel.setChannelName(channelName);
                }
                int businessTypeId=Integer.parseInt(request.getParameter("businessType"));
                if(businessTypeId >0){
                    chargeChannel.setBusinessType(businessTypeId);
                }
                int supplyId=Integer.parseInt(request.getParameter("supplyId"));
                if(supplyId >0){
                    chargeChannel.setSupplyId(supplyId);
                }
                //面值多选
                String parValue = request.getParameter("parvalue");
                if(StringUtil.isNotEmpty(parValue)){
                    List<String> parValueList = new ArrayList<>();
                    String ids[] = parValue.split(",");
                    for (String strs:ids){
                        parValueList.add(strs);
                    }
                    chargeChannel.setParValue(parValueList);
                }
                int operatorId=Integer.parseInt(request.getParameter("operatorId"));
                if(operatorId >0){
                    chargeChannel.setOperatorId(operatorId);
                }
                int status=Integer.parseInt(request.getParameter("status"));
                if(status >0){
                    chargeChannel.setStatus(status);
                }
                chargeChannel.setProvinceId(Integer.parseInt(str));
                channelListService.updateAllChannel(chargeChannel);
                operateLogService.logInfo(userId,"渠道列表",userId+"在渠道列表一键修改了渠道供应商id:"+supplyId+"的信息");
            }
        }

        return true;
    }

    /**
     * 获取渠道相应的产品
     * @param chargeSupplyPosition
     * @return
     */
    @RequestMapping("/getSupplierProduct")
    @ResponseBody
    public Object getSupplierProduct(ChargeSupplyPosition chargeSupplyPosition){
        List<ChargeSupplyPosition> list=channelListService.getSupplierProduct(chargeSupplyPosition);
        return list;
    }

    /**
     * 去查看卡品列表
     * @param
     * @return
     */
    @RequestMapping("/CheckCardProductList")
    public String CheckCardProductList(HttpServletRequest request){
        String supplyId = request.getParameter("supplyId");
        request.setAttribute("supplyId",supplyId);
        return "charge/channel/CheckCardProduct";
    }


//    /**
//     * 查看渠道相应的卡品
//     * @param chargeSupplyPosition
//     * @return
//     */
//    @RequestMapping("/selectChargeSupplyPosition")
//    @ResponseBody
//    public Object selectChargeSupplyPosition(Integer pageNumber, Integer pageSize,ChargeSupplyPosition chargeSupplyPosition){
//        PageInfo<ChargeSupplyPosition> list=channelListService.selectChargeSupplyPosition(pageNumber,pageSize,chargeSupplyPosition);
//        return list;
//    }
}
