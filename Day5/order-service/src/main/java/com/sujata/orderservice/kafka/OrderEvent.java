package com.sujata.orderservice.kafka;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long orderId;
	private String orderNumber;

	private Long customerId;
	private String customerName;
	private String customerEmail;

	private Long productId;
	private String productName;

	private Integer quantity;
	private BigDecimal totalAmount;

	private String status;

	public OrderEvent() {
	}

	public OrderEvent(Long orderId, String orderNumber, Long customerId, String customerName, String customerEmail,
			Long productId, String productName, Integer quantity, BigDecimal totalAmount, String status) {
		super();
		this.orderId = orderId;
		this.orderNumber = orderNumber;
		this.customerId = customerId;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.productId = productId;
		this.productName = productName;
		this.quantity = quantity;
		this.totalAmount = totalAmount;
		this.status = status;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}