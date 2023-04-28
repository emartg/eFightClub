package com.urjcdad.efightclub.application.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Matches {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Date date;
	private int winner = 0;
	private int tempWinner = 0;
	
	@ManyToOne
	private Users player1;
	@ManyToOne
	private Users player2;
	
	@ManyToOne
	private Event event;
	
	/*
	 * Constructors 
	 */
	protected Matches() {}
	
	public Matches(Event event, Date date) {
		this.event = event;
		this.date = date;
	}
	
	public Matches(Event event, Date date, Users player1) {
		this.event = event;
		this.date = date;
		this.player1 = player1;
	}

	public Matches(Event event, Date date, Users player1, Users player2) {
		this.event = event;
		this.date = date;
		this.player1 = player1;
		this.player2 = player2;
	}
	
	public Matches(Event event) {
		this.event = event;
        long millis = System.currentTimeMillis();  
		this.date = new java.sql.Date(millis);
	}
	
	public Matches(Event event, Users player1) {
		this.event = event;
	    long millis = System.currentTimeMillis();  
	    date = new java.sql.Date(millis);
		this.player1 = player1;
	}

	public Matches(Event event, Users player1, Users player2) {
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
	
	public int getPlayerNumber(Users user) {
		if (player1 == user) {
			return 1;
		}else {
			return 2;
		}		
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
	private void setWinner(int winner) {
		if (winner < 1 || winner > 2)
			throw new IllegalArgumentException("The winner must be either 1 or 2");
		this.winner = winner;
	}
	
	
	public void selectWinner(int winner) {
		if (this.winner == 0) {			
			if (winner < 1 || winner > 2)
				throw new IllegalArgumentException("The winner must be either 1 or 2");
			if (tempWinner == 0) {			
				this.tempWinner = winner;
			}else {			
				if (this.tempWinner == winner) {				
					setWinner(winner);
				}else {
					throw new IllegalArgumentException("Both players voted for a diffferent Winner");
				}
			}
		}else {
			throw new IllegalArgumentException("Winner has already been decided");			
		}		
	}
	
	public void forfeit (Users user) {
		if (this.player1==null || this.player2==null) {
			throw new IllegalArgumentException("There are not 2 contestants yet, forfeiting now is not allowed");
		}
		if (this.winner!=0) {
			throw new IllegalArgumentException("Winner has already been decided");				
		}
		//This integer get assigned the value of the other player
		int winnerByDefault = (getPlayerNumber(user)%2)+1;
		setWinner(winnerByDefault);
	}
	
	private int nextMatchAssignment(int currentMatch, int rosterSize) {
		
		return 1;
	}
	
}
