package com.jsqix.gxt.tv.obj;

import android.os.Parcel;
import android.os.Parcelable;

public class CartObj extends BaseObj implements Parcelable {
	private String id;
	private String goodsId;
	private String goodsName;
	private String imgUrl;
	private String price;
	private String stock;
	private String amout;
	private boolean isChecked = true;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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

	public String getAmout() {
		return amout;
	}

	public void setAmout(String amout) {
		this.amout = amout;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(goodsId);
		dest.writeString(goodsName);
		dest.writeString(imgUrl);
		dest.writeString(price);
		dest.writeString(stock);
		dest.writeString(amout);

	}

	public static final Creator<CartObj> CREATOR = new Creator<CartObj>() {

		@Override
		public CartObj createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			CartObj cart = new CartObj();
			cart.id = source.readString();
			cart.goodsId = source.readString();
			cart.goodsName = source.readString();
			cart.imgUrl = source.readString();
			cart.price = source.readString();
			cart.stock = source.readString();
			cart.amout = source.readString();
			return cart;
		}

		@Override
		public CartObj[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CartObj[size];
		}
	};

}
