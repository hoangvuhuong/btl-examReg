package com.examReg.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.omg.CORBA.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.examReg.contract.ResponseContract;
import com.examReg.model.Admindownload;
import com.examReg.model.CaThi;
import com.examReg.model.CaThiPhongThi;
import com.examReg.model.Exam;
import com.examReg.model.HocPhan;
import com.examReg.model.PhongThi;
import com.examReg.model.ResponseRegis;
import com.examReg.model.User;
import com.examReg.model.UserCathi;
import com.examReg.model.UserDownload;
import com.examReg.model.UserHocphan;
import com.examReg.repository.CaThiPhongThiRepository;
import com.examReg.repository.CaThiRepository;
import com.examReg.repository.HocPhanRepository;
import com.examReg.repository.KiThiRepository;
import com.examReg.repository.PhongThiRepository;
import com.examReg.repository.UserCathiRepository;
import com.examReg.repository.UserHocPhanRepository;
import com.examReg.repository.UserRepository;
import com.examReg.security.TokenProvider;
import com.examReg.security.UserPrincipal;

@Service
public class UserService implements IUserService {
	@Autowired
	KiThiRepository kiThiRep;

	@Autowired
	PhongThiRepository phongthiRep;

	@Autowired
	private CaThiPhongThiRepository caThiphongThiRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenProvider tokenProvider;

	@Autowired
	CaThiRepository cathiRepository;

	@Autowired
	UserCathiRepository ucRepository;

	@Autowired
	UserHocPhanRepository uhRepository;

	@Autowired
	HocPhanRepository hocphanRepository;

	@Override
	public String login(String userName, String pass) {
		Optional<User> userOptional = userRepository.findByUserName(userName);
		User user = null;
		if (userOptional.isPresent()) {
			user = userOptional.get();
		} else {
			return null;
		}

		if (checkPass(pass, user.getPass())) {
			UserPrincipal userPrincipal = UserPrincipal.create(user);
			String jwtToken = tokenProvider.createToken(userPrincipal);
			return jwtToken;
		}
		return null;
	}

	@Override
	public ResponseContract<?> create(User user) {
		String pass = encrytePassword(user.getPass());
		user.setPass(pass);
		return new ResponseContract<User>("ok", "ok", userRepository.create(user));
	}

