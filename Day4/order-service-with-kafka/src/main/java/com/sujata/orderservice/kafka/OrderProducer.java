package com.sujata.orderservice.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderProducer {

	@Value("${app.kafka.topic.order-created}")
	private String topic;

	//private String topic="order-created";
	
	private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

	public OrderProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {

		this.kafkaTemplate = kafkaTemplate;

	}

	/**
	 * Publish Order Event to Kafka Topic
	 */
	public void publish(OrderEvent event) {

		kafkaTemplate.send(topic, event);

		System.out.println("Order Event Published : " + event.getOrderNumber());

	}

}