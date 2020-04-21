package com.wissen.trading.utils;

import java.io.Serializable;
import java.util.List;

public class Result<T> implements Serializable {

	private static final long serialVersionUID = 7659301163104098050L;
	
	private T data;
	private List<Message> messages;
		
	public T getData() {
		return data;
	}
	
	public void setData(T data) {
		this.data = data;
	}
	
	public List<Message> getMessages() {
		return messages;
	}
	
	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
		
}
