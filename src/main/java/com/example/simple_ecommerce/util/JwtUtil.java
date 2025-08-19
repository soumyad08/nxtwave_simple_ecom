package com.example.simple_ecommerce.util;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    private final String secretKey = "nxtwavegfdkjdiojadsidsakhjasdkjasdkbjdaskjadsdfskjdsfkjdsfjkdfs123";

    private final long expTime = 1000 * 60 * 60;

    public String generateToken(String userName){
        return Jwts.builder().setSubject(userName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String extractToken(String token){
        return (Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject());
    }

    public boolean validateToken(String token){
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }

}
