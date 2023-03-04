package com.urjcdad.efightclub.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.urjcdad.efightclub.application.model.Users;
import com.urjcdad.efightclub.application.repository.UsersRepository;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
	@Autowired
	private UsersRepository userRepository;

	@GetMapping("/")
	public String home(Model model) {
		return "home";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		
		return "login";
	}
	
	@GetMapping("/events")
	public String events(Model model) {
		
		return "events";
	}
	
	@PostMapping("/home")
	public String home(Model model, 
			@RequestParam String username, @RequestParam String email, @RequestParam String password) {
		model.addAttribute("username", username);
		model.addAttribute("email", email);
		model.addAttribute("password", password);
		model.addAttribute("logged", true);		
		Users n = new Users(username, email, password);
		//n.SetUsername(username);
		//n.SetEmail(email);
		//n.SetPassword(password);
		userRepository.save(n);
		
		return "home";
	}
}
