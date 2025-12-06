package com.JwtToken.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Component
public class JwtUtil{

    private final String SECRET = "qazwsxedcrfvtgbyhnujmikolpqazwsxedcrfvtgbyhnujmikwsxedcrfvtgbyhnujqazwsxedcrfvtgbyhnujmikolpqazwsxedcrfvtgbyhnujmikwsxedcrfvtgbyhnuj";

    private final long EXP = 3600 * 1000;

    private SecretKey mySigninKey = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));

    public String generateToken(UserDetails userDetails){
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+EXP))
                .signWith(mySigninKey, SignatureAlgorithm.HS512)
                .claim("role",role)
                .compact();
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(mySigninKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getSubject(String token){
        return extractAllClaims(token).getSubject();
    }

    public Date getExpiration(String token){
        return extractAllClaims(token).getExpiration();
    }

    public boolean isTokenExpired(String token){
        Date exp = extractAllClaims(token).getExpiration();
        return exp.before(new Date());
    }

    public static void main(String[] args) {
        UserDetails user = new User("Shiv","1234",Set.of(new SimpleGrantedAuthority("ADMIN")));
        JwtUtil jwt = new JwtUtil();

        String token = jwt.generateToken(user);

        System.out.println(token);
        System.out.println(jwt.getSubject(token));
        System.out.println(jwt.isTokenExpired(token));

    }
}

