package model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idUser;
	
	private String username;
	private String email;
	private String password;
	
	private int wins;
	private int losses;
	private float ratio;
	
	@OneToMany(mappedBy = "winner")
	private List<Event> eventsWon = null;
	
	@OneToMany(mappedBy = "creator", cascade = CascadeType.REMOVE)
	private List<Event> eventsCreated = null;

	//Constructores 
	protected User () {} 
	
	public User (String usernameNew, String emailNew, String passwordNew, String passwordNewCheck) {
		if (!CheckPassword(passwordNew, passwordNewCheck)) {			
			throw new IllegalArgumentException ("Las contraseñas no son iguales");
		}
		this.username = usernameNew;
		this.email = emailNew;
		this.password = passwordNew;
		
		wins = 0;
		losses = 0;
		ratio = 0.0f;			
	}
	
	//Getters
	public String GetUsername() {
		return this.username;		
	}
	public String GetPassword() {
		return this.password;		
	}
	public String GetEmail() {
		return this.email;		
	}
	public int GetWins() {
		return this.wins;		
	}
	public int GetLosses() {
		return this.losses;		
	}
	public float GetRatio() {
		if (this.wins + this.losses != 0) {
			this.ratio=(float) (this.wins/(this.wins+ this.losses));
		}		
		return this.ratio;		
	}
	
	//Setters
	public void SetUsername (String newUsername) {
		this.username = newUsername;
	}
	
	public void SetPassword (String newPassword) {
		this.password = newPassword;
	}
	
	public void SetEmail (String newEmail) {
		this.email = newEmail				;
	}
	
	
	//en caso de hacer lo de introducir contraseña 2 veces
	public boolean CheckPassword (String passwordA, String passwordB) {
		if (passwordA.equals(passwordB)) {
			return true;		
		}		
			return false;		
	}
	
	
}
	