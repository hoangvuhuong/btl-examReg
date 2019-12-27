package com.examReg.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
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

	@PostMapping("/create-user")
	public ResponseContract<?> createUser(@RequestBody List<Map<String, String>> input) {
		for (Map<String, String> a : input) {
			User user = new User();
			user.setUserName(a.get("username"));
			user.setPass(a.get("password"));
			userService.create(user);
		}
		return new ResponseContract<String>("200", "OK", null);
	}

	@GetMapping("/show-user")
	public ResponseContract<?> showuUser() {
		return userService.getAll();
	}

	// show ca thi
	@GetMapping("/show-exam")
	public ResponseContract<?> getAllExam() {
		return userService.getAllExam();
	}

	// tao ca thi
	@PostMapping("/create-exam")
	@Transactional(rollbackFor = Exception.class)
	public ResponseContract<?> taoCaThi(@RequestBody ResponseCreateExam response) {
		try {
			int cathiId = caThiRepository.create(response.getCaThi());
			//CaThi caThi = caThiRepository.getCaThi(response.getCaThi());
			CaThi caThi = caThiRepository.getById(cathiId);
			HocPhan hp = new HocPhan();
			hp.setCathiId(caThi.getId());
			hp.setMaHp(response.getCourseCode());
			hp.setName(response.getCourseName());
			hpRepository.create(hp);
			// tao cathi_phongthi
			for (String tenPhongThi : response.getPhongThi()) {
				ctptService.create(tenPhongThi, caThi.getId());
			}
//			// tao user_cathi
//			for(String tenPhongThi :response.getPhongThi() ) {
//			userService.createUserCathi(caThi.getId(), tenPhongThi, response.getListStudent());
//			}
			// tao user_hocphan
			userService.createUserHocphan(hpRepository.getHocPhan(hp).getId(), response.getListStudent());
			// danh sach cam thi
			userService.updateCamthi(response.getBanStudent(), hpRepository.getHocPhan(hp).getId());
			return new ResponseContract<String>("200", "OK", "Tao Ca Thi Thanh Cong!");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseContract<String>("Loi", e.getMessage(), "Tao Ca Thi That Bai!");
		}

	}

	@GetMapping("/get-all-pt")
	public ResponseContract<?> getAllPt() {
		return userService.getAllPt();
	}

	// show tat ca hoc phan
	@GetMapping("/show-subjects")
	public ResponseContract<?> getAllSubjects() {
		return userService.getAllHocPhan();
	}
	
	@GetMapping("/download")
	public ResponseContract<?> download(){
		return userService.adminDownload();
	}
	@DeleteMapping("/delete-course")
	public ResponseContract<?> deleteCourse(@RequestBody Map<String, Object> input){
		return userService.deleteCourse((String)input.get("tenMon"), (String)input.get("tenPhongThi"));
		
	}
}
