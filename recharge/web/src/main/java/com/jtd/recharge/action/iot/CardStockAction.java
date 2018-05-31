package com.jtd.recharge.action.iot;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.po.IotCard;
import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.dao.po.IotSupply;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.SaleStatus;
import com.jtd.recharge.service.iot.CardService;
import com.jtd.recharge.service.iot.IotProductService;
import com.jtd.recharge.service.iot.SuppplierService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by ${zyj} on 2018/4/11.
 */
@Controller
@RequestMapping("/iot/cardStock")
public class CardStockAction {

    @Resource
    private IotProductService iotProductService;
    @Resource
    public CardService cardService;
    @Resource
    private SuppplierService suppplierService;
    /**
     * 跳转到卡库存查询页面
     * @param request
     * @return
     */
    @RequestMapping("index")
    public String index(HttpServletRequest request){
        List<IotProduct> allProduct = iotProductService.listAllProduct();
        PageInfo<IotSupply> allSupply = suppplierService.getAllSupply();
        request.setAttribute("operatorList", CardOperator.values());
        request.setAttribute("cardSizeList", CardSize.values());
        request.setAttribute("iotProductList",allProduct);
        request.setAttribute("iotSupplyList", allSupply.getList());
        return "/iot/cardStock";
    }
    @RequestMapping("list")
    @ResponseBody
    public PageInfo<IotCard> list(Integer pageNumber, Integer pageSize, IotCard card) {
        card.setSaleStatus(SaleStatus.待售.getValue());
        PageInfo<IotCard> list = cardService.selectByCard(pageNumber, pageSize, card);
        return list;
    }
    @RequestMapping("getCard")
    @ResponseBody
    public List<IotCard> getCard(IotCard card){
        List<IotCard> list = cardService.selectById(card);
        return list;
    }
}
