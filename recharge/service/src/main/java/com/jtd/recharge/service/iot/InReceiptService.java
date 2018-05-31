package com.jtd.recharge.service.iot;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jtd.recharge.dao.mapper.IotInReceiptMapper;
import com.jtd.recharge.dao.po.AdminUser;
import com.jtd.recharge.dao.po.IotCard;
import com.jtd.recharge.dao.po.IotInReceipt;
import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.dao.po.IotPurchase;
import com.jtd.recharge.dao.po.IotSubOrder;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.CardStatus;
import com.jtd.recharge.define.InReceiptStatus;
import com.jtd.recharge.define.IotOrderStatus;
import com.jtd.recharge.define.PurchaseStatus;
import com.jtd.recharge.define.SaleStatus;
import com.jtd.recharge.define.SerialNum;

/**
 * 入库单服务
 * 
 * @author ninghui
 *
 */
@Service
public class InReceiptService {
	@Resource
	IotInReceiptMapper iotInReceiptMapper;
	@Resource
	PurchaseService purchaseService;
	@Resource
	public CardService cardService;
	@Resource
	public IotSubOrderService iotSubOrderService;

	public PageInfo<IotInReceipt> selectByCondition(Integer pageNumber, Integer pageSize, IotInReceipt inReceipt) {
		PageHelper.startPage(pageNumber, pageSize, "update_time desc");
		List<IotInReceipt> list = iotInReceiptMapper.selectByCondition(inReceipt);
		for (IotInReceipt item : list) {
			item.setInReceiptStatusLiteral(InReceiptStatus.parse(item.getInReceiptStatus()).name());
			item.setCardSizeLiteral(CardSize.parse(item.getCardSize()).name());
		}
		PageInfo<IotInReceipt> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	/**
	 * 新增采购单
	 * 
	 * @param inReceipt
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Integer add(IotInReceipt inReceipt) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		inReceipt.setSerialNum(SerialNum.入库单.getPrefix() + sdf.format(new Date()).toUpperCase());
		return iotInReceiptMapper.insertSelective(inReceipt);
	}
	/**
	 * 修改采购单状态
	 * @param inReceipt
	 * @return
	 */
	public Integer changeStatus(IotInReceipt inReceipt) {
		if(inReceipt.getCurrentInReceiptStatus() == null) {
			throw new IllegalArgumentException("未指定入库单当前状态");
		}else if(inReceipt.getInReceiptStatus().equals(inReceipt.getCurrentInReceiptStatus())) {
			throw new IllegalArgumentException("前后状态相同");
		}
		return iotInReceiptMapper.changeStatus(inReceipt);
	}

	public IotInReceipt getBySerialNum(String serialNum) {
		return iotInReceiptMapper.selectBySerialNum(serialNum);
	}

	/**
	 * 修改入库单
	 * 
	 * @param inReceipt
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public int update(IotInReceipt inReceipt) {
		return iotInReceiptMapper.updateByPrimaryKeySelective(inReceipt);
	}
    /**
     * 卡入库
     * @param importExcel
     * @param purchase
     * @param product
     * @param inReceipt
     * @param user
     * @throws InvalidFormatException
     * @throws IOException
     */
	public void parseExcel(MultipartFile importExcel, IotPurchase purchase, IotProduct product, IotInReceipt inReceipt,
			AdminUser user) throws InvalidFormatException, IOException {
		//不允许更新的字段
		inReceipt.setSerialNum(null);
		inReceipt.setCreaterId(null);
		IotInReceipt lastInReceipt = iotInReceiptMapper.selectByPurchaseId(purchase.getId());
		if(lastInReceipt == null) {
			inReceipt.setPurchaseId(purchase.getId());
			inReceipt.setTotal(0);
			inReceipt.setCreaterId(user.getId());
			inReceipt.setInReceiptStatus(InReceiptStatus.入库中.getValue());
			this.add(inReceipt);
		}else  {
			inReceipt.setId(lastInReceipt.getId());
			inReceipt.setTotal(lastInReceipt.getTotal());
			inReceipt.setInReceiptStatus(lastInReceipt.getInReceiptStatus());
			if(inReceipt.getTotal() >= purchase.getTotal()) {
				throw new IllegalStateException("采购单["+purchase.getSerialNum()+"]已入库完成，无法再次入库");
			}
			inReceipt.setCurrentInReceiptStatus(inReceipt.getInReceiptStatus());
			inReceipt.setInReceiptStatus(InReceiptStatus.入库中.getValue());
			if(changeStatus(inReceipt) <= 0) {
				throw new IllegalStateException("修改入库单状态失败");
			}
		}
		Workbook workbook = WorkbookFactory.create(importExcel.getInputStream());
		// 获取工作表
		Sheet sheet = workbook.getSheetAt(0);
		int lastRowNum = sheet.getLastRowNum();
		int excelTotal = lastRowNum + 1 - 2;
		if (excelTotal > purchase.getTotal()) {
			throw new IllegalArgumentException("上传的卡数量["+excelTotal+"]大于采购数量["+purchase.getTotal()+"]，请核对");
		}
		int metaRowIndex = 1;
		Row row = sheet.getRow(metaRowIndex);
		short lastCellNum = row.getLastCellNum();
		Map<String, Integer> metaData = new HashMap<>();
		for (int cellIndex = 0; cellIndex < lastCellNum; cellIndex++) {
			metaData.put(row.getCell(cellIndex).getStringCellValue(), cellIndex);
		}
		int dataBeginIndex = 2;
		//已入库数量
		Integer currentCount = cardService.countByPurchaseId(purchase.getId());
		for (int rowIndex = dataBeginIndex; rowIndex <= lastRowNum; rowIndex++) {
			if(currentCount >= purchase.getTotal()) {
				inReceipt.setTotal(currentCount);
				finishInReceipt(purchase, inReceipt);
				throw new IllegalArgumentException("上传的卡数量超出采购单采购的卡数量");
			}
			Row dataRow = sheet.getRow(rowIndex);
			Cell cell = dataRow.getCell(metaData.get("iccid"));
			IotCard card = new IotCard();
			cell.setCellType(Cell.CELL_TYPE_STRING);
			String iccid = cell.getStringCellValue().trim();
			if (StringUtils.isBlank(iccid)) {
				break;
			}
			card.setIccid(iccid);

			cell = dataRow.getCell(metaData.get("msisdn"));
			cell.setCellType(Cell.CELL_TYPE_STRING);
			String msisdn = cell.getStringCellValue().trim();
			if (StringUtils.isBlank(msisdn)) {
				throw new IllegalArgumentException("第" + rowIndex + "行 msisdn 不能为空!");
			}
			card.setMsisdn(msisdn);
			card.setOperator(product.getOperator());
			card.setStatus(CardStatus.未知.getValue());
			card.setSaleStatus(SaleStatus.待售.getValue());
			card.setPurchaseId(purchase.getId());
			card.setInReceiptId(inReceipt.getId());
			card.setCardSize(purchase.getCardSize());
			card.setSupplyId(purchase.getSupplyId());
			card.setIsRecharge(purchase.getIsRecharge());
			card.setIsSms(purchase.getIsSms());
			card.setCostDiscount(inReceipt.getCostDiscount());
			card.setCost(inReceipt.getCost());
			card.setFlowProductId(purchase.getFlowProductId());
			if(cardService.add(card) > 0) {
				currentCount++;
			}
		}
		currentCount = cardService.countByInReceiptId(inReceipt.getId());
		inReceipt.setTotal(currentCount);
		if(currentCount < purchase.getTotal()) {
			inReceipt.setInReceiptStatus(InReceiptStatus.未完成.getValue());
			this.update(inReceipt);
		}else if (currentCount >= purchase.getTotal()) {
			finishInReceipt(purchase, inReceipt);
			if(currentCount > purchase.getTotal()) {
				throw new IllegalStateException("入库单["+inReceipt.getSerialNum()+"]的实际入库数量["+currentCount+"]大于采购数量["+purchase.getTotal()+"]");
			}
		}
	}

	private void finishInReceipt(IotPurchase purchase, IotInReceipt inReceipt) {
		inReceipt.setInReceiptStatus(InReceiptStatus.已完成.getValue());
		this.update(inReceipt);
		purchase.setPurchaseStatus(PurchaseStatus.已入库.getValue());
		purchaseService.update(purchase);
		Integer subOrderId = purchase.getSubOrderId();
		if(subOrderId != null) {
			IotSubOrder iotSubOrder = new IotSubOrder();
			iotSubOrder.setId(subOrderId);
			iotSubOrder.setCurrentStatus(IotOrderStatus.待入库.getValue());
			iotSubOrder.setStatus(IotOrderStatus.待出库.getValue());
			iotSubOrderService.changeOrderStatus(iotSubOrder);
		}
	}

	public IotInReceipt selectById(Integer inReceiptId) {
		return iotInReceiptMapper.selectByPrimaryKey(inReceiptId);
    }

    public IotInReceipt getByPurchaseId(Integer purchaseId) {
    	return iotInReceiptMapper.selectByPurchaseId(purchaseId);
    }
}
