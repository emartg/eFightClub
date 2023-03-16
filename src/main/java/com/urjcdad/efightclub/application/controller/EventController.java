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
		
		// Ensure the user has entered all the required parameters to
		// create an event. Otherwise, reload the page
		if (eventName == "" || game == "" || regDate == null || 
				kickoffDate == null || numSlots==null)
			return "redirect:/events/create";
		
		// Get the current user and create an event under its name
		Users user = userRepository.findByUsername(session.getAttribute("username").toString());		
		Event event = new Event(eventName, game, regDate, kickoffDate, numSlots, user);
		
		// Ensure that the new event registration due date is a 
		// date in the future. Otherwise, reload the page
		/*
		long your_milliseconds = System.currentTimeMillis();
		Date currentDate = new Date(your_milliseconds);	
		if (event.getRegDate().compareTo(currentDate) < 0)
			return "redirect:/events/create";
		*/
		
		// Ensure that the new event registration due date is 
		// an earlier date that the kick-off date. 
		// Otherwise, reload the page
		if (event.getKickoffDate().compareTo(event.getRegDate()) < 0)
			return "redirect:/events/create";
		
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
	
	@GetMapping("/events/{id}/register")
	public String competeEvent(Model model, HttpSession session, 
			@PathVariable long id) {
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
			
			long your_milliseconds = System.currentTimeMillis();
			Date currentDate = new Date(your_milliseconds);
			Users user = userRepository.findByUsername(session.getAttribute("username").toString());
			Event event = eventRepository.findById(id).get();
			
			// Only add user to the event if the registration
			// has not closed yet
			if (event.getRegDate().compareTo(currentDate) > 0) {
				event.addParticipant(user);	
				eventRepository.save(event);
			}
		}
	
		return "redirect:/home";
	}
	
	@GetMapping("/events/{id}/subscribe")
	public String subscribeEvent(Model model, HttpSession session, 
			@PathVariable long id) {
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
			
			Users user = userRepository.findByUsername(session.getAttribute("username").toString());
			Event event = eventRepository.findById(id).get();
			
			event.addSubscriber(user);		
		}		
	
		return "redirect:/home";
	}
	
	@GetMapping("/events/my_events")
	public String myEvents(Model model, HttpSession session) {
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
		}
		
		// Get all the events created by the current user
		String currentUsername = session.getAttribute("username").toString();
		Users currentUser = userRepository.findByUsername(currentUsername);
		List<Event> events = eventRepository.findByCreator(currentUser);
		
		// Lists for ongoing and upcoming events
		List<Event> ongoingEvents = new ArrayList<Event>();
		List<Event> upcomingEvents = new ArrayList<Event>();
		
		// Sort events by descending date
		eventService.sortEventsByDescDate(events);
		
		// Check the current time to determine
		// whether each event is an ongoing or upcoming event
		long your_milliseconds = System.currentTimeMillis();
		Date currentDate = new Date(your_milliseconds);	
		for (Event event: events)
			if (event.getKickoffDate().compareTo(currentDate) < 0)
				ongoingEvents.add(event);
			else
				upcomingEvents.add(event);
		
		model.addAttribute("ongoingEvents", ongoingEvents);
		model.addAttribute("upcomingEvents", upcomingEvents);
		
		return "my_events";
	}
	
}
