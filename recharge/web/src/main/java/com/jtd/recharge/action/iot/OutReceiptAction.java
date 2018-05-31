package com.jtd.recharge.action.iot;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.jtd.recharge.base.util.DateUtil;
import com.jtd.recharge.dao.bean.util.ReturnMsg;
import com.jtd.recharge.dao.po.IotCard;
import com.jtd.recharge.dao.po.IotOutReceipt;
import com.jtd.recharge.dao.po.IotProduct;
import com.jtd.recharge.define.CardOperator;
import com.jtd.recharge.define.CardSize;
import com.jtd.recharge.define.OutReceiptStatus;
import com.jtd.recharge.service.iot.CardService;
import com.jtd.recharge.service.iot.IotProductService;
import com.jtd.recharge.service.iot.IotSubOrderService;
import com.jtd.recharge.service.iot.OutReceiptService;

/**
 * 物联网卡出库
 * @author ninghui
 *
 */
@Controller
@RequestMapping("/iot/outReceipt")
public class OutReceiptAction {
	private Logger logger = Logger.getLogger(this.getClass());
	@Resource
	public OutReceiptService outReceiptService;
	@Resource
	public IotSubOrderService iotSubOrderService;
	@Resource
	public CardService cardService;
	@Resource
	private IotProductService iotProductService;
	/**
	 * 到应用页面
	 *
	 * @return
	 */
	@RequestMapping("index")
	public String index(HttpServletRequest request) throws UnsupportedEncodingException {
		PageInfo<IotProduct> allProduct = iotProductService.getAllProduct();
		request.setAttribute("cardSizeList", CardSize.values());
		request.setAttribute("operatorList", CardOperator.values());
		request.setAttribute("productList", allProduct.getList());
		request.setAttribute("outReStatusList", OutReceiptStatus.values());
		return "/iot/outReceipt";
	}

	@RequestMapping("list")
	@ResponseBody
	public PageInfo<IotOutReceipt> list(Integer pageNumber, Integer pageSize, IotOutReceipt outReceipt) {
		PageInfo<IotOutReceipt> list = outReceiptService.selectByCondition(pageNumber, pageSize, outReceipt);
		return list;
	}
	/**
	 * 已配货
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("picked")
	@ResponseBody
	public ReturnMsg picked(HttpServletRequest request, Integer id) {
		ReturnMsg msg = new ReturnMsg();
		msg.setSuccess(false);
		msg.setMessage("配货成功");
		try {
			outReceiptService.picked(id);
			msg.setSuccess(true);
		}catch(Exception e) {
			logger.error(e.getMessage(),e);
			msg.setMessage(e.getMessage());
		}
		return msg;
	}

	/**
	 * 导出出库单详情
	 * @param outReceiptId   出库单id
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping("exportOutReceipt")
	@ResponseBody
	public void exportPriceList( Integer outReceiptId, HttpServletResponse response, HttpServletRequest request) throws Exception {
		List<IotCard> iotCards = cardService.selectByOutReceiptId(outReceiptId);
		IotOutReceipt iotOutReceipt = outReceiptService.selectById(outReceiptId);
		OutputStream os = response.getOutputStream();// 取得输出流
		String tableName = "出库单明细";
		String showFileName = iotOutReceipt.getSerialNum()+ "-"+ DateUtil.Date2String(new Date(), "yyyy-MM-dd");
		try {
			//创建一个Excel工作簿对象
			HSSFWorkbook workbook = new HSSFWorkbook();
			//创建一个合并单元格做为表头根据下标合并单元格
			CellRangeAddress c1 = new CellRangeAddress(0, 0, 0, 9);
			//得到Excel工作表对象(sheet的名字为title的值)
			HSSFSheet sheet = workbook.createSheet(tableName);
			//创建单元格样式
			HSSFCellStyle style1 = workbook.createCellStyle();
			style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			//创建字体样式
			HSSFFont font1 = workbook.createFont();
			font1.setFontHeightInPoints((short) 18);
			//把字体赋予样式
			style1.setFont(font1);
			//创建单元格样式
			HSSFCellStyle style2 = workbook.createCellStyle();
			style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			//创建字体样式
			HSSFFont font2 = workbook.createFont();
			font2.setFontHeightInPoints((short) 12);
			//把字体赋予样式
			style2.setFont(font2);
			//将创建好的单元格赋予表1
			sheet.addMergedRegion(c1);
			//获取第一行
			HSSFRow row1 = sheet.createRow(0);
			//获取第一行中的第一个单元格
			HSSFCell cell = row1.createCell(0);
			//把样式赋予单元格
			cell.setCellStyle(style1);
			cell.setCellValue(iotOutReceipt.getSerialNum()+"出库单明细");
			HSSFRow row2 = sheet.createRow(1);
			String[] titles = {"ICCID", "MSISDN", "运营商", "流量套餐", "供应商",  "入库时间"};
			for (int i = 0; i < titles.length; i++) {
				String title = titles[i];
				HSSFCell cell2 = row2.createCell(i);
				cell2.setCellStyle(style2);
				cell2.setCellValue(title);

			}
			for (int j = 0; j < iotCards.size(); j++) {
				//输出list
				IotCard  card = iotCards.get(j);
				HSSFRow row = sheet.createRow(j + 2);
				HSSFCell cc1 = row.createCell(0);
				cc1.setCellValue(card.getIccid());
				HSSFCell cc2 = row.createCell(1);
				cc2.setCellValue(card.getMsisdn());
				HSSFCell cc3 = row.createCell(2);
				cc3.setCellValue(card.getOperatorLiteral());
				HSSFCell cc4 = row.createCell(3);
				cc4.setCellValue(card.getProductName());
				HSSFCell cc5 = row.createCell(4);
				cc5.setCellValue(card.getSupplyName());
				HSSFCell cc6 = row.createCell(5);
				cc6.setCellValue(card.getCreateTime().substring(0,19));
			}
			response.reset();// 清空输出流
			response.setHeader("Content-disposition", "attachment; filename="
					+ showFileName + ".xls"); // 设定输出文件头,该方法有两个参数，分别表示应答头的名字和值。
			response.setContentType("application/msexcel");
			workbook.write(os);
			try {
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
