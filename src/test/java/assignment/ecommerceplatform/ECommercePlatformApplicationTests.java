package assignment.ecommerceplatform;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import assignment.ecommerceplatform.data.Order;
import assignment.ecommerceplatform.data.OrderItem;
import assignment.ecommerceplatform.data.User;
import assignment.ecommerceplatform.data.auth.JwtResponse;
import assignment.ecommerceplatform.data.auth.LoginForm;


@SpringBootTest
public class ECommercePlatformApplicationTests {

	final static String url = "http://localhost:8080";
	
	@Test
	void contextLoads() {
		String username = "user1";
		JwtResponse jwtResponse = auth( username, "user123" );
		System.out.println( "Authenticated with token " + jwtResponse.getToken() );
		
		User user = testGetUser(username, jwtResponse);
		
		runOrderTests(jwtResponse, user);
		runOrderItemTests(jwtResponse, user);
		
		OrderItemTests orderItemTests = new OrderItemTests(url, jwtResponse);
		OrderItem returnedItem = orderItemTests.testAddNewOrderItem(getDummyOrderItem());
		ArrayList<OrderItem> list = new ArrayList<>();
		list.add(returnedItem);
		Order order = new Order(0, new Date(), new Date(), user.getId(), list);
		OrderTests orderTests = new OrderTests(url, jwtResponse);
		orderTests.testAddNewOrder(order);
	}
	
	public static void main(String[] args)
	{
		String username = "user1";
		JwtResponse jwtResponse = auth( username, "user123" );
		System.out.println( "Authenticated with token " + jwtResponse.getToken() );
		
		User user = testGetUser(username, jwtResponse);
		
		runOrderTests(jwtResponse, user);
		runOrderItemTests(jwtResponse, user);
		
		OrderItemTests orderItemTests = new OrderItemTests(url, jwtResponse);
		OrderItem returnedItem = orderItemTests.testAddNewOrderItem(getDummyOrderItem());
		ArrayList<OrderItem> list = new ArrayList<>();
		list.add(returnedItem);
		Order order = new Order(0, new Date(), new Date(), user.getId(), list);
		OrderTests orderTests = new OrderTests(url, jwtResponse);
		orderTests.testAddNewOrder(order);
		
	}
	
	private static void runOrderItemTests(JwtResponse jwtResponse, User user) {
		OrderItemTests itemTests = new OrderItemTests(url, jwtResponse);
		
		
		OrderItem item = getDummyOrderItem();
		itemTests.runAllTests(item );
	}

	private static OrderItem getDummyOrderItem() {
		OrderItem item = new OrderItem(0, "Dell 5450", "Laptop", "Croma", 500, 500000);
		return item;
	}

	private static void runOrderTests(JwtResponse jwtResponse, User user) {
		OrderTests orderTests = new OrderTests(url, jwtResponse);
		
		OrderItemTests itemTests = new OrderItemTests(url, jwtResponse);
		
		ArrayList<OrderItem> dummyOrderItems = new ArrayList<>();
		for (OrderItem item : getDummyOrderItems())
		{
			item = itemTests.testAddNewOrderItem(item);
			dummyOrderItems.add(item);
		}
		orderTests.runAllTests(new Order(0, new Date(), new Date(), user.getId(), dummyOrderItems ));
	}
	
	private static HttpHeaders makeHeaders(JwtResponse jwtResponse) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer "+jwtResponse.getToken());
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		return headers;
	}
	
	@Test
	private static User testGetUser(String username, JwtResponse jwtResponse) {
		HttpHeaders headers = makeHeaders(jwtResponse);	

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url + "/user/")
				.path(username);
		
		HttpEntity<?> entity = new HttpEntity<>(headers);

		RestTemplate restTemplate = new RestTemplate();

		System.out.println( "uri: " + builder.toUriString());

		HttpEntity< User > response = restTemplate.exchange(
				builder.toUriString(), 
				HttpMethod.GET, 	
				entity,	 
				User.class)	;
		return response.getBody();
		
	}

	public static ArrayList<OrderItem> getDummyOrderItems() {
		OrderItem phone = new OrderItem(0, "Samsung M20", "Mobile Phone", "Samsung", 50, 50000);
		OrderItem makeUp = new OrderItem(0, "MAC", "Lipsick", "MAC", 50, 5000);
		OrderItem dress = new OrderItem(0, "Libas", "dress", "Libas", 500, 1500);
		OrderItem watch = new OrderItem(0, "Apple 410", "Smart Watch", "Apple", 100, 2500);
		OrderItem toy = new OrderItem(0, "Racing car", "Toy", "Hamleys", 200, 1000);
		
		ArrayList<OrderItem> orderItems = new ArrayList<>();
		orderItems .add(phone);
		orderItems.add(makeUp);
		orderItems.add(toy);
		orderItems.add(watch);
		orderItems.add(dress);
		return orderItems;
	}

	

	@Test
	public static JwtResponse auth(String username, String password) 
	{
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url +"/authentication/signin");
		LoginForm result = new LoginForm();
		result.setUsername( username );
		result.setPassword( password );
		HttpEntity<?> entity = new HttpEntity<LoginForm>(result,headers);

		RestTemplate restTemplate = new RestTemplate();

		System.out.println( "uri: " + builder.toUriString());

		HttpEntity<JwtResponse> response = restTemplate.exchange(
				builder.toUriString(), 
				HttpMethod.POST, 	
				entity,	 
				JwtResponse.class)	;
		return response.getBody();
		
	}


}
