package com.miu.waa.services.impl;

import com.miu.waa.dto.response.LoginResponse;
import com.miu.waa.entities.User;
import com.miu.waa.repositories.UserRepository;
import com.miu.waa.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final UserRepository userRepository;

    @Value(value = "${application.security.jwt.secret-key}")
    private String secret;

    @Value(value = "${application.security.jwt.expiration}")
    private long jwtExpiration;

    @Value(value = "${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    public LoginResponse generateToken(User user){
        try {
            Map<String, Object> claims = Map.of("user", user);
            user.setCreatedAt(null);
            user.setUpdatedAt(null);
            System.out.println("date: " + (new Date(System.currentTimeMillis())));
            LocalDateTime issuedAt = LocalDateTime.now();
            LocalDateTime expiration = issuedAt.plusSeconds(jwtExpiration);
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getEmail())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                    .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                    .compact();
            return new LoginResponse(token, issuedAt, expiration);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public User getUserFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken == null || !bearerToken.startsWith("Bearer ")){
            throw new RuntimeException("Please enter a valid token");
        }

        String jwtToken= bearerToken.substring(7);
        String userEmail = extractUsername(jwtToken);
        return userRepository.findByEmail(userEmail);
    }

    public String extractUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    private String buildToken(
            Map<String, Object> extractClaims,
            UserDetails userDetails,
            long expiration

    ){
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private Key getSecretKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateVerificationToken(String email){
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }
}
