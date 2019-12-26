package com.examReg.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.examReg.model.HocPhan;
@Repository
public class HocPhanRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	public int create(HocPhan hocPhan) {
		String sql = "INSERT INTO hoc_phan(name, cathi_id, ma_hp) VALUES(:name, :cathiId, :maHp);";
		Map <String, Object > maps = new HashMap<String,Object>();
		maps.put("name", hocPhan.getName());
		maps.put("cathiId",hocPhan.getCathiId());
		maps.put("maHp", hocPhan.getMaHp());
		return jdbcTemplate.update(sql,maps);
		
	}
	public HocPhan getHocPhan(HocPhan hp) {
		String sql = "SELECT * FROM hoc_phan WHERE name =:name AND cathi_id =:cathiId AND ma_hp=:maHp";
		Map<String , Object> maps = new HashMap<String , Object>();
		maps.put("name", hp.getName());
		maps.put("cathiId",hp.getCathiId());
		maps.put("maHp", hp.getMaHp());
		return jdbcTemplate.queryForObject(sql, maps, new BeanPropertyRowMapper<HocPhan>(HocPhan.class));
	}
	public List<HocPhan> getAll(){
		String sql = "SELECT * FROM hoc_phan;";
		return jdbcTemplate.query(sql,new HashMap<>(),new BeanPropertyRowMapper<HocPhan>(HocPhan.class));
	}
	public HocPhan getById(int id){
		String sql = "SELECT * FROM hoc_phan WHERE id =:id";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("id", id);
		return jdbcTemplate.queryForObject(sql, maps, new BeanPropertyRowMapper<HocPhan>(HocPhan.class));
	}
	public HocPhan getByCaThiId(int cathiId) {
		String sql = "SELECT * FROM hoc_phan WHERE cathi_id =:cathiId;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("cathiId", cathiId);
		return jdbcTemplate.queryForObject(sql, maps, new BeanPropertyRowMapper<HocPhan>(HocPhan.class));
	}
	
	public String name(int cathiId) {
		String sql = "SELECT name from hoc_phan WHERE cathi_id =:cathiId;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("cathiId",cathiId);
		return jdbcTemplate.queryForObject(sql, maps, String.class);
		
				
	}
}
