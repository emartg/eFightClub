package com.urjcdad.efightclub.application.internalServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Component
public class Producer {

	@Autowired
    private RabbitTemplate rabbitTemplate;
	
	public void SendNotification(String userEmail, String notification) 
	{
		String email = userEmail;
		String subject = "New Notification";
		String text = notification;
		String fullMessage = email + "//" + subject + "//" + text;
		
		rabbitTemplate.convertAndSend("messages", fullMessage);
	}
	public void SendNewUser(String userEmail, String welcomeText) 
	{
		String email = userEmail;
		String subject = "Welcome to eFightClub!";
		String text = welcomeText;
		String fullMessage = email + "//" + subject + "//" + text;
		
		rabbitTemplate.convertAndSend("messages", fullMessage);
	}
	public void SendNewEvent(String userEmail, String createEventNotification) 
	{
		String email = userEmail;
		String subject = "New Event Created!";
		String text = createEventNotification;
		String fullMessage = email + "//" + subject + "//" + text;
		
		rabbitTemplate.convertAndSend("messages", fullMessage);
	}
	
}
