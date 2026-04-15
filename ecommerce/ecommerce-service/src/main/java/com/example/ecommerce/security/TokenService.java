package com.example.ecommerce.security;

import com.example.ecommerce.model.Customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Value("${api.security.token.expiration}")
    private Long expiration;

    public String generateToken(Customer customer) {
        return Jwts.builder()
                .issuer("ecommerce-api")
                .subject(customer.getEmail())
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(LocalDateTime.now().plusSeconds(expiration).toInstant(ZoneOffset.of("-03:00"))))
                .signWith(getSignInKey())
                .compact();
    }

    public String validateToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    private SecretKey getSignInKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
