package com.examReg.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.examReg.model.CaThiPhongThi;
import com.examReg.service.CathiPhongthiService;

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
	public List<Integer> getPhongthiIdByCathiId(int cathiId){
		String sql = "SELECT phongthi_id FROM cathi_phongthi WHERE cathi_id =:cathiId;";
		Map<String, Object> maps = new HashMap<String, Object>();
		maps.put("cathiId",cathiId);
		return jdbcTemplate.queryForList(sql, maps,Integer.class);
	}
	public int updateSlot(CaThiPhongThi ctpt) {
		String sql = "UPDATE cathi_phongthi SET slot_con_lai =:slotConLai WHERE id =:id;";
		BeanPropertySqlParameterSource source = new BeanPropertySqlParameterSource(ctpt);
		return jdbcTemplate.update(sql,source);
	}
	public CaThiPhongThi getByIds(int cathiId, int phongthiId) {
		String sql = "SELECT * FROM cathi_phongthi WHERE cathi_id =:cathiId AND phongthi_id =:phongthiId";
		Map<String, Object> maps = new HashMap<String , Object>();
		maps.put("cathiId", cathiId);
		maps.put("phongthiId", phongthiId);
		return jdbcTemplate.queryForObject(sql, maps, new BeanPropertyRowMapper<CaThiPhongThi>(CaThiPhongThi.class));
		
	}
	public int delete(int id) {
		String sql = "DELETE FROM cathi_phongthi WHERE id=:id;";
		Map<String, Object> maps = new HashMap<String , Object>();
		maps.put("id", id);
		return jdbcTemplate.update(sql, maps);
	}
}
