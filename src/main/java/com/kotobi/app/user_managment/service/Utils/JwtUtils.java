package com.kotobi.app.user_managment.service.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

@Service
public class JwtUtils {

    private final SecretKey key;
    private static final Long EXPIRATION_TIME = 86400000L;
    public JwtUtils() {
        String secretKey = System.getenv("JWT_SECRET");
        if (secretKey == null || secretKey.trim().isEmpty()) {
            throw new IllegalStateException("JWT_SECRET is not set or is empty");
        }
        try {
            byte[] keyBytes = Base64.getDecoder().decode(secretKey.getBytes(StandardCharsets.UTF_8));
            this.key = new SecretKeySpec(keyBytes, "HmacSHA256");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("JWT_SECRET is not properly base64 encoded", e);
        }
    }


    public String generateToken(UserDetails userDetails){
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(HashMap<String,Object> claims , UserDetails userDetails){
        return Jwts.builder()
                .claims(claims)
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction) {
        return claimsTFunction.apply(
                Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload());
    }

    public boolean isTokenValid(String token , UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractClaims(token,Claims::getExpiration).before(new Date());
    }

}
