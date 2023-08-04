package assignment.ecommerceplatform.security;

import java.util.Collection;


public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private String username;
	
	public JwtResponse() {}

	public JwtResponse(String accessToken, String username) {
		this.token = accessToken;
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
    
}