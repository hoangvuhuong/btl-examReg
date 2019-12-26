package com.examReg.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.examReg.model.User;


@Repository
public class UserRepository {
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	public User create(User user) {
		String sql="INSERT INTO `user` (`username`, `pass`, `role`, `name`) VALUES (:userName, :userPass, :userRole, :name);";
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("userName", user.getUserName());
		params.put("userPass", user.getPass());
		params.put("userRole", user.getRole());
		params.put("name", user.getName());
		
		jdbcTemplate.update(sql, params);
		return findByUserName(user.getUserName()).orElseGet(null);
	}
	public Optional<User> findByUserName(String userName) {
		String sql="SELECT * FROM user where username =:userName;";
		Map<String, Object> argMap = new HashMap<>();
		argMap.put("userName", userName);
		try {
			return Optional
					.ofNullable(jdbcTemplate.queryForObject(sql, argMap, new BeanPropertyRowMapper<User>(User.class)));
		} catch (Exception e) {
			return Optional.ofNullable(null);
		
	}
	}
	public Optional<User> findByUserId(int userId) {
		String sql="SELECT * FROM user where user_id =:userId;";
		Map<String, Object> argMap = new HashMap<>();
		argMap.put("userId", userId);
		try {
			return Optional
					.ofNullable(jdbcTemplate.queryForObject(sql, argMap, new BeanPropertyRowMapper<User>(User.class)));
		} catch (Exception e) {
			return Optional.ofNullable(null);
		
	}
	}
	public Optional<User> findByMssv(int mssv) {
		String sql="SELECT * FROM user where mssv =:mssv;";
		Map<String, Object> argMap = new HashMap<>();
		argMap.put("mssv", mssv);
		try {
			return Optional
					.ofNullable(jdbcTemplate.queryForObject(sql, argMap, new BeanPropertyRowMapper<User>(User.class)));
		} catch (Exception e) {
			return Optional.ofNullable(null);
		
	}
	}
	public List<User> getAll(){
		String sql ="SELECT * FROM user";
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
	}
	public int doiMk(String username, String newPass) {
		String sql ="UPDATE user SET pass =:newPass WHERE username =:username;";
		Map<String,String> maps = new HashMap<String,String>();
		maps.put("newPass", newPass);
		maps.put("username",username);
		return jdbcTemplate.update(sql, maps);
	}
	public User findByUsername(String userName) {
		
		String sql="SELECT * FROM user where username =:userName;";
		Map<String, Object> argMap = new HashMap<>();
		argMap.put("userName", userName);
		try {
		return jdbcTemplate.queryForObject(sql, argMap, new BeanPropertyRowMapper<User>(User.class));
		}catch (Exception e) {
			return null;
		}
	}
	public User findById(int userId) {
		String sql="SELECT * FROM user where user_id =:userId;";
		Map<String, Object> argMap = new HashMap<>();
		argMap.put("userId", userId);
		return jdbcTemplate.queryForObject(sql, argMap, new BeanPropertyRowMapper<User>(User.class));
	}
}
