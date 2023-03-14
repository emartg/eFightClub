package com.urjcdad.efightclub.application.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.urjcdad.efightclub.application.model.Event;
import com.urjcdad.efightclub.application.model.Users;
import com.urjcdad.efightclub.application.repository.EventRepository;
import com.urjcdad.efightclub.application.repository.UsersRepository;
import com.urjcdad.efightclub.application.service.EventService;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EventController {
	
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private EventService eventService;


	@GetMapping("/events/create")
	public String createEvent(Model model, HttpSession session) {
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
		}
		
		return "create_event";
	}
	
	@PostMapping("/events/new")
	public String createEvent(Model model, HttpSession session, 
			@RequestParam String eventName, @RequestParam String game,
			@RequestParam Date regDate, @RequestParam Date kickoffDate,
			@RequestParam Integer numSlots) {
		if (eventName==""||game==""||regDate==null||kickoffDate==null||numSlots==null) {
			return "redirect:/events/create";
		}
	
		Users user = userRepository.findByUsername(session.getAttribute("username").toString());		
		Event event = new Event(eventName, game, regDate, kickoffDate, numSlots, user);
		if (event.getKickoffDate().compareTo(event.getRegDate())<0) {
			return "redirect:/events/create";
		}
		
		/*
		//Method to make sure the a new event created has already started / closed registration
		long yourmilliseconds = System.currentTimeMillis();
		Date currentDate = new Date(yourmilliseconds);	
		if (event.getRegDate().compareTo(currentDate)<0) {
			return "redirect:/events/create";
		}*/
		
		
		eventRepository.save(event);
		
		return "redirect:/events/my_events";
	}
	
	@GetMapping("/events/{id}")
	public String showEvent(Model model, HttpSession session, 
			@PathVariable long id) {
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
		}
		
		Event event = eventRepository.findById(id).get();
		model.addAttribute("event", event);
	
		return "show_event";
	}
	
	@GetMapping("/events/{id}/delete")
	public String deleteEvent(Model model, HttpSession session, 
			@PathVariable long id) {
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
		}
		
		eventRepository.deleteById(id);
	
		return "redirect:/home";
	}
	
	@GetMapping("/events/my_events")
	public String myEvents(Model model, HttpSession session) {
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
		}
		
		String currentUsername = session.getAttribute("username").toString();
		Users currentUser = userRepository.findByUsername(currentUsername);
		List<Event> events = eventRepository.findByCreator(currentUser);
		eventService.sortEventsByDescDate(events);
		List<Event> ongoingEvents = new ArrayList<Event>();
		List<Event> upcomingEvents = new ArrayList<Event>();
		
		// Check the current time to determine
		// whether the event is ongoing or upcoming
		long yourmilliseconds = System.currentTimeMillis();
		Date currentDate = new Date(yourmilliseconds);	
		for (Event event: events)
			if(event.getKickoffDate().compareTo(currentDate) < 0)
				ongoingEvents.add(event);
			else
				upcomingEvents.add(event);
		
		model.addAttribute("ongoingEvents", ongoingEvents);
		model.addAttribute("upcomingEvents", upcomingEvents);
		
		return "my_events";
	}
	
}
