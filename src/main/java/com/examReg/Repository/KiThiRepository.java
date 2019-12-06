package com.examReg.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.examReg.model.KiThi;

@Repository
public class KiThiRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	public KiThi create(KiThi kithi) {
		String sql = "INSERT INTO ki_thi(name) VALUES (:name);";
		Map<String, String> maps = new HashMap<String, String>();
		maps.put("name", kithi.getName());
		 jdbcTemplate.update(sql,maps);
		 return findByName(kithi.getName());
	}
	public KiThi findByName(String name) {
		String sql = "SELECT * FROM ki_thi WHERE name =:name;";
		Map<String, String> maps = new HashMap<String,String>();
		maps.put("name", name);
		return jdbcTemplate.queryForObject(sql,maps,new BeanPropertyRowMapper<KiThi>(KiThi.class));
	}
}
