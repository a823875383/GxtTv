package com.jsqix.gxt.tv.obj;

public class Debris extends BaseObj {
	// {"NAME":"宝马X5","IMG_URL":"b62f1bf3750849e790db42c3d7e26e0c.png",
	// "CT":6,"PRIZE_ID":263,"CREATE_DT":"2015-03-17"}
	private String NAME;
	private String IMG_URL;
	private String CT;
	private String PRIZE_ID;
	private String CREATE_DT;
	private String typeID;

	public String getID() {
		return typeID;
	}

	public void setID(String iD) {
		typeID = iD;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String nAME) {
		NAME = nAME;
	}

	public String getIMG_URL() {
		return IMG_URL;
	}

	public void setIMG_URL(String iMG_URL) {
		IMG_URL = iMG_URL;
	}

	public String getCT() {
		return CT;
	}

	public void setCT(String cT) {
		CT = cT;
	}

	public String getPRIZE_ID() {
		return PRIZE_ID;
	}

	public void setPRIZE_ID(String pRIZE_ID) {
		PRIZE_ID = pRIZE_ID;
	}

	public String getCREATE_DT() {
		return CREATE_DT;
	}

	public void setCREATE_DT(String cREATE_DT) {
		CREATE_DT = cREATE_DT;
	}

	// {"amount":7,"prize_id":73,"pname":"iphone6","user_id":6,"imageurl":"/include/sy_ad9.jpg"}
	// private String amount;
	// private String prize_id;
	// private String pname;
	// private String user_id;
	// private String imageurl;
	// public String getAmount() {
	// return amount;
	// }
	// public void setAmount(String amount) {
	// this.amount = amount;
	// }
	// public String getPrize_id() {
	// return prize_id;
	// }
	// public void setPrize_id(String prize_id) {
	// this.prize_id = prize_id;
	// }
	// public String getPname() {
	// return pname;
	// }
	// public void setPname(String pname) {
	// this.pname = pname;
	// }
	// public String getUser_id() {
	// return user_id;
	// }
	// public void setUser_id(String user_id) {
	// this.user_id = user_id;
	// }
	// public String getImageurl() {
	// return imageurl;
	// }
	// public void setImageurl(String imageurl) {
	// this.imageurl = imageurl;
	// }

}
