package com.sujata.productservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sujata.productservice.entity.Product;
import com.sujata.productservice.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

	private final ProductService productService;

	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * Create Product
	 */
	@PostMapping
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {

		Product savedProduct = productService.createProduct(product);

		return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
	}

	/**
	 * Get Product by Id
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {

		Product product = productService.getProductById(id);

		return ResponseEntity.ok(product);
	}

	/**
	 * Get All Products
	 */
	@GetMapping
	public ResponseEntity<List<Product>> getAllProducts() {

		List<Product> products = productService.getAllProducts();

		return ResponseEntity.ok(products);
	}

	/**
	 * Update Product
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable Long id,@RequestBody Product product) {

		Product updatedProduct = productService.updateProduct(id, product);

		return ResponseEntity.ok(updatedProduct);
	}

	/**
	 * Delete Product
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

		productService.deleteProduct(id);

		return ResponseEntity.noContent().build();
	}
//    @DeleteMapping("/{id}")
//    public ResponseEntity<String> deleteProduct(
//            @PathVariable Long id) {
//
//        productService.deleteProduct(id);
//
//        return ResponseEntity.ok("Product deleted successfully.");
//    }

	/**
	 * Search by Category
	 */
	@GetMapping("/category/{category}")
	public ResponseEntity<List<Product>> searchByCategory(@PathVariable String category) {

		List<Product> products = productService.searchByCategory(category);

		return ResponseEntity.ok(products);
	}

	/**
	 * Search by Product Name
	 */
	@GetMapping("/search")
	public ResponseEntity<List<Product>> searchByName(@RequestParam String keyword) {

		List<Product> products = productService.searchByName(keyword);

		return ResponseEntity.ok(products);
	}

}