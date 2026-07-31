package com.sujata.notificationservice.service;

import java.util.List;

import com.sujata.notificationservice.entity.Notification;
import com.sujata.notificationservice.event.OrderEvent;

public interface NotificationService {

	Notification createNotification(Notification notification);

	List<Notification> getAllNotifications();

	Notification getNotificationById(Long id);

	void deleteNotification(Long id);

	Notification sendOrderNotification(OrderEvent orderEvent);
}