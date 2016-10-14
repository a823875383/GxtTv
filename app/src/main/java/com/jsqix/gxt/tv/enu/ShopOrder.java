package com.jsqix.gxt.tv.enu;

import java.util.HashMap;

public class ShopOrder {
	HashMap<Integer, String> status = new HashMap<Integer, String>();
	private static ShopOrder instance;

	public static ShopOrder getInstance() {
		if (instance == null) {
			instance = new ShopOrder();
		}
		return instance;
	}

	public ShopOrder() {
		status.put(-1, "已删除");
		status.put(-2, "已取消");
		status.put(0, "待付款");
		status.put(1, "待发货");
		status.put(2, "待收货");
		status.put(3, "已收货");
	}

	public HashMap<Integer, String> getStatus() {
		return status;
	}
}
