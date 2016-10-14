package com.jsqix.gxt.tv.obj;

import java.util.ArrayList;
import java.util.List;

public class ShopOrderListObj extends BaseObj {
	private String order_number;
	private String order_time;
	private String status;
	private String remark;
	private List<ShopOrderInforObj> brandShops = new ArrayList<ShopOrderInforObj>();

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

	public List<ShopOrderInforObj> getBrandShops() {
		return brandShops;
	}

	public void setBrandShops(List<ShopOrderInforObj> brandShops) {
		this.brandShops = brandShops;
	}

}
