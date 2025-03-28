package com.example.spring_security_6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	private final UserDetailsService userdetailService;
	private final JwtAuthenticationFilter jwtAuthenticationFilter ;

	public WebSecurityConfig(UserDetailsService userdetailService, JwtAuthenticationFilter jwtAuthenticationFilter ) {
		this.userdetailService = userdetailService;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	} 
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
		security
		.csrf(csrf -> csrf.disable())
		.authorizeHttpRequests(
				request ->request
				.requestMatchers("register", "login").permitAll()
				.anyRequest().authenticated()
				)
		
		.httpBasic(Customizer.withDefaults())
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return security.build();
		
	}
	//@Bean //bean is used to take the control back and define our configuration
	public UserDetailsService service() {
		UserDetails niralee = User.withUsername("niralee")
									.password("{noop}password") //noop is used as a passwrod encoder but we should not use this in actual production
									.roles("USER")
									.build();
		
		UserDetails nikhil = User.withUsername("nikhil")
				.password("{noop}nikhil")
				.roles("USER")
				.build();
		
		return new InMemoryUserDetailsManager(niralee, nikhil);
		
	}
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder(14);
	}
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userdetailService);
		//provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		provider.setPasswordEncoder(bCryptPasswordEncoder());
		return provider;
	}
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
		
		
	}
	
}
