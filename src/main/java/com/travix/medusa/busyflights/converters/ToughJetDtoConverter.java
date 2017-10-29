package com.travix.medusa.busyflights.converters;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.service.UnderlyingApiRequest;
import com.travix.medusa.busyflights.service.UnderlyingApiResponse;

public class ToughJetDtoConverter implements DtoConverter {
	
	/*
	 * (non-Javadoc)
	 * @see com.travix.medusa.busyflights.converters.DtoConverter#convert(com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest)
	 */
	@Override
	public UnderlyingApiRequest convert(BusyFlightsRequest request) {
		return new ToughJetRequest(request);
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.travix.medusa.busyflights.converters.DtoConverter#convert(com.travix.medusa.busyflights.service.UnderlyingApiResponse)
	 */
	@Override
	public BusyFlightsResponse convert(UnderlyingApiResponse response) {
		ToughJetResponse apiResponse = (ToughJetResponse) response;
		BusyFlightsResponse converted = new BusyFlightsResponse();
		
		double priceWithDiscount = apiResponse.getBasePrice() * (1 - apiResponse.getDiscount());
		double priceWithDiscountAndTaxes = priceWithDiscount * (1 + apiResponse.getTax());
		
		converted.setAirline(apiResponse.getCarrier());
		converted.setDepartureAirportCode(apiResponse.getDepartureAirportName());
		converted.setDestinationAirportCode(apiResponse.getArrivalAirportName());
		converted.setFare(priceWithDiscountAndTaxes);
		converted.setSupplier("ToughJet");
		
		Instant instant = Instant.parse(apiResponse.getInboundDateTime());
		LocalDateTime dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		converted.setArrivalDate(DateTimeFormatter.ISO_DATE_TIME.format(dateTime));
		
		instant = Instant.parse(apiResponse.getOutboundDateTime());
		dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		converted.setDepartureDate(DateTimeFormatter.ISO_DATE_TIME.format(dateTime));
		
		return converted;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.travix.medusa.busyflights.converters.DtoConverter#responseType()
	 */
	@Override
	public Class<?> responseType() {
		return ToughJetResponse[].class;
	}
}
