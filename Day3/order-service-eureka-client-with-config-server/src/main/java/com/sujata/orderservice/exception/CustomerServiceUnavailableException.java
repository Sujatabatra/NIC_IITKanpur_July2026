package com.sujata.orderservice.exception;

public class CustomerServiceUnavailableException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomerServiceUnavailableException() {
		super();
	}

	public CustomerServiceUnavailableException(String message) {
		super(message);
	}

}