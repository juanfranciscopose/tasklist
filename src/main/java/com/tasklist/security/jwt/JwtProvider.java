package com.tasklist.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.tasklist.security.model.MainUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/*
 * jwt facade 
 */
@Component
public class JwtProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	
	private String key = "secret-key";
	private int expiration = 3600000; //in millisec. -> one hour
	
	public String generateToken(Authentication auth) {
		MainUser mainUser = (MainUser) auth.getPrincipal();
		return Jwts.builder().setSubject(mainUser.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expiration))
				.signWith(SignatureAlgorithm.HS512, key)
				.compact();
				
	}
	
	public String getEmailFromToken(String token) {
		return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			logger.error("fail JwtProvider - validateToken: "+e.toString());
			return false;
		}
	}
}
