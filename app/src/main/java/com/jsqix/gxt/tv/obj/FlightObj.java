package com.jsqix.gxt.tv.obj;

import java.util.List;

public class FlightObj extends BaseObj {

	private String subChannelId;
	private String source;
	private String takeoffPoint;
	private String arrivalPoint;
	private String flightNo;
	private String airCompanyCode;
	private String airCompanyName;
	private String takeOffTime;
	private String takeOffCity;
	private String arrivalTime;
	private String arrivalCity;
	private String takeoffAirportCode;
	private String takeoffAirportName;
	private String takeoffAirportShortName;
	private String arrivalAirportCode;
	private String arrivalAirportName;
	private String arrivalAirportShortName;
	private String equipmentCode;
	private String equipmentName;
	private String diet;
	private String journeyTime;
	private String voyage;
	private String fuelTax;// 燃油税
	private String childFuelTax;// 儿童燃油税
	private String airportTax;// 机场税
	private String stopNum;
	private String isRecommended;
	private String recommendReason;
	private String tcPlat;
	private String isBookSelfHelpFly;
	private boolean ispand = false;
	private List<FlightCabinSeatObj> cabinSeatList;

	public String getSubChannelId() {
		return subChannelId;
	}

	public void setSubChannelId(String subChannelId) {
		this.subChannelId = subChannelId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTakeoffPoint() {
		return takeoffPoint;
	}

	public void setTakeoffPoint(String takeoffPoint) {
		this.takeoffPoint = takeoffPoint;
	}

	public String getArrivalPoint() {
		return arrivalPoint;
	}

	public void setArrivalPoint(String arrivalPoint) {
		this.arrivalPoint = arrivalPoint;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getAirCompanyCode() {
		return airCompanyCode;
	}

	public void setAirCompanyCode(String airCompanyCode) {
		this.airCompanyCode = airCompanyCode;
	}

	public String getAirCompanyName() {
		return airCompanyName;
	}

	public void setAirCompanyName(String airCompanyName) {
		this.airCompanyName = airCompanyName;
	}

	public String getTakeOffTime() {
		return takeOffTime;
	}

	public void setTakeOffTime(String takeOffTime) {
		this.takeOffTime = takeOffTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getTakeOffCity() {
		return takeOffCity;
	}

	public void setTakeOffCity(String takeOffCity) {
		this.takeOffCity = takeOffCity;
	}

	public String getArrivalCity() {
		return arrivalCity;
	}

	public void setArrivalCity(String arrivalCity) {
		this.arrivalCity = arrivalCity;
	}

	public String getTakeoffAirportCode() {
		return takeoffAirportCode;
	}

	public void setTakeoffAirportCode(String takeoffAirportCode) {
		this.takeoffAirportCode = takeoffAirportCode;
	}

	public String getTakeoffAirportName() {
		return takeoffAirportName;
	}

	public void setTakeoffAirportName(String takeoffAirportName) {
		this.takeoffAirportName = takeoffAirportName;
	}

	public String getTakeoffAirportShortName() {
		return takeoffAirportShortName;
	}

	public void setTakeoffAirportShortName(String takeoffAirportShortName) {
		this.takeoffAirportShortName = takeoffAirportShortName;
	}

	public String getArrivalAirportCode() {
		return arrivalAirportCode;
	}

	public void setArrivalAirportCode(String arrivalAirportCode) {
		this.arrivalAirportCode = arrivalAirportCode;
	}

	public String getArrivalAirportName() {
		return arrivalAirportName;
	}

	public void setArrivalAirportName(String arrivalAirportName) {
		this.arrivalAirportName = arrivalAirportName;
	}

	public String getArrivalAirportShortName() {
		return arrivalAirportShortName;
	}

	public void setArrivalAirportShortName(String arrivalAirportShortName) {
		this.arrivalAirportShortName = arrivalAirportShortName;
	}

	public String getEquipmentCode() {
		return equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}

	public String getEquipmentName() {
		return equipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		this.equipmentName = equipmentName;
	}

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
	}

	public String getJourneyTime() {
		return journeyTime;
	}

	public void setJourneyTime(String journeyTime) {
		this.journeyTime = journeyTime;
	}

	public String getVoyage() {
		return voyage;
	}

	public void setVoyage(String voyage) {
		this.voyage = voyage;
	}

	public String getFuelTax() {
		return fuelTax;
	}

	public void setFuelTax(String fuelTax) {
		this.fuelTax = fuelTax;
	}

	public String getChildFuelTax() {
		return childFuelTax;
	}

	public void setChildFuelTax(String childFuelTax) {
		this.childFuelTax = childFuelTax;
	}

	public String getAirportTax() {
		return airportTax;
	}

	public void setAirportTax(String airportTax) {
		this.airportTax = airportTax;
	}

	public String getStopNum() {
		return stopNum;
	}

	public void setStopNum(String stopNum) {
		this.stopNum = stopNum;
	}

	public String getIsRecommended() {
		return isRecommended;
	}

	public void setIsRecommended(String isRecommended) {
		this.isRecommended = isRecommended;
	}

	public String getRecommendReason() {
		return recommendReason;
	}

	public void setRecommendReason(String recommendReason) {
		this.recommendReason = recommendReason;
	}

	public String getTcPlat() {
		return tcPlat;
	}

	public void setTcPlat(String tcPlat) {
		this.tcPlat = tcPlat;
	}

	public String getIsBookSelfHelpFly() {
		return isBookSelfHelpFly;
	}

	public void setIsBookSelfHelpFly(String isBookSelfHelpFly) {
		this.isBookSelfHelpFly = isBookSelfHelpFly;
	}

	public boolean isIspand() {
		return ispand;
	}

	public void setIspand(boolean ispand) {
		this.ispand = ispand;
	}

	public List<FlightCabinSeatObj> getCabinSeatList() {
		return cabinSeatList;
	}

	public void setCabinSeatList(List<FlightCabinSeatObj> cabinSeatList) {
		this.cabinSeatList = cabinSeatList;
	}

}
