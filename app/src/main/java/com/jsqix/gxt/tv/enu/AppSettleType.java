package com.jsqix.gxt.tv.enu;

import java.util.HashMap;

public class AppSettleType {
	HashMap<Integer, String> status = new HashMap<Integer, String>();
	private static AppSettleType instance;

	public static AppSettleType getInstance() {
		if (instance == null) {
			instance = new AppSettleType();
		}
		return instance;
	}

	public AppSettleType() {
		status.put(0, "后返");
		status.put(1, "现返");
		
	}

	public HashMap<Integer, String> getStatus() {
		return status;
	}
}
