package com.practice.toDoList.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;

import com.practice.toDoList.filter.CsrfLoggerFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig  {

	@Bean
	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.authorizeRequests()
		.antMatchers("/tasks").hasAnyRole("ADMIN","USER")
		.antMatchers("/tasks/newTask/**").hasRole("ADMIN")
		.antMatchers("/signUp/**","/addUser/**","/api/**").permitAll()
		.and()
		.formLogin()
			.loginPage("/showLoginPage")
			.loginProcessingUrl("/authenticateTheUser")
			.permitAll()
		.and()
	    .exceptionHandling()
	         .accessDeniedPage("/access-denied")
	    .and()
	         .logout().permitAll();

		http.addFilterAfter(new CsrfLoggerFilter(), CsrfFilter.class);

		return http.build();
	}
	
	  @Bean
	    public WebSecurityCustomizer webSecurityCustomizer() {
	        return (web) -> web.ignoring().antMatchers("/css/**");
	    }

		@Bean
		public PasswordEncoder passwordEncoder()
		{
			return new BCryptPasswordEncoder(10);
		}
	  
}
