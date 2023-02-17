package service;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Event;
import model.User;
import repository.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private EventRepository events;
	
	@PostConstruct
	public void init() {
		events.save(new Event("Ultimate Showdown", "Tekken 7", new User ("mikk23", "mikkHd23@gmail.com","pass01")));
		events.save(new Event("Street Eleven", "Street Fighter V", new User ("HiFiMusic", "musicHi@gmail.com","pass02")));
	}
	
	public Collection<Event> findAll() {
		return events.findAll();
	}

	public Optional<Event> findById(long id) {
		return events.findById(id);
	}

	public void save(Event post) {
		events.save(post);
	}

	public void deleteById(long id) {
		this.events.deleteById(id);
	}

}
