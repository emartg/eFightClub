package com.urjcdad.efightclub.application.repository;

import java.util.Optional;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.urjcdad.efightclub.application.model.Event;
import com.urjcdad.efightclub.application.model.Users;

//@CacheConfig(cacheNames="usuarios")
public interface UsersRepository extends JpaRepository <Users, Long> {
	
	//@Cacheable
	Optional<Users> findById(Long id);
	
	//@Cacheable
	Users findByUsername(String username);	
	
	//@Cacheable
	Users findByEmail(String email);	
	
	//@CacheEvict(allEntries=true)
	//Users save(Users user);
}