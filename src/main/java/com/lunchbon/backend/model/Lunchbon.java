package com.lunchbon.backend.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Lunchbon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String userEmail;
	
	private String userEmployeeNumber;
	
	private Restaurant restaurant;
	
	private String timeStampAsDateString;
	
	public Lunchbon() {
		
	}

	public Lunchbon(String userEmail, String userEmployeeNumber, Restaurant restaurant,
			String timeStampAsDateString) {
		this.userEmail = userEmail;
		this.userEmployeeNumber = userEmployeeNumber;
		this.restaurant = restaurant;
		this.timeStampAsDateString = timeStampAsDateString;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserEmployeeNumber() {
		return userEmployeeNumber;
	}

	public void setUserEmployeeNumber(String userEmployeeNumber) {
		this.userEmployeeNumber = userEmployeeNumber;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getTimeStampAsDateString() {
		return timeStampAsDateString;
	}

	public void setTimeStampAsDateString(String timeStampAsDateString) {
		this.timeStampAsDateString = timeStampAsDateString;
	}
	
}
