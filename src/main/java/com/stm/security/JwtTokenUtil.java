package com.stm.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenUtil {
    private final String jwtSecret = "your_jwt_secret";
    private final String refreshTokenSecret = "your_refresh_token_secret";
    private final long jwtExpirationMs = 86400000; // 1 день
    private final long refreshTokenExpirationMs = 2592000000L; // 30 дней

    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(SignatureAlgorithm.HS512, refreshTokenSecret)
                .compact();
    }

    public String getUsernameFromToken(String token, String secret) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token, String secret) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean validateAccessToken(String token) {
        return validateToken(token, jwtSecret);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token, refreshTokenSecret);
    }

    public String getUsernameFromAccessToken(String token) {
        return getUsernameFromToken(token, jwtSecret);
    }

    public String getUsernameFromRefreshToken(String token) {
        return getUsernameFromToken(token, refreshTokenSecret);
    }
}