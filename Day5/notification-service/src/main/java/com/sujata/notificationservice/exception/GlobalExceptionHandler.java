package com.sujata.notificationservice.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Resource Not Found
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ProblemDetail handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {

		logger.error(ex.getMessage());

		ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);

		problem.setTitle("Resource Not Found");
		problem.setDetail(ex.getMessage());

		problem.setProperty("timestamp", LocalDateTime.now());

		problem.setProperty("path", request.getRequestURI());

		return problem;
	}

	/**
	 * Validation Errors
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, String> handleValidationException(MethodArgumentNotValidException ex) {

		logger.error("Validation Failed");

		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getFieldErrors()
				.forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		return errors;
	}

	/**
	 * Generic Exception
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ProblemDetail handleException(Exception ex, HttpServletRequest request) {

		logger.error(ex.getMessage(), ex);

		ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

		problem.setTitle("Internal Server Error");

		problem.setDetail(ex.getMessage());

		problem.setProperty("timestamp", LocalDateTime.now());

		problem.setProperty("path", request.getRequestURI());

		return problem;
	}

}