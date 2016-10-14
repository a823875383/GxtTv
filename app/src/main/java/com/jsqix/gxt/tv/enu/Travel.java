package com.jsqix.gxt.tv.enu;

import java.util.HashMap;

public class Travel {
	HashMap<Integer, String> status = new HashMap<Integer, String>();
	private static Travel instance;

	public static Travel getInstance() {
		if (instance == null) {
			instance = new Travel();
		}
		return instance;
	}

	public Travel() {
		status.put(0, "");
		status.put(1, "swz_num");
		status.put(2, "tz_num");
		status.put(3, "zy_num");
		status.put(4, "ze_num");
		status.put(5, "gr_num");
		status.put(6, "rw_num");
		status.put(7, "yw_num");
		status.put(8, "rz_num");
		status.put(9, "yz_num");
	}

	public HashMap<Integer, String> getStatus() {
		return status;
	}
}
