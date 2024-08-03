package com.mrmachine.apifinalapp.auth.filters;

import java.io.IOException;
//import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mrmachine.apifinalapp.auth.SimpleGrantedAuthorityJsonCreator;
import com.mrmachine.apifinalapp.auth.TokenJwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Arrays;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtValidationFilter extends BasicAuthenticationFilter {

	public JwtValidationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
		
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String header = request.getHeader(TokenJwtConfig.HEADER_AUTHORIZATION);
		
		if (header == null || !header.startsWith(TokenJwtConfig.PREFIX_TOKEN)) {
			chain.doFilter(request, response);
			return;
		}
		
		String token = header.replace(TokenJwtConfig.PREFIX_TOKEN, "");
		
		try {
			
			Claims claims = Jwts
				.parser()
				.verifyWith(TokenJwtConfig.SECRET_KEY)
				.build()
				.parseSignedClaims(token)
				.getPayload();
			
			String username = claims.getSubject();
			Object authoritiesClaims = claims.get("authorities");
			
			List<GrantedAuthority> authorities = Arrays
					.asList(new ObjectMapper()
							.addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
							.readValue(authoritiesClaims
									.toString()
									.getBytes(), SimpleGrantedAuthority[].class));
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			chain.doFilter(request, response);
			
		} catch (JwtException e) {
			Map<String, String> body = new HashMap<String, String>();
			body.put("error", e.getMessage());
			body.put("message", "JWT Token invalid");
			response.getWriter().write(new ObjectMapper().writeValueAsString(body));
			response.setStatus(401);
			response.setContentType("application/json");
		}

 	}
}
