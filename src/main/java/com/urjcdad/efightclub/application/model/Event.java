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
	private Long id;
	
	private String eventName;
	private String game;
	private Date regDate;
	private Date kickoffDate;
	private String regDateStr;
	private String kickoffDateStr;
	private Integer numParticipants;
	private Integer numSlots;
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
			Date kickoffDate, Integer numSlots, Users creator) {		
		this.eventName = eventName;
		this.game = game;
		this.regDate = regDate;
		this.kickoffDate = kickoffDate;
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
		regDateStr = sdf.format(regDate);
		kickoffDateStr = sdf.format(kickoffDate);
		this.numParticipants = 0;
		this.numSlots = numSlots;
		this.creator = creator;
		this.winner = null;
		matches.add(new Match(this, this.kickoffDate));
	}
	
	//Getters
	public Long getId() {
		return id;
	}
	public String getEventName() {
		return eventName;		
	}
	public String getGame() {
		return game;		
	}
	public Date getRegDate() {
		return regDate;		
	}
	public Date getKickoffDate() {
		return kickoffDate;		
	}
	public Integer getNumParticipants() {
		return numParticipants;
	}
	public Integer getNumSlots() {
		return numSlots;
	}
	public Users getCreator() {
		return creator;		
	}
	public Users getWinner() {
		if (winner == null)
			throw new NullPointerException("There's no winner defined yet");
		return winner;
	}
	public List<Users> getParticipants(){
		return participants;
	}
	public List<Users> getSubscribers(){
		return subscribers;
	}
	public List<Match> getMatches(){
		return matches;
	}
	public List<Notification> getNotifications(){
		return notifications;
	}
	
	//Setters
	public void setId(long id) {
		this.id = id;
	}
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
	public void setNumParticipants(Integer numParticipants) {
		this.numParticipants = numParticipants;
	}
	public void setNumSlots(Integer numSlots) {
		this.numSlots = numSlots;
	}
	public void setWinner(Users winner) {
		this.winner = winner;
	}
	
	//Add methods for lists
	public void addParticipant(Users participant) {
		if (participants.contains(participant))
			throw new IllegalArgumentException("This player is already participating in the event");
		else if (numParticipants >= numSlots)
			throw new IllegalArgumentException("This event is full");
		participants.add(participant);
		numParticipants++;
		addSubscriber(participant);
	}
	public void addSubscriber(Users subscriber) {
		if (!subscribers.contains(subscriber))
			subscribers.add(subscriber);
		else
			throw new IllegalArgumentException("This player is already subscribed to the event");
	}
	public void addMatch(Date matchDate) {
		matches.add(new Match(this, matchDate));		
	}
	public void addMatch(Date matchDate, Users p1) {
		matches.add(new Match(this, matchDate, p1));		
	}
	public void addMatch(Date matchDate, Users p1, Users p2) {
		matches.add(new Match(this, matchDate, p1, p2));		
	}
	public void addNotification(String titleNew) {
		notifications.add(new Notification(this, titleNew));
	}
	public void addNotification(String titleNew, String textNew) {
		notifications.add(new Notification(this, titleNew, textNew));
	}
	
	//Remove methods for lists
	public void removeParticipant(Users participantRemove) {		
		if (participants.contains(participantRemove)) {
			participants.remove(participantRemove);
			numParticipants--;
			removeSubscribers(participantRemove);
		}
	}
	public void removeSubscribers(Users subscriberRemove) {
		if (subscribers.contains(subscriberRemove))
			subscribers.remove(subscriberRemove);	
	}
}
