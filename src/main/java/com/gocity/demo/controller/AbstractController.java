package com.gocity.demo.controller;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.gocity.demo.constant.Constants;
import com.gocity.demo.dto.AvailableFlight;
import com.gocity.demo.exception.CocityServiceException;
import com.gocity.demo.exception.ResourceNotFoundException;


public class AbstractController {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);
	  

	/***
	 * 
	 * @param result
	 * @return
	 * @throws CocityServiceException 
	 */
	protected ResponseEntity<?> createResponseEntity(final Collection<AvailableFlight> result) throws Exception
	{
		if(result!=null && !result.isEmpty() && result.size()>=1) {
			LOGGER.debug("result.size() : "+ result.size());
			return new ResponseEntity<Collection<AvailableFlight>>(result, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
		}
			
		 
	}

	

	 
	
		

}
