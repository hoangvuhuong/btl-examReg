package com.examReg.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.examReg.model.CaThiPhongThi;

@Repository
public class CaThiPhongThiRepository {
	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;
	public int create(CaThiPhongThi ctpt) {
		String sql = "INSERT INTO cathi_phongthi(cathi_id,phongthi_id,slot_con_lai) VALUES(:cathiId, :phongthiId,:slotConLai);";
		Map<String ,Object> maps = new HashMap<String, Object>();
		maps.put("cathiId", ctpt.getCathiId());
		maps.put("phongthiId", ctpt.getPhongthiId());
		maps.put("slotConLai", ctpt.getSlotConLai());
		return jdbcTemplate.update(sql, maps);
	}
	public CaThiPhongThi getCaThiPhongThi(CaThiPhongThi ctpt) {
		String sql = "SELECT * FROM cathi_phongthi WHERE cathi_id=:cathiId AND phongthi_id=:phongthiId AND slot_con_lai=:slotConLai;";
		Map<String ,Object> maps = new HashMap<String, Object>();
		maps.put("cathiId", ctpt.getCathiId());
		maps.put("phongthiId", ctpt.getPhongthiId());
		maps.put("slotConLai", ctpt.getSlotConLai());
		return jdbcTemplate.queryForObject(sql, maps, new BeanPropertyRowMapper<CaThiPhongThi>(CaThiPhongThi.class));
	}
}
