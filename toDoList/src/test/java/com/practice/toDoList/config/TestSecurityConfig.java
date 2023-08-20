package com.practice.toDoList.config;

import com.practice.toDoList.filter.CsrfLoggerFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
@EnableWebSecurity
public class TestSecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				.antMatchers("/api/**").hasAnyRole("USER","ADMIN")
				.and()
				.exceptionHandling()
				.accessDeniedPage("/access-denied")
				.and()
				.logout().permitAll();

		http.addFilterAfter(new CsrfLoggerFilter(), CsrfFilter.class);

		return http.build();
	}

	@Bean
	    public InMemoryUserDetailsManager userDetailsService() {
		  UserDetails user1 = User.withDefaultPasswordEncoder()
		            .username("user")
		            .password("user")
		            .roles("USER")
		            .build();

		  UserDetails user2 = User.withDefaultPasswordEncoder()
		            .username("admin")
		            .password("admin")
		            .roles("ADMIN")
		            .build();
	        return new InMemoryUserDetailsManager(user1,user2);
	    }
}
