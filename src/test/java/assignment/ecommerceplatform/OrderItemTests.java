package assignment.ecommerceplatform;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import assignment.ecommerceplatform.data.OrderItem;
import assignment.ecommerceplatform.data.auth.JwtResponse;
import assignment.ecommerceplatform.list.OrderItemList;

public class OrderItemTests {
	
private String url;
private JwtResponse jwtResponse;
	
	public OrderItemTests( String url , JwtResponse jwtResponse ) {
		this.url = url + "/api/order-items";
		this.jwtResponse = jwtResponse;
	}
	
	public void runAllTests(OrderItem orderItem)
	{
		orderItem = testAddNewOrderItem(orderItem);
		testGetOrderItem(orderItem.getOrderItemId());
		testGetOrderItem(orderItem.getOrderItemId());
		testUpdateOrderItem(orderItem);
		testDeleteOrderItem(orderItem.getOrderItemId());
		
	}
	
	
	@Test
	private void testGetOrderItems(long orderId) {
		HttpHeaders headers = makeHeaders();
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).
				queryParam("orderId", orderId);
		
		HttpEntity<?> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();

		System.out.println( "uri: " + builder.toUriString());

		HttpEntity<OrderItemList> response = restTemplate.exchange(
				builder.toUriString(), 
				HttpMethod.GET, 	
				entity,	 
				OrderItemList.class)	;

		List<OrderItem> orders = response.getBody();
		orders.forEach(order -> printOrderItem(order));
	}
	
	@Test
	private void testGetOrderItem(long id) {
		HttpHeaders headers = makeHeaders();	

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + "/")
				.path(String.valueOf(id));
		
		HttpEntity<?> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();

		System.out.println( "uri: " + builder.toUriString());

		HttpEntity< OrderItem > response = restTemplate.exchange(
				builder.toUriString(), 
				HttpMethod.GET, 	
				entity,	 
				OrderItem.class)	;
		
		printOrderItem( response.getBody() );
	}
	
	@Test
	public OrderItem testAddNewOrderItem(OrderItem order) {
		HttpHeaders headers = makeHeaders();	

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
		
		HttpEntity<?> entity = new HttpEntity<>( order, headers);

		RestTemplate restTemplate = new RestTemplate();

		System.out.println( "uri: " + builder.toUriString());

		HttpEntity< OrderItem > response = restTemplate.exchange(
				builder.toUriString(), 
				HttpMethod.POST, 	
				entity,	 
				OrderItem.class);
		OrderItem body = response.getBody();
		printOrderItem( body );
		return body;
	}
	
	@Test
	private void testUpdateOrderItem(OrderItem orderItem) {
		HttpHeaders headers = makeHeaders();	

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + "/").path(String.valueOf(orderItem.getOrderItemId()));
		
		HttpEntity<?> entity = new HttpEntity<>( orderItem, headers);

		RestTemplate restTemplate = new RestTemplate();

		System.out.println( "uri: " + builder.toUriString());

		HttpEntity< OrderItem > response = restTemplate.exchange(
				builder.toUriString(), 
				HttpMethod.PUT, 	
				entity,	 
				OrderItem.class);
		printOrderItem( response.getBody() );
	}
	
	@Test
	private void testDeleteOrderItem(long id) {
		
		HttpHeaders headers = makeHeaders();	

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + "/").path(String.valueOf(id));
		
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
	
	private void printOrderItem(OrderItem orderItem)
	{
		System.out.println("Item name is "+orderItem.getName());
		System.out.println("Item description is "+orderItem.getDescription());
		System.out.println("Item seller is "+orderItem.getSellerName());
		System.out.println("Item price is "+orderItem.getPrice());
	}

}
