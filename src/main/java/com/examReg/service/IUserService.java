package com.examReg.service;

import java.util.List;
import java.util.Map;

import com.examReg.contract.ResponseContract;
import com.examReg.model.CaThi;
import com.examReg.model.ResponseRegis;
import com.examReg.model.User;

public interface IUserService {
	public String login(String userName, String pass);

	public ResponseContract<?> create(User user);

	public ResponseContract<?> getAll();

	public ResponseContract<?> taoCathi(CaThi cathi, String tenPhongThi);

	public int doiMk(Map<String, String> input);

	public int createUserCathi(int caThiId, String tenPhong, List<String> listUser);

	public int updateCamthi(List<String> listUser, int hocPhanId);

	public int createUserHocphan(int hocPhanId, List<String> listUser);
	
	public ResponseContract<?> getAllHocPhan();
	
	public ResponseContract<?> getAllHocPhanByUserId(int userId);
	
	public ResponseContract<?> getAllExam();
	
	public ResponseContract<?> getAllExamByUserId(int userId);
	
	public ResponseContract<?> DangKiThi(ResponseRegis resonseReg);
	
	public ResponseContract<?> getDownload(int userId);
	
	public ResponseContract<?> getAllPt();
	
	public ResponseContract<?> adminDownload();
}
