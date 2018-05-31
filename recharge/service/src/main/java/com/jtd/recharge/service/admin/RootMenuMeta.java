package com.jtd.recharge.service.admin;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

/**
 * 一级菜单
 * @author ninghui
 *
 */
public enum RootMenuMeta {
	 流量话费(1,"充值业务"),物联网卡(70,""),SDK_导流(54,"SDK/导流"),蜜糖折扣(36,""),销售管理(50,""),商户管理(2,""),系统管理(3,"");
	 private int value;
	 private String displayName;
	 private RootMenuMeta(int value,String displayName) {
		 this.value = value;
		 this.displayName = StringUtils.isBlank(displayName) ? this.name() : displayName ;
	 }
	public int getValue() {
		return value;
	}
	
	public static RootMenuMeta parse(int inValue) {
		for(RootMenuMeta oper:RootMenuMeta.values()) {
			if(oper.getValue() == inValue){
				return oper;
			}
		}
		throw new IllegalArgumentException("非法的二级菜单Id:"+inValue);
	}
	
	public static Set<Integer> getIdSet() {
		Set<Integer> idSet = new HashSet<>();
		for(RootMenuMeta oper:RootMenuMeta.values()) {
			idSet.add(oper.getValue());
		}
		return idSet;
	}
	
	public String getDisplayName() {
		return displayName;
	}
}
