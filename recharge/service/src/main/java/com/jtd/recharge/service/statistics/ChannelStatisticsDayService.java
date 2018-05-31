package com.jtd.recharge.service.statistics;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.mapper.ChannelStatisticsDayMapper;
import com.jtd.recharge.dao.po.ChannelStatisticsDay;
import com.jtd.recharge.dao.po.ChargeSupply;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lyp on 2017/5/24.
 */
@Service
@Transactional(readOnly = true)
public class ChannelStatisticsDayService {

    @Resource
    private ChannelStatisticsDayMapper channelStatisticsDayMapper;

    /**
     * 统计渠道成功的订单数和金额
     * @param orderTime
     * @return
     */
     public List<ChannelStatisticsDay> selectSuccessChannelTotalOrder(String orderTime){
        return channelStatisticsDayMapper.selectSuccessChannelTotalOrder(orderTime);
     }

    /**
     * 增加每天统计出来的渠道成功数和金额
     * @param channelStatisticsDay
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int insertChannelStatisticsDay(ChannelStatisticsDay channelStatisticsDay){
        return channelStatisticsDayMapper.insertSelective(channelStatisticsDay);
    }


    /**
     * 统计渠道总的订单数
     * @param orderTime
     * @return
     */
    public List<ChannelStatisticsDay> selectChannelTotalOrder(String orderTime){
        return channelStatisticsDayMapper.selectChannelTotalOrder(orderTime);
    }

    /**
     * 修改渠道总订单数
     * @param channelStatisticsDay
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateChannelStatisticsDay(ChannelStatisticsDay channelStatisticsDay){
        return channelStatisticsDayMapper.updateChannelStatisticsDay(channelStatisticsDay);
    }

    /**
     * 渠道日统计查询分页
     * @param channelStatisticsDay
     * @return
     */
    public PageInfo<ChannelStatisticsDay> selectByPrimaryKeySelective(ChannelStatisticsDay channelStatisticsDay,Integer pageNumber,Integer pageSize){
        PageHelper.startPage(pageNumber,pageSize,"id desc");
        List<ChannelStatisticsDay> list = channelStatisticsDayMapper.selectByPrimaryKeySelective(channelStatisticsDay);
        PageInfo<ChannelStatisticsDay> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }
}
