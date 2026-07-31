package com.sujata.notificationservice.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.sujata.notificationservice.event.OrderEvent;
import com.sujata.notificationservice.service.NotificationService;

@Component
@Profile("!test")
public class OrderConsumer {

	private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

	private final NotificationService notificationService;

	public OrderConsumer(NotificationService notificationService) {

		this.notificationService = notificationService;

	}

	/**
	 * Consume Order Created Event
	 */
	@KafkaListener(topics = "${app.kafka.topic.order-created}", groupId = "${spring.kafka.consumer.group-id}")
	public void consume(OrderEvent orderEvent) {

		logger.info("========================================");

		logger.info("Order Event Received");

		logger.info("Order Number : {}", orderEvent.getOrderNumber());

		logger.info("Customer Name : {}", orderEvent.getCustomerName());

		logger.info("Customer Email : {}", orderEvent.getCustomerEmail());

		logger.info("Product Name : {}", orderEvent.getProductName());

		logger.info("Quantity : {}", orderEvent.getQuantity());

		notificationService.sendOrderNotification(orderEvent);

		logger.info("Notification Process Completed");

		logger.info("========================================");

	}

}