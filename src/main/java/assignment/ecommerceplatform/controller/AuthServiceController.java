package assignment.ecommerceplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import assignment.ecommerceplatform.data.User;
import assignment.ecommerceplatform.data.auth.JwtResponse;
import assignment.ecommerceplatform.data.auth.LoginForm;
import assignment.ecommerceplatform.repository.UserRepository;
import assignment.ecommerceplatform.security.jwt.JwtProvider;

//@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
//@RequestMapping("/authentication")
public class AuthServiceController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;
	

	@Autowired
	private JwtProvider jwtProvider;
	
	
	@PostMapping("/authentication/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginForm loginRequest) {

		System.out.println("b4 auth");
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		System.out.println("After auth");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		String jwt = jwtProvider.generateJwtToken(loginRequest.getUsername());
		User userDetails = (User) authentication.getPrincipal();
		
		JwtResponse response = new JwtResponse(jwt, userDetails.getUsername() );
//		for ( GrantedAuthority authority : userDetails.getAuthorities() )
//			response.getRoles().add(authority.getAuthority());

		return ResponseEntity.ok( response );
	}
	
	@GetMapping( "user/{username}" )
	public ResponseEntity<User> getOrder(@PathVariable( name = "username" ) String username) 
	{
		User returnedUser= userRepository.findByUsername(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
							"User with given name " + username + " does not exist"));
		return ResponseEntity.ok( returnedUser);
	}
	
	

}