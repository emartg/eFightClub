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
	private Long id;
	
	private String username;
	private String email;
	private String password;
	
	private int wins = 0;
	private int losses = 0;
	private float ratio = 0.0f;
	
	@OneToMany(mappedBy = "winner") 
	private List<Event> eventsWon = new ArrayList<>();
	
	@OneToMany(mappedBy = "creator", cascade = CascadeType.REMOVE)
	private List<Event> eventsCreated = new ArrayList<>();

	/*
	 * Constructors 
	 */
	protected Users() {} 
	
	public Users(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;				
	}
	
	public Users(String username, String email, String password, 
			String passwordCheck) {
		if (!password.equals(passwordCheck))	
			throw new IllegalArgumentException("The passwords do not match");
		this.username = username;
		this.email = email;
		this.password = password;			
	}
	
	/*
	 * Getters
	 */
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
			ratio = (float) (wins / (wins + losses));
		return ratio;		
	}
	
	/*
	 * Setters
	 */
	public void setId(long id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
	