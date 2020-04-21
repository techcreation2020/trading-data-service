package com.wissen.trading.utils;

import java.io.Serializable;

public class Message implements Serializable {
	
	private static final long serialVersionUID = 6109885660039084907L;
	
	private String message;
	private MessageType messaageType;
	
	public Message(){
		
	}
	
	public Message(final String message, final MessageType messageType){
		this.message = message;
		this.messaageType = messageType;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MessageType getMessaageType() {
		return messaageType;
	}
	public void setMessaageType(MessageType messaageType) {
		this.messaageType = messaageType;
	}
	
}
