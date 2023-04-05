package com.gocity.demo.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gocity.demo.constant.Constants;
import com.gocity.demo.dto.AvailableFlight;
import com.gocity.demo.dto.FlyRequast;
import com.gocity.demo.exception.CocityServiceException;
import com.gocity.demo.exception.ResourceNotFoundException;
import com.gocity.demo.model.Flight;
import com.gocity.demo.model.Outbound;
import com.gocity.demo.service.IRestClientService;
import com.gocity.demo.utility.UtilityParser;

@CrossOrigin
@RestController
@RequestMapping(Constants.API)
public class CocityController extends AbstractController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CocityController.class);
	private final String AUTHORITY_KEY = "authorization";

	@Autowired
	private final IRestClientService restClientService;

	public CocityController(IRestClientService restClientService) {
		this.restClientService = restClientService;
	}

	@PostMapping(Constants.POST_FLIGHT_SEARC_API)
	ResponseEntity<?> flightSearchResult(@RequestBody FlyRequast requestSearch,
			@RequestHeader Map<String, String> headers) throws URISyntaxException, IOException, InterruptedException, Exception {

		
		if(headers == null){
			throw new NullPointerException(Constants.ERROR_AUTH);
		 }
		
		if(requestSearch == null){
			throw new NullPointerException(Constants.ERROR_NULL);
		 }
		
		LOGGER.debug(String.format("Header '%s' = %s", AUTHORITY_KEY, headers.get(AUTHORITY_KEY)));
		headers.forEach((key, value) -> {
			LOGGER.debug(String.format("Header '%s' = %s", key, value));
		});

		Collection<AvailableFlight> result = null;
		try {
			result = getResponseService(requestSearch,  headers);
			LOGGER.info("result " + result);
		} catch (Exception e) {
			throw new ResourceNotFoundException(Constants.ERROR_INTER);
		}

		return createResponseEntity(result);
	}

	
	
	/***
	 * 
	 * @param requestSearch
	 * @param headers
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private Collection<AvailableFlight>  getResponseService(FlyRequast requestSearch, Map<String, String> headers) 
	throws URISyntaxException, IOException, InterruptedException {
		
		Collection<AvailableFlight> result = null;
		
		String jsonString = restClientService.getRequest(requestSearch, headers.get(AUTHORITY_KEY));
		UtilityParser utilityparser = new UtilityParser();

		Collection<Flight> listFlights = new ArrayList<>();
		Outbound outband = (jsonString != null) ? utilityparser.executeParsing(jsonString) : null;
		if (outband != null && outband.flights != null)
			listFlights = outband.flights;

		if (listFlights.size() > 0 && !listFlights.isEmpty()) {
			result = utilityparser.getAvailableFlight(listFlights);
		} else {
			result = List.of();
		}

		
		return result;
	}
}
