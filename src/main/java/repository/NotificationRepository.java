package repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import model.Event;
import model.Notification;

public interface NotificationRepository extends JpaRepository <Notification, Long>{
	List<Notification> findByEvent (Event event);	
}