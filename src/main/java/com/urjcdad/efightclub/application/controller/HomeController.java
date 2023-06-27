package com.urjcdad.efightclub.application.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.urjcdad.efightclub.application.model.AuxiliarEventUsers;
import com.urjcdad.efightclub.application.model.Event;
import com.urjcdad.efightclub.application.model.Notification;
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
	
	 @Autowired
	 private PasswordEncoder passwordEncoder;


	@GetMapping("/home")
	public String viewHome(Model model, HttpSession session) {
		if (session.getAttribute("error") != null) {
			session.removeAttribute("error");
			session.removeAttribute("errorMsg");
			session.removeAttribute("errorUsername");
			session.removeAttribute("errorEmail");
		}
		Users currentUser = null;
		
		//Get current User

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUsername = authentication.getName();
			currentUser = userRepository.findByUsername(currentUsername);
			model.addAttribute("username", currentUsername);
			model.addAttribute("logged", true);
			currentUser = userRepository.findByUsername(currentUsername);
		}
	
		
		// Get all the events in the repository
		List<Event> events = eventRepository.findAll();
		
		// Lists for ongoing and upcoming events
		List<Event> finishedEvents = new ArrayList<Event>();
		List<Event> currentEvents = new ArrayList<Event>();
		List<Boolean> participant = new ArrayList<Boolean>();
		List <AuxiliarEventUsers> cuEvents = new ArrayList<AuxiliarEventUsers>();
		List <AuxiliarEventUsers> fiEvents = new ArrayList<AuxiliarEventUsers>();


		// Sort events by descending date
		eventService.sortEventsByDescDate(events);
		
		// Check the current time to determine
		// whether each event is an ongoing or upcoming event
		long your_milliseconds = System.currentTimeMillis();
		Date currentDate = new Date(your_milliseconds);	
		for (Event event: events)
			if(event.getWinner()!=null)
			{
				finishedEvents.add(event);
				if (currentUser!=null) {
					fiEvents.add(new AuxiliarEventUsers(event, currentUser));
				}
			}
			else
			{
				currentEvents.add(event);
				if (currentUser!=null) {
					cuEvents.add(new AuxiliarEventUsers(event, currentUser));
				}
			}
		
		model.addAttribute("finishedEvents", finishedEvents);
		model.addAttribute("fiEvents", fiEvents);
		model.addAttribute("currentEvents", currentEvents);
		model.addAttribute("cuEvents", cuEvents);

		return "home";
	}
	

	
	@GetMapping("/login")
	public String login(Model model, HttpSession session) {
		if (session.getAttribute("error") != null) {
			model.addAttribute("error", true);
			model.addAttribute("errorMsg",session.getAttribute("errorMsg"));
			session.removeAttribute("error");
			session.removeAttribute("errorMsg");

		}
		return "login";
	}
	
	@GetMapping("/login_error")
	public String loginError(Model model, HttpSession session) {			
		session.setAttribute("error", true);
		session.setAttribute("errorMsg", "Asegurese de rellenar correctamente los datos");
		return "redirect:/login";
	}
	
	@GetMapping("/create_account")
	public String create_account(Model model, HttpSession session) {
		if (session.getAttribute("error") != null) {
			model.addAttribute("error", true);
			model.addAttribute("errorMsg",session.getAttribute("errorMsg"));
			model.addAttribute("errorUsername", session.getAttribute("errorUsername"));
			model.addAttribute("errorEmail", session.getAttribute("errorEmail"));
			session.removeAttribute("error");
			session.removeAttribute("errorMsg");
		}
		return "createAccount";
	}
	
	@PostMapping("/create_account")
	public String signedUp(Model model, HttpSession session, 
			@RequestParam String username, @RequestParam String email, 
			@RequestParam String password ,@RequestParam String reenterPassword) {	
		if (username == ""||email==""||password==""|| reenterPassword=="") {
			session.setAttribute("error", true);
			session.setAttribute("errorUsername", username);
			session.setAttribute("errorEmail", email);
			session.setAttribute("errorMsg", "Rellene todos los campos para continuar");
			
			return "redirect:/create_account";
		}
		
		model.addAttribute("username", username);
		model.addAttribute("email", email);
		model.addAttribute("password", password);
		model.addAttribute("logged", true);
		
		String pass = passwordEncoder.encode(password);
		Users user = new Users(username, email, pass);
		Users check = userRepository.findByUsername(username);
		if (check != null) {
			session.setAttribute("error", true);
			session.setAttribute("errorUsername", username);
			session.setAttribute("errorEmail", email);
			session.setAttribute("errorMsg", "El nombre de usuario ya existe");	
			return "redirect:/create_account";

		}
		check =  userRepository.findByEmail(email);
		if (check != null) {
			session.setAttribute("error", true);
			session.setAttribute("errorUsername", username);
			session.setAttribute("errorEmail", email);
			session.setAttribute("errorMsg", "El correo ya está en uso");	
			return "redirect:/create_account";
		}
		
		if (!password.equals(reenterPassword)) {
			session.setAttribute("error", true);
			session.setAttribute("errorUsername", username);
			session.setAttribute("errorEmail", email);
			session.setAttribute("errorMsg", "Las contraseñas no coinciden");
			return "redirect:/create_account";
		}
		
		userRepository.save(user);
		session.removeAttribute("error");
		session.removeAttribute("errorMsg");
		session.removeAttribute("errorUsername");
		session.removeAttribute("errorEmail");
		return "redirect:/home";			
	}
	
	
	/*
	@GetMapping("/logged_in")
	public String logedIn(Model model, HttpSession session) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUserName = authentication.getName();
		    session.setAttribute("username", currentUserName);
			session.setAttribute("logged", true);
		}
		return "redirect:/home";
	}
	*/
	/*
	@PostMapping("/created_account")
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
	*/
	
	@GetMapping("/logged_out")
	public String viewHomeLoggedOut(Model model, HttpSession session) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication instanceof AnonymousAuthenticationToken) {
			session.setAttribute("username", null);
			session.setAttribute("logged", null);
		}
		return "redirect:/home";
	}
	
	/*
	@PostMapping("/logout")
	public String logOut(Model model, HttpSession session) {	
		session.setAttribute("username", null);
		session.setAttribute("logged", null);
		
		return "redirect:/home";
	}*/
	
}