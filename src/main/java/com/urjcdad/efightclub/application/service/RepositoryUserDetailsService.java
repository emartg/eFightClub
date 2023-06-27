package com.urjcdad.efightclub.application.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.urjcdad.efightclub.application.model.Event;
import com.urjcdad.efightclub.application.model.Users;
import com.urjcdad.efightclub.application.repository.UsersRepository;

@Service
public class RepositoryUserDetailsService implements UserDetailsService {

	@Autowired
	private UsersRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		Users user = userRepository.findByUsername(username);
		List <GrantedAuthority> rol = new ArrayList<>();
		rol.add(new SimpleGrantedAuthority("ROLE_USER"));
		if (user == null) {
			new UsernameNotFoundException("User not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), rol);		
	}
			
}
