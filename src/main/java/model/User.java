package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idUser;
	
	private String username;
	private String email;
	private String password;
	
	private int wins;
	private int losses;
	private float ratio;
	
	@OneToMany(mappedBy = "winner")
	private List<Event> eventsWon = null;
	
	@OneToMany(mappedBy = "creator", cascade = CascadeType.REMOVE)
	private List<Event> eventsCreated = null;
	
}
	