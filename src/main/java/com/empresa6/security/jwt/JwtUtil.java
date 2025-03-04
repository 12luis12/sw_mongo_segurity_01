package com.empresa6.security.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Service
public class JwtUtil {
	private String secret="Bx4MeizkOfmx5buS0AeRdfwkK1H4GI8abShDqtZq6gA=";
	
	
	public String extractUsername(String token) {
	 return extractClaims(token, Claims:: getSubject);	
	}
	
	public Date extractExpriration(String token) {
		return extractClaims(token,Claims::getExpiration);
	}
	public <T> T extractClaims(String token, Function<Claims,T> claimsResolver){
	 final Claims claims=extractAllClaims(token);
	 return claimsResolver.apply(claims);
	}
	
	
	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}

	
	
	private boolean isTokenExpired (String token) {
		return extractExpriration(token).before(new Date());
		
	}
	public String generateToken(String username, String role, ObjectId id, String nombre) {
		Map<String,Object> claims=new HashMap<>();
		claims.put("role", role);
		claims.put("userId", id);  
	    claims.put("nombre", nombre); 
		return createToken(claims,username);
	}
	
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()*100*60*60*10))
				.signWith(SignatureAlgorithm.HS256,secret )
				.compact();
	}
	public boolean validateToken(String token,UserDetails userDetails) {
		final String username=extractUsername(token);
		return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
	}
	
	
	public String extractUserId(String token) {
	    return extractAllClaims(token).get("userId", String.class);
	}

	public String extractNombre(String token) {
	    return extractAllClaims(token).get("nombre", String.class);
	}

	
	
	
	
}
