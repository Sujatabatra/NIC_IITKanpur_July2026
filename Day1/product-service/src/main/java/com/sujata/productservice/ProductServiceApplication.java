package com.sujata.productservice;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sujata.productservice.entity.Product;
import com.sujata.productservice.repository.ProductRepository;

@SpringBootApplication
public class ProductServiceApplication implements CommandLineRunner {

	private ProductRepository repository;
	
	
	public ProductServiceApplication(ProductRepository repository) {
		super();
		this.repository = repository;
	}

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repository.save(new Product("P001", "Iphone", "i Phone 17", "Phone", BigDecimal.valueOf(67000), 20));
		repository.save(new Product("P002", "Dell", "Dell Inspiron", "Laptop", BigDecimal.valueOf(167000), 26));
	}

}
