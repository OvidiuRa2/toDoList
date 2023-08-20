package com.practice.toDoList.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.toDoList.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{

}
