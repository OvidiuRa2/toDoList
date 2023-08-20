package com.practice.toDoList.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.toDoList.entity.MyUser;
import com.practice.toDoList.exceptions.InvalidUserException;
import com.practice.toDoList.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	

	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	@Override
	public MyUser findByUsername(String username) {
		Optional<MyUser> userOptional =  userRepository.findByUsername(username);
		
		return userOptional.orElseThrow(()->new InvalidUserException("Username is not valid"));
		
	}
	@Override
	public MyUser save(MyUser user) {

		user.setEnabled(true);
		user.setPassword( passwordEncoder.encode(user.getPassword()));

		return userRepository.save(user);
	}

	@Override
	public boolean checkValidPassword(String newPassword, String repeatedNewPassword) {
		return newPassword.equals("") || repeatedNewPassword.equals("")  || 
				!newPassword.equals(repeatedNewPassword);
	}
}
