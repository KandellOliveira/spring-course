package com.example.springcourse.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.springcourse.constant.SecurityConstant;
import com.example.springcourse.resource.exception.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;

public class AuthorizationFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

			String jwt = request.getHeader(HttpHeaders.AUTHORIZATION);
			
			if(jwt == null || !jwt.startsWith(SecurityConstant.JWT_PROVIDER)) {
				ApiError apiError = new ApiError(org.springframework.http.HttpStatus.UNAUTHORIZED.value(), SecurityConstant.JWT_INVALID_MSG, new Date());
				PrintWriter write =  response.getWriter();
				
				ObjectMapper mapper = new ObjectMapper();
				String apiErrorString = mapper.writeValueAsString(apiError);
				
				write.write(apiErrorString);
				
				response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
				response.setStatus(org.springframework.http.HttpStatus.UNAUTHORIZED.value());
				
				return;
				
			}
			
			jwt = jwt.replace(SecurityConstant.JWT_PROVIDER, "");
			
			try {
				
				Claims claims = new JwtManager().parseToken(jwt);
				String email = claims.getSubject();
				List<String> roles = (List<String>) claims.get(SecurityConstant.JWT_ROLE_KEY);
				
				List<GrantedAuthority> grantAuthorities = new ArrayList<GrantedAuthority>();
				
				roles.forEach(role -> {
					grantAuthorities.add(new SimpleGrantedAuthority(role));
				});
				
				Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, grantAuthorities);
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				
			} catch (Exception e) {
				ApiError apiError = new ApiError(org.springframework.http.HttpStatus.UNAUTHORIZED.value(), e.getMessage(), new Date());
				PrintWriter write =  response.getWriter();
				
				ObjectMapper mapper = new ObjectMapper();
				String apiErrorString = mapper.writeValueAsString(apiError);
				
				write.write(apiErrorString);
				
				response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
				response.setStatus(org.springframework.http.HttpStatus.UNAUTHORIZED.value());
				
				return;
			}
			
			filterChain.doFilter(request, response);
		
	}

	
}
