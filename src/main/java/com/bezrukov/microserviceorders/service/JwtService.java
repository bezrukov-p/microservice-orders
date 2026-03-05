package com.bezrukov.microserviceorders.service;

import com.bezrukov.microserviceorders.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtService {

    private final String secret;
    private final Long expiration;

    public JwtService(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") Long expiration) {
        this.secret = secret;
        this.expiration = expiration;
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    public Instant getExpiration(String token) {
        return extractAllClaims(token).getExpiration().toInstant();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    @SuppressWarnings("unchecked")
    public List<String> extractRoles(String token) {
        return extractAllClaims(token).get("role", List.class);
    }

    public boolean isTokenValid(String token) {
        return isTokenValid(token, null);
    }

    public boolean isTokenValid(String token, String username) {
        try {
            String extractedUsername = extractUsername(token);

            boolean isUsernameValid = username == null || extractedUsername.equals(username);
            boolean isTokenExpired = getExpiration(token).isBefore(Instant.now());

            return isUsernameValid && !isTokenExpired;
        } catch (Exception ex) {
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = java.util.Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
