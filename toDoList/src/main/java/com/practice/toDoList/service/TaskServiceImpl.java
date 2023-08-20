package com.practice.toDoList.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.practice.toDoList.entity.Task;
import com.practice.toDoList.exceptions.InvalidTaskException;
import com.practice.toDoList.repository.TaskRepository;

@Service
public class TaskServiceImpl implements TaskService{

	
	TaskRepository taskRepository;
	

	
	public TaskServiceImpl (TaskRepository taskRep) {
		taskRepository = taskRep;
	}

	@Override
	public List<Task> findAll() {
	
		return taskRepository.findAll();
	}

	@Override
	public Task findById(int id) {
		
		if(taskRepository.findById(id).isPresent()) {
			return taskRepository.findById(id).get();

		}else {
			throw new InvalidTaskException("Task id not found "  +id);
		}
	}
	
	public Task save(Task task) {
		 
	 return taskRepository.save(task);
	}
	
	
	public void delete(Task task) {
		taskRepository.delete(task);
		
	}	
	
	
}
