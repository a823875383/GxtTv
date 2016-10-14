package com.jsqix.gxt.tv.obj;

import android.os.Parcel;
import android.os.Parcelable;

public class TrainContactObj extends BaseObj implements Parcelable {
	private String code;
	private String name;
	private String sex;
	private String birthdate;
	private String contry;
	private String IDType;
	private String ID;
	private String passenger;
	private String phone;
	private String seatType;

	public TrainContactObj() {
		super();
	}

	public TrainContactObj(String name, String sex, String birthdate, String contry,
			String iDType, String iD, String passenger, String phone) {
		super();
		this.name = name;
		this.sex = sex;
		this.birthdate = birthdate;
		this.contry = contry;
		IDType = iDType;
		ID = iD;
		this.passenger = passenger;
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getContry() {
		return contry;
	}

	public void setContry(String contry) {
		this.contry = contry;
	}

	public String getIDType() {
		return IDType;
	}

	public void setIDType(String iDType) {
		IDType = iDType;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getPassenger() {
		return passenger;
	}

	public void setPassenger(String passenger) {
		this.passenger = passenger;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSeatType() {
		return seatType;
	}

	public void setSeatType(String seatType) {
		this.seatType = seatType;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(code);
		dest.writeString(name);
		dest.writeString(sex);
		dest.writeString(birthdate);
		dest.writeString(contry);
		dest.writeString(IDType);
		dest.writeString(ID);
		dest.writeString(passenger);
		dest.writeString(phone);
		dest.writeString(seatType);

	}

	public static final Creator<TrainContactObj> CREATOR = new Creator<TrainContactObj>() {

		@Override
		public TrainContactObj createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			TrainContactObj contactObj = new TrainContactObj();
			contactObj.code = source.readString();
			contactObj.name = source.readString();
			contactObj.sex = source.readString();
			contactObj.birthdate = source.readString();
			contactObj.contry = source.readString();
			contactObj.IDType = source.readString();
			contactObj.ID = source.readString();
			contactObj.passenger = source.readString();
			contactObj.phone = source.readString();
			contactObj.seatType = source.readString();
			return contactObj;
		}

		@Override
		public TrainContactObj[] newArray(int size) {
			// TODO Auto-generated method stub
			return new TrainContactObj[size];
		}
	};

}
