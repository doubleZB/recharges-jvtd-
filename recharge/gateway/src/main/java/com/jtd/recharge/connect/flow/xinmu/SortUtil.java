package com.jtd.recharge.connect.flow.xinmu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortUtil {

	public static List<Map.Entry<String,String>> mapKeySort(Map<String, String> map) {
		// 通过ArrayList构造函数把map.entrySet()转换成list
		List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());
		// 通过比较器实现比较排序
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1,
					Map.Entry<String, String> o2) {
				return o1.getKey().compareTo (o2.getKey()); //升序
//				return o2.getKey().compareTo(o1.getKey()); // 倒序
			}
		});
		return list;
	}
	
	public static List<Map.Entry<String,String>> mapValueSort(Map<String, String> map) {
		// 通过ArrayList构造函数把map.entrySet()转换成list
		List<Map.Entry<String,String>> list = new ArrayList<Map.Entry<String,String>>(map.entrySet());
		// 通过比较器实现比较排序
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			public int compare(Map.Entry<String, String> o1,
					Map.Entry<String, String> o2) {
				return o1.getValue().compareTo (o2.getValue()); //升序
//				return o2.getValue().compareTo(o1.getValue()); // 倒序
			}
		});
		return list;
	}
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Rdbaqxw", "sdf");
		map.put("Jwdbaqzt", "sdfs");
		map.put("Zyhjbl", "sdf");
		map.put("Glqx", "sdf");
	}
}
