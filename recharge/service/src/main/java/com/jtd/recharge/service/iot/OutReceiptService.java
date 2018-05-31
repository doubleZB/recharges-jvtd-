package com.jtd.recharge.service.iot;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.mapper.IotOutReceiptMapper;
import com.jtd.recharge.dao.po.IotCard;
import com.jtd.recharge.dao.po.IotOutReceipt;
import com.jtd.recharge.dao.po.IotSubOrder;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.IotOrderStatus;
import com.jtd.recharge.define.OutReceiptStatus;
import com.jtd.recharge.define.SaleStatus;
import com.jtd.recharge.define.SerialNum;

/**
 * 出库单服务
 * @author ninghui
 *
 */
@Service
public class OutReceiptService {
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource
	IotOutReceiptMapper iotOutReceiptMapper;
	@Resource
	CardService cardService;
	@Resource
	IotSubOrderService  iotSubOrderService;
	
	public PageInfo<IotOutReceipt> selectByCondition(Integer pageNumber, Integer pageSize, IotOutReceipt outReceipt) {
		PageHelper.startPage(pageNumber, pageSize, "update_time desc");
		List<IotOutReceipt> list = iotOutReceiptMapper.selectByCondition(outReceipt);
		for (IotOutReceipt item : list) {
			item.setOutReceiptStatusLiteral(OutReceiptStatus.parse(item.getOutReceiptStatus()).name());
			item.setCardSizeLiteral(CardSize.parse(item.getCardSize()).name());
		}
		PageInfo<IotOutReceipt> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	/**
	 * 修改出库单状态
	 * @param outReceipt
	 */
	public void changeStatus(IotOutReceipt outReceipt) {
		if(outReceipt.getCurrentOutReceiptStatus() == null) {
			throw new IllegalArgumentException("未指定出库单当前状态");
		}
		int result = iotOutReceiptMapper.changeStatus(outReceipt);
		if(result <= 0) {
			throw new IllegalArgumentException("修改出库单状态失败");
		}
	}
	/**
	 * 已配货
	 * @param outReceipt
	 */
	public void picked(Integer outReceiptId) {
		IotOutReceipt outReceipt = new IotOutReceipt();
		outReceipt.setId(outReceiptId);
		outReceipt.setCurrentOutReceiptStatus(OutReceiptStatus.待配货.getValue());
		outReceipt.setOutReceiptStatus(OutReceiptStatus.已完成.getValue());
		this.changeStatus(outReceipt);
		outReceipt = iotOutReceiptMapper.selectByPrimaryKey(outReceiptId);
		IotSubOrder subOrder = iotSubOrderService.getById(outReceipt.getSubOrderId());
		subOrder.setCurrentStatus(IotOrderStatus.待配货.getValue());
		subOrder.setStatus(IotOrderStatus.已完成.getValue());
		iotSubOrderService.changeOrderStatus(subOrder);
	}
	
	 /**
     * 新增采购单
     * @param outReceipt
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Integer add(IotOutReceipt outReceipt) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    	outReceipt.setSerialNum(SerialNum.出库单.getPrefix()+sdf.format(new Date()).toUpperCase());
    	return iotOutReceiptMapper.insertSelective(outReceipt);
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public IotOutReceipt outByCondition(IotSubOrder subOrder,IotOutReceipt outReceipt,IotCard card) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    	outReceipt.setSerialNum(SerialNum.出库单.getPrefix()+sdf.format(new Date()).toUpperCase());
    	outReceipt.setOutReceiptStatus(OutReceiptStatus.待配货.getValue());
    	iotOutReceiptMapper.insertSelective(outReceipt);
		card.setOutReceiptId(outReceipt.getId());
		card.setSaleStatus(SaleStatus.已售.getValue());
		card.setCustomerId(subOrder.getUserId());
		card.setPriceDiscount(subOrder.getPriceDiscount());
		card.setPrice(subOrder.getPrice());
		card.setLimitPriceDiscount(subOrder.getPriceDiscount());
		int count = cardService.outByCondition(card);
		if(count == 0) {
			throw new IllegalArgumentException("未找到符合订单条件的卡");
		}
		logger.info("子订单编号:"+subOrder.getSerialNum()+" 出库数量:"+count);
		checkForSplit(outReceipt,subOrder, count);
		return outReceipt;
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public IotOutReceipt outByCardIdList(IotSubOrder subOrder,IotOutReceipt outReceipt,List<Integer> cardIdList) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    	outReceipt.setSerialNum(SerialNum.出库单.getPrefix()+sdf.format(new Date()).toUpperCase());
    	outReceipt.setOutReceiptStatus(OutReceiptStatus.待配货.getValue());
    	iotOutReceiptMapper.insertSelective(outReceipt);
    	if(outReceipt.getId() == null) {
    		throw new IllegalArgumentException("创建出库单失败");
    	}
		Map<String,Object> params = new HashMap<>();
		params.put("outReceiptId", outReceipt.getId());
		params.put("saleStatus", SaleStatus.已售.getValue());
		params.put("customerId", subOrder.getUserId());
		params.put("priceDiscount", subOrder.getPriceDiscount());
		params.put("price", subOrder.getPrice());
		params.put("cardIdList", cardIdList);
		params.put("updateLimit", cardIdList.size());
		params.put("limitPriceDiscount", subOrder.getPriceDiscount());
		int count = cardService.outByCardIdList(params);
		if(count == 0) {
			throw new IllegalArgumentException("未找到符合订单条件的卡");
		}
		logger.info("子订单编号:"+subOrder.getSerialNum()+" 出库数量:"+count+" Id:"+cardIdList.toString());
		checkForSplit(outReceipt,subOrder, count);
		return outReceipt;
    }
    
    
    /**
     * 检查是否需要拆分子订单
     * @param outReceipt 
     * @param subOrder
     * @param count
     */
	private void checkForSplit(IotOutReceipt outReceipt, IotSubOrder subOrder, int count) {
		if(count < subOrder.getTotal()) {
			IotSubOrder newSubOrder = splitSubOrder(subOrder,count);
			iotSubOrderService.addIotSubOrder(newSubOrder);
			if(newSubOrder.getId() == null) {
				throw new IllegalArgumentException("拆分订单失败:"+subOrder.getSerialNum());
			}
			subOrder.setCurrentStatus(subOrder.getStatus());
			subOrder.setStatus(IotOrderStatus.待配货.getValue());
			Integer result = iotSubOrderService.safeUpdateIotSubOrderById(subOrder);
			if(result <= 0) {
				throw new IllegalArgumentException("更新拆分后的订单失败:"+subOrder.getSerialNum());
			}
			logger.info("拆分订单Id:"+newSubOrder.getId()+ " 编号:"+newSubOrder.getSerialNum()+" 待出库数量:"+newSubOrder.getTotal());
		} else {
			IotSubOrder updateSubOrder = new IotSubOrder();
			updateSubOrder.setId(subOrder.getId());
			updateSubOrder.setStatus(IotOrderStatus.待配货.getValue());
			updateSubOrder.setCurrentStatus(subOrder.getStatus());
			iotSubOrderService.changeOrderStatus(updateSubOrder);
		}
		Map<String, BigDecimal> sumCostAndPrice = cardService.sumCostAndPrice(outReceipt.getId());
		BigDecimal totalCost = sumCostAndPrice.get("totalCost");
		BigDecimal totalStdPrice = sumCostAndPrice.get("totalStdPrice");
		outReceipt.setCostDiscount(totalCost.divide(totalStdPrice,3,RoundingMode.HALF_UP));
		outReceipt.setCost(totalCost);
		outReceipt.setTotal(count);
		outReceipt.setAmount(subOrder.getAmount());
		if(totalCost.compareTo(outReceipt.getAmount()) > 0) {
			throw new IllegalArgumentException("订单:"+subOrder.getSerialNum()+"的成本"+totalCost+"大于售价"+subOrder.getAmount()+",出库失败");
		}
		int result = iotOutReceiptMapper.updateByPrimaryKey(outReceipt);
		if(result <= 0) {
			throw new IllegalArgumentException("订单:"+subOrder.getSerialNum()+"更新出库单成本失败");
		}
	}
	/**
	 * 重新计算出库单折扣
	 * @param outReceiptId
	 */
	public void reCalc(Integer outReceiptId) {
		IotOutReceipt outReceipt = selectById(outReceiptId);
		Map<String, BigDecimal> sumCostAndPrice = cardService.sumCostAndPrice(outReceipt.getId());
		BigDecimal totalCost = sumCostAndPrice.get("totalCost");
		BigDecimal totalStdPrice = sumCostAndPrice.get("totalStdPrice");
		outReceipt.setCostDiscount(totalCost.divide(totalStdPrice,3,RoundingMode.HALF_UP));
		outReceipt.setCost(totalCost);
		IotSubOrder subOrder = iotSubOrderService.getById(outReceipt.getSubOrderId());
		outReceipt.setTotal(subOrder.getTotal());
		outReceipt.setAmount(subOrder.getAmount());
		if(totalCost.compareTo(outReceipt.getAmount()) > 0) {
			throw new IllegalArgumentException("订单:"+subOrder.getSerialNum()+"的成本"+totalCost+"大于售价"+subOrder.getAmount()+",出库失败");
		}
		int result = iotOutReceiptMapper.updateByPrimaryKey(outReceipt);
		if(result <= 0) {
			throw new IllegalArgumentException("订单:"+subOrder.getSerialNum()+"更新出库单成本失败");
		}
	}
    /**
     * 拆分子订单
     * @param subOrder
     * @return
     */
    private IotSubOrder splitSubOrder(IotSubOrder subOrder,int finishCount) {
    	IotSubOrder newSubOrder = new IotSubOrder();
    	newSubOrder.setParentId(subOrder.getParentId());
    	newSubOrder.setPriceDiscount(subOrder.getPriceDiscount());
    	newSubOrder.setPrice(subOrder.getPrice());
    	newSubOrder.setCostDiscount(subOrder.getCostDiscount());
    	newSubOrder.setCost(subOrder.getCost());
    	newSubOrder.setIsRecharge(subOrder.getIsRecharge());
    	newSubOrder.setIsSms(subOrder.getIsSms());
    	newSubOrder.setSize(subOrder.getSize());
    	newSubOrder.setOperator(subOrder.getOperator());
    	newSubOrder.setFlowProductId(subOrder.getFlowProductId());
    	newSubOrder.setUserId(subOrder.getUserId());
    	newSubOrder.setCreaterId(subOrder.getCreaterId());
    	newSubOrder.setStatus(subOrder.getStatus());
    	int leftCount = subOrder.getTotal() - finishCount;
    	subOrder.setTotal(finishCount);
    	BigDecimal finishAmount = subOrder.getPrice().multiply(new BigDecimal(finishCount));
    	BigDecimal leftAmount = subOrder.getAmount().subtract(finishAmount);
    	subOrder.setAmount(finishAmount);
    	newSubOrder.setTotal(leftCount);
    	newSubOrder.setAmount(leftAmount);
    	return newSubOrder;
    }

	public List<IotOutReceipt> selectBySubOrderId(Integer iotSubOrderId) {
		return iotOutReceiptMapper.selectBySubOrderId(iotSubOrderId);
	}

	public IotOutReceipt selectById(Integer outReceiptId) {
    	return iotOutReceiptMapper.selectByPrimaryKey(outReceiptId);
	}
}
