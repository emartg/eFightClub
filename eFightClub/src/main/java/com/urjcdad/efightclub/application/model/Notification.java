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

	/*
	 * Constructors
	 */
	protected Notification() {}
	
	public Notification(Event event, String title) {	
		this.event = event;
		this.title = title;
	}
	
	public Notification(Event event, String title, String text) {	
		this.event = event;
		this.title = title;
		this.text = text;
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
	public String getTitle() {
		return title;
	}
	public String getText() {
		return text;
	}
	
	/*
	 * Setters
	 */
	public void setId(long id) {
		this.id = id;
	}
	public void setTitle(String title) {		
		this.title = title;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
