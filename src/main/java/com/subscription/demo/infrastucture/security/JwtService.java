package com.subscription.demo.infrastucture.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    public static final String SECRET_KEY =  "mi-clave-secreta-super-segura-para-firmar-jwt-123456789";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    public String generateToken(String username){

        Date now = new Date();

        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);
        return Jwts.builder()
                .subject(username)
                .issuedAt(now)
                .expiration(expiration)
                .signWith(getSigningKey())
                .compact();
    }

    public String getUserName(String token){
        return extractAllClaims(token).getSubject();
    }


    public boolean isValidToken (String token, String username){
        String tokenusername = getUserName(token);
        return tokenusername.equals(username) && !isTokenExpired(token);
    }

    private SecretKey getSigningKey(){
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);

    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token){
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }
}
