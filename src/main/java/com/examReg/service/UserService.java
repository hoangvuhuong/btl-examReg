package com.examReg.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.omg.CORBA.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.examReg.contract.ResponseContract;
import com.examReg.model.CaThi;
import com.examReg.model.CaThiPhongThi;
import com.examReg.model.PhongThi;
import com.examReg.model.User;
import com.examReg.model.UserCathi;
import com.examReg.model.UserHocphan;
import com.examReg.repository.CaThiPhongThiRepository;
import com.examReg.repository.CaThiRepository;
import com.examReg.repository.KiThiRepository;
import com.examReg.repository.PhongThiRepository;
import com.examReg.repository.UserCathiRepository;
import com.examReg.repository.UserHocPhanRepository;
import com.examReg.repository.UserRepository;
import com.examReg.security.TokenProvider;
import com.examReg.security.UserPrincipal;


@Service
public class UserService implements IUserService {
	@Autowired
	KiThiRepository kiThiRep;
	@Autowired
	PhongThiRepository phongthiRep;
	@Autowired
	private CaThiPhongThiRepository caThiphongThiRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenProvider tokenProvider;
	@Autowired
	CaThiRepository cathiRepository;
	@Autowired
	UserCathiRepository ucRepository;
	@Autowired
	UserHocPhanRepository uhRepository;
	@Override
	public String login(String userName, String pass) {
		Optional<User> userOptional = userRepository.findByUserName(userName);
		User user = null;
		if (userOptional.isPresent()) {
			user = userOptional.get();
		} else {
			return null;
		}

		if (checkPass(pass, user.getPass())) {
			UserPrincipal userPrincipal = UserPrincipal.create(user);
			String jwtToken = tokenProvider.createToken(userPrincipal);
			return jwtToken;
		}
		return null;
	}

	@Override
	public ResponseContract<?> create(User user) {
		String pass = encrytePassword(user.getPass());
		user.setPass(pass);
		return new ResponseContract<User>("ok", "ok", userRepository.create(user));
	}

	public static String encrytePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	public static boolean checkPass(String pass, String dataBasePass) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(pass, dataBasePass);
	}

	@Override
	public ResponseContract<?> getAll() {
		try {
			return new ResponseContract<List<User>>("200", "Ok", userRepository.getAll());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public ResponseContract<?> taoCathi(CaThi cathi, String tenPhongThi) {
		try {
			
			cathiRepository.create(cathi);
			CaThi ct = cathiRepository.getCaThi(cathi);
			PhongThi pt = phongthiRep.findByName(tenPhongThi);
			CaThiPhongThi ctpt = new CaThiPhongThi();
			ctpt.setCathiId(ct.getId());
			ctpt.setPhongthiId(pt.getId());
			ctpt.setSlotConLai(pt.getSlot());
			caThiphongThiRepository.create(ctpt);
			

			return null;

		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@Override
	public int doiMk(Map<String,String> input) {
		try {
			if(input.get("pass1").equals(input.get("pass2"))) {
				String newPass = encrytePassword(input.get("pass1"));
				return userRepository.doiMk(SecurityContextHolder.getContext().getAuthentication().getName(), newPass);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 0;
		
	}
	@Override
	public int createUserCathi(int caThiId, String tenPhong, List<String> listUser) {
		try {
			PhongThi pt = phongthiRep.findByName(tenPhong);
			for(String username : listUser) {
				Optional<User> userOptional = userRepository.findByUserName(username);
				User user = null;
				if(userOptional.isPresent()) {
					user = userOptional.get();
				}
				else {
					return 0;
				}
				String SBD = pt.getName() + username;
				UserCathi uc = new UserCathi();
				uc.setCathiId(caThiId);
				uc.setPhongthiId(pt.getId());
				uc.setSbd(SBD);
				uc.setUserId(user.getUserId());
				ucRepository.create(uc);
				
		}
			return listUser.size();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		
		
		}

	@Override
	public int updateCamthi(List<String> listUser, int hocPhanId) {
		// TODO Auto-generated method stub
		
		for(String username : listUser) {
			User user = userRepository.findByUsername(username);
			if(user != null) {
				uhRepository.updateDK(user.getUserId(), hocPhanId, false);
			}
		}
		return listUser.size();
	}

	@Override
	public int createUserHocphan(int hocPhanId, List<String> listUser) {
		// TODO Auto-generated method stub
		for(String username : listUser) {
			User user = userRepository.findByUsername(username);
			if(user != null) {
			UserHocphan uh = new UserHocphan();
			uh.setHocphanId(hocPhanId);
			uh.setUserId(user.getUserId());
			uh.setDkThi(true);
			uhRepository.create(uh);
			}
		}
		return listUser.size();
	}
		
		
	
}
