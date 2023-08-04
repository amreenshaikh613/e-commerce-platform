package assignment.ecommerceplatform.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import assignment.ecommerceplatform.data.Order;

@Repository
public interface OrderRepository extends JpaRepository< Order, Long >
{

	List<Order> findByUserId(long userId);
	
	
}
