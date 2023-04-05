package com.gocity.demo.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.gocity.demo.constant.Constants;
import com.gocity.demo.dto.FlyRequast;
import com.gocity.demo.exception.CocityServiceException;
import com.gocity.demo.utility.UtilityParser;

@Service
public class RestClientService implements IRestClientService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RestClientService.class);
	 
	 
	/***
	 * 
	 * @param reuest
	 * @return
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public  String  getRequest(FlyRequast reuest, String basicAuthenticationHeader) throws URISyntaxException, IOException, InterruptedException {
		 
		LOGGER.info("START : basicAuthenticationHeader "+basicAuthenticationHeader);
		
		if(basicAuthenticationHeader ==null){
			throw new NullPointerException(Constants.ERROR_AUTH);
		}
		
		LOGGER.info("START : getRequest");
		HttpRequest request = HttpRequest.newBuilder()
				  .GET()
				  .uri(new URI(preparingUrl(reuest)))
				  .header("Authorization",basicAuthenticationHeader)
				  .build();
		
		  
		
		LOGGER.info("END : getRequest");
		return HttpClient.newHttpClient().send(request, BodyHandlers.ofString()).body();
	}
	

	
	/***
	 * 
	 * @param requestDTO
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private final String preparingUrl(final FlyRequast apiObject) throws UnsupportedEncodingException {
		LOGGER.info("START preparingUrl(...)");
		LOGGER.info("START setting MapParams - parameters ");
		
		if(apiObject==null) {
			throw new NullPointerException(Constants.ERROR_NULL);
		}
		
		Map<String, Object> parameters = Map.of(
				Constants.PARAM_DEPART, UtilityParser.setLocalDateConverter(apiObject.getDepartureDate()), 
				Constants.PARAM_DEST, apiObject.getDestinetion(), 
				Constants.PARAM_FARE, "low", 
				Constants.PARAM_NUM_ADU, apiObject.getNumAdults(), 
				Constants.PARAM_NUM_CH,apiObject.getNumChildren(),
				Constants.PARAM_NUM_INF,apiObject.getNumYouths(),
				Constants.PARAM_NUM_Y,apiObject.getNumInfants(),
				Constants.PARAM_ORI,apiObject.getOrigin()
		);
		
		LOGGER.info("END setting MapParams - parameters ");
	    
		
		StringBuilder postData = new StringBuilder(Constants.URL_BASE);
		LOGGER.info("StringBuilder -> postData : " + postData.toString());
		
	    for (Map.Entry<String,Object> param : parameters.entrySet()) {
	        if (postData.length() != 0) postData.append('&');
	        postData.append(param.getKey());
	        postData.append('=');
	        postData.append(param.getValue());
	    }
		
	    LOGGER.info("postData.toString() " + postData.toString());
	    LOGGER.info("END preparingUrl(...)");
		return postData.toString(); 
	}
	
	
	
	
	
	
}
