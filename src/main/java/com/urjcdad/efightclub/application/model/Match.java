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
	private long idMatch;
	
	private Date date;
	private int winner = 0;
	
	@ManyToOne
	private Users player1;
	@ManyToOne
	private Users player2;
	
	@ManyToOne
	private Event event;
	
	//Constructors 
	
	protected Match () {}
	
	public Match (Event eventNew, Date dateNew) {
		this.event = eventNew;
		this.date = dateNew;
		this.winner = 0;
	}
	
	public Match (Event eventNew, Date dateNew, Users p1) {
		this.event = eventNew;
		this.date = dateNew;
		this.winner = 0;
		this.player1 = p1;
	}

	public Match (Event eventNew, Date dateNew, Users p1, Users p2) {
		this.event = eventNew;
		this.date = dateNew;
		this.winner = 0;
		this.player1 = p1;
		this.player2 = p2;
	}
	
	public Match (Event eventNew) {
		this.event = eventNew;
        long millis=System.currentTimeMillis();  
		this.date = new java.sql.Date(millis);
		this.winner = 0;
	}
	
	public Match (Event eventNew, Users p1) {
		this.event = eventNew;
	    long millis=System.currentTimeMillis();  
	    this.date = new java.sql.Date(millis);
	    this.winner = 0;
		this.player1 = p1;
	}

	public Match (Event eventNew, Users p1, Users p2) {
		this.event = eventNew;
	    long millis=System.currentTimeMillis();  
		this.date = new java.sql.Date(millis);
		this.winner = 0;
		this.player1 = p1;
		this.player2 = p2;
	}
	
	//Getters
	public Event getEvent () {
		return this.event;		
	}
	public Date getDate () {
		return this.date;		
	}
	public Users getPlayer1 () {
		if (this.player1==null) {			
			throw new NullPointerException("No player assigned yet");
		}
		return this.player1;		
	}
	public Users getPlayer2 () {
		if (this.player2==null) {			
			throw new NullPointerException("No player assigned yet");
		}
		return this.player2;		
	}
	
	public int getWinner() {
		if (this.winner ==0) {
			throw new NullPointerException ("No winner assigned yet");
		}
		return this.winner;
	}
	//Alternative Winner Getter returns the User object instead of int value
	public Users getWinnerUser() {
		if (this.winner ==0) {
			throw new NullPointerException ("No winner assigned yet");
		}
		if (this.winner ==1) {
			return this.player1;
		}else {
			return this.player2;		
		}		
	}
	
	
	//Setters 
	public void setDate (Date dateNew) {		
		this.date = dateNew;
	}
	public void setPlayer1 (Users p1) {
		this.player1=p1;		
	}
	public void setPlayer2 (Users p2) {
		this.player2=p2;		
	}
	//Alternative SetPlayers
	public void setPlayerEmpty (Users pNew) {
		if (this.player1!=null) {
			if (this.player2 != null) {
				throw new IllegalArgumentException("There are already 2 players assigned");
			}else {
				setPlayer2(pNew);
			}			
		}else {
			setPlayer1(pNew);
		}
	}
	public void setWinner (int winnerNew) {		
		if (winnerNew < 1 || winnerNew>2) {
			throw new IllegalArgumentException("Winner value must be either 1 or 2");
		}
		this.winner = winnerNew;
	}
	
	
}
