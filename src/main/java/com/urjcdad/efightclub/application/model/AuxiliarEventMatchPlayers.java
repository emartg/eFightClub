package com.urjcdad.efightclub.application.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

	public class AuxiliarEventMatchPlayers {
		
		public Matches eventMatch;
		public boolean isP1;
		public boolean isP2;
		public boolean canAct;


		
		
	public AuxiliarEventMatchPlayers (Matches match1, Users user) {
		this.eventMatch = match1;
		if (eventMatch.checkP1()) {
			isP1 = true;
		}else {
			isP1 = false;
		}
		if (eventMatch.checkP2()) {
			isP2 = true;
		}else {
			isP2 = false;
		}
		if (user != null) {
			if (isP1 ==true && isP2 == true && eventMatch.checkNoWinner() && (eventMatch.getPlayer1().equals(user)||eventMatch.getPlayer2().equals(user))) {
				canAct = true;
			}else {
				canAct = false;
			}			
		}else {
			canAct = false;
		}
		
	}

	

	
}
