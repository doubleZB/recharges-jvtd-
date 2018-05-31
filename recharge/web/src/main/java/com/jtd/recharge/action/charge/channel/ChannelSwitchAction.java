package com.jtd.recharge.action.charge.channel;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.dao.po.ChargeChannel;
import com.jtd.recharge.dao.po.ChargeOrder;
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
import java.util.List;

/**
 * Created by lyp on 2016/11/14.
 * 渠道开关
 */
@Controller
@RequestMapping("channels")
public class ChannelSwitchAction {

    @Resource
    private ChannelListService channelListService;
    @Resource
    public OperateLogService operateLogService;

    @RequestMapping("/switch")
    public String switchmanagement(){


        return "/charge/channel/channelSwitch";
    }

    /**
     * 页面数据条件分页查询 lyp
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/getchannellist")
    @ResponseBody
    public Object getChannelList(HttpServletRequest request, HttpServletResponse response){
        ChargeChannel chargeChannel=new ChargeChannel();
        String typ=request.getParameter("typ");
        String sta=request.getParameter("sta");
        String yys=request.getParameter("yys");
        String name=request.getParameter("name");
        String pageNum=request.getParameter("pageNum");
        String sf=request.getParameter("sf");
        int gysid=Integer.parseInt(request.getParameter("gys"));
        int pageNumint;
        if(pageNum==null||"".equals(pageNum)){
            pageNumint=1;
        }else{
            pageNumint=Integer.parseInt(pageNum);
        }
        if (typ != "0" && !"0".equals(typ)) {
            int typid=Integer.parseInt(typ);
            chargeChannel.setBusinessType(typid);
        }
        if (sta != "0" && !"0".equals(sta)) {
            int staid=Integer.parseInt(sta);
            chargeChannel.setStatus(staid);
        }
        if (yys != "0" && !"0".equals(yys)) {
            int yysid=Integer.parseInt(yys);
            chargeChannel.setOperatorId(yysid);
        }
        if (name != null && !"".equals(name)) {
                String names="%"+name+"%";
            chargeChannel.setChannelName(names);
        }
        if(gysid>0){
            chargeChannel.setSupplyId(gysid);
        }
        List<Integer> privanceIdList = new ArrayList<>();
        if(!",".equals(sf)){
            String ids[] = sf.split(",");
            for (int i = 0; i <ids.length ; i++) {
                privanceIdList.add(Integer.parseInt(ids[i]));
            }
            chargeChannel.setPrivanceIds(privanceIdList);
        }
        PageInfo<ChargeChannel> list=channelListService.getChannelDetil(chargeChannel,pageNumint,10);
        updataTime(list.getList());
        return list;
    }

    public void updataTime(List<ChargeChannel> details){
        if(details!=null && !details.isEmpty()){
            for (int i = 0; i <details.size() ; i++) {
                ChargeChannel chargeChannel = details.get(i);
                chargeChannel.setDateStr(DateUtil.Date2String(chargeChannel.getUpdateTime(),DateUtil.SQL_TIME_FMT));
            }
        }
    }

    /**
     * 通道开关修改
     * @param request
     * @return
     */
    @RequestMapping("/editorchannelswitch")
    @ResponseBody
    public Object editorChannelSwitch(HttpServletRequest request){
        String checkID=request.getParameter("checkID");
        String selecte= request.getParameter("selecte");
        String username=request.getParameter("username");
        String updateresone=request.getParameter("yuanyin");

        String userId=request.getParameter("userId");
        String[] checkIDs=checkID.split(",");
        int statusid=Integer.parseInt(selecte);
        int i=0;
        for(String s:checkIDs){
            int id=Integer.parseInt(s);
            i= channelListService.editorChannelSwitch(id,statusid,username,updateresone);
            operateLogService.logInfo(userId,"渠道开关维护",userId+"在渠道开关维护列表修改了开关状态id:"+id);
        }
        return i;
    }

    /**
     * 开关一键批量修改
     * @param request
     * @return
     */
    @RequestMapping("/updateallswitchchannel")
    @ResponseBody
    public Object updateAllChannel(HttpServletRequest request){
        ChargeChannel chargeChannel=new ChargeChannel();
        int selecte= Integer.parseInt(request.getParameter("selecte"));
        chargeChannel.setUpdateStatus(selecte);
        String username = request.getParameter("username");
        chargeChannel.setUpdateName(username);
        String updateresone=request.getParameter("yuanyin");
        chargeChannel.setUpdateReason(updateresone);
        String channelName=request.getParameter("channelName");

        String userId=request.getParameter("userId");
        String provinceId = request.getParameter("provinceId");
        String[] split = provinceId.split(",");
        if(provinceId.equals(",")){
            if(channelName!=null&&!"".equals(channelName)) {
                chargeChannel.setChannelName(channelName);
            }
            int lxid=Integer.parseInt(request.getParameter("businessType"));
            if(lxid >0){
                chargeChannel.setBusinessType(lxid);
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
            channelListService.updateAllSwitch(chargeChannel);
            operateLogService.logInfo(userId,"渠道开关维护",userId+"在渠道列表一键修改了渠道供应商id:"+supplyId+"的信息");
        }else{
            for(String str:split){
                if(channelName!=null&&!"".equals(channelName)) {
                    chargeChannel.setChannelName(channelName);
                }
                int lxid=Integer.parseInt(request.getParameter("businessType"));
                if(lxid >0){
                    chargeChannel.setBusinessType(lxid);
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
                chargeChannel.setProvinceId(Integer.parseInt(str));
                channelListService.updateAllSwitch(chargeChannel);
                operateLogService.logInfo(userId,"渠道开关维护",userId+"在渠道列表一键修改了渠道供应商id:"+supplyId+"的信息");
            }
        }
        return true;
    }

}
