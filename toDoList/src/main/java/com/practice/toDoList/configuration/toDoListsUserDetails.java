package com.practice.toDoList.configuration;

import com.practice.toDoList.entity.MyUser;
import com.practice.toDoList.service.AuthorityService;
import com.practice.toDoList.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class toDoListsUserDetails implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String userName, password;
        List<GrantedAuthority> authorities;
        MyUser customer = userService.findByUsername(username);

            userName = customer.getUsername();
            password = customer.getPassword();
            authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(authorityService.findByUsername(username).getAuthority()));

        return new User(userName,password,authorities);
    }
}
