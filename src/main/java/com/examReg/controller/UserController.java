package com.examReg.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examReg.contract.ResponseContract;
import com.examReg.service.IUserService;

@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/user")
public class UserController {
	@Autowired
	IUserService userService;
	@PostMapping("/setPass")
	public ResponseContract<?> setPass(@RequestBody Map<String,String> input){
		try {
		return new ResponseContract<Integer>("200", "OK",userService.doiMk(input) ) ;
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseContract<String>("Loi", e.getMessage(),null ) ;
		}
	}
}
