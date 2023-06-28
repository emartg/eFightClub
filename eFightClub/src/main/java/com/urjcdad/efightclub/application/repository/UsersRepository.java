package com.urjcdad.efightclub.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.urjcdad.efightclub.application.model.Users;

public interface UsersRepository extends JpaRepository <Users, Long> {
	
	Optional<Users> findById(Long id);
	Users findByUsername(String username);	
	Users findByEmail(String email);	
}