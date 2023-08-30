package com.practice.toDoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.toDoList.entity.Authority;

import java.util.Optional;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

    Optional<Authority> findByUsername(String username);
}
