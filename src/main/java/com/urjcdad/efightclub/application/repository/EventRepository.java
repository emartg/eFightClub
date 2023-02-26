package com.urjcdad.efightclub.application.repository;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.urjcdad.efightclub.application.model.Event;
import com.urjcdad.efightclub.application.model.Users;

public interface EventRepository extends JpaRepository <Event, Long>{
	List<Event> findByCreator (Users creator);	
	List<Event> findByWinner (Users winner);	
	List<Event> findByStartingDate (Date startingDate);
	List<Event> findByName (String name);	
	List<Event> findByGame (String game);
}