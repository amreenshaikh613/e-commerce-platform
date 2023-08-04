package assignment.ecommerceplatform.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class OrderItem
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String name;
	
	private String description;
	
	private String sellerName;
	
	private double weight;
	
	private int price;

	public OrderItem() {
		super();
	}

	

	public OrderItem(long orderItemId, String name, String description, String sellerName,
			double weight, int price) {
		super();
		this.id = orderItemId;
		this.name = name;
		this.description = description;
		this.sellerName = sellerName;
		this.weight = weight;
		this.price = price;
	}



	public long getOrderItemId() {
		return id;
	}

	public void setOrderItemId(long orderItemId) {
		this.id = orderItemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}




	public double getWeight() {
		return weight;
	}



	public void setWeight(double weight) {
		this.weight = weight;
	}



	public int getPrice() {
		return price;
	}



	public void setPrice(int price) {
		this.price = price;
	}
	
	

}
