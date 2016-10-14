package com.jsqix.gxt.tv.obj;

public class HotelRoomInforObj extends BaseObj {
	private String roomBreakfast;
	private String policyName;
	private String roomAdviceAmount;
	private String guaranteeType;//0-无担保；1-担保冻结；2-担保预付；3-代收代付
	private String guaranteeFlag;//0-非担保;1-担保
	private String overtime;// 超时时间
	private String danBaoType;//担保策略
	private String bookingFlag;// 0可预订
	private String surplusRooms;

	public String getRoomBreakfast() {
		return roomBreakfast;
	}

	public void setRoomBreakfast(String roomBreakfast) {
		this.roomBreakfast = roomBreakfast;
	}

	public String getPolicyName() {
		return policyName;
	}

	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}

	public String getRoomAdviceAmount() {
		return roomAdviceAmount;
	}

	public void setRoomAdviceAmount(String roomAdviceAmount) {
		this.roomAdviceAmount = roomAdviceAmount;
	}

	public String getBookingFlag() {
		return bookingFlag;
	}

	public void setBookingFlag(String bookingFlag) {
		this.bookingFlag = bookingFlag;
	}

	public String getSurplusRooms() {
		return surplusRooms;
	}

	public void setSurplusRooms(String surplusRooms) {
		this.surplusRooms = surplusRooms;
	}

	public String getGuaranteeType() {
		return guaranteeType;
	}

	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	public String getGuaranteeFlag() {
		return guaranteeFlag;
	}

	public void setGuaranteeFlag(String guaranteeFlag) {
		this.guaranteeFlag = guaranteeFlag;
	}

	public String getDanBaoType() {
		return danBaoType;
	}

	public void setDanBaoType(String danBaoType) {
		this.danBaoType = danBaoType;
	}

	public String getOvertime() {
		return overtime;
	}

	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}

}
