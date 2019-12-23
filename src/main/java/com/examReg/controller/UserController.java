package com.examReg.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examReg.contract.ResponseContract;
import com.examReg.model.ResponseRegis;
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
	@GetMapping("/get-subjects/{id}")
	public ResponseContract<?> getCathiByUserId(@PathVariable Integer id){
		return userService.getAllExamByUserId(id);
	}
	@PostMapping("/registration")
	public ResponseContract<?> registration(@RequestBody List<ResponseRegis> responses){
		ResponseContract<?> res = null;
		for(ResponseRegis response : responses) {
			try {
		 res = userService.DangKiThi(response);
			}catch (Exception e) {
				e.printStackTrace();
				break;
			}
		}
		return res;
	}
}
