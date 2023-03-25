package com.urjcdad.efightclub.application.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import com.urjcdad.efightclub.application.model.Users;

public interface UsersRepository extends JpaRepository <Users, Long>{
	Users findByUsername(String username);	
	List<Users> findByEmail(String email);	
}