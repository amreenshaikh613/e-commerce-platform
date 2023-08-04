package assignment.ecommerceplatform.security.jwt;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${ecommerce.auth..jwtsecret}")
    private String jwtSecret;

    @Value("${ecommerce.auth..jwtexpiration}")
    private int jwtExpiration;

    public String generateJwtToken(String subject) {
    	return generateJwtToken(subject, jwtExpiration);
    }
    
    public String generateJwtToken(String subject, int expirationSecs) {

      return Jwts.builder()
      				.setSubject( subject )
		                .setIssuedAt(new Date())
		                .setExpiration(new Date((new Date()).getTime() + expirationSecs*1000))
		                .signWith(SignatureAlgorithm.HS512, jwtSecret)
		                .compact();
  }
    
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature -> Message: {} ", e);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token -> Message: {}", e);
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token -> Message: {}", e);
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token -> Message: {}", e);
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty -> Message: {}", e);
        }
        
        return false;
    }
    
    public String getSubjectFromJwtToken(String token) {
        return Jwts.parser()
			                .setSigningKey(jwtSecret)
			                .parseClaimsJws(token)
			                .getBody().getSubject();
    }
}