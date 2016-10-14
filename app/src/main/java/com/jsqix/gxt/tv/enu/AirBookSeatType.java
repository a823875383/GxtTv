package com.jsqix.gxt.tv.enu;

import java.util.HashMap;

public class AirBookSeatType {
	HashMap<Integer, String> status = new HashMap<Integer, String>();
	private static AirBookSeatType instance;

	public static AirBookSeatType getInstance() {
		if (instance == null) {
			instance = new AirBookSeatType();
		}
		return instance;
	}

	public AirBookSeatType() {
		status.put(0, "不限");
		status.put(1, "商务舱");
		status.put(2, "经济舱");

	}

	public HashMap<Integer, String> getStatus() {
		return status;
	}
}
