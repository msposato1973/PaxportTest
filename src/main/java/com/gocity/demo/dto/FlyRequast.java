package com.gocity.demo.dto;

public class FlyRequast {
 
	private String departureDate;
	private String destinetion;
	private String origin;
	
	private int numAdults = 1;
	private int numYouths = 0;
	private int numInfants = 0;
	private int numChildren = 0;
	private String fare ;
	
	public FlyRequast() {
		super();
	}
	 
	
	public FlyRequast(String departureDate, String destinetion, String origin) {
		super();
		this.departureDate = departureDate;
		this.destinetion = destinetion;
		this.origin = origin;
		this.fare = "law";
	}

	 

	public String getDepartureDate() {
		return departureDate;
	}

	public String getDestinetion() {
		return destinetion;
	}

	public String getOrigin() {
		return origin;
	}

	public int getNumAdults() {
		return numAdults;
	}

	public int getNumYouths() {
		return numYouths;
	}

	public int getNumInfants() {
		return numInfants;
	}

	public int getNumChildren() {
		return numChildren;
	}

	public String getFare() {
		return fare;
	}


	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public void setDestinetion(String destinetion) {
		this.destinetion = destinetion;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public void setNumAdults(int numAdults) {
		this.numAdults = numAdults;
	}

	public void setNumYouths(int numYouths) {
		this.numYouths = numYouths;
	}

	public void setNumInfants(int numInfants) {
		this.numInfants = numInfants;
	}

	public void setNumChildren(int numChildren) {
		this.numChildren = numChildren;
	}

	public void setFare(String fare) {
		this.fare = fare;
	}
	
	
	
	
	
}
