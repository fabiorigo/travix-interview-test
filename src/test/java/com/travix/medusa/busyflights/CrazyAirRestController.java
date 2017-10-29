package com.travix.medusa.busyflights;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;

/**
 * Mocked service that generate one valid result for CrazyAir
 */
@RestController
@RequestMapping("/crazyair")
public class CrazyAirRestController {

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<List<CrazyAirResponse>> searchFlights(@RequestBody CrazyAirRequest request) {
		CrazyAirResponse response = new CrazyAirResponse();
		response.setAirline("KLM");

		LocalDate date = LocalDate.parse(request.getDepartureDate(), DateTimeFormatter.ISO_LOCAL_DATE);
		LocalTime time = LocalTime.of((int) (Math.random() * 100) % 24, (int) (Math.random() * 100) % 60);
		LocalDateTime departureDateWithTime = LocalDateTime.of(date, time);
		response.setDepartureDate(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(departureDateWithTime));

		date = LocalDate.parse(request.getReturnDate(), DateTimeFormatter.ISO_LOCAL_DATE);
		time = LocalTime.of((int) (Math.random() * 100) % 24, (int) (Math.random() * 100) % 60);
		LocalDateTime returnDateWithTime = LocalDateTime.of(date, time);
		response.setArrivalDate(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(returnDateWithTime));

		response.setCabinclass("E");
		response.setDepartureAirportCode(request.getOrigin());
		response.setDestinationAirportCode(request.getDestination());
		response.setPrice(Math.random() * 100);
		
		return ResponseEntity.ok(Arrays.asList(response));
	}
}
