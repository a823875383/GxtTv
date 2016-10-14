package com.jsqix.gxt.tv.obj;

import java.util.ArrayList;
import java.util.List;

public class ShopGoodsObj extends BaseObj {
	private String id;
	private String name;
	private String brandId;
	private String brandName;
	private String mainImg;
	private ArrayList<String> imgUrl = new ArrayList<String>();
	private String description;
	private String argument;
	private List<ShopProductObj> productObjs = new ArrayList<ShopProductObj>();
	private List<ShopPropertyObj> propertyObjs = new ArrayList<ShopPropertyObj>();

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

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getMainImg() {
		return mainImg;
	}

	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}

	public ArrayList<String> getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(ArrayList<String> imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getArgument() {
		return argument;
	}

	public void setArgument(String argument) {
		this.argument = argument;
	}

	public List<ShopProductObj> getProductObjs() {
		return productObjs;
	}

	public void setProductObjs(List<ShopProductObj> productObjs) {
		this.productObjs = productObjs;
	}

	public List<ShopPropertyObj> getPropertyObjs() {
		return propertyObjs;
	}

	public void setPropertyObjs(List<ShopPropertyObj> propertyObjs) {
		this.propertyObjs = propertyObjs;
	}

}
