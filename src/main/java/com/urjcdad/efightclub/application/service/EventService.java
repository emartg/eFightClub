package com.urjcdad.efightclub.application.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.urjcdad.efightclub.application.model.Event;
import com.urjcdad.efightclub.application.repository.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository events;
	
	@PostConstruct
	public void init() {
	}
	
	public Collection<Event> findAll() {
		return events.findAll();
	}

	public Optional<Event> findById(long id) {
		return events.findById(id);
	}
	
	public void deleteById(long id) {
		this.events.deleteById(id);
	}

	public void save(Event post) {
		events.save(post);
	}
	
	public void sortEventsByDescDate(List<Event> list) {
		list.sort((o1, o2) 
				-> o1.getKickoffDate().compareTo(o2.getKickoffDate()));
	}

}
