package org.helix;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.helix.configuration.JwtTokenProperties;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class JwtTokenProvider {

    private final JwtTokenProperties jwtTokenProperties;
    private SecretKey key;

    public JwtTokenProvider(final JwtTokenProperties jwtTokenProperties) {
        this.jwtTokenProperties = jwtTokenProperties;
    }

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtTokenProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(final String subject, final List<String> roles, final Map<String, Object> additionalClaims) {
        Map<String, Object> allClaims = new HashMap<>();
        allClaims.put("roles", roles.stream().map(String::toUpperCase).toList());

        if (additionalClaims != null && !additionalClaims.isEmpty()) {
            allClaims.putAll(additionalClaims);
        }

        Claims claims = Jwts.claims()
                .subject(subject)
                .add(allClaims)
                .build();

        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtTokenProperties.getExpirationMs());

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }

    public boolean validateToken(final String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public String getSubject(final String token) {
        return parseClaims(token).getSubject();
    }

    public List<String> getRoles(final String token) {
        Object roles = parseClaims(token).get("roles");
        if (roles instanceof List<?>) {
            return ((List<?>) roles).stream()
                    .map(String::valueOf)
                    .toList();
        }
        return Collections.emptyList();
    }

    private Claims parseClaims(final String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}