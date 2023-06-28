package com.urjcdad.efightclub.application.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.urjcdad.efightclub.application.model.Users;
import com.urjcdad.efightclub.application.repository.UsersRepository;

@Controller
public class UserController {
	
	@Autowired
	private UsersRepository userRepository;

	@GetMapping("/my_account")
	public String viewMyAccount(Model model, HttpSession session) {			
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
		}
		
		
		
		
		return "my_account";
	}
	
	@PostMapping("/my_account/modify")
	public String signUp(Model model, HttpSession session, 
			@RequestParam String username, @RequestParam String email, 
			@RequestParam String currentPassword, @RequestParam String newPassword,
			@RequestParam String reenterNewPassword) {
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
		}
		
		
		
		
		
		
		return "redirect:/my_account";
	}
}
