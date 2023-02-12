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

}
