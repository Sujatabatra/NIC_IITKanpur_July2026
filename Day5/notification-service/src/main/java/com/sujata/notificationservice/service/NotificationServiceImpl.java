package com.sujata.notificationservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sujata.notificationservice.entity.Notification;
import com.sujata.notificationservice.event.OrderEvent;
import com.sujata.notificationservice.exception.ResourceNotFoundException;
import com.sujata.notificationservice.repository.NotificationRepository;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

	private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

	private final NotificationRepository notificationRepository;

	public NotificationServiceImpl(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Override
	public Notification createNotification(Notification notification) {

		logger.info("Creating notification for Order Number : {}", notification.getOrderNumber());

		Notification saved = notificationRepository.save(notification);

		logger.info("Notification created successfully.");

		return saved;
	}

	@Override
	public List<Notification> getAllNotifications() {

		logger.info("Fetching all notifications.");

		return notificationRepository.findAll();
	}

	@Override
	public Notification getNotificationById(Long id) {

		logger.info("Fetching notification with Id : {}", id);

		return notificationRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Notification not found with Id : " + id));
	}

	@Override
	public void deleteNotification(Long id) {  //fortesting callong service methods

		Notification notification = getNotificationById(id);

		notificationRepository.delete(notification);  //mimic the behaviout of repo methods

		logger.info("Notification deleted with Id : {}", id);
	}

	@Override
	public Notification sendOrderNotification(OrderEvent orderEvent) {

		logger.info("Sending order notification for Order Number : {}", orderEvent.getOrderNumber());

		Notification notification = new Notification();

		notification.setOrderNumber(orderEvent.getOrderNumber());

		notification.setCustomerName(orderEvent.getCustomerName());

		notification.setCustomerEmail(orderEvent.getCustomerEmail());

		notification.setNotificationType("EMAIL");

		notification.setMessage(buildMessage(orderEvent));

		notification.setStatus("SENT");

		notification.setNotificationDate(LocalDateTime.now());

		Notification saved = notificationRepository.save(notification);

		logger.info("Notification sent successfully to {}", orderEvent.getCustomerEmail());

		return saved;
	}

	private String buildMessage(OrderEvent orderEvent) {

		return String.format("Hello %s, your order %s for %d x %s has been placed successfully. Total Amount: %s",
				orderEvent.getCustomerName(), orderEvent.getOrderNumber(), orderEvent.getQuantity(),
				orderEvent.getProductName(), orderEvent.getTotalAmount());
	}

}