package com.examReg.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CaThi {
	private int id;
	private Date ngayThi;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private Date start;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
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
