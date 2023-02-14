package model;

import java.sql.Blob;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Event {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idEvent;
	
	private String name;
	private String game;
	private Date startingDate;
	private String banner = null;
	private String icon = null;
	
	@ManyToOne
	private User creator;
	@ManyToOne
	private User winner;
	
	@OneToMany
	private List<User>participants;
	@OneToMany
	private List<User>subscribers;
	
	@OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
	private List<Match> matches;
	
	@OneToMany(mappedBy = "event", cascade = CascadeType.REMOVE)
	private List<Notification> notifications;
	
	@Lob
	@JsonIgnore
	private Blob bannerFile = null;
	@Lob
	@JsonIgnore
	private Blob iconFile = null;

	//Constructors
	protected Event () {		
	}
	
	public Event (String nameNew, String gameNew, Date startingDateNew, User creatorNew) {		
		this.name = nameNew;
		this.game = gameNew;
		this.startingDate = startingDateNew;
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
	public User GetCreator() {
		return this.creator;		
	}
	public User GetWinner() {
		if (this.winner==null) {
			throw new NullPointerException("No hay ganador definido");
		}
		return this.winner;
	}
	public List<User> GetParticipants(){
		return this.participants;
	}
	public List<User> GetSubscribers(){
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
	public void SetWinner (User winnerNew) {
		this.winner= winnerNew;
	}
	
	//Add methods for lists
	public void AddParticipant(User participantNew) {
		if (this.participants.contains(participantNew)) {
			throw new IllegalArgumentException("Jugador ya se encuentra en el evento");
		}
		this.participants.add(participantNew);
		this.AddSubscriber(participantNew);
	}
	
	public void AddSubscriber(User subscriberNew) {
		if (!this.subscribers.contains(subscriberNew)) {
			this.subscribers.add(subscriberNew);
		}else {
			throw new IllegalArgumentException("Jugador ya se encuentra suscrito al evento");
		}
	}
	public void AddMatch (Date matchDate) {
		this.matches.add(new Match(this,matchDate));		
	}
	public void AddMatch (Date matchDate, User p1) {
		this.matches.add(new Match(this,matchDate, p1));		
	}
	public void AddMatch (Date matchDate, User p1, User p2) {
		this.matches.add(new Match(this,matchDate, p1, p2));		
	}
	public void AddNotification (String titleNew) {
		this.notifications.add(new Notification(this, titleNew));
	}
	public void AddNotification (String titleNew, String textNew) {
		this.notifications.add(new Notification(this, titleNew, textNew));
	}
	
	//Remove methods for lists
	public void RemoveParticipant(User participantRemove) {		
		if (this.participants.contains(participantRemove)) {
			this.participants.remove(participantRemove);
			this.RemoveSubscribers(participantRemove);
		}
	}
	
	public void RemoveSubscribers(User subscriberRemove) {
		if (this.subscribers.contains(subscriberRemove)) {
			this.subscribers.remove(subscriberRemove);
		}		
	}
	
	
}
