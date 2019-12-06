package com.examReg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examReg.model.CaThiPhongThi;
import com.examReg.model.PhongThi;
import com.examReg.repository.CaThiPhongThiRepository;
import com.examReg.repository.CaThiRepository;
import com.examReg.repository.PhongThiRepository;
@Service
public class CathiPhongthiService {
	@Autowired
	PhongThiRepository phongThiRepository;
	@Autowired
	CaThiPhongThiRepository ctptRepository;
	public int create(String tenPhong,int cathiId) {
		PhongThi phongThi = phongThiRepository.findByName(tenPhong);
		CaThiPhongThi ctpt = new CaThiPhongThi();
		ctpt.setCathiId(cathiId);
		ctpt.setPhongthiId(phongThi.getId());
		ctpt.setSlotConLai(phongThi.getSlot());
		return ctptRepository.create(ctpt);
	}
}
