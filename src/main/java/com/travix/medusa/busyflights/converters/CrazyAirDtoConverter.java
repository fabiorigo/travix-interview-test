package com.travix.medusa.busyflights.converters;

import java.time.format.DateTimeFormatter;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.service.UnderlyingApiRequest;
import com.travix.medusa.busyflights.service.UnderlyingApiResponse;

public class CrazyAirDtoConverter implements DtoConverter {

	/*
	 * (non-Javadoc)
	 * @see com.travix.medusa.busyflights.converters.DtoConverter#convert(com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest)
	 */
	@Override
	public UnderlyingApiRequest convert(BusyFlightsRequest request) {
		return new CrazyAirRequest(request);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.travix.medusa.busyflights.converters.DtoConverter#convert(com.travix.medusa.busyflights.service.UnderlyingApiResponse)
	 */
	@Override
	public BusyFlightsResponse convert(UnderlyingApiResponse response) {
		CrazyAirResponse apiResponse = (CrazyAirResponse) response;
		BusyFlightsResponse converted = new BusyFlightsResponse();
		
		converted.setAirline(apiResponse.getAirline());
		converted.setArrivalDate(DateTimeFormatter.ISO_DATE_TIME.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(apiResponse.getArrivalDate())));
		converted.setDepartureAirportCode(apiResponse.getDepartureAirportCode());
		converted.setDepartureDate(DateTimeFormatter.ISO_DATE_TIME.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(apiResponse.getDepartureDate())));
		converted.setDestinationAirportCode(apiResponse.getDestinationAirportCode());
		converted.setFare(apiResponse.getPrice());
		converted.setSupplier("CrazyAir");
		return converted;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.travix.medusa.busyflights.converters.DtoConverter#responseType()
	 */
	@Override
	public Class<?> responseType() {
		return CrazyAirResponse[].class;
	}
}
