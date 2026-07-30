package com.sujata.inventoryservice.client;

import java.math.BigDecimal;

public class Product {

	private Long id;
	private String productCode;
	private String name;
	private String description;
	private String category;
	private BigDecimal price;
	private Integer stockQuantity;

	public Product() {
	}

	public Product(Long id, String productCode, String name, String description, String category, BigDecimal price,
			Integer stockQuantity) {
		this.id = id;
		this.productCode = productCode;
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
		this.stockQuantity = stockQuantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

}