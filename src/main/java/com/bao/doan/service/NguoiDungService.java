package com.bao.doan.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bao.doan.dao.MovieDao;
import com.bao.doan.dao.NguoiDungDao;
import com.bao.doan.entity.Movie;
import com.bao.doan.entity.MovieRating;
import com.bao.doan.entity.Nguoidung;
import com.bao.doan.user.ApplicationUser;
import com.bao.doan.user.UserDao;
import com.bao.doan.utils.UniConstant;
import com.bao.doan.utils.UniUtil;

@Service
@Transactional
public class NguoiDungService {

	@Autowired
	private NguoiDungDao nguoiDungDao;
	
	@Autowired
	private MovieDao movieDao;

	@Autowired
	private UserDao userDao;

	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public NguoiDungService(BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	private static final Log log = LogFactory.getLog(NguoiDungService.class);

	public Map<String, Object> getDanhSachNguoiDung() {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = UniConstant.Message.QUERY_SUCCESS;
		List<Nguoidung> list = nguoiDungDao.getDanhSachNguoiDung();

		if (null == list || list.isEmpty()) {
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.QUERY_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, list);
	}

	public Map<String, Object> layThongTinNguoiDungTheoTenDangNhap(String tendangnhap) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Nguoidung nd = nguoiDungDao.layThongTinNguoiDungTheoTenDangNhap(tendangnhap);
			result.put("nguoidung", nd);
		} catch (Exception e) {
			log.error("layThongTinNguoiDungTheoTenDangNhap:" + e.getMessage());
		}
		return result;
	}

	public Map<String, Object> getUserIdByEmail(String email) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			Nguoidung nd = nguoiDungDao.getUserIdByEmail(email);
			result.put("nguoidung", nd);
		} catch (Exception e) {
			log.error("userid:" + e.getMessage());
		}
		return result;
	}

	public Map<String, Object> themMoiNguoiDung(Map<String, Object> thongtin) {
		int code = UniConstant.ErrorCode.SUCCESS;
		String message = "";
		ApplicationUser user = new ApplicationUser();
		Nguoidung nguoidung = new Nguoidung();
		try {
			int checkTontai = nguoiDungDao.checkTontaiTenDangNhapEmail(thongtin.get("email").toString(),
					thongtin.get("email").toString(), 0);
			if (checkTontai > 0) {
				code = UniConstant.ErrorCode.FAIL;
				message = UniConstant.Message.TENDANGNHAP_EXISTS;
			} else {
				long id = UniUtil.getTableId();
				nguoidung.setEmail(thongtin.get("email").toString());
				nguoidung.setHoten(thongtin.get("hoten").toString());
				nguoidung.setTendangnhap(thongtin.get("email").toString());
				nguoidung.setNguoitao(thongtin.get("email").toString());
				nguoidung.setId(id);
				nguoidung.setNgaytao(new Date());
				nguoidung.setVaitro("ROLE_USER");
				nguoidung.setKichhoat((short) 1);
				nguoidung.setDaxoa((short) 0);
				nguoiDungDao.saveNguoiDung(nguoidung);
				// application
				user.setId(id);
				System.out.println(user);
				user.setPassword(bCryptPasswordEncoder.encode(thongtin.get("password").toString()));
				user.setUsername(thongtin.get("email").toString());
				user.setIsdefaultpassword((short) 1);
				user.setSystem("ADMIN");
				System.out.println(user);
				userDao.saveUser(user);
				// add value
				List<Movie> movieList =  movieDao.getDanhSachPhim();
				for (int i = 0; i < movieList.size(); i++) {
					MovieRating movieRating = new MovieRating();
					movieRating.setId(UniUtil.getTableId()/2);
					movieRating.setMovieid(movieList.get(i).getId());
					movieRating.setRating(0);
					movieRating.setUserid(id);
					movieRating.setDaxoa((short) 0);
					movieDao.saveMovieRating(movieRating);
				}
				code = UniConstant.ErrorCode.SUCCESS;
				message = UniConstant.Message.INSERT_SUCCESS;
			}
		} catch (Exception e) {
			e.getMessage();
			code = UniConstant.ErrorCode.FAIL;
			message = UniConstant.Message.INSERT_FAIL;
		}
		return UniConstant.CommonService.setResult(code, message, nguoidung);
	}
	
	public Map<String, Object> capNhatNguoiDung(Nguoidung nguoidung) {
		return null;
	}
}
