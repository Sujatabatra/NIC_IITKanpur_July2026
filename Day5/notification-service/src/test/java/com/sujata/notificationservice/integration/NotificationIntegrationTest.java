package com.sujata.notificationservice.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sujata.notificationservice.entity.Notification;
import com.sujata.notificationservice.repository.NotificationRepository;
import com.sujata.notificationservice.service.NotificationService;

/*
 * Loads the complete Spring application context.
 *
 * Controller -> Service implementation -> Repository -> MySQL
 */
@SpringBootTest
/*
 * @AutoConfigureMockMvc is used with @SpringBootTest to automatically create and configure a MockMvc object.
 */
@AutoConfigureMockMvc
@ActiveProfiles("test")
class NotificationIntegrationTest {

	/*
	 * Sends HTTP requests through Spring MVC without mocking the controller.
	 */
	@Autowired
	private MockMvc mockMvc;

	/*
	 * Converts Java objects into JSON request bodies.
	 */
	@Autowired
	private ObjectMapper objectMapper;

	/*
	 * Real service implementation is injected.
	 */
	@Autowired
	private NotificationService notificationService;

	/*
	 * Real repository connected to notification_test_db is injected.
	 */
	@Autowired
	private NotificationRepository notificationRepository;

	@BeforeEach
	void setUp() {

		/*
		 * Remove records before every test.
		 *
		 * Never point application-test.properties to the production database.
		 */
		notificationRepository.deleteAll();
	}

	// =========================================================
	// COMPLETE CONTROLLER + SERVICE + REPOSITORY TESTS
	// =========================================================

	@Test
	@DisplayName("""
			POST /api/v1/notifications should create a notification
			through controller, service, repository and MySQL
			""")
	void shouldCreateNotificationThroughAllLayers() throws Exception {

		// Arrange
		Notification notification = createNotification("ORD-1001", "Sujata Batra", "sujata@example.com");

		// Act and verify controller response
		mockMvc.perform(post("/api/v1/notifications").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(notification))).andDo(print()).andExpect(status().isCreated())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").isNumber()).andExpect(jsonPath("$.orderNumber").value("ORD-1001"))
				.andExpect(jsonPath("$.customerName").value("Sujata Batra"))
				.andExpect(jsonPath("$.customerEmail").value("sujata@example.com"))
				.andExpect(jsonPath("$.notificationType").value("EMAIL"))
				.andExpect(jsonPath("$.status").value("PENDING"));

		// Verify that the real repository saved data in MySQL
		Optional<Notification> databaseNotification = notificationRepository.findByOrderNumber("ORD-1001");

		assertThat(databaseNotification).isPresent();

		assertThat(databaseNotification.get().getId()).isNotNull();

