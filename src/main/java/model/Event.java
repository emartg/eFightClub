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

}
