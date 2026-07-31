package com.sujata.notificationservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "notifications")
public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Order Number is required")
	@Column(nullable = false, unique = true, length = 30)
	private String orderNumber;

	@NotBlank(message = "Customer Name is required")
	@Column(nullable = false, length = 100)
	private String customerName;

	@NotBlank(message = "Customer email is required")
	@Email(message = "Invalid Email")
	@Column(nullable = false, length = 100)
	private String customerEmail;

	@NotBlank(message = "Notification Type is required")
	@Column(nullable = false, length = 20)
	private String notificationType;

	@NotBlank(message = "message is required")
	@Column(nullable = false, length = 500)
	private String message;

	@NotBlank(message = "status is required")
	@Column(nullable = false, length = 20)
	private String status;

	@NotNull(message = "Notification Date is required")
	@Column(nullable = false)
	private LocalDateTime notificationDate;

	public Notification() {
	}

	public Notification(Long id, String orderNumber, String customerName, String customerEmail, String notificationType,
			String message, String status, LocalDateTime notificationDate) {
		this.id = id;
		this.orderNumber = orderNumber;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.notificationType = notificationType;
		this.message = message;
		this.status = status;
		this.notificationDate = notificationDate;
	}

	public Notification(String orderNumber, String customerName, String customerEmail, String notificationType,
			String message, String status, LocalDateTime notificationDate) {
		super();
		this.orderNumber = orderNumber;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.notificationType = notificationType;
		this.message = message;
		this.status = status;
		this.notificationDate = notificationDate;
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

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDateTime getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(LocalDateTime notificationDate) {
		this.notificationDate = notificationDate;
	}

}