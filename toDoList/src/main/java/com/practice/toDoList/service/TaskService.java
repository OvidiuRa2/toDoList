package com.practice.toDoList.service;

import java.util.List;

import com.practice.toDoList.entity.Task;

public interface TaskService {

	List<Task> findAll();
	
	Task findById(int id);
	
	Task save(Task task);
		
	 void delete(Task task);
	
}
