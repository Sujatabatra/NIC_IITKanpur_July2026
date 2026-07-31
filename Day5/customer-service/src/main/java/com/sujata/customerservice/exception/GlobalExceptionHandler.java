package com.sujata.customerservice.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<ApiError> handleCustomerNotFoundException(CustomerNotFoundException ex,
			HttpServletRequest request) {

		ApiError error = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage(), request.getRequestURI());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(DuplicateCustomerException.class)
	public ResponseEntity<ApiError> handleDuplicateCustomerException(DuplicateCustomerException ex,
			HttpServletRequest request) {

		ApiError error = new ApiError(LocalDateTime.now(), HttpStatus.CONFLICT.value(),
				HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage(), request.getRequestURI());

		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach(error -> {

			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();

			errors.put(fieldName, message);
		});

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleException(Exception ex, HttpServletRequest request) {

		ApiError error = new ApiError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage(), request.getRequestURI());

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}