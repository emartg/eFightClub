package com.urjcdad.efightclub.application.repository;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.urjcdad.efightclub.application.model.Event;
import com.urjcdad.efightclub.application.model.Matches;

public interface MatchesRepository extends JpaRepository <Matches, Long>{
	List<Matches> findByEvent(Event event);	
	List<Matches> findByDate(Date date);
}