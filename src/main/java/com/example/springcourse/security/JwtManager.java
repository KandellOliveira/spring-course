package com.example.springcourse.security;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.springcourse.constant.SecurityConstant;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtManager {

	public String createToken(String email, List<String> roles) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, SecurityConstant.JWT_EXP_DAYS);
		String jwt = Jwts.builder()
				.setSubject(email)
				.setExpiration(calendar.getTime())
				.claim(SecurityConstant.JWT_ROLE_KEY, roles)
				.signWith(SignatureAlgorithm.ES512, SecurityConstant.API_KEY.getBytes())
				.compact();
		
		return jwt;
	}
}