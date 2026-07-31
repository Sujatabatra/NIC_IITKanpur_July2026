package com.sujata.notificationservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sujata.notificationservice.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	Optional<Notification> findByOrderNumber(String orderNumber);

}