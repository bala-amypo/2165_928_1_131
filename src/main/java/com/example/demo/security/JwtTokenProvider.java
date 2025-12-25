package com.example.demo.security;

import com.example.demo.entity.AppUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component; // <--- REQUIRED IMPORT

import javax.crypto.SecretKey;
import java.util.Date;

@Component // <--- THIS ANNOTATION WAS MISSING
public class JwtTokenProvider {

    // WARNING: This generates a NEW key every time you restart the app.
    // Tokens created before a restart will become invalid.
    // For production, store this key in application.properties.
    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String createToken(AppUser user) {
        return Jwts.builder()
                .subject(user.getEmail())
                // Ensure roles is not empty before accessing .next()
                .claim("role", user.getRoles().isEmpty() ? "USER" : user.getRoles().iterator().next())
                .claim("userId", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600000)) // 1 Hour Expiration
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 👇 for tests
    public Claims getClaims(String token) {
        return parse(token);
    }
    
    // Helper needed for JwtAuthenticationFilter
    public String extractUsername(String token) {
        return parse(token).getSubject();
    }
}