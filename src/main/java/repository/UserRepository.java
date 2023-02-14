package repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import model.User;

public interface UserRepository extends JpaRepository <User, Long>{
	List<User> findByUsername (String username);	
	List<User> findByEmail (String email);	
}