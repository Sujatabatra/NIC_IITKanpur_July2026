package com.sujata.inventoryservice.event;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderEvent implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String orderNumber;

	private Long customerId;

	private Long productId;

	private Integer quantity;

	private BigDecimal totalAmount;

	private String status;

	public OrderEvent() {
	}

	public OrderEvent(Long id, String orderNumber, Long customerId, Long productId, Integer quantity,
			BigDecimal totalAmount, String status) {

		this.id = id;
		this.orderNumber = orderNumber;
		this.customerId = customerId;
		this.productId = productId;
		this.quantity = quantity;
		this.totalAmount = totalAmount;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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