	public static String encrytePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	public static boolean checkPass(String pass, String dataBasePass) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.matches(pass, dataBasePass);
	}

	@Override
	public ResponseContract<?> getAll() {
		try {
			List<String> listUsername = new ArrayList<String>();
			for (User user : userRepository.getAll()) {
				listUsername.add(user.getUserName());
			}
			return new ResponseContract<List<String>>("200", "Ok", listUsername);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseContract<?> taoCathi(CaThi cathi, String tenPhongThi) {
		try {

			int cathiId = cathiRepository.create(cathi);
			// CaThi ct = cathiRepository.getCaThi(cathi);
			PhongThi pt = phongthiRep.findByName(tenPhongThi);
			CaThiPhongThi ctpt = new CaThiPhongThi();
			ctpt.setCathiId(cathiId);
			ctpt.setPhongthiId(pt.getId());
			ctpt.setSlotConLai(pt.getSlot());
			caThiphongThiRepository.create(ctpt);

			return new ResponseContract<Integer>("200", "OK", cathiId);

		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseContract<String>("Loi", e.getMessage(), null);
		}

	}

	@Override
	public int doiMk(Map<String, String> input) {
		try {
			if (input.get("pass1").equals(input.get("pass2"))) {
				String newPass = encrytePassword(input.get("pass1"));
				return userRepository.doiMk(SecurityContextHolder.getContext().getAuthentication().getName(), newPass);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}
		return 0;

	}

	@Override
	public int createUserCathi(int caThiId, String tenPhong, List<String> listUser) {
		try {
			PhongThi pt = phongthiRep.findByName(tenPhong);
			for (String username : listUser) {
				Optional<User> userOptional = userRepository.findByUserName(username);
				User user = null;
				if (userOptional.isPresent()) {
					user = userOptional.get();
				} else {
					return 0;
				}
				String SBD = pt.getName() + username;
				UserCathi uc = new UserCathi();
				uc.setCathiId(caThiId);
				uc.setPhongthiId(pt.getId());
				uc.setSbd(SBD);
				uc.setUserId(user.getUserId());
				ucRepository.create(uc);

			}
			return listUser.size();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return 0;
		}

	}

	@Override
	public int updateCamthi(List<String> listUser, int hocPhanId) {
		// TODO Auto-generated method stub

		for (String username : listUser) {
			User user = userRepository.findByUsername(username);
			if (user != null) {
				uhRepository.updateDK(user.getUserId(), hocPhanId, false);
			}
		}
		return listUser.size();
	}

	@Override
	public int createUserHocphan(int hocPhanId, List<String> listUser) {
		// TODO Auto-generated method stub
		for (String username : listUser) {
			User user = userRepository.findByUsername(username);
			if (user != null) {
				UserHocphan uh = new UserHocphan();
				uh.setHocphanId(hocPhanId);
				uh.setUserId(user.getUserId());
				uh.setDkThi(true);
				uhRepository.create(uh);
			}
		}
		return listUser.size();
	}

	@Override
	public ResponseContract<?> getAllHocPhan() {
		try {
			return new ResponseContract<List<HocPhan>>("200", "OK", hocphanRepository.getAll());
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>("Loi", e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> getAllHocPhanByUserId(int userId) {
		try {
			List<Integer> listHocPhanId = uhRepository.getListHocPhanIdByUserId(userId);
			List<HocPhan> listHocPhan = new ArrayList<HocPhan>();
			for (Integer id : listHocPhanId) {
				listHocPhan.add(hocphanRepository.getById(id));
			}
			return new ResponseContract<List<HocPhan>>("200", "OK", listHocPhan);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>("Loi", e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> getAllExam() {
		try {
			List<Exam> listExam = new ArrayList<Exam>();
			List<CaThi> listCaThi = cathiRepository.getAll();
			for (CaThi cathi : listCaThi) {
				List<Integer> listPhongThiId = caThiphongThiRepository.getPhongthiIdByCathiId(cathi.getId());
				HocPhan hocphan = hocphanRepository.getByCaThiId(cathi.getId());
				for (Integer id : listPhongThiId) {
					SimpleDateFormat hour = new SimpleDateFormat("HH:mm");
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
					Exam exam = new Exam();
					exam.setCathiId(cathi.getId());
					exam.setTenCaThi(cathi.getName());
					String ngayThi = date.format(cathi.getNgayThi());
					exam.setDate(ngayThi);
					String end = hour.format(cathi.getEnd());
					exam.setEnd(end);
					String start = hour.format(cathi.getStart());
					exam.setStart(start);
					exam.setMaMon(hocphan.getMaHp());
					exam.setTenMon(hocphan.getName());
					exam.setTenPhongThi(phongthiRep.getNameById(id));
					listExam.add(exam);
				}
			}
			return new ResponseContract<List<Exam>>("200", "OK", listExam);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>("Loi", e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> getAllExamByUserId(int userId) {
		try {
			List<Exam> listExams = new ArrayList<Exam>();
			// lay danh sach hoc phan id
			List<Integer> listHocPhanId = uhRepository.getListHocPhanIdByUserId(userId);
			for (Integer hocphanId : listHocPhanId) {

				HocPhan hocPhan = hocphanRepository.getById(hocphanId);
				CaThi cathi = cathiRepository.getById(hocPhan.getCathiId());
				List<Integer> phongthiIds = caThiphongThiRepository.getPhongthiIdByCathiId(cathi.getId());
				for (Integer phongthiId : phongthiIds) {
					SimpleDateFormat hour = new SimpleDateFormat("HH:mm");
					SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
					Exam exam = new Exam();
					exam.setCathiId(cathi.getId());
					exam.setTenCaThi(cathi.getName());
					String ngayThi = date.format(cathi.getNgayThi());
					exam.setDate(ngayThi);
					String end = hour.format(cathi.getEnd());
					exam.setEnd(end);
					String start = hour.format(cathi.getStart());
					exam.setStart(start);
					exam.setTenMon(hocPhan.getName());
					exam.setMaMon(hocPhan.getMaHp());
					exam.setTenPhongThi(phongthiRep.getNameById(phongthiId));
					exam.setDkThi(uhRepository.getUserHocphan(userId, hocPhan.getId()).isDkThi());
					exam.setIsDk(checkDK(userId, cathi.getId(), phongthiId));
					listExams.add(exam);
					
				}
			}
			return new ResponseContract<List<Exam>>("200", "OK", listExams);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>("Loi", e.getMessage(), null);
		}
	}
	
	private Boolean checkDK(int userId, int cathiId, int phongthiId) {
		UserCathi userCathi = ucRepository.getByIds(userId, cathiId,
				phongthiId);
		if(userCathi != null) {
			return true;
		}
		else
			return false;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public ResponseContract<?> DangKiThi(ResponseRegis resonseReg) {
		try {
			if (resonseReg.getIsRegis() == true) {
				int phongthiId = phongthiRep.getIdByName(resonseReg.getTenPhong());
				UserCathi userCathi = new UserCathi();
				userCathi.setCathiId(resonseReg.getCathiId());
				userCathi.setPhongthiId(phongthiId);
				userCathi.setUserId(resonseReg.getUserId());
				String sbd = resonseReg.getUserId().toString();
				userCathi.setSbd(sbd);

				// cap nhat slot
				if(!checkDangKiThi(userCathi.getUserId(), phongthiId, userCathi.getCathiId(), sbd)) {
				CaThiPhongThi ctpt = caThiphongThiRepository.getByIds(resonseReg.getCathiId(), phongthiId);
				if (ctpt.getSlotConLai() > 0) {
					int slot = ctpt.getSlotConLai() - 1;
					ctpt.setSlotConLai(slot);
					caThiphongThiRepository.updateSlot(ctpt);
					return new ResponseContract<Integer>("200", "OK", ucRepository.create(userCathi));
				}
				return new ResponseContract<String>("Loi", null, "Ca thi không còn chỗ nữa!!");
				}
				else
					return new ResponseContract<String>("Ca thi nay da dang ki",null, null);
			} else { // false khi huy dang ki
				int phongthiId = phongthiRep.getIdByName(resonseReg.getTenPhong());
				UserCathi userCathi = ucRepository.getByIds(resonseReg.getUserId(), resonseReg.getCathiId(),
						phongthiId);
				// cap nhat slot
				CaThiPhongThi ctpt = caThiphongThiRepository.getByIds(resonseReg.getCathiId(), phongthiId);
				int slot = ctpt.getSlotConLai() + 1;
				ctpt.setSlotConLai(slot);
				caThiphongThiRepository.updateSlot(ctpt);
				return new ResponseContract<Integer>("200", "OK", ucRepository.delete(userCathi.getId()));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>("Loi", e.getMessage(), null);
		}
	}
	private Boolean checkDangKiThi(int userId, int phongthiId, int cathiId, String sbd) {
		List<UserCathi> list = ucRepository.getAll();
		for(UserCathi uc : list) {
			if(uc.getCathiId() == cathiId
					&& uc.getUserId() == userId
					&& uc.getSbd().equals(sbd)
					&& uc.getPhongthiId() == phongthiId) {
				return true;
			}
		}
		return false;
	}
	@Override
	public ResponseContract<?> getDownload(int userId) {
		try {
		List<UserDownload> userDowns = new ArrayList<UserDownload>();
		List<UserCathi> listUserCathi = ucRepository.getByUserId(userId);
		for (UserCathi userCathi : listUserCathi) {
			UserDownload userDown = new UserDownload();
			userDown.setCathiId(userCathi.getCathiId());
			userDown.setSbd(userCathi.getSbd());
			userDown.setTenPhongThi(phongthiRep.getNameById(userCathi.getPhongthiId()));
			CaThi cathi = cathiRepository.getById(userCathi.getCathiId());
			SimpleDateFormat hour = new SimpleDateFormat("HH:mm");
			SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
			String ngayThi = date.format(cathi.getNgayThi());
			String end = hour.format(cathi.getEnd());

			String start = hour.format(cathi.getStart());
			userDown.setDate(ngayThi);
			userDown.setEnd(end);
			userDown.setStart(start);
			userDown.setTenCaThi(cathi.getName());
			userDown.setTenMon(hocphanRepository.getByCaThiId(userCathi.getCathiId()).getName());
			userDown.setMaMon(hocphanRepository.getByCaThiId(userCathi.getCathiId()).getMaHp());
			userDowns.add(userDown);
		}			
		return new ResponseContract<List<UserDownload>>("200","OK", userDowns);
		}catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>("Loi", e.getMessage(), null);
		}
		
	}

	@Override
	public ResponseContract<?> getAllPt() {
		try {
			return new ResponseContract<List<String>>("200", "OK",phongthiRep.getAll());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseContract<String>("Loi", e.getMessage(), null);
		}
	}

	@Override
	public ResponseContract<?> adminDownload() {
		try {
			List<CaThi> listCaThi = cathiRepository.getAll();
			List<Map<String, List<Admindownload>>> adminDownloads = new ArrayList<Map<String, List<Admindownload>>>();
			for(CaThi cathi : listCaThi) {
				SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
				String ngayThi = date.format(cathi.getNgayThi());
				Map<String, List<Admindownload>> maps = new HashMap<String , List<Admindownload>>();
				List<Admindownload> list = new ArrayList<Admindownload>();
				String tenMon = hocphanRepository.name(cathi.getId());
				
				List<UserCathi> listUserCathi = ucRepository.getByCathiId(cathi.getId());
				for(UserCathi uc : listUserCathi) {
					Admindownload adminDownload = new Admindownload();
					adminDownload.setName(cathi.getName() + " ngày " + ngayThi);
					adminDownload.setMssv(userRepository.findById(uc.getUserId()).getUserName());
					adminDownload.setHoten(userRepository.findById(uc.getUserId()).getName());
					adminDownload.setMonThi(tenMon);
					adminDownload.setPhongThi(phongthiRep.getNameById(uc.getPhongthiId()));
					adminDownload.setSbd(uc.getSbd());
					list.add(adminDownload);
					
				}
				
				maps.put(cathi.getName() + "ngày" + ngayThi, list);
				adminDownloads.add(maps);

			}
			return new ResponseContract<List<Map<String, List<Admindownload>>>>("200", "OK", adminDownloads);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseContract<String>("Loi", e.getMessage(), null); 
		}
	
	}

}
