package com.gocity.demo.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocity.demo.constant.Constants;
import com.gocity.demo.dto.AvailableFlight;
import com.gocity.demo.dto.FlyRequast;

public class AbstractTest {
	
	protected final String AUTHORITY_KEY = "authorization";
	protected final String  AUTH = "Authorization";
	
	/***
	 * 
	 * @return
	 */
	public String buildAutorityHeader() {
		String basic= "Basic bWFzc2ltb3Nwb3NhdG86VGhaUWZlVHo0NWJXeG1jNVhWTTRGNEtScU9ySHh5ZGw=";
		return basic;
	}

	protected  Map<String, String> buildAutorityHeaderObject() {
		
		String basic = "Basic bWFzc2ltb3Nwb3NhdG86VGhaUWZlVHo0NWJXeG1jNVhWTTRGNEtScU9ySHh5ZGw=";
		Map<String, String> header = Map.of(
									AUTHORITY_KEY, basic,
									Constants.CONTENT , Constants.CONTENT_VAL,
									Constants.ACC,Constants.ACC_VAL,
									Constants.ACCENC,Constants.ACCENC_VAL ,
									Constants.CONN, Constants.CONN_VAL
							);
		 
		return header;
	}
	
	/***
	 * 
	 * @return
	 * 
	 * String departureDate, String destinetion, String origin
	 */
	public FlyRequast buildFlyRequasts() {
		return new FlyRequast("22/04/2023","SFO","LHR");
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

		/***
		 * 
		 * @param departureAirport
		 * @param arrivalAirport
		 * @param departureDate
		 * @param departureTime
		 * @param arrivalDate
		 * @param arrivalTime
		 * @param flightNumber
		 * @param adultFareCostCurrency
		 * @return
		 */
		private AvailableFlight buildAvailableFlight(String departureAirport, String arrivalAirport,
				String departureDate, String departureTime, String arrivalDate, String arrivalTime, String flightNumber,
				Collection<String> adultFareCostCurrency) {

			return new AvailableFlight(departureAirport, arrivalAirport, departureDate, departureTime, arrivalDate,
					arrivalTime, flightNumber, adultFareCostCurrency);
		}
		
		/***
		 * 
		 * @param adultFareCostCurrency
		 * @return
		 */
		private Collection<String> buildAdultFareCostCurrency(String... adultFareCostCurrency){
		 	return List.of(adultFareCostCurrency);
		}
		
		
		
		protected  String asJsonString(final Object obj) {
		    try {
		        return new ObjectMapper().writeValueAsString(obj);
		    } catch (Exception e) {
		        throw new RuntimeException(e);
		    }
		}
}
