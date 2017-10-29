package com.travix.medusa.busyflights.converters;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.UnderlyingApiRequest;
import com.travix.medusa.busyflights.service.UnderlyingApiResponse;

/**
 * Defines the contract for a DTO converter for underlying services
 *
 */
public interface DtoConverter {
	/**
	 * Converts a Busy Flight Request to an underlying API request
	 * 
	 * @param request
	 *            A Busy Flight Request
	 * @return An request to the underlying service
	 */
	UnderlyingApiRequest convert(BusyFlightsRequest request);

	/**
	 * Converts an underlying API response to a Busy Flight Response
	 * 
	 * @param response
	 *            An response from an underlying service
	 * @return A Busy Flight Response
	 */
	BusyFlightsResponse convert(UnderlyingApiResponse response);

	/**
	 * The class type of an underlying API response
	 */
	Class<?> responseType();
}
