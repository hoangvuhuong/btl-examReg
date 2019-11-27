package com.examReg.Repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.examReg.model.CaThi;
import com.examReg.model.KiThi;

@Repository
public class CaThiRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	public int create(CaThi caThi) {
		String sql = "INSERT INTO ca_thi(ngay_thi,start,end,kithi_id,name) VALUES (:ngayThi,:start,:end,:kithiId,:name);";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("ngayThi", caThi.getNgayThi());
		maps.put("start", caThi.getStart());
		maps.put("end", caThi.getEnd());
		maps.put("kithiId", caThi.getKithiId());
		maps.put("name", caThi.getName());
		return  jdbcTemplate.update(sql,maps);
		 
	}
	public CaThi getCaThi(CaThi caThi) {
		String sql = "SELECT * FROM ca_thi WHERE ngay_thi = :ngayThi and start =:start and end =:end and name=:name and kithi_id=:kithiId;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("ngayThi", caThi.getNgayThi());
		maps.put("start", caThi.getStart());
		maps.put("end", caThi.getEnd());
		maps.put("kithiId", caThi.getKithiId());
		maps.put("name", caThi.getName());
		return  jdbcTemplate.queryForObject(sql, maps,new BeanPropertyRowMapper<CaThi>(CaThi.class));
		 
	}
}
