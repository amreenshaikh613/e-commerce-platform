package assignment.ecommerceplatform.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import assignment.ecommerceplatform.data.OrderItem;
import assignment.ecommerceplatform.message.SimpleResponse;
import assignment.ecommerceplatform.repository.OrderItemRepository;
import assignment.ecommerceplatform.repository.OrderRepository;

/*
 *         ◦ GET /api/orders - Get a list of all orders
    ◦ GET /api/orders/{id} - Get an order by ID
    ◦ POST /api/orders - Create a new order
    ◦ PUT /api/orders/{id} - Update an existing order
    ◦ DELETE /api/orders/{id} - Delete an order by ID
 */

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController 
{
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	
	@Autowired
	private OrderRepository orderRepository;
	
	@GetMapping( "" )
	public ResponseEntity<List<OrderItem>> getOrders(@RequestParam(value="ytc") long orderId) 
	{
		Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"Order with given id " + orderId + " does not exist"));
		List<OrderItem> orderItems = order.getOrderItems();
		return ResponseEntity.ok( orderItems);
	}
	
	@GetMapping( "/{id}" )
	public ResponseEntity<OrderItem> getOrderItem(@PathVariable( name = "id" ) long id) 
	{
		OrderItem returnedOrder = orderItemRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Order item with given id " + id + " does not exist"));
		return ResponseEntity.ok( returnedOrder);
	}
	
	@PostMapping( "" )
	public ResponseEntity< OrderItem > addNewOrderItem( @RequestBody OrderItem received ) {
		received = orderItemRepository.save(received);

		return ResponseEntity.ok( received );
	}
	
	@PutMapping( "/{id}" )
	public ResponseEntity< OrderItem > updateExistingOrderItem( @PathVariable(value = "id") long id, @RequestBody OrderItem received ) {
		{
			OrderItem existing = orderItemRepository.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"Order item with given id " + id + " does not exist"));
			received = orderItemRepository.save(received);
			return ResponseEntity.ok( received );
		}
	}
	
	@DeleteMapping( "/{id}" )
	public ResponseEntity< SimpleResponse > deleteOrderItem( @PathVariable( name = "id" ) long id )
	{
		Optional<OrderItem> existing = orderItemRepository.findById(id);
		if(existing.isPresent() == false)
		{
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
					"Order item with given id " + id + " does not exist");
		}
		else
		{
			orderItemRepository.deleteById(id);
		}
		 return ResponseEntity.ok( SimpleResponse.success() );
	}
}
