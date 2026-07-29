package com.sujata.customerservice.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "customers")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Customer Name is required")
	@Column(nullable = false, length = 100)
	private String name;

	@NotBlank(message = "Email is required")
	@Email(message = "Please enter a valid Email Address")
	@Column(nullable = false, unique = true, length = 100)
	private String email;

	@NotBlank(message = "City is required")
	@Column(nullable = false, length = 50)
	private String city;

	@NotBlank(message = "Phone Number is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone Number must contain exactly 10 digits")
	@Column(nullable = false, length = 10)
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