/**
 * 
 */
package com.gocity.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gocity.demo.constant.Constants;

/**
 * @author massimo.sposato
 *
 */
@ExtendWith(MockitoExtension.class)
class RestClientServiceTest extends AbstractTest{
	
	@InjectMocks
	public RestClientService service;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		service = new RestClientService();
	}

	@Test
	void tesCostructor() throws Exception{
		assertThat(service).isNotNull();
	}
	
	
	@Test
    void testGetRequest() throws Exception {
		try {
		  String json = service.getRequest(buildFlyRequasts(), buildAutorityHeader());
		  verifyAssetJsonFly(json);
	       //System.out.println(jsonResponse);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
    }
	
	@Test
    void testGetRequestNUll() throws Exception {
		try {
		  String json = service.getRequest(buildFlyRequasts(), buildAutorityHeader());
		  verifyAssetJsonFly(json);
	       //System.out.println(jsonResponse);
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
    }
	
	
	
	@Test
    void testPreparingUrl_NullPointerException() throws Exception {
		 
		Exception exception = assertThrows(NullPointerException.class, () -> {
			String jsonResponse = service.getRequest(null, AUTH);
	    });

	    String expectedMessage = Constants.ERROR_NULL;
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
	}

	
	@Test
    void testPreparingUrl_NoAtority() throws Exception {
		 
		 
		 
		Exception exception = assertThrows(NullPointerException.class, () -> {
			String jsonResponse = service.getRequest(buildFlyRequasts(), null);
	    });

	    String expectedMessage = Constants.ERROR_AUTH ;
	    String actualMessage = exception.getMessage();

	    
	    assertTrue(actualMessage.contains(expectedMessage));
	}
	
}
