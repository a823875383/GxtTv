package com.jsqix.gxt.tv.obj;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class CartListObj extends BaseObj implements Parcelable {
	private String brandName;
	private String brandId;
	private String brandImg;
	private boolean isCheckedAll = true;
	private List<CartObj> cartObjs = new ArrayList<CartObj>();

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

	public boolean isCheckedAll() {
		return isCheckedAll;
	}

	public void setCheckedAll(boolean isCheckedAll) {
		this.isCheckedAll = isCheckedAll;
	}

	public List<CartObj> getCartObjs() {
		return cartObjs;
	}

	public void setCartObjs(List<CartObj> cartObjs) {
		this.cartObjs = cartObjs;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(brandId);
		dest.writeString(brandName);
		dest.writeList(cartObjs);

	}

	public static final Creator<CartListObj> CREATOR = new Creator<CartListObj>() {

		@Override
		public CartListObj createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			CartListObj cartListObj = new CartListObj();
			cartListObj.brandId = source.readString();
			cartListObj.brandName = source.readString();
			source.readList(cartListObj.cartObjs,
					CartListObj.class.getClassLoader());
			return cartListObj;
		}

		@Override
		public CartListObj[] newArray(int size) {
			// TODO Auto-generated method stub
			return new CartListObj[size];
		}
	};

}
