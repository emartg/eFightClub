package com.urjcdad.efightclub.application.security;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.urjcdad.efightclub.application.service.RepositoryUserDetailsService;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	/*
	 @Value("${security.user}")
	 private String user;
	 
	 @Value("${security.encodedPassword}")
	 private String encodedPassword;
	 */
	
	@Autowired
	public RepositoryUserDetailsService userDetailService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(10, new SecureRandom());
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());
		//auth.inMemoryAuthentication().withUser("admin").password(encoder.encode("adminpass")).roles("USER", "ADMIN");

	}
	
	
	
	 @Override
	 protected void configure(HttpSecurity http) throws Exception{
		  
		 
	    http.authorizeHttpRequests().antMatchers("/styles/**","/scripts/**", "/images/**").permitAll();

		 //Public Pages
		 http.authorizeHttpRequests().antMatchers("/").permitAll();
		 http.authorizeHttpRequests().antMatchers("/home").permitAll();
		 http.authorizeHttpRequests().antMatchers("/login").permitAll();
		 http.authorizeHttpRequests().antMatchers("/login_error").permitAll();
		 http.authorizeHttpRequests().antMatchers("/create_account").permitAll();
		 http.authorizeHttpRequests().antMatchers("/logged_out").permitAll();
		 http.authorizeHttpRequests().antMatchers("/events/**").permitAll();
		 http.authorizeHttpRequests().antMatchers("/cache").permitAll();


		 
		 //Private Pages (the rest)
		 
		 
		 http.authorizeHttpRequests().anyRequest().authenticated();
		 
		 //Login Form	 
		 
		 
		
		http.formLogin().loginPage("/login");
		http.formLogin().usernameParameter("username");
		http.formLogin().passwordParameter("password");
		http.formLogin().defaultSuccessUrl("/home");
		http.formLogin().failureUrl("/login_error");

		//Log-Out Form
		
		http.logout().logoutUrl("/logout");
		http.logout().logoutSuccessUrl("/logged_out");
		
		
		//Disable CSRF (temporal)
		//http.csrf().disable();
		
		 
	 }
	
	
	
	
	
}