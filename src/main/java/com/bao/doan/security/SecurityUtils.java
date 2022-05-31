package com.bao.doan.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class SecurityUtils {
	public static final String SECRET = "k96juop4jmknhkxnlhjridyp[i190i5kp208klwsgn;klsNMGI!JHIOHG902hjr91hu9~!P)JU09]";
	public static final long EXPIRATION_TIME = 360000000L; // 30 phut
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
	public static final String SIGN_UP_URL = "/users/sign-up";
	public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
	public static String generateToken(String username) {		
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
				.compact();
	}
}
