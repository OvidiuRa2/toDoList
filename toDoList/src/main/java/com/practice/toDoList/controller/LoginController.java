package com.practice.toDoList.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practice.toDoList.entity.Authority;
import com.practice.toDoList.entity.MyUser;
import com.practice.toDoList.exceptions.InvalidUserException;
import com.practice.toDoList.repository.AuthorityRepository;
import com.practice.toDoList.service.AuthorityService;
import com.practice.toDoList.service.UserService;

@Controller
public class LoginController {
   
	private final UserService userService;
	private final AuthorityService authorityService;
	

	public LoginController(UserService userService, AuthorityService authorityService) {
		this.userService = userService;
		this.authorityService = authorityService;
	}


	@GetMapping("/showLoginPage")
	public String showLoginForm(Model theModel) {
		theModel.addAttribute("currentUser",new MyUser());

		return "login";
	}
	
	
	@GetMapping("/")
	public String showTasks() {
		 return "redirect:/tasks";
	}
	
	@GetMapping("/access-denied")
	public String showAccessDenied() {
		 return "denied";
	}
	
	@RequestMapping("/signUp")
	public String showSignUpPage(Model theModel) {
		
		MyUser user = new MyUser();
		
		theModel.addAttribute("newUser",user);
		return "signUp";
	}
	
	
	@PostMapping("/addUser")
	public String showAddUserForm(@ModelAttribute("newUser") MyUser user) {
	
		userService.save(user);
		authorityService.save(new Authority(user.getUsername(), "ROLE_USER"));
		
				
		return "redirect:/showLoginPage";
	}
	
	@GetMapping("/forgotPassword")
	public String showForgotPasswordForm() {
		
		return "forgotPassword";
	}
	
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("username") String username,@RequestParam("newPassword") String newPassword,
								 @RequestParam("repeatedNewPassword") String repeatedNewPassword,Model theModel ) {

		MyUser user = null;
		try {
		 user =  userService.findByUsername(username);
		}catch(InvalidUserException ex) {
			theModel.addAttribute("invalidUsername", true);
			return "forgotPassword";			
		}
		
			if(userService.checkValidPassword(newPassword, repeatedNewPassword)) {
				theModel.addAttribute("invalidPassword", true);
				return "forgotPassword";
			}
			
			userService.save(user);
		
		return "redirect:/showLoginPage";
	}
}
