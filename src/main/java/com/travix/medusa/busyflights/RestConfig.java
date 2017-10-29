package com.travix.medusa.busyflights;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.travix.medusa.busyflights.service.AggregatableService;
import com.travix.medusa.busyflights.service.RestTemplateHelper;

/**
 * This configuration creates and configures the REST Template helper bean, with
 * the URLs of each underlying service
 *
 */
@Configuration
@ConfigurationProperties("classpath:application.properties")
public class RestConfig {

	@Value("${api.crazyair.url}")
	private String crazyAirUrl;

	@Value("${api.toughjet.url}")
	private String toughJetUrl;

	@Bean
	public RestTemplateHelper restTemplateHelper() {
		Map<AggregatableService, String> urls = new HashMap<AggregatableService, String>();
		urls.put(AggregatableService.CRAZYAIR, crazyAirUrl);
		urls.put(AggregatableService.TOUGHJET, toughJetUrl);
		return new RestTemplateHelper(urls);
	}
}
