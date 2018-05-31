package com.jtd.recharge.action.charge.product;

import com.jtd.recharge.dao.po.ChargePosition;
import com.jtd.recharge.dao.po.Dict;
import com.jtd.recharge.service.charge.product.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by WXP on 2016-11-16 17:59:03.
 */
@Controller
@RequestMapping("/product")
public class ProductAction {
    @Resource
    public ProductService service;

    /**
     * 跳转到商品管理页面
     * @return
     */
    @RequestMapping("/toProductPage")
    public String toProductPage(HttpServletRequest request){
        List<Dict> dicts = service.getAllPrivence();
        List<ChargePosition> flowPosition = service.getFlowPosition();
        List<ChargePosition> phoneCostPosition = service.getPhoneCostPosition();
        request.setAttribute("dicts",dicts);
        request.setAttribute("flowPosition",flowPosition);
        request.setAttribute("phoneCostPosition",phoneCostPosition);
        return "charge/product/productManage";
    }

    /**
     * 初始化商品数据
     * @return
     */
    @ResponseBody
    @RequestMapping("/initProductData")
    public String initProductData(){
        long start = System.currentTimeMillis();
        String msg = service.initProductData();
        msg = msg+"耗时"+(System.currentTimeMillis()-start);
//        System.out.println(msg);
        return msg;
    }

}
