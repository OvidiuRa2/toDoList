package com.practice.toDoList.service;

import com.practice.toDoList.entity.MyUser;

public interface UserService {

	 MyUser findByUsername(String username);
	 
	 MyUser save(MyUser user);
	 
	 boolean checkValidPassword(String newPassword, String repeatedNewPassword);


}
