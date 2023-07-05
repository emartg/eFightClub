package com.urjcdad.efightclub.application.repository;

import java.util.List;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.urjcdad.efightclub.application.model.Event;
import com.urjcdad.efightclub.application.model.Notification;

@CacheConfig(cacheNames="notificaciones")
public interface NotificationRepository extends JpaRepository <Notification, Long> {
	@CacheEvict(allEntries=true)
	Notification save(Notification notification);
	
	@Cacheable
	List<Notification> findByEvent(Event event);
	
	@Cacheable
	List<Notification> findAll();	
}