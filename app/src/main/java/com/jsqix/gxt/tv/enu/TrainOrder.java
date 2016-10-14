package com.jsqix.gxt.tv.enu;

import java.util.HashMap;

public class TrainOrder {
	HashMap<Integer, String> status = new HashMap<Integer, String>();
	private static TrainOrder instance;

	public static TrainOrder getInstance() {
		if (instance == null) {
			instance = new TrainOrder();
		}
		return instance;
	}

	public TrainOrder() {
		status.put(-1, "已取消");
		status.put(0, "已受理");
		status.put(1, "待付款");
		status.put(2, "已付款");
		status.put(3, "成功");
		status.put(4, "失败");
	}

	public HashMap<Integer, String> getStatus() {
		return status;
	}
}
