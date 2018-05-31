package com.jtd.recharge.action.charge.cache;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.po.CacheOrder;
import com.jtd.recharge.dao.po.Dict;
import com.jtd.recharge.service.charge.cache.CacheOrderService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by lhm on 2017/8/11.
 * 缓存订单
 */
@Controller
@RequestMapping("/cache")
public class CachingOrderAction {

    private Logger log = Logger.getLogger(this.getClass());
    @Resource
    private CacheOrderService cacheOrderService;

    @RequestMapping(method = RequestMethod.GET, value = "/cacheOrder")
    public String cacheRule(HttpServletRequest request, HttpServletResponse response) {
        List<Dict> dict = cacheOrderService.selectDictByModule("province");
        request.setAttribute("dict", dict);
        request.setAttribute("dict_json", JSON.toJSONString(dict));
        return "charge/cache/cachingOrder";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/cacheOrderSearch")
    public PageInfo cacheOrderSearch(Integer pageNumber, Integer pageSize, String startTime, String endTime, CacheOrder cacheOrder, HttpServletRequest request, HttpServletResponse response) {
        if (pageNumber == null || "".equals(pageNumber)) {
            pageNumber = 0;
        }
        if (pageSize == null || "".equals(pageSize)) {
            pageSize = 10;
        }
        PageInfo pageInfo = new PageInfo();
        try {
            pageInfo = cacheOrderService.getOrderPage(pageNumber, pageSize, startTime, endTime, cacheOrder);
        } catch (Exception e) {
            log.error(e);
        }
        return pageInfo;
    }

    @ResponseBody
    @RequestMapping(value = "/continueCharge")
    public Boolean continueCharge(int OrderStatus, String startTime,
                                  String endTime, CacheOrder cacheOrder,Integer ischeckalls,
                                  @RequestParam(value = "orderNums[]", required = false) String[] orderNums) {

        return cacheOrderService.updateChargeStatus(orderNums, OrderStatus,startTime, endTime, cacheOrder,ischeckalls);
    }

}
