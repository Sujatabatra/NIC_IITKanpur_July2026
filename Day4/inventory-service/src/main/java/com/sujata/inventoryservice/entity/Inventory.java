package com.sujata.inventoryservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "inventory")
public class Inventory implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Product Id is required")
	@Column(nullable = false, unique = true)
	private Long productId;

	
	@Column(nullable = false, length = 100)
	private String productName;

	@NotNull(message = "Available Quantity is required")
	@Min(value = 0, message = "Quantity cannot be negative")
	@Column(nullable = false)
	private Integer availableQuantity;

	@Column(nullable = false)
	private LocalDateTime lastUpdated;

	public Inventory() {
	}

	public Inventory(Long id, Long productId, String productName, Integer availableQuantity,
			LocalDateTime lastUpdated) {

		this.id = id;
		this.productId = productId;
		this.productName = productName;
		this.availableQuantity = availableQuantity;
		this.lastUpdated = lastUpdated;
	}

	@PrePersist
	@PreUpdate
	public void updateTimestamp() {
		this.lastUpdated = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Integer getAvailableQuantity() {
		return availableQuantity;
	}

	public void setAvailableQuantity(Integer availableQuantity) {
		this.availableQuantity = availableQuantity;
	}

	public LocalDateTime getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDateTime lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

}