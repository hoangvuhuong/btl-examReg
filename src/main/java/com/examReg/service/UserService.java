package com.examReg.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.examReg.Repository.UserRepository;
import com.examReg.contract.ResponseContract;
import com.examReg.model.User;
import com.examReg.security.TokenProvider;
import com.examReg.security.UserPrincipal;



@Service
public class UserService implements IUserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenProvider tokenProvider;

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
	public static boolean checkPass(String pass,String dataBasePass) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(pass, dataBasePass);
	}

	@Override
	public ResponseContract<?> getAll() {
		try {
			return new ResponseContract<List<User>>("200","Ok",userRepository.getAll());
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
}
