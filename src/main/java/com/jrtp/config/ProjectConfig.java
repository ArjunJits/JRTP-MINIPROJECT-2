package com.jrtp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http.httpBasic()
//				.and()
//				.authorizeRequests()
//				.mvcMatchers(HttpMethod.POST,"http://localhost:8080/elig/add").permitAll()
//				.mvcMatchers("/swagger-ui.html").authenticated()
//				.and().build();
//				
				.and().authorizeRequests()
                .antMatchers("/elig/**").permitAll()
                .antMatchers("/search").permitAll()
                .antMatchers("/").permitAll()
                .anyRequest()
                .authenticated()                  
                .and()
                .csrf().disable()
                .headers().frameOptions().disable().and().build();
		
//                .and()
//                .authorizeRequests()
//				.mvcMatchers("/swagger-ui.html").authenticated()
//				.mvcMatchers(HttpMethod.POST,"/elig/**").permitAll().and()
//				.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		var uds = new InMemoryUserDetailsManager();
		var user = User.withUsername("arjun")
				.authorities("read","write")
				.password(passwordEncoder().encode("12345"))
				.build();
		uds.createUser(user);
		return uds;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
