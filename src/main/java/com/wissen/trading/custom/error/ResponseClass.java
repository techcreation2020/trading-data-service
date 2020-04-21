package com.wissen.trading.custom.error;

import java.util.Date;

public class ResponseClass {

	private String message;

	private Date timestamp;

	private String details;

	public ResponseClass() {
	}

	public ResponseClass(Date timestamp, String message, String details) {
		super();
		this.message = message;
		this.timestamp = timestamp;
		this.details = details;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
}
