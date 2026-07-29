package com.sujata.orderservice.service;

import java.util.List;

import com.sujata.orderservice.entity.Order;

public interface OrderService {

	/**
	 * Places a new order.
	 *
	 * @param order Order details
	 * @return Saved Order
	 */
	Order placeOrder(Order order);

	/**
	 * Updates an existing order.
	 *
	 * @param id    Order Id
	 * @param order Updated Order
	 * @return Updated Order
	 */
	Order updateOrder(Long id, Order order);

	/**
	 * Cancels an order.
	 *
	 * @param id Order Id
	 */
	void cancelOrder(Long id);

	/**
	 * Returns an order by Id.
	 *
	 * @param id Order Id
	 * @return Order
	 */
	Order getOrderById(Long id);

	/**
	 * Returns all orders.
	 *
	 * @return List of Orders
	 */
	List<Order> getAllOrders();

	/**
	 * Returns all orders placed by a customer.
	 *
	 * @param customerId Customer Id
	 * @return List of Orders
	 */
	List<Order> getOrdersByCustomer(Long customerId);

	/**
	 * Returns all orders with the given status.
	 *
	 * @param status Order Status
	 * @return List of Orders
	 */
	List<Order> getOrdersByStatus(String status);

}