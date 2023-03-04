package com.urjcdad.efightclub.application.controller;

import java.sql.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.urjcdad.efightclub.application.model.Event;
import com.urjcdad.efightclub.application.model.Users;
import com.urjcdad.efightclub.application.repository.UsersRepository;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class HomeController {
	
	@Autowired
	private UsersRepository userRepository;

	@GetMapping("/home")
	public String viewHome(Model model, HttpSession session) {
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
		}
		return "home";
	}
	
	@GetMapping("/login")
	public String logIn() {
		return "login";
	}
	
	@PostMapping("/home")
	public String logIn(Model model, HttpSession session, 
			@RequestParam String username, @RequestParam String email, 
			@RequestParam String password) {
		
		model.addAttribute("username", username);
		model.addAttribute("email", email);
		model.addAttribute("password", password);
		model.addAttribute("logged", true);
		
		Users user = new Users(username, email, password);
		userRepository.save(user);
		session.setAttribute("username", user.getUsername());
		session.setAttribute("logged", true);
		
		return "home";
	}
	
	@GetMapping("/create_event")
	public String createEvent() {
		return "create_event";
	}
	
	/*
	@PostMapping("/home")
	public String createEvent(Model model, HttpSession session, 
			@RequestParam String eventName, @RequestParam String game,
			@RequestParam Date regDate, @RequestParam Date kickoffDate) {
		
		Users user = userRepository.findByUsername(session.getAttribute("username").toString());

		return "home";
	}
	*/
}
