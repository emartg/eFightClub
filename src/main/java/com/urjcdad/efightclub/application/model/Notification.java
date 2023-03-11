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
	private Long id;
	
	@ManyToOne
	private Event event;
	
	private String title;
	private String text = null;

	//Constructors
	protected Notification() {
	}
	
	public Notification(Event parentEvent, String titleNew) {	
		this.event = parentEvent;
		this.title = titleNew;
	}
	
	public Notification(Event parentEvent, String titleNew, String textNew) {	
		this.event = parentEvent;
		this.title = titleNew;
		this.text = textNew;
	}
	
	//Getters
	public Long getId() {
		return id;
	}
	public Event getEvent() {		
		return event;
	}
	public String getTitle() {
		return title;
	}
	public String getText() {
		return text;
	}
	
	//Setters
	public void setId(long id) {
		this.id = id;
	}
	public void setTitle(String titleNew) {		
		this.title = titleNew;
	}
	public void setText(String textNew) {
		this.text = textNew;
	}
}
