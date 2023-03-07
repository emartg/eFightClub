package com.urjcdad.efightclub.application.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.urjcdad.efightclub.application.model.Event;
import com.urjcdad.efightclub.application.model.Notification;

public interface NotificationRepository extends JpaRepository <Notification, Long>{
	List<Notification> findByEvent (Event event);	
}