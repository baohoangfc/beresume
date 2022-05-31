package com.bao.doan.user;

import static com.bao.doan.security.SecurityUtils.HEADER_STRING;
import static com.bao.doan.security.SecurityUtils.TOKEN_PREFIX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bao.doan.security.SecurityUtils;
import com.bao.doan.security.UsernamePasswordSystemToken;

@RestController
@RequestMapping("/users")
public class UserController {

	private static final Log log = LogFactory.getLog(UserController.class);
	
	private ApplicationUserRepository applicationUserRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private AuthenticationManager authen;

	public UserController(ApplicationUserRepository applicationUserRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authen) {
		this.applicationUserRepository = applicationUserRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.authen = authen;
	}
	
	@PostMapping("/verify")
	public String verify(HttpServletResponse res) {
		return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
	}

	@PostMapping("/register")
	public Map<String, Object> register(@RequestBody ApplicationUser user, HttpServletResponse res,
			HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			user.setIsdefaultpassword((short) 1);
			applicationUserRepository.save(user);
			res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + SecurityUtils.generateToken(user.getUsername()));
			result.put("code", 0);
		} catch (Exception e) {
			log.error("register:" + e.getMessage());
			result.put("code", 1);
		}
		return result;
	}

	@PostMapping("/checkisdefaultpass")
	public Map<String, Object> checkisdefaultpass(HttpServletResponse res, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		ApplicationUser user = applicationUserRepository.findByUsername(username);
		if(null!=user) {
			result.put("code", user.getIsdefaultpassword());
		}else {
			result.put("code", 0);
		}
		result.put("message", "Kiểm tra mật khẩu mặc định");
		return result;
	}

	@PostMapping(value = "/changepassword", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	@CrossOrigin(origins = { "*" })
	public Map<String, Object> capNhatByUser(@RequestBody Map<String, Object> thongTinNguoiDung) {
		Map<String, Object> result = new HashMap<String, Object>();
		String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
		ApplicationUser user = applicationUserRepository.findByUsername(username);
		Authentication au = null;
		try {
			au = authen.authenticate(new UsernamePasswordSystemToken(username,
					thongTinNguoiDung.get("matkhaucu").toString(), "QLNSNN", new ArrayList<>()));
			if (au != null) {
				user.setPassword(bCryptPasswordEncoder.encode(thongTinNguoiDung.get("matkhaumoi").toString()));
				user.setIsdefaultpassword((short) 0);
				applicationUserRepository.save(user);
				result.put("code", true);
				result.put("message", "");
			}
		} catch (Exception e) {
			log.error("capNhatByUser:" + e.getMessage());
			result.put("code", false);
			result.put("message", "Mật khẩu cũ không đúng !");
		}
		return result;
	}

	@PostMapping("/deleteapplicationuser")
	public Map<String, Object> deleteUser(@RequestBody Map<String, Object> listUsernameObj, HttpServletResponse res) {
		Map<String, Object> result = new HashMap<String, Object>();
		String listUserName = listUsernameObj.get("listUsername").toString();
		try {
			List<String> listItems = Arrays.asList(listUserName.split(","));
			for (String username : listItems) {
				ApplicationUser user = applicationUserRepository.findByUsername(username);
				if (null != user) {
					applicationUserRepository.delete(user);
				}
			}
			result.put("code", 0);
		} catch (Exception e) {
			log.error("deleteUser:" + e.getMessage());
			result.put("code", 1);
		}
		return result;
	}

}
