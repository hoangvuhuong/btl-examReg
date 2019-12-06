package com.examReg.model;

import java.util.List;

public class ResponseCreateExam {
	private String courseName;
	private String courseCode;
	private List<String> listStudent;
	private List<String> banStudent;
	private CaThi caThi;
	private String phongThi;
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseCode() {
		return courseCode;
	}
	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}
	
	public List<String> getListStudent() {
		return listStudent;
	}
	public void setListStudent(List<String> listStudent) {
		this.listStudent = listStudent;
	}
	public List<String> getBanStudent() {
		return banStudent;
	}
	public void setBanStudent(List<String> banStudent) {
		this.banStudent = banStudent;
	}
	public CaThi getCaThi() {
		return caThi;
	}
	public void setCaThi(CaThi caThi) {
		this.caThi = caThi;
	}
	public String getPhongThi() {
		return phongThi;
	}
	public void setPhongThi(String phongThi) {
		this.phongThi = phongThi;
	}
	
}
