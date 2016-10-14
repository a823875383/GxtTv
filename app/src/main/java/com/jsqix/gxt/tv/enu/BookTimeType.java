package com.jsqix.gxt.tv.enu;

import java.util.HashMap;

public class BookTimeType {
	HashMap<Integer, String> status = new HashMap<Integer, String>();
	private static BookTimeType instance;

	public static BookTimeType getInstance() {
		if (instance == null) {
			instance = new BookTimeType();
		}
		return instance;
	}

	public BookTimeType() {
		status.put(0, "00:00——24:00");
		status.put(1, "00:00——06:00");
		status.put(2, "06:00——12:00");
		status.put(3, "12:00——18:00");
		status.put(4, "18:00——24:00");
	}

	public HashMap<Integer, String> getStatus() {
		return status;
	}
}
