package com.travix.medusa.busyflights;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;

/**
 * Mocked service that generates one valid result for ToughJet
 */
@RestController
@RequestMapping("/toughjet")
public class ToughJetRestController {

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<List<ToughJetResponse>> searchFlights(@RequestBody ToughJetRequest request) {
		ToughJetResponse response = new ToughJetResponse();
		response.setCarrier("American Airlines");
		
		LocalDate date = LocalDate.parse(request.getOutboundDate(), DateTimeFormatter.ISO_LOCAL_DATE);
		LocalTime time = LocalTime.of((int) (Math.random() * 100) % 24, (int) (Math.random() * 100) % 60);
		LocalDateTime dateTime = LocalDateTime.of(date, time);
		ZoneOffset offset = ZoneId.systemDefault().getRules().getOffset(dateTime);
		Instant outboundDateWithTime = dateTime.toInstant(offset);
		response.setOutboundDateTime(DateTimeFormatter.ISO_INSTANT.format(outboundDateWithTime));

		date = LocalDate.parse(request.getInboundDate(), DateTimeFormatter.ISO_LOCAL_DATE);
		time = LocalTime.of((int) (Math.random() * 100) % 24, (int) (Math.random() * 100) % 60);
		dateTime = LocalDateTime.of(date, time);
		offset = ZoneId.systemDefault().getRules().getOffset(dateTime);
		Instant inboundDateWithTime = dateTime.toInstant(offset);
		response.setInboundDateTime(DateTimeFormatter.ISO_INSTANT.format(inboundDateWithTime));
		
		response.setDepartureAirportName(request.getFrom());
		response.setArrivalAirportName(request.getTo());
		response.setBasePrice(Math.random() * 100);
		response.setDiscount((int)(Math.random() * 100));
		response.setTax((int)(Math.random() * 100));
		return ResponseEntity.ok(Arrays.asList(response));
	}
}
