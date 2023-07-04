package com.urjcdad.efightclub.application.internalServices;

import java.io.Serializable;

public class Message implements Serializable
{
	String email;
	String subject;
	String text;
	
	public Message(String email,String subject,String text) {
		this.email=email;
		this.subject=subject;
		this.text=text;
	}
}
