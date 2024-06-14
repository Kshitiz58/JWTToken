package JWT.Token.Web;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTService {

	private static final String SECRET = "BE648062D9E00253BFA7EC30DC0DFAB3A0CE237C0631527CD2985334AA8361B4168422BE50E48F2DEE73B599386D02CEA1C47123F02804BF30ABF25D072EA2FE";

	public static final long VALIDITY = TimeUnit.DAYS.toMillis(1);

	public String generateToken(UserDetails userDetails) {
		Map<String, String> claims = new HashMap<>();
		claims.put("iss", "http://locolhost:8080/jwt.com");
		return Jwts
				.builder()
				.claims(claims)
				.subject(userDetails.getUsername())
				.issuedAt(Date.from(Instant.now()))
				.expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
				.signWith(generateKey())
				.compact();
	}

	private SecretKey generateKey() {
		byte[] decodedKey = Base64.getDecoder().decode(SECRET);
		return Keys.hmacShaKeyFor(decodedKey);
	}

	public String extractUsername(String jwt) {
		Claims claims = getCliams(jwt);
		return claims.getSubject();	}

	public boolean isTokenValid(String jwt) {
		Claims claims = getCliams(jwt);
		return claims
				.getExpiration()
				.after(Date.from(Instant.now()));
	}

	private Claims getCliams(String jwt) {
		return Jwts
				.parser()
				.verifyWith(generateKey())
				.build()
				.parseSignedClaims(jwt)
				.getPayload();
	}
	
}
