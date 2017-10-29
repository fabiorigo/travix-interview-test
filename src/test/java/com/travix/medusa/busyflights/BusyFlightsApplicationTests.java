package com.travix.medusa.busyflights;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;

/**
 * The test class for the Busy Flight Application.
 * It launches the service at port 8080 (DEFINED PORT) for removing the complexity of service location
 * The underlying services are mocked during the tests, and generate one result each with random values for time and price
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class BusyFlightsApplicationTests {
	
	@Autowired
	private WebApplicationContext context;
	
	private BusyFlightsRequest request;
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
		
		this.request = new BusyFlightsRequest();
		request.setOrigin("LHR");
		request.setDestination("AMS");
		request.setDepartureDate("2018-12-03");
		request.setReturnDate("2019-01-31");
		request.setNumberOfPassengers(2);
	}
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void mustAggregateResultsAndSortByFare() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);
		
		MvcResult result = mockMvc.perform(post("http://localhost:8080/").content(content).contentType("application/json")).andExpect(status().isOk()).andReturn();
		List<BusyFlightsResponse> response = mapper.readValue(result.getResponse().getContentAsString(), new TypeReference<List<BusyFlightsResponse>>() {});
		
		assertEquals(2, response.size());
		assertTrue(response.get(0).getFare() < response.get(1).getFare());
	}
	
	@Test
	public void mustValidateIATA() throws Exception {
		request.setOrigin("LHRY@#$%");
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);
		
		mockMvc.perform(post("http://localhost:8080/").content(content).contentType("application/json")).andExpect(status().is(400));
	}
	
	@Test
	public void mustValidateNumberOfPassengers() throws Exception {
		request.setNumberOfPassengers(7);
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);
		
		mockMvc.perform(post("http://localhost:8080/").content(content).contentType("application/json")).andExpect(status().is(400));
	}
	
	@Test
	public void mustValidateDateFormat() throws Exception {
		request.setDepartureDate("20111203");
		
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(request);
		
		mockMvc.perform(post("http://localhost:8080/").content(content).contentType("application/json")).andExpect(status().is(400));
	}
}
