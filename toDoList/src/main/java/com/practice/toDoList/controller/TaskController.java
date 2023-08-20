package com.practice.toDoList.controller;

import java.util.logging.Logger;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.practice.toDoList.entity.Task;
import com.practice.toDoList.service.TaskService;


@Controller
@RequestMapping("/tasks")
public class TaskController {

	private TaskService taskService;
    Logger logger = Logger.getLogger(getClass().getName());
	
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}
	
	@GetMapping
	public String getTasks(Model theModel){
		theModel.addAttribute("tasks",taskService.findAll());
		return "index";
	}
	
	@GetMapping("/taskInfo")
	public String showTaskInfo(@RequestParam("taskId") int taskId,Model theModel) {
		Task task = taskService.findById(taskId);
		theModel.addAttribute("task",task);
		return "taskInfo";
	}
	
	@GetMapping("/newTask")
	public String showNewTaskForm(Model theModel) {
		Task task = new Task();
		theModel.addAttribute("task",task);
		return "newTask";
	}
	
	@PostMapping("/save")
	public String save(@ModelAttribute("task") Task task) {
		logger.info("------>>>>>>>"+task);
		 taskService.save(task);
		 
		 return "redirect:/tasks";
	}
	
	@PutMapping("check/{taskId}")
	public ResponseEntity<String> updateCheck(@PathVariable("taskId") int taskId) {
		Task task = taskService.findById(taskId);
		if(task.isChecked()) {
			task.setChecked(false);
		}else {
			task.setChecked(true);
		}
		
		taskService.save(task);
		
		return ResponseEntity.ok("Task updated");
	}
	
	@DeleteMapping("/{taskId}")
	public ResponseEntity<String> delete(@PathVariable("taskId") int taskId){
				
		Task task = taskService.findById(taskId);
		taskService.delete(task);
		
		return ResponseEntity.ok("Task deleted");
	}

}
