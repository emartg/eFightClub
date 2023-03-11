package com.urjcdad.efightclub.application.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Users {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String username;
	private String email;
	private String password;
	
	private int wins;
	private int losses;
	private float ratio;
	
	@OneToMany(mappedBy = "winner") 
	private List<Event> eventsWon = new ArrayList<>();
	
	@OneToMany(mappedBy = "creator", cascade = CascadeType.REMOVE)
	private List<Event> eventsCreated = new ArrayList<>();

	//Constructors 
	protected Users () {
	} 
	
	public Users (String usernameNew, String emailNew, String passwordNew) {
		this.username = usernameNew;
		this.email = emailNew;
		this.password = passwordNew;		
		wins = 0;
		losses = 0;
		ratio = 0.0f;			
	}
	
	public Users (String usernameNew, String emailNew, String passwordNew, String passwordNewCheck) {
		if (!checkPassword(passwordNew, passwordNewCheck)) {			
			throw new IllegalArgumentException ("Passwords do not match");
		}
		this.username = usernameNew;
		this.email = emailNew;
		this.password = passwordNew;		
		wins = 0;
		losses = 0;
		ratio = 0.0f;			
	}
	
	//Getters
	public Long getId() {
		return id;
	}
	public String getUsername() {
		return username;		
	}
	public String getPassword() {
		return password;		
	}
	public String getEmail() {
		return email;		
	}
	public int getWins() {
		return wins;		
	}
	public int getLosses() {
		return losses;		
	}
	public float getRatio() {
		if (wins + losses != 0)
			ratio = (float) (wins/(wins + losses));
		return ratio;		
	}
	
	//Setters
	public void setId(long id) {
		this.id = id;
	}
	public void setUsername(String newUsername) {
		this.username = newUsername;
	}
	
	public void setPassword(String newPassword) {
		this.password = newPassword;
	}
	
	public void setEmail(String newEmail) {
		this.email = newEmail;
	}
	
	
	//auxiliary function to check whether 2 passwords are equal or not
	public boolean checkPassword(String passwordA, String passwordB) {
		if (passwordA.equals(passwordB)) {
			return true;		
		}		
			return false;		
	}
	
	
}
	