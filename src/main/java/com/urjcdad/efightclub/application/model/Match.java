package com.urjcdad.efightclub.application.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Date date;
	private int winner = 0;
	
	@ManyToOne
	private Users player1;
	@ManyToOne
	private Users player2;
	
	@ManyToOne
	private Event event;
	
	/*
	 * Constructors 
	 */
	protected Match() {}
	
	public Match(Event event, Date date) {
		this.event = event;
		this.date = date;
	}
	
	public Match(Event event, Date date, Users player1) {
		this.event = event;
		this.date = date;
		this.player1 = player1;
	}

	public Match(Event event, Date date, Users player1, Users player2) {
		this.event = event;
		this.date = date;
		this.player1 = player1;
		this.player2 = player2;
	}
	
	public Match(Event event) {
		this.event = event;
        long millis = System.currentTimeMillis();  
		this.date = new java.sql.Date(millis);
	}
	
	public Match(Event event, Users player1) {
		this.event = event;
	    long millis = System.currentTimeMillis();  
	    date = new java.sql.Date(millis);
		this.player1 = player1;
	}

	public Match(Event event, Users player1, Users player2) {
		this.event = event;
	    long millis = System.currentTimeMillis();  
		date = new java.sql.Date(millis);
		this.player1 = player1;
		this.player2 = player2;
	}
	
	/*
	 * Getters
	 */
	public Long getId() {
		return id;
	}
	public Event getEvent() {
		return event;		
	}
	public Date getDate() {
		return date;		
	}
	public Users getPlayer1() {
		if (player1 == null)		
			throw new NullPointerException("No player assigned yet");
		return player1;		
	}
	public Users getPlayer2() {
		if (player2 == null)			
			throw new NullPointerException("No player assigned yet");
		return player2;		
	}
	public int getWinner() {
		if (winner == 0)
			throw new NullPointerException("No winner assigned yet");
		return winner;
	}
	public Users getWinnerUser() {
		if (winner == 0)
			throw new NullPointerException("No winner assigned yet");
		if (winner == 1)
			return player1;
		else
			return player2;
	}
	
	/*
	 * Setters 
	 */
	public void setId(long id) {
		this.id = id;
	}
	public void setDate(Date date) {		
		this.date = date;
	}
	public void setPlayer1(Users player1) {
		this.player1 = player1;		
	}
	public void setPlayer2(Users player2) {
		this.player2 = player2;		
	}
	public void setAnyPlayer(Users player) {
		if (player1 != null)
			if (player2 != null)
				throw new IllegalArgumentException("There are already two players in the match");
			else
				setPlayer2(player);	
		else
			setPlayer1(player);
		
	}
	public void setWinner(int winner) {		
		if (winner < 1 || winner > 2)
			throw new IllegalArgumentException("The winner must be either 1 or 2");
		this.winner = winner;
	}
	
}
