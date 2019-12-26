package com.examReg.model;

public class Exam {
	private int cathiId;
	private String tenCaThi;
	private String tenMon;
	private String maMon;
	private String start;
	private String end;
	private String date;
	private String tenPhongThi;
	private Boolean dkThi;
	private Boolean isDk;
	private Integer slotConLai;
	
	
	public Integer getSlotConLai() {
		return slotConLai;
	}
	public void setSlotConLai(Integer slotConLai) {
		this.slotConLai = slotConLai;
	}
	public Boolean getIsDk() {
		return isDk;
	}
	public void setIsDk(Boolean isDk) {
		this.isDk = isDk;
	}
	public Boolean getDkThi() {
		return dkThi;
	}
	public void setDkThi(Boolean dkThi) {
		this.dkThi = dkThi;
	}
	public int getCathiId() {
		return cathiId;
	}
	public void setCathiId(int cathiId) {
		this.cathiId = cathiId;
	}
	public String getTenCaThi() {
		return tenCaThi;
	}
	public void setTenCaThi(String tenCaThi) {
		this.tenCaThi = tenCaThi;
	}
	public String getTenMon() {
		return tenMon;
	}
	public void setTenMon(String tenMon) {
		this.tenMon = tenMon;
	}
	public String getMaMon() {
		return maMon;
	}
	public void setMaMon(String maMon) {
		this.maMon = maMon;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTenPhongThi() {
		return tenPhongThi;
	}
	public void setTenPhongThi(String tenPhongThi) {
		this.tenPhongThi = tenPhongThi;
	}
	
}
