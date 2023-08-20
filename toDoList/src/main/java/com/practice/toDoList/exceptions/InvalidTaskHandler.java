package com.practice.toDoList.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvalidTaskHandler {

@ExceptionHandler(InvalidTaskException.class)
public ResponseEntity<InvalidTaskResponse> handleInvalidTaskException(InvalidTaskException exc){
	
		String message = "Invalid Data";
		
		if(exc.getMessage()!=null) {
			message = exc.getMessage();
		}
		
		InvalidTaskResponse itr = new InvalidTaskResponse(HttpStatus.NOT_FOUND,System.currentTimeMillis(),message);
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(itr);
	}
}
