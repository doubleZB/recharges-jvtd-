package com.jtd.recharge.dao.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.po.IotCard;

public interface IotCardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(IotCard record);

    int insertSelective(IotCard record);

    IotCard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(IotCard record);

    int updateByPrimaryKey(IotCard record);

	int deleteByInReceiptId(Integer inReceiptId);

	List<IotCard> selectByCondition(IotCard card);
    /**
     * 根据查询条件出库
     * @param card
     * @return
     */
	int outByCondition(IotCard card);
    /**
     * 根据卡Id出库
     * @param params
     * @return
     */
	int outByCardIdList(Map<String,Object> params);
    /**
     * 根据查询条件统计
     * @param card
     * @return
     */
	int coutByCondition(IotCard card);
	/**
	 * 根据出库单编号统计总成本
	 * @param outReceiptId
	 */
	Map<String, BigDecimal> sumCostAndPrice(Integer outReceiptId);
	/**
	 * 取消出库
	 * @param params
	 */
	int refundByOutReceiptId(Map<String, Object> params);

	/**
	 * 查询库存数据
	 * @param card
	 * @return
	 */
    List<IotCard> selectByCard(IotCard card);
    /**
	 * 根据运营商编号查询卡信息(仅供状态同步使用,没有获取iot_card表的全部字段)
	 * @param pageNumber
	 * @param pageSize
	 * @param supplyId
	 * @return
	 */
	PageInfo<IotCard> selectForSyncBySupplyId(Integer supplyId);
    /**
     * 统计采购单已入库的卡数量
     * @param purchaseId
     * @return
     */
	Integer countByPurchaseId(Integer purchaseId);
	/**
     * 统计入库单已入库的卡数量
     * @param purchaseId
     * @return
     */
	Integer countByInReceiptId(Integer inReceiptId);

}