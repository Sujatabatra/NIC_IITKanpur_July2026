package com.sujata.orderservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sujata.orderservice.entity.Order;
import com.sujata.orderservice.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	/**
	 * Place a New Order
	 */
	@PostMapping
	public ResponseEntity<Order> placeOrder(@Valid @RequestBody Order order) {

		Order savedOrder = orderService.placeOrder(order);

		return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
	}

	/**
	 * Get All Orders
	 */
	@GetMapping
	public ResponseEntity<List<Order>> getAllOrders() {

		return ResponseEntity.ok(orderService.getAllOrders());
	}

	/**
	 * Get Order By Id
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Order> getOrderById(@PathVariable Long id) {

		return ResponseEntity.ok(orderService.getOrderById(id));
	}

	/**
	 * Get Orders By Customer
	 */
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable Long customerId) {

		return ResponseEntity.ok(orderService.getOrdersByCustomer(customerId));
	}

	/**
	 * Get Orders By Status
	 */
	@GetMapping("/status/{status}")
	public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status) {

		return ResponseEntity.ok(orderService.getOrdersByStatus(status));
	}

	/**
	 * Update Order
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Valid @RequestBody Order order) {

		return ResponseEntity.ok(orderService.updateOrder(id, order));
	}

	/**
	 * Cancel Order
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> cancelOrder(@PathVariable Long id) {

		orderService.cancelOrder(id);

		return ResponseEntity.ok("Order cancelled successfully.");
	}

}