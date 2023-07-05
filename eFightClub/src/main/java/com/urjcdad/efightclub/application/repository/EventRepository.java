package com.urjcdad.efightclub.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.urjcdad.efightclub.application.model.Event;
import com.urjcdad.efightclub.application.model.Users;

//@CacheConfig(cacheNames="eventos")
public interface EventRepository extends JpaRepository <Event, Long> {
	
	//@Cacheable
	Optional<Event> findById(Long id);
	//@Cacheable
	List<Event> findByCreator(Users creator);	
	//@Cacheable
	List<Event> findByWinner(Users winner);
	//@Cacheable
	Event findByEventName(String eventName);
	//@Cacheable
	List<Event> findAll();	
	//@Override
	//@CacheEvict(allEntries=true)
	//Event save(Event event);
}