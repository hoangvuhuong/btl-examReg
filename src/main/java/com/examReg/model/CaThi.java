package com.examReg.model;

import java.util.Date;

public class CaThi {
	private int id;
	private Date ngayThi;
	private Date start;
	private Date end;
	private int kithiId;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getNgayThi() {
		return ngayThi;
	}
	public void setNgayThi(Date ngayThi) {
		this.ngayThi = ngayThi;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public int getKithiId() {
		return kithiId;
	}
	public void setKithiId(int kithiId) {
		this.kithiId = kithiId;
	}
	
}
