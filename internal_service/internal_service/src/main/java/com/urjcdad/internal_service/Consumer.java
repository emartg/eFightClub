package com.urjcdad.internal_service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Consumer {
	@Autowired
	EmailSenderService emailService;
	
	@RabbitListener(queues = "mensajes", ackMode = "AUTO")
	public void recv (String message){
		
		String parts[] = message.split("//");
 		emailService.SendSimpleEmail(parts[0], parts[1], parts[2]);
 		
	}
}