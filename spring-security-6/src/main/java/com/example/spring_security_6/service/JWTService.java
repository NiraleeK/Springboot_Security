package com.example.spring_security_6.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import com.example.spring_security_6.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {
	
	private String secretKey = null;

	public String generateToken(User user) {
		// TODO Auto-generated method stub
		
		 Map<String, Object> claims
         = new HashMap<>();
		 return Jwts
				 .builder()
				 .claims()
				 .add(claims)
				 .subject(user.getUsername())
				 .issuer("DCB")
				 .issuedAt(new Date(System.currentTimeMillis()))
				 .expiration(new Date(System.currentTimeMillis()+ 60*10*1000))
				 .and()
				 .signWith(generateKey())
				 .compact();
	}

	private SecretKey generateKey() {
        byte[] decode
                = Decoders.BASE64.decode(getSectretKey());

        return Keys.hmacShaKeyFor(decode);
    }

	public String getSectretKey() {
		return secretKey = "g3dM+dbYHbLZ3M30CH8gADnUU3FlBMQXnBsXTuOL3FA=";
	}

	

	public String extractusername(String token) {
		// TODO Auto-generated method stub
		
		return extractClaims(token, Claims::getSubject);
	}

	private <T>T extractClaims(String token, Function<Claims, T> claimResolver) {
		// TODO Auto-generated method stub
		Claims claims = extractClaims(token);
		return claimResolver.apply(claims);
	}

	 private Claims extractClaims(String token) {
	        return Jwts
	                .parser()
	                .verifyWith(generateKey())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload();
	    }


	public boolean isTokenValid(String token, UserDetails userdetails) {
		// TODO Auto-generated method stub
		final String username = extractusername(token);
		return (username.equals(userdetails.getUsername()) && !isTokenExpired(token));
	}

	private boolean isTokenExpired(String token) {
		// TODO Auto-generated method stub
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		// TODO Auto-generated method stub
		return extractClaims(token, Claims::getExpiration);
	}
}
