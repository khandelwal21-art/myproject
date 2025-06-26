package com.example.transportsystem.transportsystem.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.transportsystem.transportsystem.dto.Userdto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;


@Component
public class Jwtservice {

	private  String Secretkey=null;
	
	
	public String generateToken(Userdto userdto) {
		Map<String,Object> claims=new HashMap<>();
		return Jwts
				.builder()
				.claims()
				.add(claims)
				.subject(userdto.getEmail())
				.issuer("DCB")
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+ 60*100000 ))
				.and()
				.signWith(generateKey())
				.compact();
	}
	
	 private SecretKey generateKey() {
	        byte[] decode = Decoders.BASE64.decode(getsecretkey()); // Decode the secret key
	        return Keys.hmacShaKeyFor(decode); // Generate a SecretKey for HMAC-SHA
	    }
	
	public String getsecretkey() {
		return Secretkey="WXvl9+DfHshMF9fZdZAKBtlwL8Ndvbg0WGg4HXK52IA=";
	}

	

	public boolean isValidToken(String token, UserDetails userdetails) {
	
		final String username=extractUserName(token);
		return (username.equals(userdetails.getUsername())&& !istokenexpired(token));
	}

	private boolean istokenexpired(String token) {

		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {

		return getClaimFromToken(token, Claims::getExpiration);
	}

	public  String extractUserName(String token) {
		
			return getClaimFromToken(token, Claims::getSubject);
		

	}
	 public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	        final Claims claims = getAllClaimsFromToken(token);
	        return claimsResolver.apply(claims);
	    }

	private Claims getAllClaimsFromToken(String token) {
		 return Jwts.parser()
				 .verifyWith(generateKey())
				 .build()
				 .parseSignedClaims(token)
				 .getPayload();
				 
				
	}


}
