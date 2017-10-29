package com.travix.medusa.busyflights.service;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.travix.medusa.busyflights.converters.DtoConverter;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

/**
 * 
 * This is the class that implements the API for BusyFlights. It validates the
 * request and run some generic code that queries all underlying services,
 * aggregating the results in a fare order.
 *
 */
@RestController
@RequestMapping("/")
public class BusyFlightsRestController {

	@Autowired
	public RestTemplateHelper helper;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<List<BusyFlightsResponse>> searchFlights(@RequestBody BusyFlightsRequest request) {
		ResponseEntity<List<BusyFlightsResponse>> apiResponse;

		if (isValidRequest(request)) {
			SortedSet<BusyFlightsResponse> responses = new TreeSet<BusyFlightsResponse>();

			for (AggregatableService service : AggregatableService.values()) {
				DtoConverter converter = service.getConverter();

				UnderlyingApiRequest requestToService = converter.convert(request);
				List<UnderlyingApiResponse> responseFromService = helper.postTo(service, requestToService,
						converter.responseType());
				if (responseFromService != null) {
					for (UnderlyingApiResponse r : responseFromService) {
						responses.add(converter.convert(r));
					}
				}
			}

			if (responses.isEmpty()) {
				apiResponse = ResponseEntity.notFound().build();

			} else {
				apiResponse = ResponseEntity.ok(new ArrayList<BusyFlightsResponse>(responses));
			}

		} else {
			apiResponse = ResponseEntity.badRequest().build();
		}

		return apiResponse;
	}

	/**
	 * Validates the request to check the constraints
	 * 
	 * @param request
	 *            An API request
	 * 
	 * @return <code>true</code> if the request was sent within specifications
	 */
	private boolean isValidRequest(BusyFlightsRequest request) {
		boolean valid = true;

		valid &= Pattern.matches("^[A-Z]{3}$", request.getOrigin());
		valid &= Pattern.matches("^[A-Z]{3}$", request.getDestination());
		valid &= request.getNumberOfPassengers() <= 4;

		try {
			DateTimeFormatter.ISO_LOCAL_DATE.parse(request.getDepartureDate());
			DateTimeFormatter.ISO_LOCAL_DATE.parse(request.getReturnDate());
		} catch (DateTimeParseException e) {
			valid = false;
		}

		return valid;
	}
}
