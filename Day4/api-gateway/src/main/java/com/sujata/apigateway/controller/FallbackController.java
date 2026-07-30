package com.sujata.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

	@GetMapping("/fallback/product")
	public ResponseEntity<String> productServiceFallback() {

		return new ResponseEntity<>("Product Service is currently unavailable. Please try again later.",
				HttpStatus.SERVICE_UNAVAILABLE);

	}

	@GetMapping("/fallback/customer")
	public ResponseEntity<String> customerServiceFallback() {

		return new ResponseEntity<>("Customer Service is currently unavailable. Please try again later.",
				HttpStatus.SERVICE_UNAVAILABLE);

	}

	@GetMapping("/fallback/order")
	public ResponseEntity<String> orderServiceFallback() {

		return new ResponseEntity<>("Order Service is currently unavailable. Please try again later.",
				HttpStatus.SERVICE_UNAVAILABLE);

	}

}