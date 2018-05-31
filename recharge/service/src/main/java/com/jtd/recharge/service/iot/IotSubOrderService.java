package com.jtd.recharge.service.iot;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.jtd.recharge.dao.mapper.IotSubOrderMapper;
import com.jtd.recharge.dao.po.IotOrder;
import com.jtd.recharge.dao.po.IotOutReceipt;
import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.dao.po.IotPurchase;
import com.jtd.recharge.dao.po.IotSubOrder;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.IotOrderStatus;
import com.jtd.recharge.define.OutReceiptStatus;
import com.jtd.recharge.define.PurchaseStatus;
import com.jtd.recharge.define.SerialNum;

/**
 * Created by ${zyj} on 2018/3/23.
 */
@Service
public class IotSubOrderService {
	@Resource
	private IotSubOrderMapper iotSubOrderMapper;
	@Resource
	private IotOrderService iotOrderService;
	@Resource
	private IotProductService iotProductService;
	@Resource
	private UserBalanceService userBalanceService;
	@Resource
	private OutReceiptService outReceiptService;
	@Resource
	private CardService cardService;
	@Resource
	PurchaseService purchaseService;

	/**
	 * @param iotSubOrder
	 * @param adminUserId
	 */
	public Integer addIotOrderAndSubOrder(IotSubOrder iotSubOrder, Integer adminUserId) {
		IotProduct product = iotProductService.getProductById(iotSubOrder.getFlowProductId());
		IotOrder iotOrder = new IotOrder();
		iotOrder.setUserId(iotSubOrder.getUserId());
		iotOrder.setCreaterId(adminUserId);
		iotOrder.setAmount(iotSubOrder.getPriceDiscount().multiply(product.getPrice())
				.multiply(new BigDecimal(iotSubOrder.getTotal())));
		iotOrderService.addIotOrder(iotOrder);
		if (iotOrder.getId() == null) {
			throw new IllegalArgumentException("创建父订单失败");
		}

		iotSubOrder.setOperator(product.getOperator());
		iotSubOrder.setParentId(iotOrder.getId());
		iotSubOrder.setStatus(IotOrderStatus.待审核.getValue());
		iotSubOrder.setCreaterId(adminUserId);
		iotSubOrder.setPriceDiscount(iotSubOrder.getPriceDiscount());
		iotSubOrder.setPrice(iotSubOrder.getPriceDiscount().multiply(product.getPrice()));
		iotSubOrder.setAmount(iotSubOrder.getPrice().multiply(new BigDecimal(iotSubOrder.getTotal())));
		return this.addIotSubOrder(iotSubOrder);
	}

