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

        // Token ETUDIANT
        String etudiantToken = Jwts.builder()
                .subject(UUID.randomUUID().toString())
                .claim("role", "ETUDIANT")
                .expiration(new Date(System.currentTimeMillis() + 86400000L * 365))
                .signWith(key)
                .compact();

        // Token ADMIN
        String adminToken = Jwts.builder()
                .subject(UUID.randomUUID().toString())
                .claim("role", "ADMIN")
                .expiration(new Date(System.currentTimeMillis() + 86400000L * 365))
                .signWith(key)
                .compact();

        System.out.println("ETUDIANT TOKEN: " + etudiantToken);
        System.out.println("ADMIN TOKEN:    " + adminToken);
    }
}