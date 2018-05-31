package com.jtd.recharge.schedule.channel;

import com.alibaba.fastjson.JSON;
import com.jtd.recharge.dao.po.ChannelStatisticsDay;
import com.jtd.recharge.dao.po.UserStatisticsDay;
import com.jtd.recharge.service.statistics.ChannelStatisticsDayService;
import com.jtd.recharge.service.statistics.UserStatisticsDayService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by lyp on 2017/3/8.
 */
@Controller
@RequestMapping("/ChannelStatisticsDay")
public class ChannelStatisticsDayTime {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private ChannelStatisticsDayService channelStatisticsDayService;
    /**
     *渠道统计
     * @return
     */
    @RequestMapping("/day")
    @ResponseBody
    public void  countChannelStatisticsDay()throws Exception{
        log.info("线程开始启动");
        List<ChannelStatisticsDay> channelStatisticsDays =  selectSuccessChannelTotalOrder();
        insertChannelStatisticsDay(channelStatisticsDays);
        List<ChannelStatisticsDay> channelStatisticsDay =  selectChannelTotalOrder();
        updateChannelStatisticsDa(channelStatisticsDay);
    }

    /**
     * 统计渠道每天成功单数和金额
     * @return
     */
    public List<ChannelStatisticsDay> selectSuccessChannelTotalOrder (){
       String orderTime = countTime();
        return channelStatisticsDayService.selectSuccessChannelTotalOrder("2017-04-26%");
    }

    /**
     * 增加每天统计出来的渠道成功数和金额
     */
    public void insertChannelStatisticsDay( List<ChannelStatisticsDay> channelStatisticsDays){
        for (ChannelStatisticsDay statisticsDay:channelStatisticsDays){
            log.info("每天插入的数据"+ JSON.toJSONString(statisticsDay));
            statisticsDay.setUpdateTime(countTime());
            channelStatisticsDayService.insertChannelStatisticsDay(statisticsDay);
        }
    }

    /**
     * 统计渠道总的订单数
     * @return
     */
    public List<ChannelStatisticsDay> selectChannelTotalOrder(){
       return channelStatisticsDayService.selectChannelTotalOrder("2017-04-26%");
    }

    /**
     * 修改渠道总订单数
     * @param channelStatisticsDay
     */
    public void  updateChannelStatisticsDa( List<ChannelStatisticsDay> channelStatisticsDay){
        for (ChannelStatisticsDay statisticsDay:channelStatisticsDay){
            log.info("每天修改的数据"+ JSON.toJSONString(statisticsDay));
            statisticsDay.setUpdateTime(countTime());
            channelStatisticsDayService.updateChannelStatisticsDay(statisticsDay);
        }
    }


    /**
     * 计算统计时间
     * @return
     */
    public String countTime(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = new Date();
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) - 1);
        return df.format(date.getTime())+"%";
    }

}
