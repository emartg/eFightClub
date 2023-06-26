package com.urjcdad.efightclub.application.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		String encodedPassword = encoder.encode("pass");
		auth.inMemoryAuthentication().withUser("user").password(encodedPassword).roles("USER");
	}
	
	
	
	 @Override
	 protected void configure(HttpSecurity http) throws Exception{
		  
		 
	    http.authorizeHttpRequests().antMatchers("/styles/**","/scripts/**", "/images/**").permitAll();

		 //Public Pages
		 http.authorizeHttpRequests().antMatchers("/").permitAll();
		 http.authorizeHttpRequests().antMatchers("/home").permitAll();
		 http.authorizeHttpRequests().antMatchers("/login").permitAll();
		 http.authorizeHttpRequests().antMatchers("/create_account").permitAll();
		 http.authorizeHttpRequests().antMatchers("/logged_in").permitAll();
		 http.authorizeHttpRequests().antMatchers("/logged_out").permitAll();
		 http.authorizeHttpRequests().antMatchers("/events/**").permitAll();
		 
		 //Private Pages (the rest)
		 http.authorizeHttpRequests().anyRequest().authenticated();
		 
		 //Login Form	 
		 
		 
		
		http.formLogin().loginPage("/login");
		http.formLogin().usernameParameter("username");
		http.formLogin().passwordParameter("password");
		http.formLogin().defaultSuccessUrl("/logged_in");
		http.formLogin().failureUrl("/login");

		//Log-Out Form
		
		http.logout().logoutUrl("/logout");
		http.logout().logoutSuccessUrl("/logged_out");
		
		
		//Disable CSRF (temporal)
		http.csrf().disable();
		
		 
	 }
	
	
	
	
	
}