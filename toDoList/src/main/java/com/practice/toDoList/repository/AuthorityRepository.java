package com.practice.toDoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.toDoList.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

}
