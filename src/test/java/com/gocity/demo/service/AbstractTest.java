package com.gocity.demo.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocity.demo.dto.AvailableFlight;
import com.gocity.demo.dto.FlyRequast;

public class AbstractTest {

	
	private final String  filename = "fly.json"; 
	public final String  AUTH = "Authorization";
	
	/***
	 * 
	 * @return
	 * 
	 * String departureDate, String destinetion, String origin
	 */
	public FlyRequast buildFlyRequasts() {
		return new FlyRequast("22/04/2023","SFO","LHR");
	}
	 
	/***
	 * 
	 * @param obj
	 * @return
	 */
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	/***
	 * 
	 * @return
	 */
	public String buildAutorityHeader() {
		String basic= "Basic bWFzc2ltb3Nwb3NhdG86VGhaUWZlVHo0NWJXeG1jNVhWTTRGNEtScU9ySHh5ZGw=";
		return basic;
	}

	public Map<String, String> buildAutorityHeaderObject() {
	
		String basic = "Basic bWFzc2ltb3Nwb3NhdG86VGhaUWZlVHo0NWJXeG1jNVhWTTRGNEtScU9ySHh5ZGw=";
		Map<String, String> header = Map.of(
									AUTH, basic,
									"Content-Type", "application/json",
									"Accept","*/*",
									"Accept-Encoding","gzip, deflate, br",
									"Connection", "keep-alive"
							);
		 
		return header;
	}
	
	//AvailableFlight
	
	public Collection<AvailableFlight> buildListOfCustomersSFO_LHR22042023() {
	 
		
		Collection<AvailableFlight> result = List.of(
				  buildAvailableFlight("LHR","SFO", "22/04/2023", "08:10", "22/04/2023", "16:55","EI8332", 
						List.of("GBP 823.32 low", "GBP 968.32 flex", "GBP 3537.31 busflex")
				  ),
				  buildAvailableFlight("LHR","SFO", "22/04/2023", "08:40", "22/04/2023", "16:55","EI151", 
							List.of("GBP 823.32 low",  "GBP 968.32 flex", "GBP 3537.31 busflex")
				  ),
				  buildAvailableFlight("LHR","SFO", "22/04/2023", "09:50", "22/04/2023", "16:55","EI8328", 
							List.of("GBP 823.32 low",  "GBP 968.32 flex",  "GBP 3537.31 busflex")
				  ),
				  buildAvailableFlight("LHR","SFO", "22/04/2023", "09:50", "22/04/2023", "16:55","EI153", 
							List.of("GBP 823.32 low",  "GBP 968.32 flex",  "GBP 3537.31 busflex")
				  ),
				  buildAvailableFlight("LHR","SFO", "22/04/2023", "10:50", "22/04/2023", "16:55","EI155", 
							List.of("GBP 823.32 low","GBP 968.32 flex","GBP 3537.31 busflex")
				  )
		);
		
		
		
		return result;
	}

	private AvailableFlight buildAvailableFlight( String departureAirport,  String arrivalAirport,  String departureDate, 
												  String departureTime,  String arrivalDate, String arrivalTime, 
												  String flightNumber, Collection<String> adultFareCostCurrency)
	      {
		 
		return new AvailableFlight(  departureAirport,   arrivalAirport,   departureDate,   departureTime,
				  arrivalDate,   arrivalTime,   flightNumber,  adultFareCostCurrency);
	}
	
	private Collection<String> buildAdultFareCostCurrency(String... adultFareCostCurrency){
	 	return List.of(adultFareCostCurrency);
	}
	
	
	protected String buildURI() {
		String uri = "https://walg.paxcache.com/api/v2/flights/fixed?&fare=low&numAdults=1&origin=LHR&destination=SFO&numChildren=0&numInfants=0&numYouths=0&departureDate=22%2F05%2F2023";
		return uri;
		
	}
	
	
	
	
	/****
	 * 
	 * @param jsonResponse
	 */
	protected void verifyAssetJsonFly(String jsonResponse) {
			assertThat(jsonResponse).isNotNull();
	     

	       assertThat(jsonResponse.contains("outbound"));
	       assertThat(jsonResponse.contains("EI149"));
	       assertThat(jsonResponse.contains("EI149"));
	       assertThat(jsonResponse.contains("\"basisCode\":\"MJWEBOW6\""));
	       assertThat(jsonResponse.contains("\flightNumber\":\"EI8332\""));
	       
	       assertThat(jsonResponse.contains("\"basisCode\":\"MJWEBOW6\""));
	       assertThat(jsonResponse.contains("\flightNumber\":\"EI61\""));
	       
	       assertThat(jsonResponse.contains("\"basisCode\":\"MJ26USFF\""));
	       assertThat(jsonResponse.contains("\flightNumber\":\"EI8332\""));
	       
	       assertThat(jsonResponse.contains("\"basisCode\":\"MJ26USFF\""));
	       assertThat(jsonResponse.contains("\flightNumber\":\"EI61\""));
	       
	       assertThat(jsonResponse.contains("\"basisCode\":\"JEU6USBC\""));
	       assertThat(jsonResponse.contains("\flightNumber\":\"EI8332\""));
	       
	       assertThat(jsonResponse.contains("\"basisCode\":\"JEU6USBC\""));
	       assertThat(jsonResponse.contains("\flightNumber\":\"EI61\""));
	       
	       assertThat(jsonResponse.contains("2023-04-22T07:10:00.000"));
	       assertThat(jsonResponse.contains("\"airportCode\":\"LHR\""));
	       
	       assertThat(jsonResponse.contains("2023-04-22T08:35:00.000"));
	       assertThat(jsonResponse.contains("\"airportCode\":\"DUB\""));
	       
	       assertThat(jsonResponse.contains("\"info\":{\n"
	       + "                           \"number\":\"8332\",\n"
	       + "                           \"code\":\"EI 8332\",\n"
	       + "                           \"carrierAirlineCode\":\"EI\",\n"
	       + "                           \"carrierAirlineName\":\"Aer Lingus\",\n"
	       + "                           \"operatingAirlineCode\":\"BA\",\n"
	       + "                           \"operatingAirlineName\":\"British Airways\",\n"
	       + "                           \"aircraftType\":\"AIRBUS A320\",\n"
	       + "                           \"airportTerminal\":\"2\",\n"
	       + "                           \"isAerlingusUK\":false\n"
	       + "}"));
	     
	}
}
