package com.example.auth.security;

import com.example.auth.config.JwtProperties;
import com.example.auth.model.Korisnik;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private final JwtProperties props;

    public JwtService(JwtProperties props) {
        this.props = props;
    }

    public String generateToken(Korisnik korisnik) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + props.getExpirationMs());

        Set<String> roles = korisnik.getUloge().stream()
                .map(Enum::name)
                .collect(Collectors.toSet());

        return Jwts.builder()
                .claim("roles", roles)
                .claim("uid", korisnik.getId())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(
                        Keys.hmacShaKeyFor(props.getSecret().getBytes(StandardCharsets.UTF_8)),
                        SignatureAlgorithm.HS256
                )
                .compact();

    }
}
