package com.sujata.orderservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sujata.orderservice.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	/**
	 * Checks whether an order number already exists.
	 */
	boolean existsByOrderNumber(String orderNumber);

	/**
	 * Finds an order using the business order number.
	 */
	Optional<Order> findByOrderNumber(String orderNumber);

	/**
	 * Returns all orders placed by a customer.
	 */
	List<Order> findByCustomerId(Long customerId);

	/**
	 * Returns all orders for a product.
	 */
	List<Order> findByProductId(Long productId);

	/**
	 * Returns all orders having the given status.
	 */
	List<Order> findByStatusIgnoreCase(String status);

}