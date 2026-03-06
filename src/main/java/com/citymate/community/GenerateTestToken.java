package com.citymate.community;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

public class GenerateTestToken {
    public static void main(String[] args) {
        String secret = "citymate-super-secret-key-must-be-at-least-256-bits-long";
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        String token = Jwts.builder()
                .subject("550e8400-e29b-41d4-a716-446655440000")
                .claim("role", "CLIENT")
                .expiration(new Date(System.currentTimeMillis() + 86400000L * 365))
                .signWith(key)
                .compact();

        System.out.println("TOKEN: " + token);
    }
}