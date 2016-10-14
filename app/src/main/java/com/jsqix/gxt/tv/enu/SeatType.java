package com.jsqix.gxt.enu;

import java.util.HashMap;

public class SeatType {
	HashMap<Integer, String> status = new HashMap<Integer, String>();
	private static SeatType instance;

	public static SeatType getInstance() {
		if (instance == null) {
			instance = new SeatType();
		}
		return instance;
	}

	public SeatType() {
		status.put(0, "商务座");
		status.put(1, "特等座");
		status.put(2, "一等座");
		status.put(3, "二等座");
		status.put(4, "高级动卧上");
		status.put(5, "高级动卧下");
		status.put(6, "动卧上");
		status.put(7, "动卧下");
		status.put(8, "高级软卧上");
		status.put(9, "高级软卧下");
		status.put(10, "软卧上");
		status.put(11, "软卧下");
		status.put(12, "硬卧上");
		status.put(13, "硬卧中");
		status.put(14, "硬卧下");
		status.put(15, "软座");
		status.put(16, "硬座");
		status.put(17, "无座 ");
		status.put(18, "其他");
	}

	public HashMap<Integer, String> getStatus() {
		return status;
	}
}
