package com.practice.toDoList.configuration;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;

import com.practice.toDoList.filter.CsrfLoggerFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig  {

	@Autowired
	@Qualifier("securityDataSource")
	private DataSource securityDataSource;

//	@Bean
//	 public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http
//		.authorizeRequests()
//		.antMatchers("/tasks").hasAnyRole("ADMIN","USER")
//		.antMatchers("/tasks/newTask/**").hasRole("ADMIN")
//		.antMatchers("/signUp/**","/addUser/**","/api/**").permitAll()
//		.and()
//		.formLogin()
//			.loginPage("/showLoginPage")
//			.loginProcessingUrl("/authenticateTheUser")
//			.permitAll()
//		.and()
//	    .exceptionHandling()
//	         .accessDeniedPage("/access-denied")
//	    .and()
//	         .logout().permitAll();
//
//		http.addFilterAfter(new CsrfLoggerFilter(), CsrfFilter.class);
//
//		return http.build();
//	}
	
	  @Bean
	    public WebSecurityCustomizer webSecurityCustomizer() {
	        return (web) -> web.ignoring().antMatchers("/css/**");
	    }

		@Bean
		public UserDetailsManager users() {

			return new JdbcUserDetailsManager(securityDataSource);
		}

		@Bean
		public PasswordEncoder passwordEncoder()
		{
			return new BCryptPasswordEncoder(10);
		}


//	  @Bean
//	    public InMemoryUserDetailsManager userDetailsService() {
//		  UserDetails user1 = User.withDefaultPasswordEncoder()
//		            .username("user")
//		            .password("user")
//		            .roles("USER")
//		            .build();
//		  
//		  UserDetails user2 = User.withDefaultPasswordEncoder()
//		            .username("admin")
//		            .password("admin")
//		            .roles("ADMIN")
//		            .build();
//	        return new InMemoryUserDetailsManager(user1,user2);
//	    }
//	  
	  
}
