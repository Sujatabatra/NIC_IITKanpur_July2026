package com.sujata.orderservice.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Handles Order Not Found Exception
	 */
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<ApiError> handleOrderNotFoundException(OrderNotFoundException exception) {

		ApiError error = new ApiError(LocalDateTime.now(), HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(), exception.getMessage());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handles Validation Errors
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException exception) {

		String message = exception.getBindingResult().getFieldErrors().stream().map(field -> field.getDefaultMessage())
				.collect(Collectors.joining(", "));

		ApiError error = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), message);

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles Runtime Exceptions
	 */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiError> handleRuntimeException(RuntimeException exception) {

		ApiError error = new ApiError(LocalDateTime.now(), HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(), exception.getMessage());

		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles All Other Exceptions
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleException(Exception exception) {

		ApiError error = new ApiError(LocalDateTime.now(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), exception.getMessage());

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}