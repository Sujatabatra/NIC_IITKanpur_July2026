package com.sujata.productservice.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "product_code",nullable=false,unique=true,length=20)
	private String productCode;
	
	@Column(nullable = false,length = 100)
	private String name;
	
	@Column(nullable = false,length = 500)
	private String description;
	
	@Column(nullable = false,length = 50)
	private String category;
	BigDecimal price;
	
	@Column(name="stock_quantity",nullable = false)
	Integer stockQunatity;
	
	
	public Product() {
		super();
	}
	
	
	public Product(String productCode, String name, String description, String category, BigDecimal price,
			Integer stockQunatity) {
		super();
		this.productCode = productCode;
		this.name = name;
		this.description = description;
		this.category = category;
		this.price = price;
		this.stockQunatity = stockQunatity;
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
	public Integer getStockQunatity() {
		return stockQunatity;
	}
	public void setStockQunatity(Integer stockQunatity) {
		this.stockQunatity = stockQunatity;
	}
	
	
	
	
}
