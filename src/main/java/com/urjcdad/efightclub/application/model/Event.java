package com.urjcdad.efightclub.application.model;

import java.sql.Blob;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idEvent;
	
	private String name;
	private String game;
	private Date startingDate;
	private String banner = null;
	private String icon = null;
	
	@ManyToOne
	private Users creator;
	@ManyToOne
	private Users winner;
	
	@OneToMany
	private List<Users>participants = new ArrayList<>();
	@OneToMany
	private List<Users>subscribers = new ArrayList<>();
	
	@OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
	private List<Match> matches = new ArrayList<>();
	
	@OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
	private List<Notification> notifications = new ArrayList<>();
	
	@Lob
	@JsonIgnore
	private Blob bannerFile = null;
	@Lob
	@JsonIgnore
	private Blob iconFile = null;

	//Constructors
	protected Event () {		
	}
	
	public Event (String nameNew, String gameNew, Date startingDateNew, Users creatorNew) {		
		this.name = nameNew;
		this.game = gameNew;
		this.startingDate = startingDateNew;
		this.creator = creatorNew;
		this.winner=null;
		matches.add(new Match(this, this.startingDate));
	}
	
	public Event (String nameNew, String gameNew, Users creatorNew) {		
		this.name = nameNew;
		this.game = gameNew;
	    long millis=System.currentTimeMillis();  
		this.startingDate = new java.sql.Date(millis);
		this.creator = creatorNew;
		this.winner=null;
		matches.add(new Match(this, this.startingDate));
	}
	
	//Getters
	public String GetName() {
		return this.name;		
	}
	public String GetGame() {
		return this.game;		
	}
	public Date GetStartingDate() {
		return this.startingDate;		
	}
	public Users GetCreator() {
		return this.creator;		
	}
	public Users GetWinner() {
		if (this.winner==null) {
			throw new NullPointerException("There's no winner defined yet");
		}
		return this.winner;
	}
	public List<Users> GetParticipants(){
		return this.participants;
	}
	public List<Users> GetSubscribers(){
		return this.subscribers;
	}
	public List<Match> GetMatches(){
		return this.matches;
	}
	public List<Notification> GetNotifications(){
		return this.notifications;
	}
	
	//Setters
	public void SetName (String nameNew) {
		this.name= nameNew;
	}
	public void SetGame (String gameNew) {
		this.game= gameNew;
	}
	public void SetStartingDate (Date startingDateNew) {
		this.startingDate= startingDateNew;
	}
	public void SetWinner (Users winnerNew) {
		this.winner= winnerNew;
	}
	
	//Add methods for lists
	public void AddParticipant(Users participantNew) {
		if (this.participants.contains(participantNew)) {
			throw new IllegalArgumentException("This player is already participating in the event");
		}
		this.participants.add(participantNew);
		this.AddSubscriber(participantNew);
	}
	
	public void AddSubscriber(Users subscriberNew) {
		if (!this.subscribers.contains(subscriberNew)) {
			this.subscribers.add(subscriberNew);
		}else {
			throw new IllegalArgumentException("This player is already subscribed to the event");
		}
	}
	public void AddMatch (Date matchDate) {
		this.matches.add(new Match(this,matchDate));		
	}
	public void AddMatch (Date matchDate, Users p1) {
		this.matches.add(new Match(this,matchDate, p1));		
	}
	public void AddMatch (Date matchDate, Users p1, Users p2) {
		this.matches.add(new Match(this,matchDate, p1, p2));		
	}
	public void AddNotification (String titleNew) {
		this.notifications.add(new Notification(this, titleNew));
	}
	public void AddNotification (String titleNew, String textNew) {
		this.notifications.add(new Notification(this, titleNew, textNew));
	}
	
	//Remove methods for lists
	public void RemoveParticipant(Users participantRemove) {		
		if (this.participants.contains(participantRemove)) {
			this.participants.remove(participantRemove);
			this.RemoveSubscribers(participantRemove);
		}
	}
	
	public void RemoveSubscribers(Users subscriberRemove) {
		if (this.subscribers.contains(subscriberRemove)) {
			this.subscribers.remove(subscriberRemove);
		}		
	}
	
	
}
