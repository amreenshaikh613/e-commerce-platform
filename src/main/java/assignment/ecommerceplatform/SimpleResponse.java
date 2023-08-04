package assignment.ecommerceplatform;

public class SimpleResponse {
	private String message;
	public final static String SUCESS_MESSAGE = "Success";
	public final static String FAILURE_MESSAGE = "Failure";
	
	public SimpleResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static SimpleResponse success() {
		return new SimpleResponse( SUCESS_MESSAGE );
	}
	
	public static SimpleResponse failure() {
		return new SimpleResponse( FAILURE_MESSAGE );
	}
}
