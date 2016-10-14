package com.jsqix.gxt.tv.obj;

import java.util.List;

public class ShopMallGoodsObj extends BaseObj{
	private String NAME;
	private String PIC;
	private int PID;
	private int ID;
	private String REMARK;
	private String ADDTIME;
	private int SORT_INDEX;
	private String IMGURL;
	
	private List<ShopMallGoodsObj> detail;
	public String getNAME() {
		return NAME;
	}
	public void setNAME(String nAME) {
		NAME = nAME;
	}
	public List<ShopMallGoodsObj> getDetail() {
		return detail;
	}
	public void setDetail(List<ShopMallGoodsObj> detail) {
		this.detail = detail;
	}
	public String getPIC() {
		return PIC;
	}
	public void setPIC(String pIC) {
		PIC = pIC;
	}
	public int getPID() {
		return PID;
	}
	public void setPID(int pID) {
		PID = pID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getREMARK() {
		return REMARK;
	}
	public void setREMARK(String rEMARK) {
		REMARK = rEMARK;
	}
	public String getADDTIME() {
		return ADDTIME;
	}
	public void setADDTIME(String aDDTIME) {
		ADDTIME = aDDTIME;
	}
	public int getSORT_INDEX() {
		return SORT_INDEX;
	}
	public void setSORT_INDEX(int sORT_INDEX) {
		SORT_INDEX = sORT_INDEX;
	}
	public String getIMGURL() {
		return IMGURL;
	}
	public void setIMGURL(String iMGURL) {
		IMGURL = iMGURL;
	}
	
}
