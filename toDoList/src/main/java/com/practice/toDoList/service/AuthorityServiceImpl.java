package com.practice.toDoList.service;

import com.practice.toDoList.exceptions.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practice.toDoList.entity.Authority;
import com.practice.toDoList.repository.AuthorityRepository;

@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Override
	public Authority save(Authority a) {
		return authorityRepository.save(a);
	}

	@Override
	public Authority findByUsername(String username) {
		return authorityRepository.findByUsername(username).orElseThrow(()->new InvalidUserException("Invalid user"));
	}

}
