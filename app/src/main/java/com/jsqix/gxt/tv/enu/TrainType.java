package com.jsqix.gxt.tv.enu;

import java.util.HashMap;

public class TrainType {
	HashMap<Integer, String> status = new HashMap<Integer, String>();
	private static TrainType instance;

	public static TrainType getInstance() {
		if (instance == null) {
			instance = new TrainType();
		}
		return instance;
	}

	public TrainType() {
		status.put(0, "商务座");
		status.put(1, "特等座");
		status.put(2, "一等座");
		status.put(3, "二等座");
		status.put(4, "高级动卧");
		status.put(5, "动卧");
		status.put(6, "高级软卧");
		status.put(7, "软卧");
		status.put(8, "硬卧");
		status.put(9, "软座");
		status.put(10, "硬座");
		status.put(11, "无座");
		status.put(13, "其他");
	}

	public HashMap<Integer, String> getStatus() {
		return status;
	}
}
