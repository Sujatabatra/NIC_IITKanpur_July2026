package com.sujata.notificationservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sujata.notificationservice.entity.Notification;
import com.sujata.notificationservice.service.NotificationService;

/*
 * Load only the Spring MVC (Web) layer required to test NotificationController
 * What gets loaded?
1. NotificationController
2. Spring MVC
3. Jackson (ObjectMapper)
4. Validation
5. Exception Handlers (@ControllerAdvice)
6. MockMvc
 */
@WebMvcTest(NotificationController.class)
/*
 * Overrides properties only for this test class.
 */
@TestPropertySource(properties = {
	    "spring.cloud.config.enabled=false",
	    "spring.config.import="
	})
class NotificationControllerTest {

	/*
	 * MockMvc simulates HTTP requests without starting Tomcat
	 */
	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private NotificationService notificationService;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Create Notification
	 */
	@Test
	void shouldCreateNotification() throws Exception {

		Notification notification = getNotification();

		when(notificationService.createNotification(any(Notification.class))).thenReturn(notification);

		mockMvc.perform(post("/api/v1/notifications").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(notification))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.orderNumber").value("ORD1001"));

	}

	/**
	 * Get Notification By Id
	 */
	@Test
	void shouldReturnNotificationById() throws Exception {

		Notification notification = getNotification();

		when(notificationService.getNotificationById(1L)).thenReturn(notification);

		mockMvc.perform(get("/api/v1/notifications/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.customerName").value("John Smith"));

	}

	/**
	 * Get All Notifications
	 */
	@Test
	void shouldReturnAllNotifications() throws Exception {

		Notification notification = getNotification();

		when(notificationService.getAllNotifications()).thenReturn(Arrays.asList(notification));

		mockMvc.perform(get("/api/v1/notifications")).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()").value(1));

	}

	/**
	 * Delete Notification
	 */
	@Test
	void shouldDeleteNotification() throws Exception {

		mockMvc.perform(delete("/api/v1/notifications/1")).andExpect(status().isNoContent());

	}

	/**
	 * Common Test Object
	 */
	private Notification getNotification() {

		Notification notification = new Notification();

		notification.setId(1L);
		notification.setOrderNumber("ORD1001");
		notification.setCustomerName("John Smith");
		notification.setCustomerEmail("john@gmail.com");
		notification.setNotificationType("EMAIL");
		notification.setMessage("Order placed successfully");
		notification.setStatus("SENT");
		notification.setNotificationDate(LocalDateTime.now());

		return notification;

	}

}