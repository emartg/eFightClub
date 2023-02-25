package com.urjcdad.efightclub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import model.User;
import repository.UserRepository;

import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;

	@GetMapping("/")
	public String home(Model model) {
		return "home";
	}
	
	@GetMapping("/log")
	public String log(Model model) {
		
		return "log";
	}
	
	@PostMapping("/login")
	public String login(Model model, 
			@RequestParam String username, @RequestParam String email, @RequestParam String password) {
		model.addAttribute("username", username);
		model.addAttribute("email", email);
		model.addAttribute("password", password);
		model.addAttribute("logged", true);
		
		User n = new User(username, email, password);
		userRepository.save(n);
		
		return "home";
	}
}
