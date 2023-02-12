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

}
