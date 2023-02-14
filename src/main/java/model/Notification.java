package model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

public class Notification {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idNotification;
	
	@ManyToOne
	private Event event;
	
	private String title;
	private String text = null;

	//Constructores
	
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
	public Event GetEvent () {		
		return this.event;
	}
	public String GetTitle () {
		return this.title;
	}
	public String GetText() {
		return this.text;
	}
	
	//Setters
	public void SetTitle (String titleNew) {		
		this.title = titleNew;
	}
	public void SetText (String textNew) {
		this.text = textNew;
	}
}
