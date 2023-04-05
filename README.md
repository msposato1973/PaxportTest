# text exercise implementation 

- I implemented the Restful API and Client rest.  
- using spring boot 
- Restful API
- Junit for test (The tests done both to check for exceptions that the cases of correct answers).

### Assumptions
   

As an assertion we have chosen to show only the data:

As an assumptions, I have chosen to display the start and origin code and datetime :
I have chosen to show the departure date and the arrival date,
also show the time separately (as shown on the various airport scoreboards).

The code has also provided for a possible case of direct flight.
- As Adult fare cost with currency: 
   List of costs for each type/class with the symbol of the coin
   (and associate them with each route)
   
   - Example  : 
   
   		"adultFareCostCurrency": [
            "USD 936.7 low",
            "USD 1112.92 flex",
            "USD 5132.92 busflex"
        ]




### Percentage code covered when running Test ?
As an assertion we have chosen to show only the data:

### Percentage code covered when running Test ?
- In image : `./codecovereg.jpeg`
If you use Maven, run the following command in a terminal window (in the complete directory) to see cover code '%' :
	`mvn verify`

### How to compile ?
- If you use Maven, run the following command in a terminal window (in the complete directory):
	`mvn clean install`
	
### How to Run and Test ?

- If you use Maven, run the following command in a terminal window (in the complete directory):
`./mvnw spring-boot:run`



Run Spring Boot application with command: `mvn spring-boot:run`

If you want to test URL by client service example : 'Postman'

- Create a new POST request and as body > raw , add this

{
        "departureDate": "22/04/2023",
        "destinetion": "SFO",
        "origin": "LHR",
        "fare": "low",
        "numAdults": 1,
        "numChildren": 0,
        "numInfants": 0,
        "numYouths": 0
}  

where :  "fare": "low", "numAdults": 1, "numChildren": 0, "numInfants": 0, "numYouths": 0,  
using as  default parameters 

case of test : 
- case 1: 

	```json
	{
	        "departureDate": "22/04/2023",
	        "destinetion": "SFO",
	        "origin": "LHR",
	        "fare": "low",
	        "numAdults": 1,
	        "numChildren": 0,
	        "numInfants": 0,
	        "numYouths": 0
	}
	```
	
Case of test : 
- case 2: 

  
	```json
	{
	        "departureDate": "22/04/2023",
	        "destinetion": "LHR",
	        "origin": "SFO",
	        "fare": "low",
	        "numAdults": 1,
	        "numChildren": 0,
	        "numInfants": 0,
	        "numYouths": 0
	}
	```
	
Case of test : 
- case 3: 

 ```json
	{
	        "departureDate": "22/03/2023",
	        "destinetion": "LHR",
	        "origin": "SFO",
	        "fare": "low",
	        "numAdults": 1,
	        "numChildren": 0,
	        "numInfants": 0,
	        "numYouths": 0
	}
	```
	
- Response  no data : []		
	

- For every request by client rest Post Header params :

	"Authorization" : "Basic bWFzc2ltb3Nwb3NhdG86VGhaUWZlVHo0NWJXeG1jNVhWTTRGNEtScU9ySHh5ZGw="
	
	"Content-Type": "application/json"
	
	"Accept-Encoding": "UTF-8"
	
	"Connection": "keep-alive"
	
	"Accept" : "*/*"





By Commandline 
Now run the service with curl (in a separate terminal window), by running the following command (shown with its output):

$ curl localhost:8080/api/v1/flightSearch

- If you want change port , go to :  `src/main/resources/application.properties`
Change the the property value for `server.port`	
 
 