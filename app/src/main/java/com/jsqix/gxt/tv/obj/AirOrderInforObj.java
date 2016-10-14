package com.jsqix.gxt.tv.obj;

import java.util.ArrayList;
import java.util.List;

public class AirOrderInforObj extends BaseObj {
	private String order_number;
	private String order_time;
	private String from_station;
	private String to_station;
	private String start_time;
	private String arrive_time;
	private String start_date;
	private String lishi;
	private String status;
	private String air_lines;
	private String air_no;
	private String air_type;
	private String contact_name;
	private String contact_tel;
	private String contact_address;
	private String vouchersflag;
	private String vouchersmoney;
	private String remark;

	private List<AirContactObj> contacts = new ArrayList<AirContactObj>();

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getOrder_time() {
		return order_time;
	}

	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}

	public String getFrom_station() {
		return from_station;
	}

	public void setFrom_station(String from_station) {
		this.from_station = from_station;
	}

	public String getTo_station() {
		return to_station;
	}

	public void setTo_station(String to_station) {
		this.to_station = to_station;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getArrive_time() {
		return arrive_time;
	}

	public void setArrive_time(String arrive_time) {
		this.arrive_time = arrive_time;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAir_lines() {
		return air_lines;
	}

	public void setAir_lines(String air_lines) {
		this.air_lines = air_lines;
	}

	public String getAir_no() {
		return air_no;
	}

	public void setAir_no(String air_no) {
		this.air_no = air_no;
	}

	public String getAir_type() {
		return air_type;
	}

	public void setAir_type(String air_type) {
		this.air_type = air_type;
	}

	public String getContact_name() {
		return contact_name;
	}

	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}

	public String getContact_tel() {
		return contact_tel;
	}

	public void setContact_tel(String contact_tel) {
		this.contact_tel = contact_tel;
	}

	public String getContact_address() {
		return contact_address;
	}

	public void setContact_address(String contact_address) {
		this.contact_address = contact_address;
	}

	public String getVouchersflag() {
		return vouchersflag;
	}

	public void setVouchersflag(String vouchersflag) {
		this.vouchersflag = vouchersflag;
	}

	public String getVouchersmoney() {
		return vouchersmoney;
	}

	public void setVouchersmoney(String vouchersmoney) {
		this.vouchersmoney = vouchersmoney;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public String getLishi() {
		return lishi;
	}

	public void setLishi(String lishi) {
		this.lishi = lishi;
	}

	public List<AirContactObj> getContacts() {
		return contacts;
	}

	public void setContacts(List<AirContactObj> contacts) {
		this.contacts = contacts;
	}

}
