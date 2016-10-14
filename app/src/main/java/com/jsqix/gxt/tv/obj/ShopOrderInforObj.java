package com.jsqix.gxt.tv.obj;

import java.util.ArrayList;
import java.util.List;

public class ShopOrderInforObj extends BaseObj {
	private String brandName;
	private String brandId;
	private String brandImg;
	private List<ShopOrderObj> cartObjs = new ArrayList<ShopOrderObj>();
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getBrandImg() {
		return brandImg;
	}
	public void setBrandImg(String brandImg) {
		this.brandImg = brandImg;
	}
	public List<ShopOrderObj> getCartObjs() {
		return cartObjs;
	}
	public void setCartObjs(List<ShopOrderObj> cartObjs) {
		this.cartObjs = cartObjs;
	}
	
}
