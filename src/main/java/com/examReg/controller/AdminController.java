package com.examReg.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examReg.Repository.UserRepository;
import com.examReg.contract.ResponseContract;
import com.examReg.model.User;
import com.examReg.service.IUserService;
import com.examReg.service.UserService;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
		@Autowired
		IUserService userService;
		@PostMapping("/create")
		public ResponseContract<?> createUser(@RequestBody List<Map<String ,String>> input){
			for(Map<String,String> a: input) {
			User user = new User();
			user.setUserName(a.get("username"));
			user.setPass(a.get("password"));
			userService.create(user);
			}
			return new ResponseContract<String>("200","OK",null); 
		}
		@GetMapping("/show")
		public ResponseContract<?> showuUser(){
			return  userService.getAll();
		}
		
}
