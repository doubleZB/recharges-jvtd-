package com.jtd.recharge.service.iot;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.mapper.IotCardMapper;
import com.jtd.recharge.dao.po.IotCard;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.CardStatus;
import com.jtd.recharge.define.SaleStatus;

/**
 * 物联网卡
 * 
 * @author ninghui
 *
 */
@Service
public class CardService {
	@Resource
	IotCardMapper iotCardMapper;
	
	public int coutByCondition(IotCard card) {
		return iotCardMapper.coutByCondition(card);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int add(IotCard card) {
		return iotCardMapper.insertSelective(card);
	}
	
	public int deleteByInReceiptId(Integer inReceiptId) {
		return iotCardMapper.deleteByInReceiptId(inReceiptId);
	}

	public List<IotCard>  transformation(List<IotCard> iotCards){
        for (IotCard item : iotCards) {
            item.setStatusLiteral(CardStatus.parse(item.getStatus()).name());
            item.setCardSizeLiteral(CardSize.parse(item.getCardSize()).name());
            item.setOperatorLiteral(CardOperator.parse(item.getOperator()).name());
            item.setSaleStatusLiteral(SaleStatus.parse(item.getSaleStatus()).name());
        }
        return iotCards;
    }

	public PageInfo<IotCard> selectByCondition(Integer pageNumber, Integer pageSize, IotCard card) {
		PageHelper.startPage(pageNumber, pageSize);
		List<IotCard> list = iotCardMapper.selectByCondition(card);
		this.transformation(list);
		PageInfo<IotCard> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	/**
	 * 根据查询条件进行卡出库
	 * @param card
	 * @return
	 */
	public int outByCondition(IotCard card) {
		return iotCardMapper.outByCondition(card);
	}
	
	/**
	 * 根据查询条件进行卡出库
	 * @param
	 * @return
	 */
	public int outByCardIdList(Map<String,Object> params) {
		return iotCardMapper.outByCardIdList(params);
	}

	/**
	 * 获取单个卡信息用于查看
	 * @param iotCard
	 * @return
	 */
	public IotCard selectOneByCondition(IotCard iotCard) {
		List<IotCard> iotCards = iotCardMapper.selectByCondition(iotCard);
			return this.transformation(iotCards).get(0);
	}

	/**
	 * 根据出库单的id获取卡信息详情
	 * @param outReceiptId
	 * @return
	 */
	public List<IotCard> selectByOutReceiptId(Integer outReceiptId) {
		IotCard iotCard = new IotCard();
		iotCard.setOutReceiptId(outReceiptId);
        List<IotCard> iotCards = iotCardMapper.selectByCondition(iotCard);
        return this.transformation(iotCards);
    }
	/**
	 * 根据出库单编号统计总成本
	 * @param outReceiptId
	 */
	public Map<String,BigDecimal> sumCostAndPrice(Integer outReceiptId) {
		return iotCardMapper.sumCostAndPrice(outReceiptId);
	}
    /**
     * 根据出库单取消出库
     * @param outReceiptId
     */
	public int refundByOutReceiptId(Integer outReceiptId) {
		Map<String,Object> params = new HashMap<>();
		params.put("outReceiptId", outReceiptId);
		params.put("saleStatus", SaleStatus.待售.getValue());
		params.put("currentSaleStatus", SaleStatus.已售.getValue());
		return iotCardMapper.refundByOutReceiptId(params);
	}

	/**
	 * 根据入库单id获取卡信息详情
	 * @param inReceiptId
	 * @return
	 */
	public List<IotCard> selectByInReceiptId(Integer inReceiptId) {
		IotCard iotCard = new IotCard();
		iotCard.setInReceiptId(inReceiptId);
		List<IotCard> iotCards = iotCardMapper.selectByCondition(iotCard);
		return this.transformation(iotCards);
	}

	public List<IotCard> selectByOutProductId(IotCard iotCard) {
		List<IotCard> iotCards = iotCardMapper.selectByCondition(iotCard);
		return this.transformation(iotCards);
	}

	/**
	 * 库存查询列表数据
	 * @param pageNumber
	 * @param pageSize
	 * @param card
	 * @return
	 */
    public PageInfo<IotCard> selectByCard(Integer pageNumber, Integer pageSize, IotCard card) {
		PageHelper.startPage(pageNumber, pageSize, "ca.create_time desc");
		List<IotCard> list = iotCardMapper.selectByCard(card);
		this.transformation(list);
		PageInfo<IotCard> pageInfo = new PageInfo<>(list);
		return pageInfo;
    }

	public List<IotCard> selectById(IotCard card) {
		List<IotCard> list = iotCardMapper.selectByCard(card);
		this.transformation(list);
		return list;
	}
	
	public PageInfo<IotCard> selectForSyncBySupplyId(Integer pageNumber, Integer pageSize, Integer supplyId) {
		PageHelper.startPage(pageNumber, pageSize, "ca.update_time");
		return iotCardMapper.selectForSyncBySupplyId(supplyId);
	}

	public Integer countByPurchaseId(Integer purchaseId) {
		return iotCardMapper.countByPurchaseId(purchaseId);
	}

	public Integer countByInReceiptId(Integer inReceiptId) {
		return iotCardMapper.countByInReceiptId(inReceiptId);
	}
}
