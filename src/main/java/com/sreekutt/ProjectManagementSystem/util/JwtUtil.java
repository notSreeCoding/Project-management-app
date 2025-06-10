package com.sreekutt.ProjectManagementSystem.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret.key}")
    private String SEC_KEY;


    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
                .signWith(SignatureAlgorithm.HS256,SEC_KEY)
                .compact();
    }

    private Claims extractAll(String token){
        return Jwts.parser()
                .setSigningKey(SEC_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public  <T>T extractClaims(String token, Function<Claims,T> claimResolver){
        final Claims claims = extractAll(token);
        return claimResolver.apply(claims);
    }

    public String extractEmail(String token){
        return extractClaims(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails user){
        final String  email = extractEmail(token);
        return (email.equals(user.getUsername())&& !isTokenExpired(token));
    }
}
