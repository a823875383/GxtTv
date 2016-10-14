package com.jsqix.gxt.tv.enu;

import java.util.HashMap;

public class HotelOrderType {
	HashMap<Integer, String> status = new HashMap<Integer, String>();
	private static HotelOrderType instance;

	public static HotelOrderType getInstance() {
		if (instance == null) {
			instance = new HotelOrderType();
		}
		return instance;
	}

	public HotelOrderType() {
		status.put(0, "已受理");
		status.put(3, "预定成功");
		status.put(4, "预定失败");
	}

	public HashMap<Integer, String> getStatus() {
		return status;
	}
}
