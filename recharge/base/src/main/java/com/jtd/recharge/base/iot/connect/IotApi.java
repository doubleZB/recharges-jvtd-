package com.jtd.recharge.base.iot.connect;

import com.jtd.recharge.base.iot.connect.protocol.DateActivatedResp;
import com.jtd.recharge.base.iot.connect.protocol.DeviceResp;
import com.jtd.recharge.base.iot.connect.protocol.UsagesResp;
/**
 * 物联网卡api接口
 * @author ninghui
 *
 */
public interface IotApi {

	/**
	 * 查询激活时间
	 * 
	 * @param sp
	 * @param iccid
	 * @return
	 */
	UsagesResp usages(SupplierParam sp, String iccid);

	/**
	 * 查询激活时间
	 * 
	 * @param sp
	 * @param iccid
	 * @return
	 */
	DateActivatedResp dateActivated(SupplierParam sp, String iccid);

	/**
	 * 查询设备信息
	 * 
	 * @param sp
	 * @param iccid
	 * @return
	 */
	DeviceResp device(SupplierParam sp, String iccid);

}