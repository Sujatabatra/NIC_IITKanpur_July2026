package com.sujata.notificationservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import com.sujata.notificationservice.entity.Notification;

/*
 * @DataJpaTest tells Spring Boot:

"Load only the components required to test the JPA layer."

Instead of starting your entire application, it starts only the persistence-related components.

It loads:

Entity classes (@Entity)
Spring Data JPA repositories (JpaRepository)
EntityManager
DataSource
Hibernate
Transaction Manager
 */
@DataJpaTest
@ActiveProfiles("test")
/*
 * If an embedded database is available, replace the configured datasource.
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class NotificationRepositoryTest {

	@Autowired
	private NotificationRepository notificationRepository;

	private Notification notification;

	@BeforeEach
	void setUp() {

		notification = new Notification();

		notification.setOrderNumber("ORD-1001");
		notification.setCustomerName("Amit Sharma");
		notification.setCustomerEmail("amit@gmail.com");
		notification.setNotificationType("EMAIL");
		notification.setMessage("Your order ORD-1001 has been placed successfully.");
		notification.setStatus("SENT");
		notification.setNotificationDate(LocalDateTime.now());
	}

	@Test
	@DisplayName("Should save notification successfully")
	void shouldSaveNotification() {

		// Act
		Notification savedNotification = notificationRepository.saveAndFlush(notification);

		// Assert
		assertThat(savedNotification).isNotNull();
		assertThat(savedNotification.getId()).isNotNull();

		assertThat(savedNotification.getOrderNumber()).isEqualTo("ORD-1001");

		assertThat(savedNotification.getCustomerName()).isEqualTo("Amit Sharma");

		assertThat(savedNotification.getStatus()).isEqualTo("SENT");
	}

	@Test
	@DisplayName("Should find notification by order number")
	void shouldFindNotificationByOrderNumber() {

		// Arrange
		notificationRepository.saveAndFlush(notification);

		// Act
		Optional<Notification> result = notificationRepository.findByOrderNumber("ORD-1001");

		// Assert
		assertThat(result).isPresent();

		Notification foundNotification = result.get();

		assertThat(foundNotification.getOrderNumber()).isEqualTo("ORD-1001");

		assertThat(foundNotification.getCustomerName()).isEqualTo("Amit Sharma");

		assertThat(foundNotification.getCustomerEmail()).isEqualTo("amit@gmail.com");

		assertThat(foundNotification.getNotificationType()).isEqualTo("EMAIL");

		assertThat(foundNotification.getStatus()).isEqualTo("SENT");
	}

	@Test
	@DisplayName("Should return empty Optional when order number does not exist")
	void shouldReturnEmptyWhenOrderNumberDoesNotExist() {

		// Act
		Optional<Notification> result = notificationRepository.findByOrderNumber("ORD-9999");

		// Assert
		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("Should find notification by ID")
	void shouldFindNotificationById() {

		// Arrange
		Notification savedNotification = notificationRepository.saveAndFlush(notification);

		Long notificationId = savedNotification.getId();

		// Act
		Optional<Notification> result = notificationRepository.findById(notificationId);

		// Assert
		assertThat(result).isPresent();

		assertThat(result.get().getOrderNumber()).isEqualTo("ORD-1001");
	}

	@Test
	@DisplayName("Should update notification status")
	void shouldUpdateNotificationStatus() {

		// Arrange
		Notification savedNotification = notificationRepository.saveAndFlush(notification);

		// Act
		savedNotification.setStatus("FAILED");

		Notification updatedNotification = notificationRepository.saveAndFlush(savedNotification);

		// Assert
		assertThat(updatedNotification.getId()).isEqualTo(savedNotification.getId());

		assertThat(updatedNotification.getStatus()).isEqualTo("FAILED");

		assertThat(updatedNotification.getOrderNumber()).isEqualTo("ORD-1001");
	}

	@Test
	@DisplayName("Should delete notification")
	void shouldDeleteNotification() {

		// Arrange
		Notification savedNotification = notificationRepository.saveAndFlush(notification);

		Long notificationId = savedNotification.getId();

		// Act
		notificationRepository.deleteById(notificationId);
		notificationRepository.flush();

		// Assert
		Optional<Notification> result = notificationRepository.findById(notificationId);

		assertThat(result).isEmpty();
	}

	@Test
	@DisplayName("Should not allow duplicate order number")
	void shouldNotAllowDuplicateOrderNumber() {

		// Arrange
		notificationRepository.saveAndFlush(notification);

		Notification duplicateNotification = new Notification();

		duplicateNotification.setOrderNumber("ORD-1001");
		duplicateNotification.setCustomerName("Neha Verma");
		duplicateNotification.setCustomerEmail("neha@gmail.com");
		duplicateNotification.setNotificationType("EMAIL");
		duplicateNotification.setMessage("Duplicate order notification.");
		duplicateNotification.setStatus("SENT");
		duplicateNotification.setNotificationDate(LocalDateTime.now());

		// Act and Assert
		assertThatThrownBy(() -> notificationRepository.saveAndFlush(duplicateNotification))
				.isInstanceOf(DataIntegrityViolationException.class);
	}
}