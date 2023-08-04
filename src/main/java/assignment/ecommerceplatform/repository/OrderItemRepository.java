package assignment.ecommerceplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import assignment.ecommerceplatform.data.OrderItem;

@Repository
public interface OrderItemRepository extends JpaRepository< OrderItem, Long >{
//
//    ◦ GET /api/order-items - Get a list of all order items
//    ◦ GET /api/order-items/{id} - Get an order item by ID
//    ◦ POST /api/order-items - Create a new order item
//    ◦ PUT /api/order-items/{id} - Update an existing order item
//    ◦ DELETE /api/order-items/{id} - Delete an order item by ID
	
}
