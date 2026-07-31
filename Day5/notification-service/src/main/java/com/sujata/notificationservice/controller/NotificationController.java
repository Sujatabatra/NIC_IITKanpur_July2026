package com.sujata.notificationservice.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sujata.notificationservice.entity.Notification;
import com.sujata.notificationservice.service.NotificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/notifications")
@Validated
public class NotificationController {

	private final NotificationService notificationService;

	public NotificationController(NotificationService notificationService) {

		this.notificationService = notificationService;

	}

	/**
	 * Create Notification
	 */
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Notification createNotification(@Valid @RequestBody Notification notification) {

		return notificationService.createNotification(notification);

	}

	/**
	 * Get All Notifications
	 */
	@GetMapping
	public List<Notification> getAllNotifications() {

		return notificationService.getAllNotifications();

	}

	/**
	 * Get Notification By Id
	 */
	@GetMapping("/{id}")
	public Notification getNotificationById(@PathVariable Long id) {

		return notificationService.getNotificationById(id);

	}

	/**
	 * Delete Notification
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteNotification(@PathVariable Long id) {

		notificationService.deleteNotification(id);

	}

}