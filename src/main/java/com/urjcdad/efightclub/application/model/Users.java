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
	private long idUser;
	
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
	protected Users () {} 
	
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
	public String getUsername() {
		return this.username;		
	}
	public String getPassword() {
		return this.password;		
	}
	public String getEmail() {
		return this.email;		
	}
	public int getWins() {
		return this.wins;		
	}
	public int getLosses() {
		return this.losses;		
	}
	public float getRatio() {
		if (this.wins + this.losses != 0) {
			this.ratio=(float) (this.wins/(this.wins+ this.losses));
		}		
		return this.ratio;		
	}
	
	//Setters
	public void setUsername (String newUsername) {
		this.username = newUsername;
	}
	
	public void setPassword (String newPassword) {
		this.password = newPassword;
	}
	
	public void setEmail (String newEmail) {
		this.email = newEmail				;
	}
	
	
	//auxiliary function to check whether 2 passwords are equal or not
	public boolean checkPassword (String passwordA, String passwordB) {
		if (passwordA.equals(passwordB)) {
			return true;		
		}		
			return false;		
	}
	
	
}
	