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
	
	@GetMapping("/log_in")
	public String logIn(Model model, HttpSession session) {
		if (session.getAttribute("error") != null) {
			model.addAttribute("error", true);
			model.addAttribute("errorMsg", session.getAttribute("errorMsg"));
			model.addAttribute("errorEmail", session.getAttribute("errorEmail"));
			session.removeAttribute("error");
		}
		return "log_in";
	}
	
	@GetMapping("/sign_up")
	public String signUp(Model model, HttpSession session) {
		if (session.getAttribute("error") != null) {
			model.addAttribute("error", true);
			model.addAttribute("errorMsg",session.getAttribute("errorMsg"));
			model.addAttribute("errorUsername", session.getAttribute("errorUsername"));
			model.addAttribute("errorEmail", session.getAttribute("errorEmail"));
			session.removeAttribute("error");
		}
		return "sign_up";
	}
	
	@PostMapping("/logged_in")
	public String logIn(Model model, HttpSession session, 
			@RequestParam String email, @RequestParam String password) {	
		if (email.isBlank() || password.isBlank()) {
			session.setAttribute("error", true);
			session.setAttribute("errorEmail", email);
			session.setAttribute("errorMsg", "You must fill the form before entering");
			
			return "redirect:/log_in";
		}
		
		model.addAttribute("email", email);
		model.addAttribute("password", password);
		model.addAttribute("logged", true);
		
		Users user = userRepository.findByEmail(email);
		if (user != null) { 
			if (password.equals(user.getPassword())) { // The credentials are correct				
				session.setAttribute("username", user.getUsername());
				session.setAttribute("logged", true);	
				
				return "redirect:/home";
			} else { // The user exists in the database, but the password is incorrect
				session.setAttribute("error", true);
				session.setAttribute("errorEmail", email);
				session.setAttribute("errorMsg", "The password is incorrect");
				
				return "redirect:/log_in";
			}
		} else { // The user does not exist in the database (the email is new)
			session.setAttribute("error", true);
			session.setAttribute("errorEmail", email);
			session.setAttribute("errorMsg", "The email has not been registered yet");
			
			return "redirect:/log_in";
		}	
	}
	
	@PostMapping("/signed_up")
	public String signUp(Model model, HttpSession session, 
			@RequestParam String username, @RequestParam String email, 
			@RequestParam String password ,@RequestParam String reenterPassword) {
		
		// Check whether all strings are not blank
		if (username.isBlank() || email.isBlank()
				|| password.isBlank() || reenterPassword.isBlank()) {
			session.setAttribute("error", true);
			session.setAttribute("errorUsername", username);
			session.setAttribute("errorEmail", email);
			session.setAttribute("errorMsg", "You must fill the form before entering");
			
			return "redirect:/sign_up";
		}
		
		// Check whether the passwords match
		if (!password.equals(reenterPassword)) {
			session.setAttribute("error", true);
			session.setAttribute("errorUsername", username);
			session.setAttribute("errorEmail", email);
			session.setAttribute("errorMsg", "The passwords do not match");
			
			return "redirect:/sign_up";
		}
		
		model.addAttribute("username", username);
		model.addAttribute("email", email);
		model.addAttribute("password", password);
		model.addAttribute("logged", true);
		
		Users otherUserByUsername = userRepository.findByUsername(username);
		Users otherUserByEmail = userRepository.findByEmail(email);
		if (otherUserByUsername != null || otherUserByEmail != null) {
			session.setAttribute("error", true);
			session.setAttribute("errorUsername", username);
			session.setAttribute("errorEmail", email);
			
			if (otherUserByUsername != null) { // The name is not unique
				session.setAttribute("errorMsg", "The username is already taken");
			
				return "redirect:/sign_up";
			}
			
			// The email is associated with an existing account
			session.setAttribute("errorMsg", "The email corresponds to an existing user");
				
			return "redirect:/sign_up";
		} else { // The user is new and can be added to the database
			Users user = new Users(username, email, password);
			userRepository.save(user);
			
			session.setAttribute("username", username);
			session.setAttribute("logged", true);
			
			session.removeAttribute("error");
			session.removeAttribute("errorMsg");
			session.removeAttribute("errorUsername");
			session.removeAttribute("errorEmail");

			return "redirect:/home";
		}	
	}

	@GetMapping("/log_out")
	public String viewHomeLoggedOut(Model model, HttpSession session) {
		session.setAttribute("username", null);
		session.setAttribute("logged", null);
		
		return "redirect:/home";
	}
	
}
