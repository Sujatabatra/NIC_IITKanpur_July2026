package com.sujata.inventoryservice.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.sujata.inventoryservice.kafka.OrderEvent;
import com.sujata.inventoryservice.service.InventoryService;

@Component
public class OrderConsumer {

	private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

	private final InventoryService inventoryService;

	public OrderConsumer(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@KafkaListener(topics = "${app.kafka.topic.order-created}", groupId = "${spring.kafka.consumer.group-id}")
	public void consume(OrderEvent orderEvent) {

		logger.info("------------------------------------------");

		logger.info("Order Event Received");

		logger.info("Order Number : {}", orderEvent.getOrderNumber());

		logger.info("Product Id : {}", orderEvent.getProductId());

		logger.info("Ordered Quantity : {}", orderEvent.getQuantity());

		inventoryService.reduceStock(orderEvent.getProductId(), orderEvent.getQuantity());

		logger.info("Inventory Updated Successfully");

		logger.info("------------------------------------------");

	}

}