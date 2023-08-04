package assignment.ecommerceplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
@EntityScan({ "assignment.*" })
public class ECommercePlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(ECommercePlatformApplication.class, args);
	}
	
	@Bean public BCryptPasswordEncoder bCryptPasswordEncoder() {
	    return new BCryptPasswordEncoder(); 
	}

}
