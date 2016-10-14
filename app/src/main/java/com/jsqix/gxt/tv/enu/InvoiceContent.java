package com.jsqix.gxt.tv.enu;

import java.util.HashMap;

public class InvoiceContent {
	HashMap<Integer, String> status = new HashMap<Integer, String>();
	private static InvoiceContent instance;

	public static InvoiceContent getInstance() {
		if (instance == null) {
			instance = new InvoiceContent();
		}
		return instance;
	}

	public InvoiceContent() {
		status.put(0, "明细");
		status.put(1, "耗材");
		status.put(2, "办公");
	}

	public HashMap<Integer, String> getStatus() {
		return status;
	}
}
