package com.urjcdad.efightclub.application.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
		if (session.getAttribute("error") != null) {
			session.removeAttribute("error");
			session.removeAttribute("errorMsg");
			session.removeAttribute("errorUsername");
			session.removeAttribute("errorEmail");
		}
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
		}
		
		// Get all the events in the repository
		List<Event> events = eventRepository.findAll();
		
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
			if(event.getKickoffDate().compareTo(currentDate) < 0)
				ongoingEvents.add(event);
			else
				upcomingEvents.add(event);
		
		model.addAttribute("ongoingEvents", ongoingEvents);
		model.addAttribute("upcomingEvents", upcomingEvents);
		
		return "home";
	}
	
	@GetMapping("/login")
	public String logIn(Model model, HttpSession session) {
		if (session.getAttribute("error") != null) {
			model.addAttribute("error", true);
			model.addAttribute("errorMsg",session.getAttribute("errorMsg"));
			model.addAttribute("errorUsername", session.getAttribute("errorUsername"));
			model.addAttribute("errorEmail", session.getAttribute("errorEmail"));
			session.removeAttribute("error");
		}
		return "login";
	}
	
	@PostMapping("/logged_in")
	public String logIn(Model model, HttpSession session, 
			@RequestParam String username, @RequestParam String email, 
			@RequestParam String password ,@RequestParam String reenterPassword) {	
		if (username == ""||email==""||password==""|| reenterPassword=="") {
			session.setAttribute("error", true);
			session.setAttribute("errorUsername", username);
			session.setAttribute("errorEmail", email);
			session.setAttribute("errorMsg", "Rellene todos los campos para continuar");
			
			return "redirect:/login";
		}
		
		if (!password.equals(reenterPassword)) {
			session.setAttribute("error", true);
			session.setAttribute("errorUsername", username);
			session.setAttribute("errorEmail", email);
			session.setAttribute("errorMsg", "Las contraseñas no coinciden");
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
				session.setAttribute("error", true);
				session.setAttribute("errorUsername", username);
				session.setAttribute("errorEmail", email);
				session.setAttribute("errorMsg", "Los datos del usuario no coinciden");
				
				return "redirect:/login";
			}
		}else {
			userRepository.save(user);
			session.setAttribute("username", user.getUsername());
			session.setAttribute("logged", true);
			session.removeAttribute("error");
			session.removeAttribute("errorMsg");
			session.removeAttribute("errorUsername");
			session.removeAttribute("errorEmail");

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
