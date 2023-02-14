package model;

import java.sql.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository <Match, Long>{
	List<Match> findByEvent (Event event);	
	List<Match> findByDate (Date date);
}