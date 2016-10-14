package com.jsqix.gxt.tv.enu;

import java.util.HashMap;

public class IDtype {
	HashMap<Integer, String> status = new HashMap<Integer, String>();
	private static IDtype instance;

	public static IDtype getInstance() {
		if (instance == null) {
			instance = new IDtype();
		}
		return instance;
	}

	public IDtype() {
		status.put(0, "二代身份证 ");
		status.put(1, "港澳通行证");
		status.put(2, "台湾通行证");
		status.put(3, "护照");

	}

	public HashMap<Integer, String> getStatus() {
		return status;
	}
}
