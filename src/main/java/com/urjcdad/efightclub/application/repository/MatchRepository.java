package com.urjcdad.efightclub.application.repository;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.urjcdad.efightclub.application.model.Event;
import com.urjcdad.efightclub.application.model.Match;

public interface MatchRepository extends JpaRepository <Match, Long> {
	
	List<Match> findByEvent(Event event);	
	List<Match> findByDate(Date date);
	
}