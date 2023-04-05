package com.gocity.demo.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.stereotype.Component;

import com.gocity.demo.dto.FlyRequast;


public interface IRestClientService {

	public  String  getRequest(FlyRequast reuest, String basicAuthenticationHeader) throws URISyntaxException, IOException, InterruptedException ;
		 
	  
	 
}
