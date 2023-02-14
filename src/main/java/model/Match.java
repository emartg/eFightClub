package model;

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
	private User player1;
	@ManyToOne
	private User player2;
	
	@ManyToOne
	private Event event;
	
	//Constructors 
	
	protected Match () {}
	
	public Match (Event eventNew, Date dateNew) {
		this.event = eventNew;
		this.date = dateNew;
		this.winner = 0;
	}
	
	public Match (Event eventNew, Date dateNew, User p1) {
		this.event = eventNew;
		this.date = dateNew;
		this.winner = 0;
		this.player1 = p1;
	}

	public Match (Event eventNew, Date dateNew, User p1, User p2) {
		this.event = eventNew;
		this.date = dateNew;
		this.winner = 0;
		this.player1 = p1;
		this.player2 = p2;
	}
	
	//Getters
	public Event GetEvent () {
		return this.event;		
	}
	public Date GetDate () {
		return this.date;		
	}
	public User GetPlayer1 () {
		if (this.player1==null) {			
			throw new NullPointerException("No se ha asignado un jugador todavía");
		}
		return this.player1;		
	}
	public User GetPlayer2 () {
		if (this.player2==null) {			
			throw new NullPointerException("No se ha asignado un jugador todavía");
		}
		return this.player2;		
	}
	
	//Alternative Winner Getter returns the User object of the winner; not the int value of the variable
	public User GetWinner() {
		if (this.winner ==0) {
			throw new NullPointerException ("No se ha asignado el ganador todavía");
		}
		if (this.winner ==1) {
			return this.player1;
		}else {
			return this.player2;		
		}		
	}
	
	
	//Setters 
	public void SetDate (Date dateNew) {		
		this.date = dateNew;
	}
	public void SetPlayer1 (User p1) {
		this.player1=p1;		
	}
	public void SetPlayer2 (User p2) {
		this.player2=p2;		
	}
	//Alternative SetPlayers
	public void SetPlayerEmpty (User pNew) {
		if (this.player1!=null) {
			if (this.player2 != null) {
				throw new IllegalArgumentException("Ya hay 2 jugadores asignados");
			}else {
				SetPlayer2(pNew);
			}			
		}else {
			SetPlayer1(pNew);
		}
	}
	public void SetWinner (int winnerNew) {		
		if (winnerNew < 1 || winnerNew>2) {
			throw new IllegalArgumentException("El valor de winner solo puede ser 1 o 2");
		}
		this.winner = winnerNew;
	}
	
	
}
