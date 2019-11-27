package com.examReg.Repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.examReg.model.HocPhan;
import com.fasterxml.jackson.core.sym.Name;

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
//	public HocPhan getHocPhan(HocPhan hp) {
//		String sql = "SELECT * FROM hoc_phan";
//	}
}
