package assignment.ecommerceplatform.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

@javax.persistence.Entity()
@Table( name="UserOrder" )
public class Order 
{
	@javax.persistence.Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private Date orderDate;
	
	private Date orderDeliveredDate;
	
	private long userId;
	
	 @ManyToMany
	    @JoinTable(
	    		name = "UserOrderItem",
	            joinColumns = @JoinColumn(name = "order_id"),
	            inverseJoinColumns = @JoinColumn(name = "item_id")
	    )
		@OrderColumn( name = "idx" )

	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	

	public Order(long orderId, Date orderDate, Date orderDeliveredDate, long userId, List<OrderItem> orderItems) {
		super();
		this.id = orderId;
		this.orderDate = orderDate;
		this.orderDeliveredDate = orderDeliveredDate;
		this.orderItems = orderItems;
	}

	public Order() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getOrderId() {
		return id;
	}

	public void setOrderId(long orderId) {
		this.id = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getOrderDeliveredDate() {
		return orderDeliveredDate;
	}

	public void setOrderDeliveredDate(Date orderDeliveredDate) {
		this.orderDeliveredDate = orderDeliveredDate;
	}


	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	
	
	
}
