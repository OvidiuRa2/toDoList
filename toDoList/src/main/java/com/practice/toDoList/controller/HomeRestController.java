package com.practice.toDoList.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.practice.toDoList.entity.Task;
import com.practice.toDoList.exceptions.InvalidTaskException;
import com.practice.toDoList.service.TaskService;

@RestController
@RequestMapping("/api")
public class HomeRestController {

	private TaskService taskService;

	public HomeRestController(TaskService  taskServ) {
		taskService = taskServ;
	}
	
	@GetMapping("/tasks")
	public List<Task> showTasks() {
		return  taskService.findAll();
	}
	
	@GetMapping("/tasks/{id}")
	public Task showTaskById(@PathVariable int id ) {

		return  taskService.findById(id);
	}
	
	@PostMapping("/tasks")
	public Task saveTask(@RequestBody Task theTask) {
		theTask.setId(0);
		return taskService.save(theTask);
	}

	@PutMapping("/tasks")
	public Task updateTask(@RequestBody Task theTask) {
		return taskService.save(theTask);
	}
	
	@DeleteMapping("/tasks/{id}")
	public void deleteTaskById(@PathVariable int id ) {
		
		taskService.delete(taskService.findById(id)) ;
	}
	
}
