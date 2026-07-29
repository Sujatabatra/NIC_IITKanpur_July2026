package com.sujata.orderservice.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import com.sujata.orderservice.exception.CustomerServiceUnavailableException;

@Component
public class CustomerClient {

	private final RestTemplate restTemplate;

	public CustomerClient(RestTemplate restTemplate) {

		this.restTemplate = restTemplate;
	}

	public Customer getCustomer(Long customerId) {

		return restTemplate.getForObject("http://customer-service/api/v1/customers/{id}", Customer.class, customerId);
	}

}