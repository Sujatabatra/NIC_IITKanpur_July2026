package com.sujata.inventoryservice.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;



@Component
public class ProductClient {

	private final RestTemplate restTemplate;

	public ProductClient(RestTemplate restTemplate) {

		this.restTemplate = restTemplate;
	}

	/**
	 * Returns Product by Id
	 */
	@CircuitBreaker(name = "productService", fallbackMethod = "productFallback")
	public Product getProduct(Long productId) {

		return restTemplate.getForObject("http://product-service/api/v1/products/{id}", Product.class, productId);
	}

	public Product productFallback(Long productId, Exception ex) {

		Product product = new Product();
		product.setId(productId);
		product.setName("Product Service Unavailable");
		product.setPrice(java.math.BigDecimal.ZERO);
		product.setStockQuantity(0);

		return product;
	}
}