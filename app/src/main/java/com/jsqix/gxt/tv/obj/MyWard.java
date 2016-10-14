package com.jsqix.gxt.tv.obj;

import java.util.ArrayList;
import java.util.List;

public class MyWard extends BaseObj {
	private List list=new ArrayList();
	private String typeID;
	private String imgUrl;

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public String getTypeID() {
		return typeID;
	}

	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

}
