package com.lunchbon.backend.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

public class Lunchbon {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @ManyToOne(optional = false)
    private Employee employee;

    @ManyToOne(optional = false)
	private Restaurant restaurant;
	
	private String timeStampAsDateString;
	
	public Lunchbon() {
		
	}

	public Lunchbon(String userEmail, String userEmployeeNumber, Restaurant restaurant,
			String timeStampAsDateString) {
		this.restaurant = restaurant;
		this.timeStampAsDateString = timeStampAsDateString;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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
