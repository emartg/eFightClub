package com.urjcdad.efightclub.application.model;

import java.sql.Blob;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
	private Boolean ongoing;
	private Integer numParticipants = 0;
	private Integer numSlots;
	
	@ManyToOne
	private Users creator;
	@ManyToOne
	private Users winner;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Users> participants = new ArrayList<>();
	@ManyToMany(cascade = CascadeType.PERSIST)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Users> subscribers = new ArrayList<>();
	
	@OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Matches> matches = new ArrayList<>();
	
	@OneToMany(mappedBy = "event", cascade = CascadeType.PERSIST)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Notification> notifications = new ArrayList<>();
	
	private String bannerName = null;
	private String imageName = null;
	@Lob
	@JsonIgnore
	private Blob bannerFile = null;
	@Lob
	@JsonIgnore
	private Blob imageFile = null;

	/*
	 * Constructors
	 */
	protected Event() {}
	
	public Event(String eventName, String game, 
			Date regDate, Date kickoffDate,
			Integer numSlots, Users creator) {
		
		this.eventName = eventName;
		this.game = game;
		
		this.regDate = regDate;
		this.kickoffDate = kickoffDate;
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
		regDateStr = sdf.format(regDate);
		kickoffDateStr = sdf.format(kickoffDate);
		long your_milliseconds = System.currentTimeMillis();
		Date currentDate = new Date(your_milliseconds);
		ongoing = kickoffDate.compareTo(currentDate) < 0;
		
		this.numSlots = numSlots;
		this.creator = creator;
		this.winner = null;
	}
	
	public Event(String eventName, String game, 
			Date regDate, Date kickoffDate,
			Integer numSlots, Users creator,
			String imageName, String bannerName,
			Blob imageFile, Blob bannerFile) {
		
		this.eventName = eventName;
		this.game = game;
		
		this.regDate = regDate;
		this.kickoffDate = kickoffDate;
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd");
		regDateStr = sdf.format(regDate);
		kickoffDateStr = sdf.format(kickoffDate);
		long your_milliseconds = System.currentTimeMillis();
		Date currentDate = new Date(your_milliseconds);
		ongoing = kickoffDate.compareTo(currentDate) < 0;
		
		this.numSlots = numSlots;
		this.creator = creator;
		this.winner = null;
		
		this.imageName = imageName;
		this.bannerName = bannerName;
		this.imageFile = imageFile;
		this.bannerFile = bannerFile;
	}
	
	/*
	 * Getters
	 */
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
	public String getRegDateStr() {
		return regDateStr;		
	}
	public Date getKickoffDate() {
		return kickoffDate;		
	}
	public String getKickoffDateStr() {
		return kickoffDateStr;		
	}
	public Boolean getOngoing() {
		long your_milliseconds = System.currentTimeMillis();
		Date currentDate = new Date(your_milliseconds);
		ongoing = kickoffDate.compareTo(currentDate) < 0;
		return ongoing;
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
		return winner;
	}
	public String getImageName() {
		return imageName;
	}
	public String getBannerName() {
		return bannerName;
	}
	public Blob getImageFile() {
		return imageFile;
	}
	public Blob getBannerFile() {
		return bannerFile;
	}
	public List<Users> getParticipants(){
		return participants;
	}
	public List<Users> getSubscribers(){
		return subscribers;
	}
	public List<Matches> getMatches(){
		return matches;
	}
	public List<Notification> getNotifications(){
		return notifications;
	}
	
	/*
	 * Setters
	 */
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
		long your_milliseconds = System.currentTimeMillis();
		Date currentDate = new Date(your_milliseconds);
		ongoing = kickoffDate.compareTo(currentDate) < 0;
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
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}
	public void setBannerName(String bannerName) {
		this.bannerName = bannerName;
	}
	public void setImageFile(Blob imageFile) {
		this.imageFile = imageFile;
	}
	public void setBannerFile(Blob bannerFile) {
		this.bannerFile = bannerFile;
	}
	
	/*
	 * Methods to add elements to the event
	 */
	public boolean addParticipant(Users user) {
		if (participants.contains(user))
			throw new IllegalArgumentException("This player is already participating in the event");
		else if (numParticipants >= numSlots)
			throw new IllegalArgumentException("This event is full");
		
		participants.add(user);
		numParticipants++;
		if(!this.getSubscribers().contains(user)) {
			addSubscriber(user);
		}		
		if (numParticipants >= numSlots) {			
			assortMatches();
			return true;
		}
		return false;
	}
	public void addSubscriber(Users user) {
		if (!subscribers.contains(user))
			subscribers.add(user);
		else {
			//throw new IllegalArgumentException("This player is already subscribed to the event");			
		}
	}
	
	public void addMatch(Date date) {
		matches.add(new Matches(this, date));		
	}
	public void addMatch(Matches match) {
		matches.add(match);		
	}
	
	public void addMatch(Date date, Users player1) {
		matches.add(new Matches(this, date, player1));	
	}
	public void addMatch(Date date, Users player1, Users player2) {
		matches.add(new Matches(this, date, player1, player2));		
	}
	public void addNotification(String title) {
		notifications.add(new Notification(this, title));
	}
	public void addNotification(Notification notification) {
		notifications.add(notification);
	}
	public void addNotification(String title, String text) {
		notifications.add(new Notification(this, title, text));
	}
	
	public boolean isParticipant(Users user) {
		if (participants.contains(user)) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isSubscriber(Users user) {
		if (subscribers.contains(user)) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isFull() {
		if (numParticipants >= numSlots) {
			return true;
		}else {
			return false;
		}
	}
	
	public void assortMatches() {
		List <Users> temp = new ArrayList<Users> (participants);
		Collections.shuffle(temp);
		
		for (int i = 0; i < (numSlots/2); i++) {
			matches.get(i).setPlayer1(temp.get(2*i));
			matches.get(i).setPlayer2(temp.get(2*i+1));
		}
		/*
		Calendar c = Calendar.getInstance(); 
		c.setTime(this.getKickoffDate());		
		while (bracket >= 2) {
			c.add(Calendar.DATE, 1);
			java.sql.Date matchDate = new Date (c.getTimeInMillis());
			for (int i = 0; i <bracket; i= i++) {
				
				addMatch(matchDate);			
			}
			bracket = bracket/2;
		}*/
	}
	
	public boolean setMatchWinner(int matchId, int winnerId, Users usuario){
		if (matches.get(matchId).selectWinner(winnerId, usuario)) {
			Users matchWinner = matches.get(matchId).getWinnerUser();
			int nextMatchId = nextMatchAssignment(matchId, numSlots);
			if (nextMatchId == -1) {
				this.winner = matchWinner;				
				return true;
			}else {						
				if (matchId % 2 == 0) {
					matches.get(nextMatchId).setPlayer1(matchWinner);
				}else {
					matches.get(nextMatchId).setPlayer2(matchWinner);
				}
				return true;
			}
		}
		return false;
	}
	
	
	private int nextMatchAssignment(int currentMatch, int rosterSize) {
		int initialMatches = rosterSize/2;
		if ((currentMatch+1)>=(rosterSize-1)) {
			return -1; //This indicates that its the final, and needs to be specified
		}else {			
			if (currentMatch%2 != 0) 
				currentMatch--;
			return (currentMatch + (initialMatches-(currentMatch/2)));
		}
	}
	/*
	 * Methods to remove elements from the event
	 */
	public void removeParticipant(Users user) {		
		if (participants.contains(user)) {
			participants.remove(user);
			numParticipants--;
			removeSubscribers(user);
		}
	}
	public void removeSubscribers(Users user) {
		if (subscribers.contains(user))
			subscribers.remove(user);
	}
	
}
