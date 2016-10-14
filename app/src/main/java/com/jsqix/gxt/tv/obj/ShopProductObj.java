package com.jsqix.gxt.tv.obj;

import java.util.ArrayList;
import java.util.List;

public class ShopProductObj extends BaseObj {
	private List<ShopPropertyObj> propertyObjs = new ArrayList<ShopPropertyObj>();
	private String id;
	private String name;
	private String price;
	private String stock;
	private String sales;
	private String sermoney;
	private String goods_id;
	public List<ShopPropertyObj> getPropertyObjs() {
		return propertyObjs;
	}
	public void setPropertyObjs(List<ShopPropertyObj> propertyObjs) {
		this.propertyObjs = propertyObjs;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}
	public String getSales() {
		return sales;
	}
	public void setSales(String sales) {
		this.sales = sales;
	}
	
	public String getSermoney() {
		return sermoney;
	}
	public void setSermoney(String sermoney) {
		this.sermoney = sermoney;
	}
	public String getGoods_id() {
		return goods_id;
	}
	public void setGoods_id(String goods_id) {
		this.goods_id = goods_id;
	}

}
