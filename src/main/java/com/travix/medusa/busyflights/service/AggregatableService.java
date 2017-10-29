package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.converters.CrazyAirDtoConverter;
import com.travix.medusa.busyflights.converters.DtoConverter;
import com.travix.medusa.busyflights.converters.ToughJetDtoConverter;

/**
 * This enum lists all supported services, with their respective converter classes
 */
public enum AggregatableService {
	CRAZYAIR(new CrazyAirDtoConverter()), 
	TOUGHJET(new ToughJetDtoConverter());
	
	private DtoConverter converter;

	private AggregatableService(DtoConverter converter) {
		this.converter = converter;
	}
 
	public DtoConverter getConverter() {
		return converter;
	};
}
