package com.practice.toDoList.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidTaskResponse {
	private HttpStatus status;
	private long timestamp;
	private String message;
	
	public InvalidTaskResponse(HttpStatus status, long timestamp, String message) {
		this.status = status;
		this.timestamp = timestamp;
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
