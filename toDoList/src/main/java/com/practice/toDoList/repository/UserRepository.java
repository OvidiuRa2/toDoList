package com.practice.toDoList.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.toDoList.entity.MyUser;

public interface UserRepository extends JpaRepository<MyUser, Integer> {

	Optional<MyUser> findByUsername(String username);
}
