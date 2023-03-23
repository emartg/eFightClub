package com.urjcdad.efightclub.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.urjcdad.efightclub.application.model.Users;

public interface UsersRepository extends JpaRepository <Users, Long>{
	Users findByUsername(String username);	
	Users findByEmail(String email);	
}