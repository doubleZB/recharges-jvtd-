package com.jtd.recharge.action.iot;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.po.IotOrder;
import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.dao.po.IotSubOrder;
import com.jtd.recharge.dao.po.IotSupply;
import com.jtd.recharge.dao.po.UserBalance;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.IotOrderStatus;
import com.jtd.recharge.service.iot.IotOrderService;
import com.jtd.recharge.service.iot.IotProductService;
import com.jtd.recharge.service.iot.IotSubOrderService;
import com.jtd.recharge.service.iot.SuppplierService;
import com.jtd.recharge.service.iot.UserBalanceService;

/**
 * 订单审核
 *
 * @author ninghui
 */
@Controller
@RequestMapping("/iotOrderAudit")
public class IotOrderAuditAction {
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    private IotOrderService iotOrderService;
    @Resource
    private IotSubOrderService iotSubOrderService;

    @Resource
    private UserBalanceService userBalanceService;
    @Resource
    private IotProductService iotProductService;
    @Resource
    private SuppplierService suppplierService;

    /**
     * 跳转到待审核订单列表页
     *
     * @param request
     * @return
     */
    @RequestMapping("/index")
    public String audit(HttpServletRequest request) {
        request.setAttribute("operatorList", CardOperator.values());
        request.setAttribute("cardSizeList", CardSize.values());
        List<IotProduct> allProduct = iotProductService.listAllProduct();
        request.setAttribute("iotProductList", allProduct);
        PageInfo<IotSupply> allSupply = suppplierService.getAllSupply();
        request.setAttribute("iotSupplyList", allSupply.getList());
        return "iot/order/audit";
    }

    /**
     * 获取订单列表信息
     *
     * @param pageNumber
     * @param pageSize
     * @param request
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public PageInfo<IotSubOrder> list(Integer pageNumber, Integer pageSize, IotSubOrder subOrder, HttpServletRequest request) {
        subOrder.setStatus(IotOrderStatus.待审核.getValue());
        List<IotSubOrder> orderList = iotSubOrderService.selectOrderList(pageNumber, pageSize, subOrder);
        PageInfo<IotSubOrder> pageInfo = new PageInfo<>(orderList);
        return pageInfo;
    }

    /**
     * 审核操作
     *
     * @param id     子订单id
     * @param adopt  通过或驳回的标识
     * @param remark 审核意见
     * @return
     */
    @RequestMapping("/adoptOrRefuse")
    @ResponseBody
    public ReturnMsg adoptOrRefuse(Integer id, Integer adopt, String remark) {
        ReturnMsg returnMsg = new ReturnMsg();
        returnMsg.setSuccess(false);
        returnMsg.setMessage("订单审核成功!");
        IotSubOrder subOrder = iotSubOrderService.selectById(id);
        IotSubOrder iotSubOrder = new IotSubOrder();
        iotSubOrder.setId(id);
        IotOrder iotOrder = new IotOrder();
        iotOrder.setRemark(remark);
        iotOrder.setId(subOrder.getParentId());
        iotOrderService.updateById(iotOrder);
        //审核通过
        if (adopt == 1) {
            UserBalance userBalance = userBalanceService.queryBalanceByUserId(subOrder.getUserId());
            if (userBalance.getUserBalance().compareTo(subOrder.getAmount()) >= 0) {
                try {
                    iotSubOrderService.updateAudit(id);
                    returnMsg.setSuccess(true);
                    return returnMsg;
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                    returnMsg.setMessage(e.getMessage());
                }
            } else {
                iotSubOrder.setStatus(IotOrderStatus.待加款.getValue());
            }
        } else {
            iotSubOrder.setStatus(IotOrderStatus.已驳回.getValue());
        }
        iotSubOrder.setCurrentStatus(IotOrderStatus.待审核.getValue());
        Integer integer = iotSubOrderService.safeUpdateIotSubOrderById(iotSubOrder);
        if (integer > 0) {
            returnMsg.setSuccess(true);
        }else{
            returnMsg.setMessage("审核失败");
        }
        return returnMsg;
    }
}
