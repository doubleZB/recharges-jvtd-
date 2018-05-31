package com.jtd.recharge.iot.connect.cmccgd.protocol.member;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PkgInfoListWrapper {
	private List<PkgInfo> list;
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	public List<PkgInfo> getList() {
		return list;
	}
	public void setList(List<PkgInfo> list) {
		this.list = list;
	}
}
