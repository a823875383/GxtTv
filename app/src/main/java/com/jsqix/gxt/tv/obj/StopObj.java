package com.jsqix.gxt.tv.obj;

public class StopObj extends BaseObj {
	private String station_no;
	private String station_name;
	private String arrive_time;
	private String start_time;
	private String stopover_time;
	private boolean isEnabled;

	public String getStation_no() {
		return station_no;
	}

	public void setStation_no(String station_no) {
		this.station_no = station_no;
	}

	public String getStation_name() {
		return station_name;
	}

	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}

	public String getArrive_time() {
		return arrive_time;
	}

	public void setArrive_time(String arrive_time) {
		this.arrive_time = arrive_time;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getStopover_time() {
		return stopover_time;
	}

	public void setStopover_time(String stopover_time) {
		this.stopover_time = stopover_time;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

}
