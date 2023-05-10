package com.urjcdad.efightclub.application.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

	public class AuxiliarEventMatchPlayers {
		
		public Matches eventMatch;
		public boolean isP1;
		public boolean isP2;
		public boolean canAct;
		public int matchPosition;


		
		
	public AuxiliarEventMatchPlayers (Matches match1, Users user, int index, int totalParticipants) {
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
		switch(totalParticipants) 
		{
			case 4:
				if(index == 0 || index == 1) 
				{
					matchPosition = 2;
				}
				else
				{
					matchPosition = 3;
				}
				break;
			case 8:
				if(index <= 3 ) 
				{
					matchPosition = 1;
				}
				else if(index == 6)
				{
					matchPosition = 3;
				}
				else
				{
					matchPosition = 2;
				}
				break;
			case 16:
				if(index > 7 && index<= 11) 
				{
					matchPosition = 1;
				}
				else if(index > 11 && index <= 13)
				{
					matchPosition = 2;
				}
				else if(index == 14)
				{
					matchPosition = 3;
				}
				else
				{
					matchPosition = 0;
				}
				break;
		}
		
		
	}

	

	
}
