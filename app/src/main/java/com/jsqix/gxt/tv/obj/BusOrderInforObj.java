package com.jsqix.gxt.tv.obj;

import java.util.ArrayList;
import java.util.List;

public class BusOrderInforObj extends BaseObj {
	private String order_number;
	private String order_time;
	private String start_station;
	private String start_time;
	private String start_date;
	private String to_station;
	private String status;
	private String remark;
	private List<BusContactObj> contacts = new ArrayList<BusContactObj>();

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public String getStart_station() {
		return start_station;
	}

	public void setStart_station(String start_station) {
		this.start_station = start_station;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getTo_station() {
		return to_station;
	}

	public void setTo_station(String to_station) {
		this.to_station = to_station;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<BusContactObj> getContacts() {
		return contacts;
	}

	public void setContacts(List<BusContactObj> contacts) {
		this.contacts = contacts;
	}

}
