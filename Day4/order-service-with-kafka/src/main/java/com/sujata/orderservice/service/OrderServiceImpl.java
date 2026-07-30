package com.sujata.orderservice.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.sujata.orderservice.client.Customer;
import com.sujata.orderservice.client.CustomerClient;
import com.sujata.orderservice.client.Inventory;
import com.sujata.orderservice.client.InventoryClient;
import com.sujata.orderservice.client.Product;
import com.sujata.orderservice.client.ProductClient;
import com.sujata.orderservice.entity.Order;
import com.sujata.orderservice.exception.OrderNotFoundException;
import com.sujata.orderservice.kafka.OrderEvent;
import com.sujata.orderservice.kafka.OrderProducer;
import com.sujata.orderservice.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	// Repository
	private final OrderRepository orderRepository;
	// For calling other services
	private final CustomerClient customerClient;
	private final ProductClient productClient;
	private final InventoryClient inventoryClient;
	// For Kafka
	private final OrderProducer orderProducer;

	public OrderServiceImpl(OrderRepository orderRepository, CustomerClient customerClient, ProductClient productClient,
			InventoryClient inventoryClient, OrderProducer orderProducer) {

		this.orderRepository = orderRepository;
		this.customerClient = customerClient;
		this.productClient = productClient;
		this.inventoryClient = inventoryClient;
		this.orderProducer = orderProducer;
	}

	@Override
	public Order placeOrder(Order order) {

		// Validate Customer
		Customer customer = customerClient.getCustomer(order.getCustomerId());

		if (customer == null) {
			throw new RuntimeException("Customer not found.");
		}

		// Validate Product
		Product product = productClient.getProduct(order.getProductId());

		if (product == null) {
			throw new RuntimeException("Product not found.");
		}

		// Check Stock
		Inventory inventory = inventoryClient.getInventory(product.getId());

		if (inventory == null) {
			throw new RuntimeException("Inventory not found.");
		}

		if (inventory.getAvailableQuantity() < order.getQuantity()) {
			throw new RuntimeException("Insufficient stock available.");
		}

		// Calculate Total Amount
		BigDecimal totalAmount = product.getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));

		order.setTotalAmount(totalAmount);

		// Generate Order Number
		order.setOrderNumber("ORD" + System.currentTimeMillis());

		// Set Initial Status
		order.setStatus("CREATED");

		// Save Order
		Order savedOrder = orderRepository.save(order);

		// Create Kafka Event
		OrderEvent event = new OrderEvent();

		event.setOrderId(savedOrder.getId());
		event.setOrderNumber(savedOrder.getOrderNumber());

		// Customer Details
		event.setCustomerId(customer.getId());
		event.setCustomerName(customer.getName());
		event.setCustomerEmail(customer.getEmail());

		// Product Details
		event.setProductId(product.getId());
		event.setProductName(product.getName());

		// Order Details
		event.setQuantity(savedOrder.getQuantity());
		event.setTotalAmount(savedOrder.getTotalAmount());
		event.setStatus(savedOrder.getStatus());

		// Publish Kafka Event
		orderProducer.publish(event);

		return savedOrder;
	}

	@Override
	public Order updateOrder(Long id, Order order) {

		Order existingOrder = getOrderById(id);

		Product product = productClient.getProduct(existingOrder.getProductId());

		BigDecimal totalAmount = product.getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));

		existingOrder.setQuantity(order.getQuantity());
		existingOrder.setTotalAmount(totalAmount);
		existingOrder.setStatus(order.getStatus());

		return orderRepository.save(existingOrder);
	}

	@Override
	public void cancelOrder(Long id) {

		Order order = getOrderById(id);

		order.setStatus("CANCELLED");

		orderRepository.save(order);
	}

	@Override
	public Order getOrderById(Long id) {

		return orderRepository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with Id : " + id));
	}

	@Override
	public List<Order> getAllOrders() {

		return orderRepository.findAll();
	}

	@Override
	public List<Order> getOrdersByCustomer(Long customerId) {

		return orderRepository.findByCustomerId(customerId);
	}

	@Override
	public List<Order> getOrdersByStatus(String status) {

		return orderRepository.findByStatusIgnoreCase(status);
	}

}