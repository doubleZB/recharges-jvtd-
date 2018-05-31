package com.jtd.recharge.action.iot;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.jtd.recharge.dao.po.*;
import com.jtd.recharge.service.iot.InReceiptService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.PurchaseStatus;
import com.jtd.recharge.service.iot.IotProductService;
import com.jtd.recharge.service.iot.PurchaseService;
import com.jtd.recharge.service.iot.SuppplierService;

/**
 * 采购单管理
 *
 * @author ninghui
 */
@Controller
@RequestMapping("/iot/purchase")
public class PurchaseAction {
    private Logger logger = Logger.getLogger(this.getClass());
    @Resource
    public PurchaseService purchaseService;
    @Resource
    public SuppplierService suppplierService;
    @Resource
    public IotProductService iotProductService;
    @Resource
    private InReceiptService inReceiptService;

    /**
     * *到应用页面
     *
     * @return
     */
    @RequestMapping("index")
    public ModelAndView index(HttpServletRequest request) throws UnsupportedEncodingException {
        ModelAndView mav = new ModelAndView("/iot/purchase");
        mav.addObject("purchaseStateList", PurchaseStatus.values());
        mav.addObject("operatorList", CardOperator.values());
        mav.addObject("cardSizeList", CardSize.values());
        return mav;
    }

    @RequestMapping("list")
    @ResponseBody
    public Object list(Integer pageNumber, Integer pageSize, IotPurchase purchase) {
        PageInfo<IotPurchase> list = purchaseService.find(pageNumber, pageSize, purchase);
        return list;
    }

    /**
     * 新增采购单
     *
     * @param purchase
     * @return
     */
    @RequestMapping("addPurchase")
    @ResponseBody
    public boolean addPurchase(HttpServletRequest request, IotPurchase purchase) {
        AdminUser user = (AdminUser) request.getSession().getAttribute("adminLoginUser");
        IotProduct product = iotProductService.getProductById(purchase.getFlowProductId());
        purchase.setCost(product.getPrice().multiply(purchase.getCostDiscount()));
        purchase.setCreaterId(user.getId());
        purchase.setIsRecharge(0);
        purchase.setIsSms(0);
        purchase.setAmount(purchase.getCost().multiply(new BigDecimal(purchase.getTotal())));
        int result = purchaseService.add(purchase);
        return result == 1;
    }

    /**
     * 去修改回显
     *
     * @return
     */
    @RequestMapping("/toUpdate")
    @ResponseBody
    public ReturnMsg toUpdate(String serialNum, HttpServletRequest request) {
        AdminUser user = (AdminUser) request.getSession().getAttribute("adminLoginUser");
        ReturnMsg msg = new ReturnMsg();
        msg.setSuccess(false);
        IotPurchase result = purchaseService.getBySerialNum(serialNum);
        if (result == null) {
            msg.setMessage("该采购单不存在!");
            return msg;
        }
        if (!user.getId().equals(result.getCreaterId())) {
            msg.setMessage("您无权修改该采购单!");
            return msg;
        }
        msg.setObject(result);
        msg.setSuccess(true);
        return msg;
    }

    //入库回显
    @RequestMapping("/getIotPurchaseByNum")
    @ResponseBody
    public ReturnMsg getIotPurchaseBy(String serialNum, HttpServletRequest request) {
        ReturnMsg msg = new ReturnMsg();
        msg.setSuccess(false);
        IotPurchase result = purchaseService.getBySerialNum(serialNum);
        if (result == null) {
            msg.setMessage("该采购单不存在!");
            return msg;
        }
        IotInReceipt inReceipt = inReceiptService.getByPurchaseId(result.getId());
        List<Object> ret = new ArrayList<Object>();
        ret.add(result);
        if (inReceipt != null) {
            ret.add(inReceipt);
        }
        msg.setObject(ret);
        msg.setSuccess(true);
        return msg;
    }

    /**
     * 修改
     *
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public ReturnMsg update(HttpServletRequest request, IotPurchase purchase) throws Exception {
        AdminUser user = (AdminUser) request.getSession().getAttribute("adminLoginUser");
        ReturnMsg msg = new ReturnMsg();
        msg.setSuccess(false);
        msg.setMessage("更新失败！");
        try {
            IotPurchase toUpdatePurchase = purchaseService.getById(purchase.getId());
            if (!user.getId().equals(toUpdatePurchase.getCreaterId())) {
                msg.setMessage("您无权修改该采购单!");
                return msg;
            }
            if (toUpdatePurchase.getSubOrderId() == null) {
                //根据订单生成的采购单不允许修改以下字段
                toUpdatePurchase.setFlowProductId(purchase.getFlowProductId());
                toUpdatePurchase.setCardSize(purchase.getCardSize());
            }

            toUpdatePurchase.setCostDiscount(purchase.getCostDiscount());
            IotProduct product = iotProductService.getProductById(purchase.getFlowProductId());
            toUpdatePurchase.setCost(product.getPrice().multiply(purchase.getCostDiscount()));
            toUpdatePurchase.setSupplyId(purchase.getSupplyId());
            toUpdatePurchase.setTotal(purchase.getTotal());
            toUpdatePurchase.setIsRecharge(0);
            toUpdatePurchase.setIsSms(0);
            toUpdatePurchase.setAmount(toUpdatePurchase.getCost().multiply(new BigDecimal(toUpdatePurchase.getTotal())));
            toUpdatePurchase.setPurchaseStatus(PurchaseStatus.待审核.getValue());
            purchaseService.update(toUpdatePurchase);
            msg.setSuccess(true);
            msg.setMessage("更新成功！");
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return msg;
    }

    /**
     * 取消
     *
     * @return
     */
    @RequestMapping("/cancel")
    @ResponseBody
    public ReturnMsg cancel(HttpServletRequest request, Integer purchaseId, Integer purchaseStatus) throws Exception {
        ReturnMsg msg = new ReturnMsg();
        msg.setSuccess(false);
        msg.setMessage("取消成功！");
        try {
            purchaseService.cancel(purchaseId, purchaseStatus);
            msg.setSuccess(true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            msg.setMessage(e.getMessage());
        }
        return msg;
    }

    /**
     * 查询供应商
     *
     * @param
     * @return
     */
    @RequestMapping("getSupply")
    @ResponseBody
    public List<IotSupply> getSupply() {
        PageInfo<IotSupply> supplierList = suppplierService.getAllSupply();
        return supplierList.getList();
    }

    /**
     * 查询流量产品
     *
     * @param
     * @return
     */
    @RequestMapping("getProduct")
    @ResponseBody
    public List<IotProduct> getProduct() {
        PageInfo<IotProduct> productList = iotProductService.getAllProduct();
        return productList.getList();
    }
}
