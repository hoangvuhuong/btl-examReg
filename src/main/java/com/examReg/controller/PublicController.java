package com.examReg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examReg.contract.ResponseContract;
import com.examReg.model.User;
import com.examReg.service.IUserService;

@RestController
@RequestMapping("/home")
public class PublicController {
	@Autowired
	IUserService userService;
	
	
}
