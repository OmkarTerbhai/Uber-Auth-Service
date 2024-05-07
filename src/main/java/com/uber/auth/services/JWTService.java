package com.uber.auth.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JWTService {

    @Value("${jwt.expiry}")
    private int JWT_EXPIRY;

    @Value("${jwt.secret}")
    private String JWT_SECRET;




    public String createJWTToken(Map<String, Object> payload, String email) {
        Date expiry = new Date(System.currentTimeMillis() + JWT_EXPIRY * 1000L);

        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .claims(payload)
                .expiration(expiry)
                .subject(email)
                .issuedAt(new Date())
                .signWith(key)
                .compact();
    }

    private Claims extractPayload(String token) {
        SecretKey key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts
                .parser()
                .setSigningKey(key)
                .build().parseSignedClaims(token).getPayload();
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolverFn) {
        Claims claim = extractPayload(token);
        return claimsResolverFn.apply(claim);
    }

    public String getEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Boolean validateToken(String token, String email) {
        final String userEmailFetchedFromToken = getEmail(token);
        return (userEmailFetchedFromToken.equals(email));
    }
}
