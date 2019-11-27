package com.examReg.model;

public class HocPhan {
	private int id;
	private String name;
	private int cathiId;
	private String maHp;
	
	public String getMaHp() {
		return maHp;
	}
	public void setMaHp(String maHp) {
		this.maHp = maHp;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCathiId() {
		return cathiId;
	}
	public void setCathiId(int cathiId) {
		this.cathiId = cathiId;
	}
}
