package com.sujata.orderservice.client;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.sujata.orderservice.exception.CustomerServiceUnavailableException;
import com.sujata.orderservice.exception.InventoryServiceUnavailableException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Configuration
public class InventoryClient {

	private final RestTemplate restTemplate;

	public InventoryClient(RestTemplate restTemplate) {
		
		this.restTemplate = restTemplate;
	}
	@CircuitBreaker(name = "inventoryService", fallbackMethod = "inventoryFallback")
	public Inventory getInventory(Long productId) {

		return restTemplate.getForObject("http://inventory-service/api/v1/inventory/{id}", Inventory.class,productId);
	}

	public Inventory inventoryFallback(Long productId, Exception ex) {

		throw new InventoryServiceUnavailableException("Inventory Service is currently unavailable.");
	}

}
