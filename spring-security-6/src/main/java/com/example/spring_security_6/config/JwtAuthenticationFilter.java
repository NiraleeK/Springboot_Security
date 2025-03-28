package com.example.spring_security_6.config;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.spring_security_6.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JWTService jwtservice;
	private final UserDetailsService userdetailService;
	
	public JwtAuthenticationFilter(JWTService jwtservice, UserDetailsService userdetailService) {
		this.jwtservice = jwtservice;
		this.userdetailService =  userdetailService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		final String authHeader = request.getHeader("Authorization");
		if(authHeader == null || !authHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		final String jwt =  authHeader.substring(7);
		final String username = jwtservice.extractusername(jwt);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		if(username != null && authentication == null ) {
			//Do authentication operation
			UserDetails userdetails = userdetailService.loadUserByUsername(username); //this will load data from database
			if(jwtservice.isTokenValid(jwt,userdetails)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userdetails, null,userdetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
			 
			
		}
		filterChain.doFilter(request, response);
	}

}
