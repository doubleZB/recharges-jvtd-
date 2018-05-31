package com.jtd.recharge.action.iot;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.dao.po.IotPurchase;
import com.jtd.recharge.dao.po.IotSupply;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.PurchaseStatus;
import com.jtd.recharge.service.iot.IotProductService;
import com.jtd.recharge.service.iot.PurchaseService;
import com.jtd.recharge.service.iot.SuppplierService;

/**
 * 采购单管理
 * @author ninghui
 *
 */
@Controller
@RequestMapping("/iot/purchaseAudit")
public class PurchaseAuditAction {
	 private Logger logger = Logger.getLogger(this.getClass());
	 @Resource
	 public PurchaseService purchaseService;
	 @Resource
	 public SuppplierService suppplierService;
	 @Resource
	 public IotProductService iotProductService;
	/**
     * 到应用页面
     * @return
     */
    @RequestMapping("index")
    public ModelAndView  index(HttpServletRequest request) throws UnsupportedEncodingException {
    	ModelAndView mav = new ModelAndView("/iot/purchaseAudit");
    	mav.addObject("purchaseStateList", PurchaseStatus.values());
    	mav.addObject("operatorList", CardOperator.values());
    	mav.addObject("cardSizeList", CardSize.values());
    	return mav;
    }
    
    @RequestMapping("list")
    @ResponseBody
    public Object list(Integer pageNumber, Integer pageSize, IotPurchase purchase){
    	purchase.setPurchaseStatus(PurchaseStatus.待审核.getValue());
        PageInfo<IotPurchase> list = purchaseService.find(pageNumber, pageSize, purchase);
        return list;
    }
    
    /**
     * 去修改
     * @return
     */
    @RequestMapping("/toUpdate")
    @ResponseBody
    public IotPurchase toUpdate(Integer id){
    	return purchaseService.getById(id);
    }
    
    /**
     * 审核
     * @return
     */
    @RequestMapping("/audit")
    @ResponseBody
    public ReturnMsg audit(HttpServletRequest request,Integer id,Integer result,String remark) throws Exception {
    	 ReturnMsg msg = new ReturnMsg();
         msg.setSuccess(false);
         msg.setMessage("审核成功！");
         try {
        	 IotPurchase toUpdatePurchase = purchaseService.getById(id);
        	 if(result == 1) {
        		 toUpdatePurchase.setPurchaseStatus(PurchaseStatus.待入库.getValue());
        	 }else {
        		 toUpdatePurchase.setPurchaseStatus(PurchaseStatus.已驳回.getValue());
        	 }
        	 toUpdatePurchase.setCurrentPurchaseStatus(PurchaseStatus.待审核.getValue());
        	 toUpdatePurchase.setRemark(remark);
        	 purchaseService.safeUpdate(toUpdatePurchase);
	    	 msg.setSuccess(true);
         }catch(Exception e) {
        	 logger.error(e.getMessage(),e);
        	 msg.setMessage(e.getMessage());
         }
    	 return msg;
    }
    
    /**
     * 查询供应商
     * @param supplyName
     * @return
     */
    @RequestMapping("getSupply")
    @ResponseBody
    public  List<IotSupply> getSupply(){
         PageInfo<IotSupply> supplierList = suppplierService.getAllSupply();
         return  supplierList.getList();
    }
    
    /**
     * 查询流量产品
     * @param supplyName
     * @return
     */
    @RequestMapping("getProduct")
    @ResponseBody
    public  List<IotProduct> getProduct(){
         PageInfo<IotProduct> productList = iotProductService.getAllProduct();
         return  productList.getList();
    }
    
}
