package com.urjcdad.efightclub.application.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idNotification;
	
	@ManyToOne
	private Event event;
	
	private String title;
	private String text = null;

	//Constructors
	
	protected Notification() {}
	
	public Notification (Event parentEvent, String titleNew) {	
		this.event = parentEvent;
		this.title = titleNew;
	}
	
	public Notification (Event parentEvent, String titleNew, String textNew) {	
		this.event = parentEvent;
		this.title = titleNew;
		this.text = textNew;
	}
	
	//Getters
	public Event getEvent () {		
		return this.event;
	}
	public String getTitle () {
		return this.title;
	}
	public String getText() {
		return this.text;
	}
	
	//Setters
	public void setTitle (String titleNew) {		
		this.title = titleNew;
	}
	public void setText (String textNew) {
		this.text = textNew;
	}
}
