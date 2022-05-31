package com.bao.doan.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bao.doan.service.NguoiDungService;

@RestController
@RequestMapping("/admin/nguoidung")
public class NguoiDungController {
	
	@Autowired
	private NguoiDungService nguoiDungService;
	
	@ResponseBody
	@GetMapping(value = "/ds", produces = { MediaType.APPLICATION_JSON_VALUE })
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> getDanhSachNguoiDung() {		
		return nguoiDungService.getDanhSachNguoiDung();
	}
	
	@PostMapping(value = "/thongtinnguoidung", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @CrossOrigin(origins= {"*"})
    public Map<String,Object> layThongTinNguoiDungTheoEmail(@RequestBody Map<String,Object> nguoiDung) {
		String username = nguoiDung.get("username").toString();
		return nguoiDungService.layThongTinNguoiDungTheoTenDangNhap(username);
	}
	
	@PostMapping(value = "/getuseridbyemail", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @CrossOrigin(origins= {"*"})
    public Map<String,Object> getUserIdByEmail(@RequestBody Map<String,Object> nguoiDung) {
		String email = nguoiDung.get("email").toString();
		return nguoiDungService.getUserIdByEmail(email);
	}
	
	@PostMapping(value = "/themmoi", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    @CrossOrigin(origins= {"*"})
    public Map<String,Object> themMoiNguoiDung(@RequestBody Map<String,Object> thongTinNguoiDung) {
		@SuppressWarnings("unchecked")
		Map<String,Object> data = (Map<String, Object>) thongTinNguoiDung.get("data");
		return nguoiDungService.themMoiNguoiDung(data);
	}
	
//	@PostMapping(value = "/capnhat", produces = { MediaType.APPLICATION_JSON_VALUE })
//    @ResponseBody
//    @CrossOrigin(origins= {"*"})
//    public Map<String,Object> capNhatNguoiDung(@RequestBody Nguoidung nguoidung) {
//		return nguoiDungService.capNhatNguoiDung(nguoidung);
//	}
	
}
