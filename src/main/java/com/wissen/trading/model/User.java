package com.wissen.trading.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="user_name")
	private String userName;
	
	@OneToOne
	@JoinColumn(name="fund_id")
	private Fund fund;
	
	@Column(name="balance")
	private Integer balance;
	
	@Column(name="updated_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updaetdOn;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		this.balance = balance;
	}

	public Date getUpdaetdOn() {
		return updaetdOn;
	}

	public void setUpdaetdOn(Date updaetdOn) {
		this.updaetdOn = updaetdOn;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", fund=" + fund + ", balance=" + balance + ", updaetdOn="
				+ updaetdOn + "]";
	}
	
}
