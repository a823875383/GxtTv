package com.jsqix.gxt.tv.obj;

import java.util.ArrayList;
import java.util.List;

public class HotelRoomObj extends BaseObj {
	private String hotelId;
	private String HotelName;
	private String HotelAddress;
	private String cityName;
	private String sectionName;
	private String nearby;
	private String street;
	private String streetAddr;
	private String roomName;
	private String comeDate;
	private String leaveDate;
	private String area;
	private String bed;
	private String photoUrl;
	private boolean ispand = false;
	private List<HotelRoomInforObj> list = new ArrayList<HotelRoomInforObj>();
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	public String getHotelName() {
		return HotelName;
	}
	public void setHotelName(String hotelName) {
		HotelName = hotelName;
	}
	public String getHotelAddress() {
		return HotelAddress;
	}
	public void setHotelAddress(String hotelAddress) {
		HotelAddress = hotelAddress;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public String getComeDate() {
		return comeDate;
	}
	public void setComeDate(String comeDate) {
		this.comeDate = comeDate;
	}
	public String getLeaveDate() {
		return leaveDate;
	}
	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getBed() {
		return bed;
	}
	public void setBed(String bed) {
		this.bed = bed;
	}
	public String getPhotoUrl() {
		return photoUrl;
	}
	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}
	public boolean isIspand() {
		return ispand;
	}
	public void setIspand(boolean ispand) {
		this.ispand = ispand;
	}
	public List<HotelRoomInforObj> getList() {
		return list;
	}
	public void setList(List<HotelRoomInforObj> list) {
		this.list = list;
	}
	public String getNearby() {
		return nearby;
	}
	public void setNearby(String nearby) {
		this.nearby = nearby;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getStreetAddr() {
		return streetAddr;
	}
	public void setStreetAddr(String streetAddr) {
		this.streetAddr = streetAddr;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

}
