package com.jsqix.gxt.tv.tv.enu;

import java.util.HashMap;

public class CostOrder {
	HashMap<Integer, String> status = new HashMap<Integer, String>();
	private static CostOrder instance;

	public static CostOrder getInstance() {
		if (instance == null) {
			instance = new CostOrder();
		}
		return instance;
	}

	public CostOrder() {
		status.put(-2, "已付款");
		status.put(-1, "已取消");
		status.put(0, "已受理");
		status.put(1, "待付款");
		status.put(2, "已付款");
		status.put(3, "充值成功");
		status.put(4, "失败");
	}

	public HashMap<Integer, String> getStatus() {
		return status;
	}
}
