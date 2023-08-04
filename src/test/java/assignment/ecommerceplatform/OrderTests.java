package assignment.ecommerceplatform;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import assignment.ecommerceplatform.data.Order;
import assignment.ecommerceplatform.data.auth.JwtResponse;
import assignment.ecommerceplatform.list.OrderList;

public class OrderTests {
	
private String url;
private JwtResponse jwtResponse;
	
	public OrderTests( String url , JwtResponse jwtResponse ) {
		this.url = url + "/api/orders";
		this.jwtResponse = jwtResponse;
	}
	
	public void runAllTests(Order order)
	{
		order = testAddNewOrder(order);
		testGetOrders(order.getUserId());
		testGetOrder(order.getOrderId());
		testUpdateOrder(order);
		testDeleteOrder(order.getOrderId());
		
	}
	
	
	@Test
	private void testGetOrders(long userId) {
		HttpHeaders headers = makeHeaders();
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).
				queryParam("userId", userId);
		
		HttpEntity<?> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();

		System.out.println( "uri: " + builder.toUriString());

		HttpEntity<OrderList> response = restTemplate.exchange(
				builder.toUriString(), 
				HttpMethod.GET, 	
				entity,	 
				OrderList.class)	;

		List<Order> orders = response.getBody();
		orders.forEach(order -> printOrder(order));
	}
	
	@Test
	private void testGetOrder(long id) {
		HttpHeaders headers = makeHeaders();	

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + "/")
				.path(String.valueOf(id));
		
		HttpEntity<?> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();

		System.out.println( "uri: " + builder.toUriString());

		HttpEntity< Order > response = restTemplate.exchange(
				builder.toUriString(), 
				HttpMethod.GET, 	
				entity,	 
				Order.class)	;
		
		printOrder( response.getBody() );
	}
	
	@Test
	public Order testAddNewOrder(Order order) {
		HttpHeaders headers = makeHeaders();	

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		
		HttpEntity<?> entity = new HttpEntity<>( order, headers);

		RestTemplate restTemplate = new RestTemplate();

		System.out.println( "uri: " + builder.toUriString());

		HttpEntity< Order > response = restTemplate.exchange(
				builder.toUriString(), 
				HttpMethod.POST, 	
				entity,	 
				Order.class);
		Order body = response.getBody();
		printOrder( body );
		return body;
	}
	
	@Test
	private void testUpdateOrder(Order order) {
		HttpHeaders headers = makeHeaders();	

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + "/").path(String.valueOf(order.getOrderId()));
		
		HttpEntity<?> entity = new HttpEntity<>( order, headers);

		RestTemplate restTemplate = new RestTemplate();

		System.out.println( "uri: " + builder.toUriString());

		HttpEntity< Order > response = restTemplate.exchange(
				builder.toUriString(), 
				HttpMethod.PUT, 	
				entity,	 
				Order.class);
		printOrder( response.getBody() );
	}
	
	@Test
	private void testDeleteOrder(long l) {
		
		HttpHeaders headers = makeHeaders();	

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + "/").path(String.valueOf(l));
		
		HttpEntity<?> entity = new HttpEntity<>(  headers);

		RestTemplate restTemplate = new RestTemplate();

		System.out.println( "uri: " + builder.toUriString());

		restTemplate.exchange(
				builder.toUriString(), 
				HttpMethod.DELETE, 	
				entity,	 
				Void.class);
	}
	

	private HttpHeaders makeHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer "+jwtResponse.getToken());
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}
	
	private void printOrder(Order order)
	{
		System.out.println("Order date is "+order.getOrderDate());
	}

}
