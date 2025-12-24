package com.example.demo.security;

import com.example.demo.entity.AppUser;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtTokenProvider {

    private final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public String createToken(AppUser user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRoles().iterator().next())
                .claim("userId", user.getId())
                .issuedAt(new Date())
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
}
