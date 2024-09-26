package com.wish.dms_api.security;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
//import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	private final List<String> revokedTokens = new ArrayList<>(); // For blacklist approach

	private String secretKey="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	 //   SecretKey key = Jwts.SIG.HS256.key().build();
//	SecretKey key= Jwts.SIG.HS256.key().build();
	
//	public JwtService() {
//        try {
//            KeyGenerator keyGenerator=KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk = keyGenerator.generateKey();
//
//            secretKey= Base64.getEncoder().encodeToString(sk.getEncoded());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
    private SecretKey getKey() {
//    	System.out.println("getKey");
        byte[] keyBytes= Decoders.BASE64.decode(secretKey);
//        System.out.println(Keys.hmacShaKeyFor(keyBytes));
        return Keys.hmacShaKeyFor(keyBytes);

    }

	public String extractUsername(String token){
		// TODO Auto-generated method stub
		return extractClaim(token, Claims::getSubject);
	}
	
	private <T> T extractClaim(String token, Function<Claims, T>claimsResolver) {
		Claims claims= extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	public Claims extractAllClaims(String token) {
		// TODO Auto-generated method stub
//		return extractClaim(token, Claims.getSubject);
//		return Jwts.parser().verifyWith(key).parse(token).getBody();
		
		return Jwts.parser()
		.verifyWith(getKey())
		.build()
		.parseSignedClaims(token)
		.getPayload();
	}

	public boolean validateToken(String token, UserDetails userDetails){
		
		final String username= extractUsername(token);
		
		
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
//		if(!extractExp(token).before(new Date())){
//			throw new ExpiredJwtException(null, null, "JWT expired");
//		}
		return extractExp(token).before(new Date());
	}

	private Date extractExp(String token)  {
		return extractClaim(token,Claims::getExpiration);
	}

	public String generateToken(String username) {


		System.out.println(getKey().getFormat().getBytes());
		
		
		return Jwts.builder()
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+1000*60*60*24))
				.signWith(getKey())
				.compact();
	}
	
	public String generateRefreshToken(String username) {
		return Jwts.builder()
//				.claims(extractClaims)
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+1000*60*60*24*2))
				.signWith(getKey())
				.compact();
	}
	
	
	

    public void invalidateToken(String token) {
        revokedTokens.add(token);
    }

    public boolean isTokenRevoked(String token) {
        return revokedTokens.contains(token);
    }
}
