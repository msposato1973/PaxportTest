package com.gocity.demo.utility;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gocity.demo.dto.AvailableFlight;
import com.gocity.demo.model.BasisCode;
import com.gocity.demo.model.Fare;
import com.gocity.demo.model.Flight;
import com.gocity.demo.model.Outbound;
import com.gocity.demo.model.PriceInfo;
import com.gocity.demo.model.Root;
import com.gocity.demo.model.Trip;

/***
 * 
 * @author massimo.sposato
 *
 */
public class UtilityParser {

	private static final Logger LOGGER = LoggerFactory.getLogger(UtilityParser.class);

	private boolean isUk;
	private String currency;

	/***
	 * 
	 * @param myJsonString
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public Outbound executeParsing(final String jsonString) 
			throws JsonMappingException, JsonProcessingException 
	{
	
		LOGGER.info("START executeParsing : ");
		ObjectMapper objectMapper = new ObjectMapper();
		Collection<Flight> listFlights = new ArrayList<>();

		Outbound outband = null;
		try {
			JavaType valueType = objectMapper.constructType(Root.class);
			Root root = (!jsonString.contains("NO_SERVICE")) ? objectMapper.readValue(jsonString, valueType) : null;

			if (root != null && root.data != null) {
				setCurrency(root.data.attributes.currency);
				outband = root.data.journey.outbound;
				listFlights = outband.flights;
				this.isUk = outband.isUk;
			}

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		LOGGER.info("END executeParsing : ");
		return outband;
	}

	/***
	 * 
	 * @param listFlights
	 * @return
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 */
	public Collection<AvailableFlight> getAvailableFlight(final Collection<Flight> listFlights)
			throws JsonMappingException, JsonProcessingException {

		LOGGER.debug("START getAvailableFlight : ");
		Collection<AvailableFlight> result = new ArrayList<AvailableFlight>();

		try {

			listFlights.stream().forEach((flight) -> {
				AvailableFlight availableFlight = null;

				if (isNotNullFlight(flight)) {
					// in case of directly flight
					LOGGER.info("flight getAvailableFlight totalDuration() : " + flight.totalDuration);

					if (flight.trips.size() == 1) {
						LOGGER.info("we are in case of directly flight  - num of trips  " + flight.trips.size());
						availableFlight = addToAvailableFlightDirectly(flight.trips.stream().findFirst().get(),
								flight.priceInfo);
					} else {
						LOGGER.info("we are in case of not directly flight - num of trips  " + flight.trips.size());
						availableFlight = addToAvailableFlight(flight.trips.stream().findFirst().get(),
								flight.trips.stream().reduce((one, two) -> two).get(), flight.priceInfo);
					}

				}

				if (availableFlight != null)
					result.add(availableFlight);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

		LOGGER.debug("END getAvailableFlight : ");

		return result;
	}

	/***
	 * 
	 * @param result
	 * @param first
	 * @param last
	 * 
	 *               Setting up the Arrival asset Setting up the Departure asset
	 */
	private AvailableFlight addToAvailableFlight(final Trip first, final Trip last, final PriceInfo priceInfo) {

		LOGGER.info("START addToAvailableFlight : ");
		AvailableFlight availableFlight = new AvailableFlight();

		// Departure
		setDeparturFlight(availableFlight, first);

		// Arrival
		setArrivalFlight(availableFlight, last);

		// FlightNumber
		availableFlight.setFlightNumber(first.info.carrierAirlineCode + "" + first.info.number);

		setFaresPriceInfo(availableFlight, priceInfo);

		LOGGER.info("END addToAvailableFlight : ");
		return availableFlight;
	}

	/***
	 * 
	 * @param availableFlight
	 * @param trip
	 */
	private void setArrivalFlight(AvailableFlight availableFlight, final Trip trip) {
		availableFlight.setArrivalDate(setDateToString(trip.arrival.date));
		availableFlight.setArrivalAirport(trip.arrival.airportCode);
		availableFlight.setArrivalTime(new SimpleDateFormat("HH:mm").format(trip.arrival.date));

	}

	private boolean isNotNullFlight(final Flight flight) {
		return (flight != null && flight.priceInfo != null && (flight.trips != null && flight.trips.size() > 0));
	}

	/***
	 * 
	 * @param availableFlight
	 * @param trip
	 */
	private void setDeparturFlight(AvailableFlight availableFlight, Trip trip) {
		availableFlight.setDepartureDate(setDateToString(trip.departure.date));
		availableFlight.setDepartureAirport(trip.departure.airportCode);
		availableFlight.setDepartureTime(new SimpleDateFormat("HH:mm").format(trip.departure.date));
	}

	/****
	 * 
	 * @param flightNumber
	 * @param fare
	 * @return
	 */
	private boolean populateAdultFareCostCurrency(final String flightNumber, final Fare fare) {
		boolean findFly = false;
		for (BasisCode basisCode : fare.basisCodes) {
			if (basisCode.flightNumber.equals(flightNumber))
				findFly = true;
		}

		return findFly;

	}

	/***
	 * 
	 * @param result
	 * @param trip
	 */
	private AvailableFlight addToAvailableFlightDirectly(final Trip trip, final PriceInfo priceInfo) {

		LOGGER.debug("START addToAvailableFlightDirectly : ");
		AvailableFlight availableFlight = new AvailableFlight();

		// Arrival
		setArrivalFlight(availableFlight, trip);

		// Departure
		setDeparturFlight(availableFlight, trip);

		// FlightNumber
		availableFlight.setFlightNumber(trip.info.carrierAirlineCode.concat(trip.info.number));

		setFaresPriceInfo(availableFlight, priceInfo);

		LOGGER.info("END addToAvailableFlightDirectly : ");

		return availableFlight;

	}

	/***
	 * 
	 * @param date
	 * @return
	 *  
	 * Convert String to LocalDate Print and display the day and month
	 */
	public static String setLocalDateConverter(final String date) {

		LOGGER.debug("START setLocalDateConverter : ");

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		// convert String to LocalDate
		LocalDate localDate = LocalDate.parse(date, formatter);

		// Print and display the day and month
		DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
		DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
		formatter = builder.appendLiteral(localDate.format(dayFormatter)).appendLiteral("%2F")
				.appendLiteral(localDate.format(monthFormatter)).appendLiteral("%2F").appendValue(ChronoField.YEAR)
				.toFormatter();

		LOGGER.debug("END setLocalDateConverter : ");
		return localDate.format(formatter);

	}

	/***
	 * 
	 * @param costCurrency
	 * @return
	 */
	private String addCurrency(double costCurrency) {
		return getCurrency() + " " + costCurrency;
	}

	/****
	 * 
	 * @param AvailableFlight availableFlight
	 * @param PriceInfo       priceInfo
	 */
	private void setFaresPriceInfo(AvailableFlight availableFlight, final PriceInfo priceInfo) {

		priceInfo.fares.stream().forEach((fare) -> {
			if (populateAdultFareCostCurrency(availableFlight.getFlightNumber(), fare)) {
				LOGGER.info("START setAdultFareCostCurrency");
				LOGGER.info("***** setAdultFareCostCurrency with price : " + fare.price);
				LOGGER.info("***** setAdultFareCostCurrency with type : " + fare.type);

				availableFlight.getAdultFareCostCurrency().add(addCurrency(fare.price).concat(" " + fare.type));
				LOGGER.info("END setAdultFareCostCurrency");

			}
		});
	}

	/***
	 * 
	 * @param date
	 * @return string
	 * 
	 * Convert Date to String
	 */
	private static String setDateToString(final Date date) {
		LOGGER.info("START convert String to LocalDate : ");
		return new SimpleDateFormat("dd/MM/yyyy").format(date);
	}

	public boolean isUk() {
		return isUk;
	}

	public void setUk(boolean isUk) {
		this.isUk = isUk;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
