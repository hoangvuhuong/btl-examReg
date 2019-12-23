package com.examReg.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.examReg.model.CaThi;
import com.examReg.model.KiThi;
import com.examReg.service.CathiPhongthiService;

@Repository
public class CaThiRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	public int create(CaThi caThi) {
		String sql = "INSERT INTO ca_thi(ngay_thi,start,end,kithi_id,name) VALUES (:ngayThi,:start,:end,:kithiId,:name);";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(caThi);
		  jdbcTemplate.update(sql,source,keyHolder);
		  return keyHolder.getKey().intValue();
		 
	}
	public CaThi getCaThi(CaThi caThi) {
		String sql = "SELECT * FROM ca_thi WHERE ngay_thi = :ngayThi and start =:start and end =:end and name=:name;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("ngayThi", caThi.getNgayThi());
		maps.put("start", caThi.getStart());
		maps.put("end", caThi.getEnd());
		
		maps.put("name", caThi.getName());
		return  jdbcTemplate.queryForObject(sql, maps,new BeanPropertyRowMapper<CaThi>(CaThi.class));
		 
	}
	public List<CaThi> getAll(){
		String sql = "SELECT * FROM ca_thi";
		return jdbcTemplate.query(sql,new HashMap<>(), new BeanPropertyRowMapper<CaThi>(CaThi.class));
	}
	public int delete(int id) {
		String sql = "DELETE FROM ca_thi WHERE id=:id;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id",id);
		return jdbcTemplate.update(sql, maps);
	}
	public CaThi getById(int id) {
		String sql = "SELECT * FROM ca_thi WHERE id =:id";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id", id);
		return jdbcTemplate.queryForObject(sql, maps, new BeanPropertyRowMapper<CaThi>(CaThi.class));
	}
}
