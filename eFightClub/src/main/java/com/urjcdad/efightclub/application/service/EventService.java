package com.urjcdad.efightclub.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.urjcdad.efightclub.application.model.Event;

@Service
public class EventService {
	public void sortEventsByDescDate(List<Event> list) {
		list.sort((o1, o2) 
				-> o1.getKickoffDate().compareTo(o2.getKickoffDate()));
	}
}
