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

import com.examReg.contract.ResponseContract;
import com.examReg.model.CaThi;
import com.examReg.model.HocPhan;
import com.examReg.model.ResponseCreateExam;
import com.examReg.model.User;
import com.examReg.repository.CaThiPhongThiRepository;
import com.examReg.repository.CaThiRepository;
import com.examReg.repository.HocPhanRepository;
import com.examReg.repository.UserRepository;
import com.examReg.service.CathiPhongthiService;
import com.examReg.service.IUserService;
import com.examReg.service.UserService;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class AdminController {
		@Autowired
		IUserService userService;
		@Autowired 
		CaThiRepository caThiRepository;
		@Autowired
		HocPhanRepository hpRepository;
		@Autowired
		CathiPhongthiService ctptService;
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
		@PostMapping("/create-exam")
		public ResponseContract<?> taoCaThi(@RequestBody ResponseCreateExam response){
			try {
				caThiRepository.create(response.getCaThi());
				CaThi caThi =caThiRepository.getCaThi(response.getCaThi());
				HocPhan hp = new HocPhan();
				hp.setCathiId(caThi.getId());
				hp.setMaHp(response.getCourseCode());
				hp.setName(response.getCourseName());
				hpRepository.create(hp);
				// tao cathi_phongthi
				ctptService.create(response.getPhongThi(), caThi.getId());
				//tao user_cathi
				userService.createUserCathi(caThi.getId(), response.getPhongThi(),response.getListStudent());
				//tao user_hocphan
				userService.createUserHocphan(hpRepository.getHocPhan(hp).getId(), response.getListStudent());
				// danh sach cam thi
				userService.updateCamthi(response.getBanStudent(), hpRepository.getHocPhan(hp).getId());
				return new ResponseContract<String>("200", "OK", "Tao Ca Thi Thanh Cong!");
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				return new ResponseContract<String>("Loi", e.getMessage(), "Tao Ca Thi That Bai!");
			}
			
		}
}
