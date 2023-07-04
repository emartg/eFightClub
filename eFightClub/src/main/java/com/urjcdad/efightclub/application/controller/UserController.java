package com.urjcdad.efightclub.application.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import com.urjcdad.efightclub.application.model.Users;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.urjcdad.efightclub.application.internalServices.Producer;
import com.urjcdad.efightclub.application.model.Event;
import com.urjcdad.efightclub.application.model.Notification;
import com.urjcdad.efightclub.application.model.Users;
import com.urjcdad.efightclub.application.repository.EventRepository;
import com.urjcdad.efightclub.application.repository.UsersRepository;

@Controller
public class UserController {
	
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	 private PasswordEncoder passwordEncoder;
	 @Autowired
	    private Producer producer;

	
	@GetMapping("/my_account")
	public String myAccount(Model model, HttpSession session) {
		if (session.getAttribute("error") != null) {
			model.addAttribute("error", true);
			model.addAttribute("errorMsg",session.getAttribute("errorMsg"));
			session.removeAttribute("error");
			session.removeAttribute("errorMsg");
		}
		
		Users currentUser = null;
		List <Notification> notifications = new ArrayList<Notification>();


		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    String currentUsername = authentication.getName();
			currentUser = userRepository.findByUsername(currentUsername);
			model.addAttribute("username", currentUsername);
			model.addAttribute("logged", true);
			currentUser = userRepository.findByUsername(currentUsername);
			model.addAttribute("email", currentUser.getEmail());
			for (Event event: eventRepository.findAll())
				if(event.isSubscriber(currentUser))
				{
					for (Notification notif: event.getNotifications()) {
						notifications.add(notif);						
					}
				}	
		}
		model.addAttribute("notifications", notifications);
		return "my_account";
	}
	
	@PostMapping("/my_account/modify")
	public String signUp(Model model, HttpSession session, 
			@RequestParam String username, @RequestParam String email, 
			@RequestParam String currentPassword, @RequestParam String newPassword,
			@RequestParam String reenterNewPassword) {

		String currentUsername = null;
		Boolean check = false;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (!(authentication instanceof AnonymousAuthenticationToken)) {
		    currentUsername = authentication.getName();
			model.addAttribute("username", currentUsername);
			model.addAttribute("logged", true);
		}else {
			return "redirect:/home";
		}
		
		Users currentUser = userRepository.findByUsername(currentUsername);
		if (currentPassword != null) {
			if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
				String ErrorMsg = "La contraseña original no coincide";
				session.setAttribute("errorMsg", ErrorMsg);
				session.setAttribute("error", true);
				return "redirect:/my_account";
			}else {
				check = true;
			}
		}else {
			String ErrorMsg = "Es obligatorio escribir la contraseña anterior para modificar datos";
			session.setAttribute("errorMsg", ErrorMsg);
			session.setAttribute("error", true);
			return "redirect:/my_account";
		}
		
		String newUsername = "";
		String newPass = "";
		String newEmail = "";

		
		if (username != "") {
			Users temp = userRepository.findByUsername(username);
			if (temp != null) {
				String ErrorMsg = "El nombre escogido ya está en uso";
				session.setAttribute("errorMsg", ErrorMsg);
				session.setAttribute("error", true);				
				return "redirect:/my_account";
			}else {
				newUsername = username;
			}			
		}
		
		if (newPassword != "") {
			if (reenterNewPassword != "" && reenterNewPassword.equals(newPassword)) {
				newPass = passwordEncoder.encode(newPassword);
			}else {
				String ErrorMsg = "Las nuevas contraseñas no coinciden";
				session.setAttribute("errorMsg", ErrorMsg);
				session.setAttribute("error", true);				
				return "redirect:/my_account";
			}				
		}
		
		if (email != "") {
			Users temp = userRepository.findByEmail(email);
			if (temp != null) {
				String ErrorMsg = "El email escogido ya está en uso";
				session.setAttribute("errorMsg", ErrorMsg);
				session.setAttribute("error", true);				
				return "redirect:/my_account";
			}else {
				newEmail = email;
			}			
		}
		
		if (newEmail != "" && check) {
			currentUser.setEmail(newEmail);
			userRepository.save(currentUser);
		};
		
		if (check) {
			if (newUsername != "") {
				currentUser.setUsername(newUsername);				
			}else {
				newUsername = currentUser.getUsername();
			}
			if (newPass != "") {
				currentUser.setPassword(newPass);
			}else {
				newPass = currentUser.getPassword();
			}
			Collection<SimpleGrantedAuthority> nowAuthorities = 
					  (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext()
					                                                           .getAuthentication()
					                                                           .getAuthorities();
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(newUsername, newPass, nowAuthorities);

					SecurityContextHolder.getContext().setAuthentication(auth);
			userRepository.save(currentUser);
			
			
		}
		producer.SendModifiedChanges(currentUser.getEmail(),"There has been new changes in your account information!");
		
	
		
		return "redirect:/home";
	}
	
	
}
