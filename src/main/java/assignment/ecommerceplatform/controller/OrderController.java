package assignment.ecommerceplatform.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import assignment.ecommerceplatform.data.Order;
import assignment.ecommerceplatform.data.User;
import assignment.ecommerceplatform.message.SimpleResponse;
import assignment.ecommerceplatform.repository.OrderItemRepository;
import assignment.ecommerceplatform.repository.OrderRepository;
import assignment.ecommerceplatform.repository.UserRepository;

/*
 *         ◦ GET /api/orders - Get a list of all orders
    ◦ GET /api/orders/{id} - Get an order by ID
    ◦ POST /api/orders - Create a new order
    ◦ PUT /api/orders/{id} - Update an existing order
    ◦ DELETE /api/orders/{id} - Delete an order by ID
 */

@RestController
@RequestMapping("/api/orders")
public class OrderController 
{
	@Autowired
	private OrderRepository orderRepository;
	
	
	@GetMapping( "" )
	public ResponseEntity< List< Order > > getOrders(@RequestParam(value="userId") long userId) 
	{
		List<Order> orders = orderRepository.findByUserId(userId);
		return ResponseEntity.ok( orders);
	}
	
	@GetMapping( "/{id}" )
	public ResponseEntity<Order> getOrder(@PathVariable( name = "id" ) long id) 
	{
		Order returnedOrder = orderRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Order with given id " + id + " does not exist"));
		return ResponseEntity.ok( returnedOrder);
	}
	
	@PostMapping( "" )
	public ResponseEntity< Order > addNewOrder( @RequestBody Order received ) {
		received = orderRepository.save(received);

		return ResponseEntity.ok( received );
	}
	
	@PutMapping( "/{id}" )
	public ResponseEntity< Order > updateExistingOrder( @PathVariable(value = "id") long id, @RequestBody Order received ) {
		{
			Order existing = orderRepository.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Order with given id " + id + " does not exist"));
			received = orderRepository.save(received);
			return ResponseEntity.ok( received );
		}
	}
	
	@DeleteMapping( "/{id}" )
	public ResponseEntity< SimpleResponse > deleteOrder( @PathVariable( name = "id" ) long id )
	{
		Optional<Order> existing = orderRepository.findById(id);
		if(existing.isPresent() == false)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Order with given id " + id + " does not exist");
		}
		else
		{
			orderRepository.deleteById(id);
		}
		 return ResponseEntity.ok( SimpleResponse.success() );
	}
}
