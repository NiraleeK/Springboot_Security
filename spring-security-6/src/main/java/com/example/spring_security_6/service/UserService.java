package com.example.spring_security_6.service;

import java.util.Objects;
import org.springframework.security.core.Authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring_security_6.entity.User;
import com.example.spring_security_6.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository userRepsitory;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final AuthenticationManager authenticationManager ;
	
	private final JWTService jwtService;
	
	public UserService(UserRepository userRepsitory , BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager,JWTService jwtService) {
		this.userRepsitory = userRepsitory;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}
	public User register(User user) {
		// TODO Auto-generated method stub
		
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return userRepsitory.save(user) ;
	}
	public String verify(User user) {
		// TODO Auto-generated method stub
		Authentication authenticate = authenticationManager .authenticate(
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
				);
		// var u = userRepsitory.findByUsername(user.getUsername());
		 if(authenticate.isAuthenticated()) { 
			 return jwtService.generateToken(user); 
			 }
		 return "failure";
	}
		
	}

