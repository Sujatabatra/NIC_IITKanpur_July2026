package com.sujata.orderservice.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

@Component
public class ProductClient {

	private final RestTemplate restTemplate;

	public ProductClient(RestTemplate restTemplate) {

		this.restTemplate = restTemplate;
	}

	public Product getProduct(Long productId) {
		return restTemplate.getForObject("http://localhost:8082/api/v1/products/{id}", Product.class, productId);
	}

}