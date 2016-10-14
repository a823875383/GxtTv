package com.jsqix.gxt.tv.enu;

import java.util.HashMap;

public class Ridingtime {
	HashMap<Integer, String> status = new HashMap<Integer, String>();
	private static Ridingtime instance;

	public static Ridingtime getInstance() {
		if (instance == null) {
			instance = new Ridingtime();
		}
		return instance;
	}

	public Ridingtime() {
		status.put(0, "00002400");
		status.put(1, "00000600");
		status.put(2, "05001200");
		status.put(3, "12001800");
		status.put(4, "18002400");
	}

	public HashMap<Integer, String> getStatus() {
		return status;
	}
}
