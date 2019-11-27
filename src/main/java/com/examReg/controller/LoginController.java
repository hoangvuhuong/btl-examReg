package com.examReg.controller;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.examReg.contract.ResponseContract;
import com.examReg.model.User;
import com.examReg.service.IUserService;


@RestController
public class LoginController {
	@Autowired IUserService userService;
	@PostMapping("/login")
	public ResponseContract<?> login(@RequestBody Map<String, String> body){
		String jwt=userService.login(body.get("userName"), body.get("userPass"));
		if (jwt==null) {
			return new ResponseContract<String>("FAIL", "login FAIL", "User name or password incorrect!");
		}
		return new ResponseContract<String>("SUCCESS", "login SUCCESS", jwt);
	}
	@GetMapping("/demo")
	public ResponseContract<?> demo(){
		return new ResponseContract<String>("200", "Passjwt", "ok hihi");
	}
	@PostMapping("/login/signup")
	public ResponseContract<?> signup(@RequestBody Map<String,String> body){
		User user = new User();
		user.setUserName(body.get("username"));
		user.setPass(body.get("password"));
		return userService.create(user);
	}
}