		assertThat(databaseNotification.get().getCustomerName()).isEqualTo("Sujata Batra");
	}

	@Test
	@DisplayName("""
			GET /api/v1/notifications should return all notifications
			through controller, service and repository
			""")
	void shouldGetAllNotificationsThroughAllLayers() throws Exception {

		// Arrange
		notificationRepository.save(createNotification("ORD-1002", "Customer One", "customer1@example.com"));

		notificationRepository.save(createNotification("ORD-1003", "Customer Two", "customer2@example.com"));

		// Act and Assert
		mockMvc.perform(get("/api/v1/notifications").accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isOk()).andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].id").isNumber())
				.andExpect(jsonPath("$[0].orderNumber").exists()).andExpect(jsonPath("$[1].id").isNumber())
				.andExpect(jsonPath("$[1].orderNumber").exists());

		assertThat(notificationRepository.count()).isEqualTo(2);
	}

	@Test
	@DisplayName("""
			GET /api/v1/notifications/{id} should return one notification
			through all three layers
			""")
	void shouldGetNotificationByIdThroughAllLayers() throws Exception {

		// Arrange
		Notification savedNotification = notificationRepository
				.save(createNotification("ORD-1004", "Rahul Sharma", "rahul@example.com"));

		Long notificationId = savedNotification.getId();

		// Act and Assert
		mockMvc.perform(get("/api/v1/notifications/{id}", notificationId).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(notificationId))
				.andExpect(jsonPath("$.orderNumber").value("ORD-1004"))
				.andExpect(jsonPath("$.customerName").value("Rahul Sharma"))
				.andExpect(jsonPath("$.customerEmail").value("rahul@example.com"))
				.andExpect(jsonPath("$.notificationType").value("EMAIL"))
				.andExpect(jsonPath("$.status").value("PENDING"));
	}

	@Test
	@DisplayName("""
			DELETE /api/v1/notifications/{id} should remove notification
			through controller, service, repository and MySQL
			""")
	void shouldDeleteNotificationThroughAllLayers() throws Exception {

		// Arrange
		Notification savedNotification = notificationRepository
				.save(createNotification("ORD-1005", "Anita Singh", "anita@example.com"));

		Long notificationId = savedNotification.getId();

		assertThat(notificationRepository.findById(notificationId)).isPresent();

		// Act and Assert
		mockMvc.perform(delete("/api/v1/notifications/{id}", notificationId)).andDo(print())
				.andExpect(status().isNoContent()).andExpect(content().string(""));

		// Verify deletion directly from MySQL
		assertThat(notificationRepository.findById(notificationId)).isEmpty();
	}

	@Test
	@DisplayName("""
			POST /api/v1/notifications should return 400
			when request data is invalid
			""")
	void shouldReturnBadRequestForInvalidNotification() throws Exception {

		// Arrange
		Notification invalidNotification = new Notification();

		invalidNotification.setOrderNumber("ORD-1006");

		// Invalid because customerName has @NotBlank
		invalidNotification.setCustomerName("");

		// Invalid because customerEmail has @Email
		invalidNotification.setCustomerEmail("invalid-email");

		// Invalid because notificationType has @NotBlank
		invalidNotification.setNotificationType("");

		invalidNotification.setMessage("Test notification");
		invalidNotification.setStatus("PENDING");
		invalidNotification.setNotificationDate(LocalDateTime.now());

		// Act and Assert
		mockMvc.perform(post("/api/v1/notifications").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidNotification))).andDo(print())
				.andExpect(status().isBadRequest());

		// Invalid request should not insert a row
		assertThat(notificationRepository.count()).isZero();
	}

	// =========================================================
	// SERVICE + REPOSITORY INTEGRATION TESTS
	// =========================================================

	@Test
	@DisplayName("""
			Service should create notification using real repository
			and MySQL database
			""")
	void shouldCreateNotificationUsingService() {

		// Arrange
		Notification notification = createNotification("ORD-2001", "Service Customer", "service@example.com");

		// Act
		Notification savedNotification = notificationService.createNotification(notification);

		// Assert service result
		assertThat(savedNotification).isNotNull();
		assertThat(savedNotification.getId()).isNotNull();

		assertThat(savedNotification.getOrderNumber()).isEqualTo("ORD-2001");

		// Verify MySQL database
		Optional<Notification> databaseNotification = notificationRepository.findByOrderNumber("ORD-2001");

		assertThat(databaseNotification).isPresent();

		assertThat(databaseNotification.get().getCustomerName()).isEqualTo("Service Customer");
	}

	@Test
	@DisplayName("Service should retrieve all notifications from MySQL")
	void shouldGetAllNotificationsUsingService() {

		// Arrange
		notificationRepository.save(createNotification("ORD-2002", "First Customer", "first@example.com"));

		notificationRepository.save(createNotification("ORD-2003", "Second Customer", "second@example.com"));

		// Act
		List<Notification> notifications = notificationService.getAllNotifications();

		// Assert
		assertThat(notifications).hasSize(2);

		assertThat(notifications).extracting(Notification::getOrderNumber).containsExactlyInAnyOrder("ORD-2002",
				"ORD-2003");
	}

	@Test
	@DisplayName("Service should retrieve notification by ID from MySQL")
	void shouldGetNotificationByIdUsingService() {

		// Arrange
		Notification savedNotification = notificationRepository
				.save(createNotification("ORD-2004", "ID Customer", "id@example.com"));

		// Act
		Notification result = notificationService.getNotificationById(savedNotification.getId());

		// Assert
		assertThat(result).isNotNull();

		assertThat(result.getId()).isEqualTo(savedNotification.getId());

		assertThat(result.getOrderNumber()).isEqualTo("ORD-2004");
	}

	@Test
	@DisplayName("Service should delete notification from MySQL")
	void shouldDeleteNotificationUsingService() {

		// Arrange
		Notification savedNotification = notificationRepository
				.save(createNotification("ORD-2005", "Delete Customer", "delete@example.com"));

		Long notificationId = savedNotification.getId();

		// Act
		notificationService.deleteNotification(notificationId);

		// Assert
		assertThat(notificationRepository.findById(notificationId)).isEmpty();
	}

	// =========================================================
	// REPOSITORY INTEGRATION TESTS
	// =========================================================

	@Test
	@DisplayName("""
			Repository should find notification using order number
			from MySQL
			""")
	void shouldFindNotificationByOrderNumber() {

		// Arrange
		notificationRepository
				.saveAndFlush(createNotification("ORD-3001", "Repository Customer", "repository@example.com"));

		// Act
		Optional<Notification> result = notificationRepository.findByOrderNumber("ORD-3001");

		// Assert
		assertThat(result).isPresent();

		assertThat(result.get().getOrderNumber()).isEqualTo("ORD-3001");

		assertThat(result.get().getCustomerName()).isEqualTo("Repository Customer");

		assertThat(result.get().getCustomerEmail()).isEqualTo("repository@example.com");
	}

	@Test
	@DisplayName("""
			Repository should return empty when order number
			does not exist
			""")
	void shouldReturnEmptyForUnknownOrderNumber() {

		// Act
		Optional<Notification> result = notificationRepository.findByOrderNumber("UNKNOWN-ORDER");

		// Assert
		assertThat(result).isEmpty();
	}

	private Notification createNotification(String orderNumber, String customerName, String customerEmail) {

		Notification notification = new Notification();

		notification.setOrderNumber(orderNumber);
		notification.setCustomerName(customerName);
		notification.setCustomerEmail(customerEmail);
		notification.setNotificationType("EMAIL");
		notification.setMessage("Order " + orderNumber + " has been placed");
		notification.setStatus("PENDING");
		notification.setNotificationDate(LocalDateTime.now());

		return notification;
	}
}