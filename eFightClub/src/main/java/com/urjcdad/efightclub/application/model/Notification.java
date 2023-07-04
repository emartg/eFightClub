package com.urjcdad.efightclub.application.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JsonIgnore
	private Event event;
	
	private String EventName;
	private String title;
	private String text = null;

	/*
	 * Constructors
	 */
	protected Notification() {}
	
	public Notification(Event event, String title) {	
		this.event = event;
		this.title = title;
		this.EventName = event.getEventName();
	}
	
	public Notification(Event event, String title, String text) {	
		this.event = event;
		this.title = title;
		this.text = text;
		this.EventName = event.getEventName();
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
	public String getEventName() {
		return EventName;
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
