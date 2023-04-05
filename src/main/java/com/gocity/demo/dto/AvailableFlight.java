package com.gocity.demo.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/***
 * 
 * @author massimo.sposato
 *
 */
public class AvailableFlight implements Serializable {

	private static final long serialVersionUID = 1L;
	private String departureAirport;
	private String arrivalAirport;
	private String departureDate;
	private String departureTime;
	private String arrivalDate;
	private String arrivalTime;
	private String flightNumber;
	private Collection<String> adultFareCostCurrency = new ArrayList<>();
	
	
	

	public AvailableFlight() {
		super();
		 
	}

	public AvailableFlight(String departureAirport, String arrivalAirport, String departureDate, String departureTime,
			String arrivalDate, String arrivalTime, String flightNumber, Collection<String> adultFareCostCurrency) {
		super();
		this.departureAirport = departureAirport;
		this.arrivalAirport = arrivalAirport;
		this.departureDate = departureDate;
		this.departureTime = departureTime;
		this.arrivalDate = arrivalDate;
		this.arrivalTime = arrivalTime;
		this.flightNumber = flightNumber;
		this.adultFareCostCurrency = adultFareCostCurrency;
	}

	public String getDepartureAirport() {
		return departureAirport;
	}

	public String getArrivalAirport() {
		return arrivalAirport;
	}

	public String getDepartureDate() {
		return departureDate;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public Collection<String> getAdultFareCostCurrency() {
		return adultFareCostCurrency;
	}

	public void setDepartureAirport(String departureAirport) {
		this.departureAirport = departureAirport;
	}

	public void setArrivalAirport(String arrivalAirport) {
		this.arrivalAirport = arrivalAirport;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public void setAdultFareCostCurrency(Collection<String> adultFareCostCurrency) {
		this.adultFareCostCurrency = adultFareCostCurrency;
	}

	@Override
	public String toString() {
		return "AvailableFlight [departureAirport=" + departureAirport + ", arrivalAirport=" + arrivalAirport
				+ ", departureDate=" + departureDate + ", departureTime=" + departureTime + ", arrivalDate="
				+ arrivalDate + ", arrivalTime=" + arrivalTime + ", flightNumber=" + flightNumber
				+ ", adultFareCostCurrency=" + adultFareCostCurrency + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(adultFareCostCurrency, arrivalAirport, arrivalDate, arrivalTime, departureAirport,
				departureDate, departureTime, flightNumber);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AvailableFlight other = (AvailableFlight) obj;
		return Objects.equals(adultFareCostCurrency, other.adultFareCostCurrency)
				&& Objects.equals(arrivalAirport, other.arrivalAirport)
				&& Objects.equals(arrivalDate, other.arrivalDate) && Objects.equals(arrivalTime, other.arrivalTime)
				&& Objects.equals(departureAirport, other.departureAirport)
				&& Objects.equals(departureDate, other.departureDate)
				&& Objects.equals(departureTime, other.departureTime)
				&& Objects.equals(flightNumber, other.flightNumber);
	}

}
