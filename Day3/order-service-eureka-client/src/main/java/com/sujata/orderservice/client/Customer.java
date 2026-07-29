package com.sujata.orderservice.client;

public class Customer {

	private Long id;
	private String name;
	private String email;
	private String city;
	private String phone;

	public Customer() {
	}

	public Customer(Long id, String name, String email, String city, String phone) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.city = city;
		this.phone = phone;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}