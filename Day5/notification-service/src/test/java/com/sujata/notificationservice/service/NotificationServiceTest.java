package com.sujata.notificationservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sujata.notificationservice.entity.Notification;
import com.sujata.notificationservice.event.OrderEvent;
import com.sujata.notificationservice.exception.ResourceNotFoundException;
import com.sujata.notificationservice.repository.NotificationRepository;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

	@Mock
	private NotificationRepository notificationRepository;

	@InjectMocks
	private NotificationServiceImpl notificationService;

	private Notification notification;

	@BeforeEach
	void setUp() {

		notification = new Notification();

		notification.setId(1L);
		notification.setOrderNumber("ORD1001");
		notification.setCustomerName("John Smith");
		notification.setCustomerEmail("john@gmail.com");
		notification.setNotificationType("EMAIL");
		notification.setMessage("Order Placed Successfully");
		notification.setStatus("SENT");
		notification.setNotificationDate(LocalDateTime.now());

	}

	/**
	 * Create Notification
	 */
	@Test
	void shouldCreateNotification() {

		//behaviour of Mock
		when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

		//calling service layer function for testing
		Notification saved = notificationService.createNotification(notification);

		assertNotNull(saved);

//		assertEquals("ORD1001", saved.getOrderNumber());

//		verify(notificationRepository, times(1)).save(notification);

	}

	/**
	 * Get Notification By Id
	 */
	@Test
	void shouldReturnNotification() {

		when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

		Notification result = notificationService.getNotificationById(1L);

		assertEquals("John Smith", result.getCustomerName());

	}

	/**
	 * Get All Notifications
	 */
	@Test
	void shouldReturnAllNotifications() {

		when(notificationRepository.findAll()).thenReturn(Arrays.asList(notification));

		List<Notification> list = notificationService.getAllNotifications();

		assertEquals(1, list.size());

	}

	/**
	 * Delete Notification
	 */
	@Test
	void shouldDeleteNotification() {

		when(notificationRepository.findById(1L)).thenReturn(Optional.of(notification));

		doNothing().when(notificationRepository).delete(notification);

		notificationService.deleteNotification(1L);

		verify(notificationRepository, times(1)).delete(notification);

	}

	/**
	 * Resource Not Found
	 */
	@Test
	void shouldThrowExceptionWhenNotificationNotFound() {

		when(notificationRepository.findById(100L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {

			notificationService.getNotificationById(100L);

		});

	}

	/**
	 * Send Notification
	 */
	@Test
	void shouldSendOrderNotification() {

		OrderEvent event = new OrderEvent();

		event.setOrderId(1L);
		event.setOrderNumber("ORD1001");
		event.setCustomerId(10L);
		event.setCustomerName("John Smith");
		event.setCustomerEmail("john@gmail.com");
		event.setProductId(101L);
		event.setProductName("Laptop");
		event.setQuantity(2);
		event.setTotalAmount(new BigDecimal("130000"));
		event.setStatus("PLACED");

		when(notificationRepository.save(any(Notification.class))).thenReturn(notification);

		notificationService.sendOrderNotification(event);

		verify(notificationRepository, times(1)).save(any(Notification.class));

	}

}