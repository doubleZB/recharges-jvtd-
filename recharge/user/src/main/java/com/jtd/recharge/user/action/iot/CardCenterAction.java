package com.jtd.recharge.user.action.iot;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.po.IotCard;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.CardStatus;
import com.jtd.recharge.service.iot.CardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by ${zyj} on 2018/4/9.
 */
@Controller
@RequestMapping("/cardCenter")
public class CardCenterAction {
    @Resource
    public CardService cardService;

    @RequestMapping("index")
    public String index (HttpServletRequest request){
        request.setAttribute("cardSizeList", CardSize.values());
        request.setAttribute("cardStatusList", CardStatus.values());
        request.setAttribute("operatorList", CardOperator.values());
        return "/iot/cardCenter";
    }

    @RequestMapping("list")
    @ResponseBody
    public PageInfo<IotCard> list(Integer pageNumber, Integer pageSize, IotCard card, HttpServletRequest request) {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        card.setCustomerId(userId);
        PageInfo<IotCard> list = cardService.selectByCondition(pageNumber, pageSize, card);
        return list;
    }

    @RequestMapping("getCardById")
    @ResponseBody
    public IotCard getCardById(Integer id){
        IotCard iotCard = new IotCard();
        iotCard.setId(id);
        return cardService.selectOneByCondition(iotCard);
    }

}
