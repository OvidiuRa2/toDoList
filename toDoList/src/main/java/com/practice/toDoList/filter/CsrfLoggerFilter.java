package com.practice.toDoList.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

public class CsrfLoggerFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
									HttpServletResponse response, 
									FilterChain filterChain)
			throws ServletException, IOException {
			CsrfToken csrfToken = (CsrfToken) request.getAttribute("_csrf");
			
			response.setHeader("CSRF-TOKEN-VALUE", csrfToken.getToken());
			
			filterChain.doFilter(request, response);
	}

}
	