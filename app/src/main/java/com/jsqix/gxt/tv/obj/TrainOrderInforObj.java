package com.jsqix.gxt.tv.obj;

import java.util.ArrayList;
import java.util.List;

public class TrainOrderInforObj extends BaseObj {
	private String order_number;
	private String order_time;
	private String Order_updtime;
	private String order_status;
	private String remark;
	private List<TrainRiderObj> riderObjs = new ArrayList<TrainRiderObj>();

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

	public String getOrder_updtime() {
		return Order_updtime;
	}

	public void setOrder_updtime(String order_updtime) {
		Order_updtime = order_updtime;
	}

	public String getOrder_status() {
		return order_status;
	}

	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<TrainRiderObj> getRiderObjs() {
		return riderObjs;
	}

	public void setRiderObjs(List<TrainRiderObj> riderObjs) {
		this.riderObjs = riderObjs;
	}

}
