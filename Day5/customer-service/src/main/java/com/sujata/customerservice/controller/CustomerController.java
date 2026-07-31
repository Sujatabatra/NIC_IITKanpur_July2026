package com.sujata.customerservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.sujata.customerservice.entity.Customer;
import com.sujata.customerservice.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/customers")
@Validated
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {

		this.customerService = customerService;

	}

	/**
	 * Create Customer
	 */
	@PostMapping
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {

		Customer savedCustomer = customerService.createCustomer(customer);

		return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
	}

	/**
	 * Get All Customers
	 */
	@GetMapping
	public ResponseEntity<List<Customer>> getAllCustomers() {

		return ResponseEntity.ok(customerService.getAllCustomers());
	}

	/**
	 * Get Customer By Id
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {

		return ResponseEntity.ok(customerService.getCustomerById(id));
	}

	/**
	 * Update Customer
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer) {

		return ResponseEntity.ok(customerService.updateCustomer(id, customer));
	}

	/**
	 * Delete Customer
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {

		customerService.deleteCustomer(id);

		return ResponseEntity.noContent().build();
	}

	/**
	 * Search Customers By City
	 */
	@GetMapping("/city/{city}")
	public ResponseEntity<List<Customer>> searchByCity(@PathVariable String city) {

		return ResponseEntity.ok(customerService.searchByCity(city));
	}

	/**
	 * Search Customers By Name
	 */
	@GetMapping("/search")
	public ResponseEntity<List<Customer>> searchByName(@RequestParam String keyword) {

		return ResponseEntity.ok(customerService.searchByName(keyword));
	}

}