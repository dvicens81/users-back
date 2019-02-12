package com.users.crud.error;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ApiError {
	
	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;
	
	public ApiError(HttpStatus status, String message) {
		this.status = status;
		this.timestamp = LocalDateTime.now();
		this.message = message;
	}
	public HttpStatus getStatus() {
		return status;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public String getMessage() {
		return message;
	}
	
	String getDebugMessage() {
		return debugMessage;
	}
}
