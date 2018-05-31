package com.jtd.recharge.service.iot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.mapper.IotPurchaseMapper;
import com.jtd.recharge.dao.po.IotPurchase;
import com.jtd.recharge.dao.po.IotSubOrder;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.IotOrderStatus;
import com.jtd.recharge.define.PurchaseStatus;
import com.jtd.recharge.define.SerialNum;

/**
 * 采购单服务
 * 
 * @author ninghui
 *
 */
@Service
public class PurchaseService {
	@Resource
	IotPurchaseMapper purchaseMapper;
	@Resource
	IotSubOrderService iotSubOrderService;
	

	public PageInfo<IotPurchase> find(Integer pageNumber, Integer pageSize, IotPurchase purchaser) {
		PageHelper.startPage(pageNumber, pageSize, "update_time desc");
		List<IotPurchase> list = purchaseMapper.selectPurchaseList(purchaser);
		for (IotPurchase item : list) {
			item.setCardSizeLiteral(CardSize.parse(item.getCardSize()).name());
			item.setPurchaseStatusLiteral(PurchaseStatus.parse(item.getPurchaseStatus()).name());
		}
		PageInfo<IotPurchase> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	public IotPurchase getById(Integer id) {
		IotPurchase result = purchaseMapper.selectByPrimaryKey(id);
		if(result != null) {
			result.setPurchaseStatusLiteral(PurchaseStatus.parse(result.getPurchaseStatus()).name());
		}
		return result;
	}
	
	public List<IotPurchase> selectBySubOrderId(Integer subOrderId) {
		return purchaseMapper.selectBySubOrderId(subOrderId);
	}

	public IotPurchase getBySerialNum(String serialNum) {
		IotPurchase pur = purchaseMapper.selectBySerialNum(serialNum);
		if(pur == null) {
			return null;
		}
		pur.setPurchaseStatusLiteral(PurchaseStatus.parse(pur.getPurchaseStatus()).name());
		pur.setCardSizeLiteral(CardSize.parse(pur.getCardSize()).name());
		return pur;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void safeAdd(IotPurchase purchase, IotSubOrder iotSubOrder) {
		 iotSubOrderService.changeOrderStatus(iotSubOrder);
		 this.add(purchase);
	}
	/**
	 * 新增采购单
	 * 
	 * @param purchase
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Integer add(IotPurchase purchase) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		purchase.setSerialNum(SerialNum.采购单.getPrefix() + sdf.format(new Date()).toUpperCase());
		purchase.setPurchaseStatus(PurchaseStatus.待审核.getValue());
		return purchaseMapper.insertSelective(purchase);
	}

	/**
	 * 修改采购单
	 * 
	 * @param purchase
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int update(IotPurchase purchase) {
		return purchaseMapper.updateByPrimaryKeySelective(purchase);
	}
	
	/**
	 * 修改采购单
	 * 
	 * @param purchase
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int safeUpdate(IotPurchase purchase) {
		this.changePurchaseStatus(purchase);
		return purchaseMapper.updateByPrimaryKeySelective(purchase);
	}
	
	public void changePurchaseStatus(IotPurchase purchase) {
		if(purchase.getCurrentPurchaseStatus() == null) {
			throw new IllegalArgumentException("未设置采购单当前状态");
		}
		int result = purchaseMapper.changePurchaseStatus(purchase);
		if(result <= 0) {
			throw new IllegalStateException("修改采购单状态失败");
		}
	}
	/**
	 * 取消采购单
	 * @param currentStatus 
	 * 
	 * @param purchase
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void cancel(Integer purchaseId, Integer currentStatus) {
		IotPurchase purchase = purchaseMapper.selectByPrimaryKey(purchaseId);
		if(purchase.getSubOrderId() != null) {
			IotSubOrder subOrder = iotSubOrderService.selectById(purchase.getSubOrderId());
			subOrder.setCurrentStatus(IotOrderStatus.待入库.getValue());
			subOrder.setStatus(IotOrderStatus.待处理.getValue());
			iotSubOrderService.changeOrderStatus(subOrder);
		}
		purchase.setPurchaseStatus(PurchaseStatus.已取消.getValue());
		purchase.setCurrentPurchaseStatus(currentStatus);
		this.changePurchaseStatus(purchase);
	}

	/**
	 * 根据订单的id查询采购单信息
	 * @param iotPurchase
	 * @return
	 */
	public List<IotPurchase> getPurchaseByOrderId(IotPurchase iotPurchase) {
		List<IotPurchase> iotPurchases = purchaseMapper.selectPurchaseList(iotPurchase);
		for (IotPurchase iot:iotPurchases) {
			iot.setPurchaseStatusLiteral(PurchaseStatus.parse(iot.getPurchaseStatus()).name());
			iot.setCardSizeLiteral(CardSize.parse(iot.getCardSize()).name());
		}
		return iotPurchases;
	}
}
