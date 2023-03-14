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
public class HomeController {
	
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private EventService eventService;

	@GetMapping("/home")
	public String viewHome(Model model, HttpSession session) {
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
		}
		
		List<Event> events = eventRepository.findAll();
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
		
		return "home";
	}
	
	@GetMapping("/login")
	public String logIn() {
		return "login";
	}
	
	@PostMapping("/logged_in")
	public String logIn(Model model, HttpSession session, 
			@RequestParam String username, @RequestParam String email, 
			@RequestParam String password) {
		
		if (username == ""||email==""||password=="") {
			return "redirect:/login";
		}
		
		model.addAttribute("username", username);
		model.addAttribute("email", email);
		model.addAttribute("password", password);
		model.addAttribute("logged", true);
		
		
		Users user = new Users(username, email, password);
		Users check = userRepository.findByUsername(username);
		if (check != null) {
			if (check.getEmail().equals(user.getEmail())&&check.getPassword().equals(user.getPassword())) {				
				session.setAttribute("username", user.getUsername());
				session.setAttribute("logged", true);			
				return "redirect:/home";
			}else {
				return "redirect:/login";
			}
		}else {
			userRepository.save(user);
			session.setAttribute("username", user.getUsername());
			session.setAttribute("logged", true);			
			return "redirect:/home";
		}	
	}
	
	
	@GetMapping("/logout")
	public String viewHomeLoggedOut(Model model, HttpSession session) {
		
		session.setAttribute("username", null);
		session.setAttribute("logged", null);
		
		return "redirect:/home";
	}
}
