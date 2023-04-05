/**
 * 
 */
package com.gocity.demo.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocity.demo.constant.Constants;
import com.gocity.demo.dto.AvailableFlight;
import com.gocity.demo.dto.FlyRequast;
import com.gocity.demo.exception.ResourceNotFoundException;
import com.gocity.demo.service.RestClientService;


/**
 * @author massimo.sposato
 *
 */
/*
 * SpringBootTest launch an instance of our application for tests purposes
 * 
 * @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 * 
 * @AutoConfigureMockMvc // we mock the http request and we don't need a server
 */
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class CocityControllerTest extends AbstractTest {

	private static final Logger logger = LoggerFactory.getLogger(CocityControllerTest.class);

	private static final String POST_URL = "/api/v1/flightSearch";

	@InjectMocks
	private CocityController controller;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private RestClientService service;

	@Test
	/***
	 * 
	 * @throws Exception
	 * 
	 * we are testing the controller instace
	 */
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

	@Test
	/***
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 *                              INCASE OF EXCPETION , HERE WE ARE TESTING A
	 *                              CUSTOME EXCETION F: Exception in Authentication
	 *                              - basic Authentication is null
	 */
	void testFlightSearchResult_AuthenticationError() throws URISyntaxException, IOException, InterruptedException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		Exception exception = assertThrows(NullPointerException.class, () -> {
			controller.flightSearchResult(buildFlyRequasts(), null);

		});

		String expectedMessage = Constants.ERROR_AUTH;
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	/***
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 *                              INCASE OF EXCPETION , HERE WE ARE TESTING A
	 *                              CUSTOME EXCETION F: Exception in flight param to
	 *                              search - Exception - FlyRequast is null
	 */
	void testFlightSearchResult_ParamSearchingError() throws URISyntaxException, IOException, InterruptedException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		Exception exception = assertThrows(NullPointerException.class, () -> {
			controller.flightSearchResult(null, buildAutorityHeaderObject());
		});

		String expectedMessage = Constants.ERROR_NULL;
		String actualMessage = exception.getMessage();
		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	/***
	 * 
	 * @throws URISyntaxException
	 * @throws IOException
	 * @throws InterruptedException
	 * 
	 *                              INCASE OF EXCPETION , HERE WE ARE TESTING A
	 *                              CUSTOME EXCETION FOR WITH MSG INTERNAL SERVER
	 *                              ERROR.....
	 */
	void testFlightSearchResult_ResourceNot() throws URISyntaxException, IOException, InterruptedException {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
			controller.flightSearchResult(buildFlyRequasts(), buildAutorityHeaderObject());

		});

		String expectedMessage = Constants.ERROR_INTER;
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));

	}

	@Test
	/***
	 * we are testing if there is and instance of service
	 */
	void testServiceInstance() {
		service = new RestClientService();
		assertThat(service).isNotNull();
	}

	@Test
	/***
	 * we are testing if there is and instance of service
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	void testServiceMockInstance() throws Exception {
		service = new RestClientService();
		assertThat(service).isNotNull();

		controller = new CocityController(service);
		assertThat(controller).isNotNull();
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		FlyRequast mockFlyRequest = buildFlyRequasts();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAll(buildAutorityHeaderObject());

		MvcResult result = mockMvc
				.perform(post("/api/v1/flightSearch").content(asJsonString(mockFlyRequest)).headers(httpHeaders)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		assertThat(result).isNotNull();
	}

	@Test
	/***
	 * we are testing if there is and instance of service
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws URISyntaxException
	 * 
     * test with : departureDate : 22/04/2023,
	 *             destinetion : SFO,  
	 *             origin": LHR,
	 *             fare": low,  
	 */
	void testFlightSearchResultSFO_TO_LHR() throws Exception {
		service = new RestClientService();
		assertThat(service).isNotNull();

		controller = new CocityController(service);
		assertThat(controller).isNotNull();
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAll(buildAutorityHeaderObject());

		MvcResult mvcResult = mockMvc
				.perform(post(POST_URL).content(asJsonString(new FlyRequast("22/04/2023","SFO","LHR"))).headers(httpHeaders)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		assertThat(mvcResult).isNotNull();

		String contentAsString = mvcResult.getResponse().getContentAsString();
		assertThat(contentAsString).isNotNull();

		Collection<AvailableFlight> result = new ObjectMapper().readValue(contentAsString,
				new TypeReference<Collection<AvailableFlight>>() {
				});

		assertThat(result).isNotNull();

		// Im expetced a list of 5 available flight
		assertTrue(result.size() > 0 && result.size() == 5);

	}
	
	@Test
	/***
	 * we are testing if there is and instance of service
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws URISyntaxException
	 * 
     * test with : departureDate : 22/04/2023,
	 *             destinetion : LHR, 
	 *             origin": SFO,
	 *             fare": low,   
	 */
	void testFlightSearchResultLHR_TO_SFO() throws Exception {
		service = new RestClientService();
		assertThat(service).isNotNull();

		controller = new CocityController(service);
		assertThat(controller).isNotNull();
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAll(buildAutorityHeaderObject());

		 
		
		MvcResult mvcResult = mockMvc
				.perform(post(POST_URL).content(asJsonString(new FlyRequast("22/04/2023","LHR","SFO"))).headers(httpHeaders)
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn();

		assertThat(mvcResult).isNotNull();

		String contentAsString = mvcResult.getResponse().getContentAsString();
		assertThat(contentAsString).isNotNull();

		Collection<AvailableFlight> result = new ObjectMapper().readValue(contentAsString,
				new TypeReference<Collection<AvailableFlight>>() {
				});

		assertThat(result).isNotNull();

		// Im expetced a list of 5 available flight
		assertTrue(result.size() > 0 && result.size() == 4);

	}
	
	
	@Test
	/***
	 * we are testing if there is and instance of service
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 * @throws URISyntaxException
	 * 
     * test with : departureDate : 22/03/2023,
	 *             destinetion : LHR, 
	 *             origin": SFO,
	 *             fare": low,   
	 */
	void testFlightSearchResultLNoFlight() throws Exception {
		service = new RestClientService();
		assertThat(service).isNotNull();

		controller = new CocityController(service);
		assertThat(controller).isNotNull();
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setAll(buildAutorityHeaderObject());

		//String departureDate, String destinetion, String origin
		MvcResult mvcResult = mockMvc
				.perform(post(POST_URL)
						.content(asJsonString(new FlyRequast("22/03/2023","LHR", "SFO")))
						.headers(httpHeaders)
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
				).andExpect(status().isNoContent()).andReturn();

		assertThat(mvcResult).isNotNull();

		String contentAsString = mvcResult.getResponse().getContentAsString();
		assertThat(contentAsString).isNotNull();

		Collection<AvailableFlight> result = new ObjectMapper().readValue(contentAsString,
				new TypeReference<Collection<AvailableFlight>>() {
				});

		assertThat(result).isNotNull();

		// Im expetced a list of 0 available flight
		assertTrue(result.size() == 0);

	}

}
