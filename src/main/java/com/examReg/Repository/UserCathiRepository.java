package com.examReg.repository;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.examReg.model.User;
import com.examReg.model.UserCathi;
import com.fasterxml.jackson.core.sym.Name;

@Repository
public class UserCathiRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	public int create(UserCathi uc) {
		String sql = "INSERT INTO user_cathi(user_id, cathi_id , phongthi_id,sbd) VALUES (:userId, :cathiId, :phongthiId,:sbd);";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("userId",uc.getUserId());
		maps.put("cathiId",uc.getCathiId());
		maps.put("phongthiId", uc.getPhongthiId());
		maps.put("sbd", uc.getSbd());
		return jdbcTemplate.update(sql,maps);
		
	}
	public UserCathi getUerCathi(UserCathi uc) {
		String sql = "SELECT * FROM user_cathi WHERE user_id =:userId AND cathi_id =:cathiId AND phongthi_id =:phongthiId AND sbd =:sbd";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("userId",uc.getUserId());
		maps.put("cathiId",uc.getCathiId());
		maps.put("phongthiId", uc.getPhongthiId());
		maps.put("sbd", uc.getSbd());
		return jdbcTemplate.queryForObject(sql, maps, new BeanPropertyRowMapper<UserCathi>(UserCathi.class));
	}
	public UserCathi getByIds(int userId, int cathiId, int phongthiId) {
		String sql = "SELECT * FROM user_cathi WHERE user_id =:userId AND cathi_id =:cathiId AND phongthi_id =:phongthiId;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("userId", userId);
		maps.put("cathiId", cathiId);
		maps.put("phongthiId", phongthiId);
		return jdbcTemplate.queryForObject(sql, maps, new BeanPropertyRowMapper<UserCathi>(UserCathi.class));
	}
	public int delete(int id) {
		String sql = "DELETE FROM user_cathi WHERE id =:id;";
		Map<String,Object> maps = new HashMap<String, Object>();
		maps.put("id", id);
		return jdbcTemplate.update(sql,maps);
	}
}
