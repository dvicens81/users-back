package com.users.crud.error;

import java.util.NoSuchElementException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EmptyResultDataAccessException ex) {
	       return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage()));
	}
	
	@ExceptionHandler(NoSuchElementException.class)
	protected ResponseEntity<Object> handleElementNotFound(NoSuchElementException ex) {
	       return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage()));
	}
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
	       return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}
