package com.urjcdad.efightclub.application.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.urjcdad.efightclub.application.model.AuxiliarEventUsers;
import com.urjcdad.efightclub.application.model.Event;
import com.urjcdad.efightclub.application.model.Matches;
import com.urjcdad.efightclub.application.model.Notification;
import com.urjcdad.efightclub.application.model.Users;
import com.urjcdad.efightclub.application.repository.EventRepository;
import com.urjcdad.efightclub.application.repository.MatchesRepository;
import com.urjcdad.efightclub.application.repository.NotificationRepository;
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
	private MatchesRepository matchesRepository;
	
	@Autowired
	private NotificationRepository notificationRepository;
	
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
			@RequestParam String numSlots) {
		
		// Ensure the user has entered all the required parameters to
		// create an event. Otherwise, reload the page
		if (eventName == "" || game == "" || regDate == null || 
				kickoffDate == null || numSlots == "")
			return "redirect:/events/create";
		
		// Ensure that the new event registration due date is a 
		// date in the future. Otherwise, reload the page
		/*
		long your_milliseconds = System.currentTimeMillis();
		Date currentDate = new Date(your_milliseconds);	
		if (regDate.compareTo(currentDate) < 0)
			return "redirect:/events/create";
		*/
		
		// Ensure that the new event registration due date is 
		// an earlier date that the kick-off date. 
		// Otherwise, reload the page
		if (kickoffDate.compareTo(regDate) < 0)
			return "redirect:/events/create";
		
		// Get the current user and create an event under its name
		Users user = userRepository.findByUsername(session.getAttribute("username").toString());
		Integer slots = Integer.parseInt(numSlots); // parse the number of participants to an integer
		Event event = new Event(eventName, game, regDate, kickoffDate, slots, user);
		eventRepository.save(event);
		slots = slots/2;
		Calendar c = Calendar.getInstance(); 
		c.setTime(kickoffDate);				
		while (slots >= 2) {
			Date matchDate = new Date (c.getTimeInMillis());
			for (int i =0; i < slots; i++) {				
				Matches match = new Matches (event, matchDate);
				matchesRepository.save(match);
				event.addMatch(match);
			}		
			c.add(Calendar.DATE, 1);
			slots = (slots/2);
		}
		Date matchDate = new Date (c.getTimeInMillis());
		Matches match = new Matches (event, matchDate);
		matchesRepository.save(match);
		event.addMatch(match);
		eventRepository.save(event);
		
		return "redirect:/events/my_events";
	}
	
	@GetMapping("/events/{id}")
	public String showEvent(Model model, HttpSession session, 
			@PathVariable long id) {
		Users currentUser = null;
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
			String currentUsername = session.getAttribute("username").toString();
			currentUser = userRepository.findByUsername(currentUsername);
		}
		
		// Get the existing event from the repository
		Event event = eventRepository.findById(id).get();		
		AuxiliarEventUsers eventUser = new AuxiliarEventUsers(event, currentUser);
		model.addAttribute("eventUser", eventUser);
		
	
		return "show_event";
	}
	
	
	@PostMapping("/events/{id}")
	public String showEvent(Model model, HttpSession session, 
			@PathVariable long id, @RequestParam int Winner) {
		Users currentUser = null;
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
			String currentUsername = session.getAttribute("username").toString();
			currentUser = userRepository.findByUsername(currentUsername);
		}
		
		// Get the existing event from the repository
		Event event = eventRepository.findById(id).get();		
		AuxiliarEventUsers eventUser = new AuxiliarEventUsers(event, currentUser);
		int matchId = eventUser.getMatchActId();
		if (event.setMatchWinner(matchId, Winner, currentUser)) {
			String title = new String(event.getEventName()+" ; match nº"+matchId+" results");
			String body = new String("player "+event.getMatches().get(matchId).getWinnerUser().getUsername()+ " won match nº"+matchId);			
			Notification notif = new Notification(event, title, body);
			notificationRepository.save(notif);
			event.addNotification(notif);
			if (event.getWinner()!=null) {				
				 title = new String(event.getEventName()+" ended");
				 body = new String("player "+event.getWinner().getUsername()+ " won the tournament!");
				Notification notif2 = new Notification(event, title, body);
				notificationRepository.save(notif2);	
				event.addNotification(notif2);
			}
		}
		eventRepository.save(event);
		return "redirect:/events/{id}";
	}
	
	
	@GetMapping("/events/{id}/edit")
	public String editEvent(Model model, HttpSession session,
			@PathVariable long id) {
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
		}
		
		// Get the existing event from the repository
		Event event = eventRepository.findById(id).get();
		model.addAttribute("event", event);
		
		return "edit_event";
	}
	
	@PostMapping("/events/{id}/modify_ongoing")
	public String editEvent(Model model, HttpSession session, 
			@RequestParam String eventName, @RequestParam String game,
			@PathVariable long id) {
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
		}
		
		// Get the existing event from the repository
		Event event = eventRepository.findById(id).get();
		
		// Update the different event fields
		if (eventName != "")
			event.setEventName(eventName);
		if (game != "")
			event.setGame(game);
		
		// Update the database
		eventRepository.save(event);
	
		return "redirect:/events/my_events";
	}
	
	@PostMapping("/events/{id}/modify_upcoming")
	public String editEvent(Model model, HttpSession session, 
			@RequestParam String eventName, @RequestParam String game,
			@RequestParam Date regDate, @RequestParam Date kickoffDate, @PathVariable long id) {
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
		}
		
		// Ensure that the event registration due date is a 
		// date in the future. Otherwise, reload the page
		/*
		long your_milliseconds = System.currentTimeMillis();
		Date currentDate = new Date(your_milliseconds);	
		if (regDate.compareTo(currentDate) < 0)
			return "redirect:/events/{id}/edit";
		*/
		
		// Ensure that the event registration due date is 
		// an earlier date that the kick-off date. 
		// Otherwise, reload the page
		if (kickoffDate.compareTo(regDate) < 0)
			return "redirect:/events/{id}/edit";
		
		// Get the existing event from the repository
		Event event = eventRepository.findById(id).get();
		
		// Update the different event fields
		if (eventName != "")
			event.setEventName(eventName);
		if (game != "")
			event.setGame(game);
		if (regDate != null)
			event.setRegDate(regDate);
		if (kickoffDate != null)
			event.setKickoffDate(kickoffDate);
			for (int i =0; i < event.getMatches().size(); i++) {				
				int slots = event.getNumSlots()/2;
				Calendar c = Calendar.getInstance(); 
				c.setTime(kickoffDate);				
				int k = 0;
				while (slots >= 2) {
					Date matchDate = new Date (c.getTimeInMillis());
					for (int j =0; j < slots; j++) {				
						event.getMatches().get(k).setDate(matchDate);
						k ++;
					}		
					c.add(Calendar.DATE, 1);
					slots = (slots/2);
				}
				Date matchDate = new Date (c.getTimeInMillis());
				event.getMatches().get(k).setDate(matchDate);
			}		
		// Update the database
		eventRepository.save(event);
	
		return "redirect:/events/my_events";
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
				if (event.addParticipant(user)) {
					String title = new String(event.getEventName()+" is full!");
					String body = new String("event "+event.getEventName() +" has filled out, get ready!");			
					Notification notif = new Notification(event, title, body);
					notificationRepository.save(notif);
					event.addNotification(notif);

				}	
				eventRepository.save(event);
			}
		}
	
		return "redirect:/home";
	}
	
	@GetMapping("/{id}/unregister")
	public String uncompeteEvent(Model model, HttpSession session, 
			@PathVariable long id) {
		if (session.getAttribute("logged") != null) {
			model.addAttribute("username", session.getAttribute("username"));
			model.addAttribute("logged", true);
			
			long your_milliseconds = System.currentTimeMillis();
			Date currentDate = new Date(your_milliseconds);
			Users user = userRepository.findByUsername(session.getAttribute("username").toString());
			Event event = eventRepository.findById(id).get();
			
			// Only remove user to the event if the registration
			// has not closed yet
			if (event.getRegDate().compareTo(currentDate) > 0) {
				event.removeParticipant(user);	
				eventRepository.save(event);
			}
		}
	
		return "redirect:/home";
	}
	
	

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
		model.addAttribute("upcomingEvents", 
				upcomingEvents);
		
		return "my_events";
	}
	@GetMapping("/events/{id}/matches")
	public String matchFinished(Model model, HttpSession session, 
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
			
			
		}
	
		return "redirect:/events/{id}";
	}
	
	
}
