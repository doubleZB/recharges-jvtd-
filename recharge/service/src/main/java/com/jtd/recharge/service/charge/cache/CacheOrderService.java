package com.jtd.recharge.service.charge.cache;

import com.alibaba.fastjson.JSON;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.base.constant.SysConstants;
import com.jtd.recharge.base.util.MessageClient;
import com.jtd.recharge.dao.bean.CallbackReport;
import com.jtd.recharge.dao.bean.Order;
import com.jtd.recharge.dao.mapper.CacheOrderMapper;
import com.jtd.recharge.dao.mapper.ChargeOrderMapper;
import com.jtd.recharge.dao.mapper.DictMapper;
import com.jtd.recharge.dao.po.*;
import com.jtd.recharge.service.admin.OperateLogService;
import com.jtd.recharge.service.charge.order.OrderService;
import com.jtd.recharge.service.charge.order.PushCallbackService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liyabin on 2017/8/22.
 */
@Service
public class CacheOrderService {

    private Log log = LogFactory.getLog(this.getClass());

    @Resource
    private CacheOrderMapper cacheOrderMapper;

    @Resource
    private DictMapper dictMapper;

    @Resource
    public PushCallbackService pushCallbackService;

    @Resource
    public OrderService orderService;

    @Resource
    public OperateLogService operateLogService;

    @Resource
    ChargeOrderMapper chargeOrderMapper;

    public int insertCacheOrder(CacheOrder cacheOrder) {
        return cacheOrderMapper.insertSelective(cacheOrder);
    }


    //查询省列表
    public List<Dict> selectDictByModule(String module) {
        Dict dict = new Dict();
        dict.setModule(module);
        return dictMapper.selectDictByModule(dict);
    }

    //查询缓存订单列表，包含分页功能
    public PageInfo<CacheOrder> getOrderPage(Integer pageNumber, Integer pageSize, String startTime, String endTime, CacheOrder cacheOrder) {
        PageHelper.startPage(pageNumber, pageSize, "cache_time desc");
        cacheOrder.setStatus(CacheOrder.statuss.CACHE);
        List<CacheOrder> roleList = cacheOrderMapper.getOrderListByCondition(cacheOrder, startTime, endTime);
        PageInfo<CacheOrder> pageInfo = new PageInfo<>(roleList);
        return pageInfo;
    }

    //修改缓存订单的状态
    public Boolean updateChargeStatus(String[] orderNums, int status,String startTime, String endTime, CacheOrder cacheOrders,Integer ischeckalls) {
        List<CacheOrder> cacheOrderList=new ArrayList<>();
        //获取缓存订单列表
        if(ischeckalls==1) {
           cacheOrderList = cacheOrderMapper.getChechorder(orderNums);
            cacheOrderMapper.updateChargeStatus(orderNums, status);
        }else if(ischeckalls==2){
            cacheOrders.setStatus(CacheOrder.statuss.CACHE);
            cacheOrderList= cacheOrderMapper.getOrderListByCondition(cacheOrders, startTime, endTime);
            String [] orderNum = new String[cacheOrderList.size()];
            for(int i=0;i<cacheOrderList.size();i++){
                orderNum[i]=cacheOrderList.get(i).getOrderNum();
            }
            cacheOrderMapper.updateChargeStatus(orderNum, status);
        }
        if (status == CacheOrder.statuss.WAIT_PUSH) {
            for (CacheOrder cacheOrder : cacheOrderList) {
                //连接消息队列服务
                MNSClient client = MessageClient.getClient();
                CloudQueue queue = client.getQueueRef(SysConstants.Queue.CACHE_QUEUE);
                Message message = new Message();
                message.setMessageBody(JSON.toJSONString(cacheOrder));
                Message putMsg = queue.putMessage(message);
                log.info("cacheorder-商户" + cacheOrder.getUserId() + "商户订单号:" + cacheOrder.getCustomid() + "缓存订单进队列cache回调---");
            }
        } else if (status == CacheOrder.statuss.COMPLETED) {
            for (CacheOrder cacheOrder : cacheOrderList) {
                Order orders = new Order();
                orders.setOrder("charge_order");
                orders.setOrderNum(cacheOrder.getOrderNum());
                Order order = orderService.selectOrderByOrderNum(orders);
                if (order != null) {
                    //推送成功消息
                    CallbackReport callbackReport = new CallbackReport();
                    callbackReport.setOrderNum(order.getOrderNum());
                    callbackReport.setToken(order.getToken());
                    callbackReport.setStatus(String.valueOf(ChargeOrder.OrderStatus.CHARGE_FAIL));
                    callbackReport.setCallbackUrl(order.getCallbackUrl());
                    callbackReport.setCustomId(order.getCustomId());
                    callbackReport.setMobile(order.getRechargeMobile());
                    callbackReport.setPushSum(CallbackReport.pushSumStatus.STATUS_ONE);
                    String success = pushCallbackService.pushCallback(callbackReport);

                    log.info("cacheorder-商户" + cacheOrder.getUserId() + "商户订单号:" + cacheOrder.getCustomid() + "缓存订单返回失败---");
                }
                chargeOrderMapper.updateStatusByOrderNums(cacheOrder.getOrderNum(), ChargeOrder.OrderStatus.CHARGE_FAIL);
            }
        }
        return true;
    }
}
