package com.jsqix.gxt.tv.enu;

import java.util.HashMap;

public class PassengerType {
	HashMap<Integer, String> status = new HashMap<Integer, String>();
	private static PassengerType instance;

	public static PassengerType getInstance() {
		if (instance == null) {
			instance = new PassengerType();
		}
		return instance;
	}

	public PassengerType() {
		status.put(0, "成人");
		status.put(1, "儿童");
		status.put(2, "学生");
		status.put(3, "残军");

	}

	public HashMap<Integer, String> getStatus() {
		return status;
	}
}
