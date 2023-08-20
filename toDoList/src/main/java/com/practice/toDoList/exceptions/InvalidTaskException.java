package com.practice.toDoList.exceptions;

public class InvalidTaskException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidTaskException() {
		super();
	}
	
	public InvalidTaskException(String message) {
		super(message);
	}

}
