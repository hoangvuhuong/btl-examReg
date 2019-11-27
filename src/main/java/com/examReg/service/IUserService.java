package com.examReg.service;

import com.examReg.contract.ResponseContract;
import com.examReg.model.User;

public interface IUserService {
	public String login(String userName,String pass);
	public ResponseContract<?> create(User user);
	public ResponseContract<?> getAll();
}
