package com.urjcdad.efightclub.application.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

	public class AuxiliarEventUsers {
		private Event event;
		private Boolean isParticipant;
		private Boolean isSubscriber; 	
		private Boolean isFull;
		public List <AuxiliarEventMatchPlayers> eventMatches = new ArrayList<AuxiliarEventMatchPlayers>();;
		
		
	public AuxiliarEventUsers (Event event1, Users user) {
		this.event = event1;	
		userChecker(user);
		for (int i = 0; i <event1.getMatches().size(); i++) {
			Matches tempMatch = event1.getMatches().get(i);
			eventMatches.add(new AuxiliarEventMatchPlayers(tempMatch, user));			
		}
	}

	public void userChecker (Users user) {
			if (user != null) {
				isParticipant = event.isParticipant(user);
				isSubscriber =event.isSubscriber(user);
				isFull =event.isFull();
			} else {
				isParticipant = false;
				isSubscriber = false;
				isFull =event.isFull();
			}
		
			
	}

	
}