	public Integer addIotSubOrder(IotSubOrder iotSubOrder) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String odsSerialNum = SerialNum.子订单.getPrefix() + sdf.format(new Date()).toUpperCase();
		iotSubOrder.setSerialNum(odsSerialNum);
		return iotSubOrderMapper.insertSelective(iotSubOrder);
	}

	public List<IotSubOrder> selectOrderList(Integer pageNumber, Integer pageSize, IotSubOrder sub) {
		PageHelper.startPage(pageNumber, pageSize, "iso.create_time desc");
		List<IotSubOrder> selectOrderList = iotSubOrderMapper.selectOrderList(sub);
		for (IotSubOrder iotSubOrder : selectOrderList) {
			iotSubOrder.setOperatorLiteral(CardOperator.parse(iotSubOrder.getOperator()).name());
			iotSubOrder.setStatusLiteral(IotOrderStatus.parse(iotSubOrder.getStatus()).name());
			iotSubOrder.setCardSizeLiteral(CardSize.parse(iotSubOrder.getSize()).name());
		}
		return selectOrderList;
	}

	/**
	 * 根据子订单编号查询子订单
	 *
	 * @param serialNum
	 * @return
	 */
	public IotSubOrder getToOutBySerialNum(String serialNum) {
		Map<String, Object> params = new HashMap<>();
		params.put("serialNum", serialNum);
		IotSubOrder result = iotSubOrderMapper.selectBySerialNum(params);
		if (result != null) {
			result.setCardSizeLiteral(CardSize.parse(result.getSize()).name());
			result.setStatusLiteral(IotOrderStatus.parse(result.getStatus()).name());
		}
		return result;
	}

	/**
	 * 根据父订单编号查询待出库子订单
	 *
	 * @param serialNum
	 * @return
	 */
	public IotSubOrder getToOutByParentSerialNum(String serialNum) {
		Map<String, Object> params = new HashMap<>();
		params.put("parentSerialNum", serialNum);
		List<Integer> statusList = new ArrayList<>();
		statusList.add(IotOrderStatus.待出库.getValue());
		statusList.add(IotOrderStatus.待处理.getValue());
		params.put("statusList", statusList);
		IotSubOrder result = iotSubOrderMapper.selectBySerialNum(params);
		if (result != null) {
			result.setCardSizeLiteral(CardSize.parse(result.getSize()).name());
			result.setStatusLiteral(IotOrderStatus.parse(result.getStatus()).name());
		}
		return result;
	}

	public IotSubOrder getById(Integer id) {
		IotSubOrder result = iotSubOrderMapper.selectByPrimaryKey(id);
		return result;
	}

	/**
	 * 带乐观锁机制的订单状态更新
	 * 
	 * @param iotSubOrder
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Integer safeUpdateIotSubOrderById(IotSubOrder iotSubOrder) {
		this.changeOrderStatus(iotSubOrder);
		return this.updateIotSubOrderById(iotSubOrder);
	}

	public Integer updateIotSubOrderById(IotSubOrder iotSubOrder) {
		return iotSubOrderMapper.updateByPrimaryKeySelective(iotSubOrder);
	}

	public IotSubOrder selectById(Integer iotSubOrderId) {
		return iotSubOrderMapper.selectByPrimaryKey(iotSubOrderId);
	}

	public IotSubOrder selectDetailById(IotSubOrder sub) {
		IotSubOrder iotSubOrder = iotSubOrderMapper.selectOrderList(sub).get(0);
		if (iotSubOrder != null) {
			iotSubOrder.setCardSizeLiteral(CardSize.parse(iotSubOrder.getSize()).name());
			iotSubOrder.setStatusLiteral(IotOrderStatus.parse(iotSubOrder.getStatus()).name());
		}
		return iotSubOrder;
	}

	public void changeOrderStatus(IotSubOrder sub) {
		if (sub.getCurrentStatus() == null) {
			throw new IllegalArgumentException("请指定订单当前状态");
		}
		Integer result = iotSubOrderMapper.changeOrderStatus(sub);
		if (result <= 0) {
			throw new IllegalStateException(
					"将订单" + sub.getId() + "从" + sub.getCurrentStatus() + "更新到" + sub.getStatus() + "状态失败");
		}
	}

	/**
	 * 订单已配货
	 */
	public void picked(Integer iotSubOrderId) {
		IotSubOrder subOrder = new IotSubOrder();
		subOrder.setStatus(IotOrderStatus.已完成.getValue());
		subOrder.setCurrentStatus(IotOrderStatus.待配货.getValue());
		subOrder.setId(iotSubOrderId);
		this.changeOrderStatus(subOrder);
		subOrder = this.getById(iotSubOrderId);
		List<IotOutReceipt> outReceiptList = outReceiptService.selectBySubOrderId(iotSubOrderId);
		for (IotOutReceipt outReceipt : outReceiptList) {
			if (outReceipt.getOutReceiptStatus().equals(OutReceiptStatus.待配货.getValue())) {
				outReceipt.setCurrentOutReceiptStatus(outReceipt.getOutReceiptStatus());
				outReceipt.setOutReceiptStatus(OutReceiptStatus.已完成.getValue());
				outReceiptService.changeStatus(outReceipt);
			}
		}

	}

	/**
	 * 退款操作
	 *
	 * @param iotSubOrderId
	 * @param currentStatus
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateRefund(Integer iotSubOrderId, Integer currentStatus) {
		if(currentStatus.equals(IotOrderStatus.已取消.getValue()) || currentStatus.equals(IotOrderStatus.已完成.getClass())) {
			throw new IllegalStateException("订单处于["+IotOrderStatus.parse(currentStatus).name()+"]状态,无法退款");
		}
		if (currentStatus.equals(IotOrderStatus.待入库.getValue())) {
			List<IotPurchase> purchaseList = purchaseService.selectBySubOrderId(iotSubOrderId);
			for (IotPurchase purchaseItem : purchaseList) {
				if (!purchaseItem.getPurchaseStatus().equals(PurchaseStatus.已入库.getValue())
						&& !purchaseItem.getPurchaseStatus().equals(PurchaseStatus.已取消.getValue())) {
					purchaseItem.setCurrentPurchaseStatus(purchaseItem.getPurchaseStatus());
					purchaseItem.setPurchaseStatus(PurchaseStatus.已取消.getValue());
					purchaseService.changePurchaseStatus(purchaseItem);
				}
			}
		} else if (currentStatus.equals(IotOrderStatus.待配货.getValue())) {
			List<IotOutReceipt> outReceiptList = outReceiptService.selectBySubOrderId(iotSubOrderId);
			for (IotOutReceipt outReceiptItem : outReceiptList) {
				if (outReceiptItem.getOutReceiptStatus().equals(OutReceiptStatus.待配货.getValue())) {
					int totalResult = cardService.refundByOutReceiptId(outReceiptItem.getId());
					if (totalResult != outReceiptItem.getTotal()) {
						throw new IllegalStateException("出库单[" + outReceiptItem.getSerialNum() + "]已取消出库的卡数量["
								+ totalResult + "]与出库的卡数量[" + outReceiptItem.getTotal() + "]不一致");
					}
					outReceiptItem.setCurrentOutReceiptStatus(OutReceiptStatus.待配货.getValue());
					outReceiptItem.setOutReceiptStatus(OutReceiptStatus.已取消.getValue());
					outReceiptService.changeStatus(outReceiptItem);
				}
			}
		}
		IotSubOrder iotSubOrder = this.getById(iotSubOrderId);
		IotOrder order =  iotOrderService.getById(iotSubOrder.getParentId());
		userBalanceService.refund(iotSubOrder.getUserId(), iotSubOrder.getAmount(), order.getSerialNum());
		IotSubOrder subOrder = new IotSubOrder();
		subOrder.setCurrentStatus(currentStatus);
		subOrder.setId(iotSubOrderId);
		subOrder.setStatus(IotOrderStatus.已退款.getValue());
		this.changeOrderStatus(subOrder);
	}

	/**
	 * 支付操作后的扣款
	 *
	 * @param iotSubOrderId
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updatePayment(Integer iotSubOrderId) {
		IotSubOrder iotSubOrder = this.getById(iotSubOrderId);
		IotSubOrder subOrder = new IotSubOrder();
		subOrder.setStatus(IotOrderStatus.待处理.getValue());
		subOrder.setCurrentStatus(IotOrderStatus.待加款.getValue());
		subOrder.setId(iotSubOrderId);
		this.changeOrderStatus(subOrder);
		BigDecimal feeAmount = iotSubOrder.getAmount();
		IotOrder order =  iotOrderService.getById(iotSubOrder.getParentId());
		userBalanceService.updateBaleanceById(iotSubOrder.getUserId(), feeAmount, order.getSerialNum());// 更新用户的可用余额
	}

	/**
	 * 审核操作中的扣款
	 * 
	 * @param iotSubOrderId
	 */
	public void updateAudit(Integer iotSubOrderId) {
		IotSubOrder iotSubOrder = this.getById(iotSubOrderId);
		IotSubOrder subOrder = new IotSubOrder();
		subOrder.setStatus(IotOrderStatus.待处理.getValue());
		subOrder.setCurrentStatus(IotOrderStatus.待审核.getValue());
		subOrder.setId(iotSubOrderId);
		this.changeOrderStatus(subOrder);
		BigDecimal feeAmount = iotSubOrder.getAmount();
		IotOrder order =  iotOrderService.getById(iotSubOrder.getParentId());
		userBalanceService.updateBaleanceById(iotSubOrder.getUserId(), feeAmount, order.getSerialNum());// 更新用户的可用余额
	}


	public List<IotSubOrder> getByProductId(IotSubOrder iotSubOrder) {
		return iotSubOrderMapper.selectOrderList(iotSubOrder);
	}
}
