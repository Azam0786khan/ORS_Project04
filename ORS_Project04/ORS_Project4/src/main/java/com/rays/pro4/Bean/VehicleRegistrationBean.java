package com.rays.pro4.Bean;

public class VehicleRegistrationBean extends BaseBean {
	
	private String ownerName;
	private String address;
	private String state;
	private String city;
	private String phoneNo;
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return ownerName;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return id+"";
	}

	
	
}
