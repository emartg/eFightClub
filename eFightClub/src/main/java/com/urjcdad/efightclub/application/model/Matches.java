package com.urjcdad.efightclub.application.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Matches {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	private Long id;
	
	private Date date;
	private int winner = 0;
	private int [] tempWinner = {0,0};
	
	@ManyToOne
	private Users player1;
	@ManyToOne
	private Users player2;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
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
			return null;
		return player1;		
	}
	public Users getPlayer2() {
		if (player2 == null)			
			return null;
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
			return -1;
		return winner;
	}
	public Users getWinnerUser() {
		if (winner == 0)
			return null;
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
	
	
	public Boolean selectWinner(int winner, Users user) {
		int position = this.getPlayerNumber(user);
		if (position == -1) {
			throw new IllegalArgumentException("This user shouldn't be able to vote on this match");
		}
		if (this.winner == 0) {			
			if (winner < 1 || winner > 2)
				throw new IllegalArgumentException("The winner must be either 1 or 2");
			if (tempWinner[position-1] == 0) {			
				this.tempWinner[position-1] = winner;
			}else {			
				throw new IllegalArgumentException("This player already voted");	
			}
			if (tempWinner[0] !=0 && tempWinner[1] != 0) {
				if(tempWinner[0] == tempWinner[1]) {
					this.setWinner(winner);
					return true;
				}else {
					throw new IllegalArgumentException("Both players voted for different winners");	
				}
			}else {
				return false;
			}
			
		}else{
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
	
	public boolean checkP1() {
		if (player1 == null) {
			return false;
		}else {
			return true;
		}		
	}
	
	public boolean checkP2() {
		if (player2 == null) {
			return false;
		}else {
			return true;
		}		
	}
	
	public boolean checkNoWinner(int pos) {
		if (tempWinner[pos]==0)
			return true;
		return false;
	}
	
	public int checkPlayerPos(Users user) {
		if (user == this.getPlayer1()) {
			return 1;
		}
		if (user == this.getPlayer2()) {
			return 2;
		}
		return -1;
	}
	
}
