package com.examReg.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.examReg.model.PhongThi;

@Repository
public class PhongThiRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	public int create(PhongThi pt) {
		String sql="INSERT INTO phong_thi(slot,name) VALUES(:slot,:name);";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("slot", pt.getSlot());
		maps.put("name", pt.getName());
		return jdbcTemplate.update(sql, maps);
	}
	public PhongThi findByName(String name) {
		String sql = "SELECT * FROM phong_thi WHERE name=:name;";
		Map<String, Object> maps = new HashMap<String, Object>();
		
		maps.put("name", name);
		return jdbcTemplate.queryForObject(sql,maps, new BeanPropertyRowMapper<PhongThi>(PhongThi.class));
	}
	public String getNameById(int id) {
		String sql= "SELECT name FROM phong_thi WHERE id =:id;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id", id);
		return jdbcTemplate.queryForObject(sql, maps, String.class);
	}
	public Integer getIdByName(String name) {
		String sql = "SELECT id FROM phong_thi WHERE name =:name";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("name",name);
		return jdbcTemplate.queryForObject(sql,maps, Integer.class);
	}
	public List<String> getAll(){
		
		String sql = "SELECT name FROM phong_thi";
		return jdbcTemplate.queryForList(sql, new HashMap<>(), String.class);
	}
}
