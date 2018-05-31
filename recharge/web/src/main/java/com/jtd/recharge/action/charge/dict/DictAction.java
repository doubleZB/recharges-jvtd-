package com.jtd.recharge.action.charge.dict;

import com.jtd.recharge.dao.bean.Order;
import com.jtd.recharge.dao.po.Dict;
import com.jtd.recharge.service.charge.dict.DictService;
import com.jtd.recharge.service.charge.order.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by lihuimin on 2016/12/1.
 */
@Controller
@RequestMapping("/order")
public class DictAction {

    @Resource
    public DictService dictservice;
    /**
     * 查找字典表中的内容
     * @param dict
     * @return
     */
    @RequestMapping("/dict")
    @ResponseBody
    public List<Dict> dictList(Dict dict){
        List<Dict> list = dictservice.selectDict(dict);
        return list;
    }
}
