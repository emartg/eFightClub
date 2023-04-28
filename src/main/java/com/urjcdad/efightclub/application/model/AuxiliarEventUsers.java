package com.urjcdad.efightclub.application.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

	public class AuxiliarEventUsers {
		private Event event;
		private Boolean isParticipant;
		private Boolean isSubscriber;
		private Boolean isFull;

	public AuxiliarEventUsers (Event event1, Users user) {
		this.event = event1;	
		userChecker(user);
	}

	public void userChecker (Users user) {
			isParticipant = event.isParticipant(user);
			isSubscriber =event.isSubscriber(user);
			isFull =event.isFull();
			
	}

	
}
