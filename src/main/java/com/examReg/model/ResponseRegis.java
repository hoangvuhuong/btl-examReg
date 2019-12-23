package com.examReg.model;

public class ResponseRegis {
	private Boolean isRegis;
	private Integer cathiId;
	private Integer userId;
	private String tenPhong;
	private String maMon;
	public Boolean getIsRegis() {
		return isRegis;
	}
	public void setIsRegis(Boolean isRegis) {
		this.isRegis = isRegis;
	}
	public Integer getCathiId() {
		return cathiId;
	}
	public void setCathiId(Integer cathiId) {
		this.cathiId = cathiId;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public String getTenPhong() {
		return tenPhong;
	}
	public void setTenPhong(String tenPhong) {
		this.tenPhong = tenPhong;
	}
	public String getMaMon() {
		return maMon;
	}
	public void setMaMon(String maMon) {
		this.maMon = maMon;
	}
	
}
