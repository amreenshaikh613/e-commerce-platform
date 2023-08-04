package assignment.ecommerceplatform.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class NetuseBriqMvcConfig implements WebMvcConfigurer {
	@Value("${ecommerce.cors.origins}")
	private String corsOrigins;
	
	@Value("${ecommerce.cors.maxAge}")
	private int maxAge;

	
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsRegistration reg = registry.addMapping("/**");
        for(String url: corsOrigins.split(",")) {
            reg.allowedOrigins(url);
        }
        reg.allowedMethods("*").allowedHeaders("*").allowCredentials(false);
        reg.maxAge(maxAge);
    }
}
