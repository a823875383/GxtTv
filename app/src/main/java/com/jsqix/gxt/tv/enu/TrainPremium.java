package com.jsqix.gxt.tv.enu;

import android.annotation.SuppressLint;
import java.util.HashMap;

public class TrainPremium {
	@SuppressLint("UseSparseArrays")
	HashMap<Integer, String> status = new HashMap<Integer, String>();
	private static TrainPremium instance;

	public static TrainPremium getInstance() {
		if (instance == null) {
			instance = new TrainPremium();
		}
		return instance;
	}

	public TrainPremium() {
		status.put(0, "5");
		status.put(1, "10");
		status.put(2, "15");
		status.put(3, "20");

	}

	public HashMap<Integer, String> getStatus() {
		return status;
	}
}
