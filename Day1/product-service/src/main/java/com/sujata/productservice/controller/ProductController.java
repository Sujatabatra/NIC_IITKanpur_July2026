package com.sujata.productservice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.sujata.productservice.entity.Product;
import com.sujata.productservice.service.ProductService;
import com.sujata.productservice.service.ProductServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
	
		this.productService = productService;
	}
	
	@GetMapping("/api/v1/products")
	public ResponseEntity<List<Product>> getAllProduct(){
		List<Product> products=productService.getAllProducts();
		return ResponseEntity.ok(products);
	}
}
