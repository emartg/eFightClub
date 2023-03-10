package com.urjcdad.efightclub.application.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.urjcdad.efightclub.application.model.Event;
import com.urjcdad.efightclub.application.model.Users;

public interface EventRepository extends JpaRepository <Event, Long>{
	Optional<Event> findById (Long id);
	List<Event> findByCreator (Users creator);	
	List<Event> findByWinner (Users winner);
	List<Event> findByEventName (String eventName);
}