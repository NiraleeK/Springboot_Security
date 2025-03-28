package com.example.spring_security_6.service;
import java.util.Objects;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.spring_security_6.CustomUserDetails;
import com.example.spring_security_6.entity.User;
import com.example.spring_security_6.repository.UserRepository;

@Component
public class CustomUserDetailService implements UserDetailsService {

	private final UserRepository userRepository;
	
	public CustomUserDetailService(UserRepository userRepository) {
		this.userRepository= userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
	
			User user = userRepository.findByUsername(username);
			
			if(Objects.isNull(user)) {
				System.out.print("User not available ");
				throw new UsernameNotFoundException("User not found");
			}
		return new CustomUserDetails(user);
	}

}
