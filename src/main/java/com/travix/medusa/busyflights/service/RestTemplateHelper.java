package com.travix.medusa.busyflights.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * This bean executes the queries to the underlying services, based on the given configuration
 */
public class RestTemplateHelper {

	private Map<AggregatableService, String> urls;

	public RestTemplateHelper(Map<AggregatableService, String> urls) {
		this.urls = urls;
	}

	public List<UnderlyingApiResponse> postTo(AggregatableService service, UnderlyingApiRequest requestBody, Class<?> responseType) {
		List<UnderlyingApiResponse> body = null;

		String url = urls.get(service);
		if (url != null) {
			try {
				RequestEntity<UnderlyingApiRequest> requestEntity = new RequestEntity<>(requestBody, HttpMethod.POST, new URI(url));
				ResponseEntity<?> response = new RestTemplate().exchange(requestEntity, responseType);

				if (response.getStatusCode().is2xxSuccessful()) {
					body = Arrays.asList((UnderlyingApiResponse[]) response.getBody());
				}
			} catch (URISyntaxException e) {
				throw new RuntimeException("Configured URL for the service is not correct: " + url, e);
			}
		}

		return body;
	}
}
