package com.jtd.recharge.action.charge.channel;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.jtd.recharge.dao.po.ChannelStatisticsDay;
import com.jtd.recharge.dao.po.ChargeSupply;
import com.jtd.recharge.service.charge.channel.SupplierListService;
import com.jtd.recharge.service.statistics.ChannelStatisticsDayService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by lyp on 2017/5/24.
 */


@Controller
@RequestMapping("/channels")
public class ChannelStatisticsAction {

    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    private ChannelStatisticsDayService channelStatisticsDayService;
    @Resource
    private SupplierListService supplierListService;
    @RequestMapping("/channeltStatistics")
    public String channeltStatistics(){
        return "/charge/channel/channelStatistics";
    }

    /**
     * 渠道统计查询
     * @param request
     * @return
     */
    @RequestMapping("/selectChannelStatisticsDay")
    @ResponseBody
    public Object selectChannelStatisticsDay(HttpServletRequest request ,Integer pageNumber,Integer pageSize){
        ChannelStatisticsDay channelStatisticsDay = new ChannelStatisticsDay();

        String supplyName = request.getParameter("supplyName");
        if(StringUtil.isNotEmpty(supplyName)){
            List<ChargeSupply> list=(List)selectSupplier(supplyName);
            int supplyId=0;
            for(ChargeSupply supply:list){
                supplyId=supply.getId();
            }
            channelStatisticsDay.setSupplyId(supplyId);
        }

        String businessType = request.getParameter("businessType");
        if(!"0".equals(businessType)){
            channelStatisticsDay.setBusinessType(Integer.parseInt(businessType));
        }
        String updateTime = request.getParameter("updateTime");
        if(StringUtil.isNotEmpty(updateTime)){
            channelStatisticsDay.setUpdateTime("%"+updateTime+"%");
        }

        PageInfo<ChannelStatisticsDay> pageInfo = channelStatisticsDayService.selectByPrimaryKeySelective(channelStatisticsDay,pageNumber,pageSize);
        return  pageInfo;
    }

    /**
     * 获取供应商
     * @param supplyName
     * @return
     */
    public Object selectSupplier(String supplyName ){
        ChargeSupply chargeSupply=new ChargeSupply();
        if(StringUtil.isNotEmpty(supplyName)){
            chargeSupply.setName(supplyName);
        }

        return supplierListService.getSupplier(chargeSupply);
    }
}
