package com.example.sluzba.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private final String secret = "MySuperLongAndSecureJwtSecretKey_1234567890_ABCDEF";


    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    private Claims getClaimsFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Long getUid(String token) {
        Claims claims = getClaimsFromToken(token);
        return Long.valueOf(claims.get("uid").toString());
    }

    public boolean isPoslodavac(String token) {
        Claims claims = getClaimsFromToken(token);
        List<String> roles = claims.get("roles", List.class);
        return roles != null && roles.contains("ROLE_POSLODAVAC");
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }
}
