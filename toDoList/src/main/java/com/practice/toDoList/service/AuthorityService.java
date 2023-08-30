package com.practice.toDoList.service;

import com.practice.toDoList.entity.Authority;

public interface AuthorityService {

	Authority save(Authority a);

    Authority findByUsername(String username);
}
