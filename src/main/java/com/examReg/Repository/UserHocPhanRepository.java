package com.examReg.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.examReg.model.UserHocphan;

@Repository
public class UserHocPhanRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	public int create(UserHocphan up) {
		String sql = "INSERT INTO user_hocphan(user_id,hocphan_id,dk_thi) VALUES(:userId, :hocphanId,:dkThi);";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("userId",up.getUserId());
		maps.put("hocphanId", up.getHocphanId());
		maps.put("dkThi",up.isDkThi());
		return jdbcTemplate.update(sql, maps);
	}
	public UserHocphan getUserHocphan(int userId, int hocphanId) {
		String sql = "SELECT * FROM user_hocphan WHERE user_id =:userId AND hocphan_id =:hocphanId;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("userId",userId);
		maps.put("hocphanId", hocphanId);
		return jdbcTemplate.queryForObject(sql, maps, new BeanPropertyRowMapper<UserHocphan>(UserHocphan.class));
	}
	public int updateDK(int userId, int hocphanId,boolean dkThi) {
		String sql = "UPDATE user_hocphan SET dk_thi=:dkThi WHERE user_id =:userId AND hocphan_id =:hocphanId;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("dkThi",dkThi);
		maps.put("userId",userId);
		maps.put("hocphanId", hocphanId);
		return jdbcTemplate.update(sql, maps);
	}
}
