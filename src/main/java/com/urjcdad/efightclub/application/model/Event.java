package com.urjcdad.efightclub.application.model;

import java.sql.Blob;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idEvent;
	
	private String eventName;
	private String game;
	private Date regDate;
	private Date kickoffDate;
	private String regDateStr;
	private String kickoffDateStr;
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
	
	public Event (String eventName, String game, Date regDate, 
			Date kickoffDate, Users creator) {		
		this.eventName = eventName;
		this.game = game;
		this.regDate = regDate;
		this.kickoffDate = kickoffDate;
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
		regDateStr = sdf.format(regDate);
		kickoffDateStr = sdf.format(kickoffDate);
		this.creator = creator;
		this.winner = null;
		matches.add(new Match(this, this.kickoffDate));
	}
	
	//Getters
	public String getEventName() {
		return this.eventName;		
	}
	public String getGame() {
		return this.game;		
	}
	public Date getRegDate() {
		return this.regDate;		
	}
	public Date getKickoffDate() {
		return this.kickoffDate;		
	}
	public Users getCreator() {
		return this.creator;		
	}
	public Users getWinner() {
		if (this.winner==null) {
			throw new NullPointerException("There's no winner defined yet");
		}
		return this.winner;
	}
	public List<Users> getParticipants(){
		return this.participants;
	}
	public List<Users> getSubscribers(){
		return this.subscribers;
	}
	public List<Match> getMatches(){
		return this.matches;
	}
	public List<Notification> getNotifications(){
		return this.notifications;
	}
	
	//Setters
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public void setGame(String game) {
		this.game = game;
	}
	public void setRegDate(Date regDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
		regDateStr = sdf.format(regDate);
		this.regDate = regDate;
	}
	public void setKickoffDate(Date kickoffDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
		kickoffDateStr = sdf.format(kickoffDate);
		this.kickoffDate = kickoffDate;
	}
	public void setWinner(Users winner) {
		this.winner = winner;
	}
	
	//Add methods for lists
	public void addParticipant(Users participantNew) {
		if (this.participants.contains(participantNew)) {
			throw new IllegalArgumentException("This player is already participating in the event");
		}
		this.participants.add(participantNew);
		this.addSubscriber(participantNew);
	}
	
	public void addSubscriber(Users subscriberNew) {
		if (!this.subscribers.contains(subscriberNew)) {
			this.subscribers.add(subscriberNew);
		}else {
			throw new IllegalArgumentException("This player is already subscribed to the event");
		}
	}
	public void addMatch (Date matchDate) {
		this.matches.add(new Match(this, matchDate));		
	}
	public void addMatch (Date matchDate, Users p1) {
		this.matches.add(new Match(this, matchDate, p1));		
	}
	public void addMatch (Date matchDate, Users p1, Users p2) {
		this.matches.add(new Match(this, matchDate, p1, p2));		
	}
	public void addNotification (String titleNew) {
		this.notifications.add(new Notification(this, titleNew));
	}
	public void addNotification (String titleNew, String textNew) {
		this.notifications.add(new Notification(this, titleNew, textNew));
	}
	
	//Remove methods for lists
	public void removeParticipant(Users participantRemove) {		
		if (this.participants.contains(participantRemove)) {
			this.participants.remove(participantRemove);
			this.removeSubscribers(participantRemove);
		}
	}
	
	public void removeSubscribers(Users subscriberRemove) {
		if (this.subscribers.contains(subscriberRemove)) {
			this.subscribers.remove(subscriberRemove);
		}		
	}
	
	//Sorting methods for dates
	public void sort() {
		
	}
}
