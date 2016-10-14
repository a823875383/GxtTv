package com.jsqix.gxt.tv.enu;

import java.util.HashMap;

public class Sex {
	HashMap<Integer, String> status = new HashMap<Integer, String>();
	private static Sex instance;

	public static Sex getInstance() {
		if (instance == null) {
			instance = new Sex();
		}
		return instance;
	}
	public Sex() {
		status.put(0, "男 ");
		status.put(1, "女");

	}

	public HashMap<Integer, String> getStatus() {
		return status;
	}
}
