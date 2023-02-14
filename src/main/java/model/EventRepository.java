package model;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository <Event, Long>{
	List<Event> findByCreator (User creator);	
	List<Event> findByWinner (User winner);	
	List<Event> findByStartingDate (Date startingDate);
	List<Event> findByName (String name);	
	List<Event> findByGame (String game);
}