package com.jsqix.gxt.tv.obj;

import java.util.ArrayList;
import java.util.List;

public class ShopPropertyObj extends BaseObj {
	private String id;
	private String name;
	private List<ShopPropertyContentObj> shopPropertyContentObjs=new ArrayList<ShopPropertyContentObj>();
	
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
	public List<ShopPropertyContentObj> getShopPropertyContentObjs() {
		return shopPropertyContentObjs;
	}
	public void setShopPropertyContentObjs(
			List<ShopPropertyContentObj> shopPropertyContentObjs) {
		this.shopPropertyContentObjs = shopPropertyContentObjs;
	}

}
